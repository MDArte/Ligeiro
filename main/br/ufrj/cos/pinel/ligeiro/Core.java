package br.ufrj.cos.pinel.ligeiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import br.ufrj.cos.pinel.ligeiro.common.Constants;
import br.ufrj.cos.pinel.ligeiro.common.FPAConfig;
import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.data.BaseClass;
import br.ufrj.cos.pinel.ligeiro.data.ClassUsage;
import br.ufrj.cos.pinel.ligeiro.data.Dependency;
import br.ufrj.cos.pinel.ligeiro.data.Entity;
import br.ufrj.cos.pinel.ligeiro.data.Event;
import br.ufrj.cos.pinel.ligeiro.data.FPAReport;
import br.ufrj.cos.pinel.ligeiro.data.IBaseClass;
import br.ufrj.cos.pinel.ligeiro.data.Method;
import br.ufrj.cos.pinel.ligeiro.data.Parameter;
import br.ufrj.cos.pinel.ligeiro.data.ReportResult;
import br.ufrj.cos.pinel.ligeiro.data.Service;
import br.ufrj.cos.pinel.ligeiro.data.UseCase;
import br.ufrj.cos.pinel.ligeiro.data.View;
import br.ufrj.cos.pinel.ligeiro.exception.ExpressFPAException;
import br.ufrj.cos.pinel.ligeiro.graph.ClassUsageGraph;
import br.ufrj.cos.pinel.ligeiro.xml.XMLUtil;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

/**
 * @author Roque Pinel
 *
 */
public class Core
{
	/*
	 * Statistics
	 */
	private Map<String, Entity> entities;

	// impl classes, services and controllers
	private Map<String, IBaseClass> allClasses;

	private Collection<UseCase> useCases;

	/*
	 * Dependencies
	 */
	//private Collection<BaseClass> dependencyClasses;
	private Map<String, BaseClass> dependencyClasses;

	private Collection<ClassUsage> classUsages;

	private FPAReport fpaReport;


	/**
	 * Default constructor.
	 */
	public Core()
	{
		this.entities = new HashMap<String, Entity>();
		this.allClasses = new HashMap<String, IBaseClass>();

		this.useCases = new ArrayList<UseCase>();

		//this.dependencyClasses = new ArrayList<BaseClass>();
		this.dependencyClasses = new HashMap<String, BaseClass>();

		this.classUsages = null;
	}

	/**
	 * Reads the FPA Configuration.
	 * 
	 * @return fileName the XML's filename
	 * @throws ReadXMLException
	 */
	public FPAConfig readFPAConfiguration(String fileName) throws ReadXMLException
	{
		// conf/ExpressFPA.xml
		FPAConfig fpaConfig = XMLUtil.readFPAConfiguration(fileName);

		return fpaConfig;
	}

	/**
	 * Reads the statistics.
	 * 
	 * @param fileName the XML's filename
	 * @throws ReadXMLException 
	 */
	public void readStatistics(String fileName) throws ReadXMLException
	{
		String statisticType = XMLUtil.readStatisticType(fileName);

		if (statisticType.equals(Constants.XML.CLASSES))
		{
			Collection<BaseClass> classes = XMLUtil.readClasses(fileName);

			for (BaseClass clazz : classes)
			{
				this.allClasses.put(clazz.getName(), clazz);
			}
		}
		else if (statisticType.equals(Constants.XML.ENTITY))
		{
			Collection<Entity> entities = XMLUtil.readEntities(fileName);

			for (Entity entity : entities)
			{
				this.entities.put(entity.getName(), entity);
				this.entities.put(entity.getImplementationName(), entity);
			}
		}
		else if (statisticType.equals(Constants.XML.SERVICE))
		{
			Collection<Service> services = XMLUtil.readServices(fileName);

			for (Service service : services)
			{
				this.allClasses.put(service.getName(), service);
				this.allClasses.put(service.getImplementationName(), service);

				for (String otherName : service.getOtherNames())
				{
					this.allClasses.put(otherName, service);
				}
			}
		}
		else if (statisticType.equals(Constants.XML.USE_CASE))
		{
			Collection<UseCase> useCases = XMLUtil.readUseCases(fileName);

			this.useCases.addAll(useCases);

			for (UseCase useCase : useCases)
			{
				if (useCase.getController() != null)
					this.allClasses.put(useCase.getController().getImplementationName(), useCase.getController());
			}
		}
		else
			throw new ReadXMLException("Could not found statistic type.");
	}

	/**
	 * Reads the dependencies.
	 * 
	 * @param fileName the XML's filename
	 * @throws ReadXMLException
	 */
	public void readDependencies(String fileName) throws ReadXMLException
	{
		Collection<BaseClass> classes = XMLUtil.readDependencies(fileName);

		for (BaseClass baseClass: classes)
		{
			// check if it's a know class, then store for future analysis
			if (this.allClasses.containsKey(baseClass.getName()))
			{
				//dependencyClasses.add(baseClass);
				dependencyClasses.put(baseClass.getName(), baseClass);
			}
		}

		Util.println("Number of dependencies after cleaning: " + dependencyClasses.size());
	}

	private Set<String> visitedMethods;

	private boolean doesMethodChangeDataOrBehavior(IBaseClass dependencyClass, String methodSignature)
	{
		// avoiding an infinite loop
		if (visitedMethods != null && visitedMethods.contains(methodSignature))
			return false;
		visitedMethods.add(methodSignature);

		for (Method dependencyMethod : dependencyClass.getMethods())
		{
			// if the target method is a controller method
			if (methodSignature.equals(dependencyMethod.getName()))
			{
				for (Dependency dependency : dependencyMethod.getDependencies())
				{
					Entity entity = entities.get(dependency.getValue());

					// if the dependency is an entity
					if (entity != null)
					{
						String entityName = entity.getName();
						if (dependency.getValue().equals(entity.getImplementationName()))
							entityName = entity.getImplementationName();

						for (Method entityMethod : entity.getMethods())
						{
							String signature = entityName + "." + entityMethod.getSignature();
		
							for (Dependency dependency2 : dependencyMethod.getDependencies())
							{
								if (dependency2.getValue().equals(signature) && entityMethod.isModifier())
								{
									return true;
								}
							}
							
						}
					}
					else
					{
						String methodClassName = Util.getClassName(dependency.getValue());
						if (methodClassName != null)
						{
							IBaseClass clazz = allClasses.get(methodClassName);

							// if the dependency is a method of a common class, service or controller
							if (clazz != null)
							{
								BaseClass newDependencyClass = dependencyClasses.get(clazz.getImplementationName());

								if (newDependencyClass != null)
								{
									String methodName = Util.getMethodName(dependency.getValue());

									String[] params = Util.getParameters(dependency.getValue());

									boolean foundBestMatch = false;
									Method methodMatched = null;

									// looking for the method to get the right signature
									for (Method method : clazz.getMethods())
									{
										// found a method with the same name,
										// but it necessary to verify each parameter
										if (methodName.equals(method.getName())
											|| (method.getImplementationName() != null && methodName.equals(method.getImplementationName())))
										{
											boolean match = true;

											int i = 0;
											for (Parameter param : method.getParameters())
											{
												if (i < params.length && !param.getType().equals(params[i]))
												{
													match = false;
													break;
												}
												i++;
											}

											if (match)
											{
												methodMatched = method;

												// matchs all parameters
												if (i >= params.length)
													foundBestMatch = true;
											}
										}
										// if the best match was found, then stop search
										if (foundBestMatch)
											break;
									}

									if (methodMatched != null
										&& doesMethodChangeDataOrBehavior(newDependencyClass,
											clazz.getImplementationName() + "." + methodMatched.getSignature()))
									{
										return true;
									}
								}
							}
						}
					}
				}

				// already found the method
				break;
			}
		}

		return false;
	}

	public void startFunctionPointAnalysisDF()
	{
		Map<String, Entity> tempEntities = new HashMap<String, Entity>();
		tempEntities.putAll(entities);

		Util.println("\n-- DATA FUNCTION --");

		for (BaseClass dependencyClass : dependencyClasses.values())
		{
			IBaseClass clazz = allClasses.get(dependencyClass.getName());

			if (clazz == null)
				continue;

			Util.println(clazz.getName());

			Set<String> methodSignatures = clazz.getMethodsSignatures();

			for (Method method : dependencyClass.getMethods())
			{
				// if the dependency method is really a method of the class
				if (methodSignatures.contains(method.getName()))
				{
					Util.println("\t" + method.getName());

					for (Dependency dependency : method.getDependencies())
					{
						Entity entity = tempEntities.get(dependency.getValue());

						// if the dependency is an entity
						if (entity != null)
						{
							Util.println("\t\t" + dependency.getValue());

							String entityName = entity.getName();

							// if the dependency is related to the implementation name, then use it 
							if (dependency.getValue().equals(entity.getImplementationName()))
								entityName = entity.getImplementationName();

							boolean found = false;

							for (Iterator<Method> iEntityMethod = entity.getMethods().iterator(); iEntityMethod.hasNext() && !found; )
							{
								Method entityMethod = iEntityMethod.next();

								String signature = entityName + "." + entityMethod.getSignature();

								for (Iterator<Dependency> iDependency2 = method.getDependencies().iterator(); iDependency2.hasNext() && !found; )
								{
									Dependency dependency2 = iDependency2.next();

									if (dependency2.getValue().equals(signature) && entityMethod.isModifier())
									{
										Util.println("\t\t\tFound: " + signature);
										entity.setAsInternal();

										tempEntities.remove(entity.getName());
										tempEntities.remove(entity.getImplementationName());

										found = true;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public FPAReport startFunctionPointAnalysisDFReport(FPAConfig fpaConfig)
	{
		if (fpaReport == null)
			fpaReport = new FPAReport();

		for (String key : entities.keySet())
		{
			Entity entity = entities.get(key);

			if (key.equals(entity.getName()))
			{
				ReportResult reportResult = new ReportResult();
				reportResult.setElement(entity.getName());

				reportResult.setRet_ftr(Constants.DF_DEFAULT_RET);
				reportResult.setDet(Util.countDET(entity, entities));

				int value = 0;
				String complexity = null;

				if (entity.isInternal())
				{
					reportResult.setType(Constants.DF_ILF);
					complexity = fpaConfig.getIFLComplexity(reportResult.getRet_ftr(), reportResult.getDet());
					value = fpaConfig.getIFLComplexityValue(reportResult.getRet_ftr(), reportResult.getDet());
				}
				else
				{
					reportResult.setType(Constants.DF_EIF);
					complexity = fpaConfig.getEIFComplexity(reportResult.getRet_ftr(), reportResult.getDet());
					value = fpaConfig.getEIFComplexityValue(reportResult.getRet_ftr(), reportResult.getDet());
				}

				reportResult.setComplexity(complexity);
				reportResult.setComplexityValue(value);

				fpaReport.addDFReportTotal(value);

				Util.println("\t" + reportResult.getType() + ": " + reportResult.getElement());
				Util.println("\t\tRET: " + reportResult.getRet_ftr());
				Util.println("\t\tDET: " + reportResult.getDet());
				Util.println("\t\tComplexity: " + reportResult.getComplexity());
				Util.println("\t\tComplexity Value: " + reportResult.getComplexityValue());
			}
		}

		return fpaReport;
	}

	public void startFunctionPointAnalysisTF()
	{
		Util.println("\n-- TRANSACTION FUNCTION --");

		for (UseCase useCase : useCases)
		{
			Util.println("UseCase: " + useCase.getName());
			Util.println("  First steps...");

			for (View view : useCase.getViews())
			{
				Util.println("\t" + view.getName());

				// for View, actions are methods
				for (Method action : view.getMethods())
				{
					if (!action.isTableLink())
					{
						for (Parameter param : action.getParameters())
						{
							Util.println("\t\t" + param.getName());

							// if the parameter is an input field
							if (!param.isPlainText() && !param.isReadOnly() && !param.isHiddenField())
							{
								Util.println("\t\t\tINPUT");

								// increase the counter
								view.addNumberInputParameters();

								// if the view wasn't already classified as an EI, verify the right type
								// if the action has a target, where to go
								if (!view.isEI() && action.getTarget() != null)
								{
									// if it is a final state, then
									// is necessary to check the use case that is being pointed
									if (action.getTarget().isFinalState())
									{
										Util.println("\t\t\t\t<FinalState>");
										// TODO: think about finding the use case
									}
									else
									{
										Util.println("\t\t\t\t<Action>");
										BaseClass dependencyClass = dependencyClasses.get(useCase.getController().getImplementationName());
										if (useCase.getController() != null && dependencyClass != null)
										{
											IBaseClass controllerClass = useCase.getController();

											String methodSignature = null;

											for (Method controllerMethod : controllerClass.getMethods())
											{
												for (Event event : action.getTarget().getEvents())
												{
													if (controllerMethod.getName().equals(event.getName()))
													{
														methodSignature = controllerClass.getImplementationName() + "." + controllerMethod.getSignature();
														break;
													}
												}
												if (methodSignature != null)
													break;
											}

											// avoiding an infinite loop
											visitedMethods = new HashSet<String>();

											// calling
											boolean ret = doesMethodChangeDataOrBehavior(dependencyClass, methodSignature);

											if (ret)
											{
												Util.println("\t\t\t\t  Is an EI");
												view.setAsEI();
											}
											else
											{
												Util.println("\t\t\t\t  Is an EQ1");
												view.setAsEQ1();
											}
										}
									}
								}
							}
						}

						view.addNumberButtons();
					}
				}

				Util.println("\t\tParameters: " + view.getNumberInputParameters() + " - Buttons: " + view.getNumberButtons());
			}

			Util.println("  Second steps...");

			for (View view : useCase.getViews())
			{
				// if the view is the first EQ, then let's look for the other part
				if (view.isEQ1())
				{
					// for View, actions are methods
					for (Method action : view.getMethods())
					{
						if (!action.isTableLink() && action.getTarget() != null)
						{
							// if it is a final state, then
							// is necessary to check the use case that is being pointed
							if (action.getTarget().isFinalState())
							{
								Util.println("\t\t\t\t<FinalState>");
								// TODO: think about finding the use case
							}
							else
							{
								Util.println("\t\t\t\t<Action>");

								Util.println("\t\t\t\t\tFrom: " + action.getName());
								Util.println("\t\t\t\t\tTo: " + action.getTarget().getName());

								if (action.getTarget().getTarget() != null)
								{
									Util.println("\t\t\t\t\t\tTarget: " + action.getTarget().getTarget().getName());

									for (View targetView : useCase.getViews())
									{
										// if it isn't an EI or an EQ, and has no input parameters
										if (!targetView.isEI() && !targetView.isEQ() && targetView.getNumberInputParameters() <= 0
											&& targetView.getName().equals(action.getTarget().getTarget().getName()))
										{
											Util.println("\t\t\t\t\t\t\tIs an EQ2");
											targetView.setAsEQ2();
											view.setResultView(targetView);
										}
									}
								}
							}
						}
					}
				}
			}

			Util.println("  Third (last) steps...");

			for (View view : useCase.getViews())
			{
				// if it isn't an EI or an EQ, and has no input parameters
				if (!view.isEI() && !view.isEQ() && view.getNumberInputParameters() <= 0)
				{
					view.setAsEO();
					Util.println("\t" + view.getName());
					Util.println("\t\tIs an EO");
				}
			}
		}
	}

	public FPAReport startFunctionPointAnalysisTFReport(FPAConfig fpaConfig)
	{
		if (fpaReport == null)
			fpaReport = new FPAReport();

		for (UseCase useCase : useCases)
		{
			for (View view : useCase.getViews())
			{
				if (!view.isEQ2())
				{
					ReportResult reportResult = new ReportResult();
	
					int det = view.getNumberInputParameters() + view.getNumberButtons();
					int value = 0;
					String complexity = null;
	
					if (view.isEI())
					{
						reportResult.setElement(view.getName());
						reportResult.setType(Constants.TF_EI);
						complexity = fpaConfig.getEIComplexity(reportResult.getRet_ftr(), det);
						value = fpaConfig.getEIComplexityValue(reportResult.getRet_ftr(), det);
					}
					else if (view.isEO())
					{
						reportResult.setElement(view.getName());
						reportResult.setType(Constants.TF_EO);
						complexity = fpaConfig.getEOComplexity(reportResult.getRet_ftr(), det);
						value = fpaConfig.getEOComplexityValue(reportResult.getRet_ftr(), det);
					}
					else if (view.isEQ1())
					{
						StringBuilder element = new StringBuilder(view.getName());
	
						if (view.getResultView() != null)
						{
							element.append(" / ");
							element.append(view.getResultView().getName());
							det += view.getResultView().getNumberInputParameters() + view.getResultView().getNumberButtons();
						}
	
						reportResult.setElement(element.toString());
						reportResult.setType(Constants.TF_EO);
						complexity = fpaConfig.getEQComplexity(reportResult.getRet_ftr(), det);
						value = fpaConfig.getEQComplexityValue(reportResult.getRet_ftr(), det);
					}
	
					reportResult.setRet_ftr(Constants.TF_DEFAULT_FTR);
					reportResult.setDet(det);
	
					reportResult.setComplexity(complexity);
					reportResult.setComplexityValue(value);
	
					fpaReport.addDFReportTotal(value);
	
					Util.println("\t" + reportResult.getType() + ": " + reportResult.getElement());
					Util.println("\t\tRET: " + reportResult.getRet_ftr());
					Util.println("\t\tDET: " + reportResult.getDet());
					Util.println("\t\tComplexity: " + reportResult.getComplexity());
					Util.println("\t\tComplexity Value: " + reportResult.getComplexityValue());
				}
			}
		}

		return fpaReport;
	}

	/**
	 * Starts the Function Point Analysis.
	 */
	public FPAReport startFunctionPointAnalysis(FPAConfig fpaConfig)
	{
		startFunctionPointAnalysisDF();
		startFunctionPointAnalysisTF();

		Util.println("\n -- REPORT --\n");

		startFunctionPointAnalysisDFReport(fpaConfig);

		Util.print("\n");

		startFunctionPointAnalysisTFReport(fpaConfig);

		Util.println("  TOTAL: " + fpaReport.getReportTotal());

		return fpaReport;
	}

	/**
	 * Creates a class usage report.
	 * 
	 * Checks which services a controller uses.
	 * Checks which services and entities a service used.
	 * 
	 * @param fileName the report's file name
	 * @throws ExpressFPAException 
	 */
	public void createClassUsageReport(String fileName) throws ExpressFPAException
	{
		ClassUsageReport classUsageReport = new ClassUsageReport(fileName, classUsages);

		classUsageReport.createCSV();
	}

	/**
	 * Loads the class usage from entities, allClasses and dependencyClasses.
	 * 
	 * The informations loaded is not complete, but it is useful
	 * for generating graphs.
	 * 
	 * @throws ExpressFPAException
	 */
	public void loadClassUsageReport() throws ExpressFPAException
	{
		loadClassUsageReport(null);
	}

	/**
	 * Loads the class usage report.
	 * 
	 * The informations loaded is not complete, but it is useful
	 * for generating graphs.
	 * 
	 * @param fileName the report's file name
	 * @throws ExpressFPAException
	 */
	public void loadClassUsageReport(String fileName) throws ExpressFPAException
	{
		ClassUsageReport classUsageReport = new ClassUsageReport(fileName);

		if (fileName == null)
			classUsages = classUsageReport.loadClassUsage(entities, allClasses, dependencyClasses);
		else
			classUsages = classUsageReport.loadCSV();
	}

	public void generateClassUsageGraph() throws ExpressFPAException
	{
		ClassUsageGraph.generate(classUsages);
	}
}
