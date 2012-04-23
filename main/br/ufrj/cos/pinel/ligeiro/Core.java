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
import br.ufrj.cos.pinel.ligeiro.data.DAO;
import br.ufrj.cos.pinel.ligeiro.data.DAOMethod;
import br.ufrj.cos.pinel.ligeiro.data.Dependency;
import br.ufrj.cos.pinel.ligeiro.data.Entity;
import br.ufrj.cos.pinel.ligeiro.data.Event;
import br.ufrj.cos.pinel.ligeiro.data.IBaseClass;
import br.ufrj.cos.pinel.ligeiro.data.Method;
import br.ufrj.cos.pinel.ligeiro.data.Parameter;
import br.ufrj.cos.pinel.ligeiro.data.Service;
import br.ufrj.cos.pinel.ligeiro.data.UseCase;
import br.ufrj.cos.pinel.ligeiro.data.View;
import br.ufrj.cos.pinel.ligeiro.exception.ExpressFPAException;
import br.ufrj.cos.pinel.ligeiro.graph.ClassUsageGraph;
import br.ufrj.cos.pinel.ligeiro.report.FPAReport;
import br.ufrj.cos.pinel.ligeiro.report.LoadReport;
import br.ufrj.cos.pinel.ligeiro.report.ReportResult;
import br.ufrj.cos.pinel.ligeiro.xml.XMLUtil;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

/**
 * @author Roque Pinel
 *
 */
public class Core
{
	private Map<String, Entity> entities;
	private Map<String, DAO> daos;

	private Collection<Service> services;

	// impl classes, services and controllers
	private Map<String, IBaseClass> allClasses;

	private Collection<UseCase> useCases;

	private Map<String, BaseClass> dependencyClasses;

	private Collection<ClassUsage> classUsages;

	private FPAReport fpaReport;


	/**
	 * Default constructor.
	 */
	public Core()
	{
		this.entities = new HashMap<String, Entity>();
		this.daos = new HashMap<String, DAO>();

		this.services = new ArrayList<Service>();

		this.allClasses = new HashMap<String, IBaseClass>();

		this.useCases = new ArrayList<UseCase>();

		this.dependencyClasses = new HashMap<String, BaseClass>();

		this.classUsages = null;
	}

	/**
	 * Clears all. Loaded information and reports.
	 */
	public void clear()
	{
		clearLoadedStatistics();

		clearLoadedDepdencies();

		clearLoadedClassUsages();

		clearFPAReport();
	}

	/**
	 * Clears all loaded statistics.
	 */
	public void clearLoadedStatistics()
	{
		entities.clear();
		daos.clear();
		services.clear();
		allClasses.clear();
		useCases.clear();
	}

	/**
	 * Clears all loaded dependecies.
	 */
	public void clearLoadedDepdencies()
	{
		dependencyClasses.clear();
	}

	/**
	 * Clears all class usages.
	 */
	public void clearLoadedClassUsages()
	{
		classUsages = null;
	}

	/**
	 * Clears all informantion from the FPA Report.
	 */
	public void clearFPAReport()
	{
		if (visitedMethods != null)
			visitedMethods.clear();

		if (fpaReport != null)
			fpaReport.clear();
	}

	/**
	 * Reads the FPA Configuration.
	 * 
	 * @return fileName the XML's filename
	 * @throws ReadXMLException
	 */
	public FPAConfig readFPAConfiguration(String fileName) throws ReadXMLException
	{
		FPAConfig fpaConfig = XMLUtil.readFPAConfiguration(fileName);

		return fpaConfig;
	}

	/**
	 * Reads the statistics.
	 * 
	 * @param fileName the XML's filename
	 * @return loadReport the report
	 * @throws ReadXMLException 
	 */
	public LoadReport readStatistics(String fileName) throws ReadXMLException
	{
		LoadReport loadReport = new LoadReport(fileName);

		String statisticType = XMLUtil.readStatisticType(fileName);
		loadReport.setType(statisticType);

		if (statisticType.equals(Constants.XML_CLASS))
		{
			Collection<BaseClass> classes = XMLUtil.readClasses(fileName);

			for (BaseClass clazz : classes)
			{
				this.allClasses.put(clazz.getName(), clazz);
			}

			loadReport.setElementsRead(classes.size());
		}
		else if (statisticType.equals(Constants.XML_ENTITY))
		{
			Collection<Entity> entities = XMLUtil.readEntities(fileName);

			for (Entity entity : entities)
			{
				if (entity.getName() != null)
					this.entities.put(entity.getName(), entity);
				if (entity.getImplementationName() != null)
					this.entities.put(entity.getImplementationName(), entity);

				DAO dao = entity.getDao();

				if (dao != null)
				{
					if (dao.getName() != null)
						this.daos.put(dao.getName(), dao);
					if (dao.getImplementationName() != null)
						this.daos.put(dao.getImplementationName(), dao);
				}
			}

			loadReport.setElementsRead(entities.size());
		}
		else if (statisticType.equals(Constants.XML_SERVICE))
		{
			Collection<Service> services = XMLUtil.readServices(fileName);

			this.services.addAll(services);

			for (Service service : services)
			{
				if (service.getName() != null)
					this.allClasses.put(service.getName(), service);
				if (service.getImplementationName() != null)
					this.allClasses.put(service.getImplementationName(), service);

				for (String otherName : service.getOtherNames())
				{
					this.allClasses.put(otherName, service);
				}
			}

			loadReport.setElementsRead(services.size());
		}
		else if (statisticType.equals(Constants.XML_USE_CASE))
		{
			Collection<UseCase> useCases = XMLUtil.readUseCases(fileName);

			this.useCases.addAll(useCases);

			for (UseCase useCase : useCases)
			{
				if (useCase.getController() != null)
					this.allClasses.put(useCase.getController().getImplementationName(), useCase.getController());
			}

			loadReport.setElementsRead(useCases.size());
		}
		else
			throw new ReadXMLException("Could not found statistic type.");

		return loadReport;
	}

	/**
	 * Reads the dependencies.
	 * 
	 * @param fileName the XML's filename
	 * @return loadReport the report
	 * @throws ReadXMLException
	 */
	public LoadReport readDependencies(String fileName) throws ReadXMLException
	{
		LoadReport loadReport = new LoadReport(fileName);
		loadReport.setType(Constants.XML_DEPENDENCY);

		Collection<BaseClass> classes = XMLUtil.readDependencies(fileName);

		int counter = 0;

		for (BaseClass baseClass: classes)
		{
			// check if it's a know class, then store for future analysis
			if (this.allClasses.containsKey(baseClass.getName()))
			{
				dependencyClasses.put(baseClass.getName(), baseClass);
				counter++;
			}
		}

		loadReport.setElementsRead(counter);

		Util.println("Number of dependencies after cleaning: " + counter);

		return loadReport;
	}

	// avoing method stack, so local instance
	private Set<String> visitedMethods = null;
	private Set<String> countedEntities = null;

	/**
	 * Verifies if a method changes data or the system's behavior.
	 * 
	 * @param dependencyClass The dependency class that contains the method.
	 * @param methodSignature The method signature to retrieve the dependencies.
	 * @return The result will be <code>true</code> if the method changes data or the system's behavior
	 */
	private boolean doesMethodChangeDataOrBehavior(IBaseClass dependencyClass, String methodSignature)
	{
		boolean ret = false;

		// avoiding an infinite loop
		if (visitedMethods != null && visitedMethods.contains(methodSignature))
			return ret;
		visitedMethods.add(methodSignature);

		for (Method dependencyMethod : dependencyClass.getMethods())
		{
			// if the target method is a controller method
			if (dependencyMethod.getName().equals(methodSignature))
			{
				for (Dependency dependency : dependencyMethod.getDependencies())
				{
					if (!dependency.isFeature())
						continue;

					String dependencyElementName = Util.getMethodClassName(dependency.getValue());

					Entity entity = entities.get(dependencyElementName);

					// if the dependency is an entity
					if (entity != null)
					{
						String entityName = entity.getName();

						if (countedEntities != null && entityName != null)
							countedEntities.add(entityName);

						if (dependencyElementName.equals(entity.getImplementationName()))
							entityName = entity.getImplementationName();

						for (Method entityMethod : entity.getMethods())
						{
							String signature = entityName + "." + entityMethod.getSignature();
		
							if (dependency.getValue().equals(signature) && entityMethod.isModifier())
							{
								ret = true;
								break;
							}
						}
					}
					else
					{
						DAO dao = daos.get(dependencyElementName);

						// if the dependency is a DAO
						if (dao != null)
						{
							Entity daoEntity = dao.getEntity();

							if (countedEntities != null && daoEntity != null && daoEntity.getName() != null)
								countedEntities.add(daoEntity.getName());

							String daoName = dao.getName();

							// if the dependency is related to the implementation name, then use it 
							if (dependencyElementName.equals(dao.getImplementationName()))
								daoName = dao.getImplementationName();

							for (DAOMethod daoMethod : dao.getMethods())
							{
								String signature = daoName + "." + daoMethod.getName();

								if (daoMethod.isDelete() && dependency.getValue().equals(signature))
								{
									ret = true;
									break;
								}
							}
						}
						else
						{
							String methodClassName = Util.getMethodClassName(dependency.getValue());
							if (methodClassName != null)
							{
								IBaseClass clazz = allClasses.get(methodClassName);

								if (clazz != null)
								{
									Set<String> methodSignatures = clazz.getMethodsSignatures();

									BaseClass newDependencyClass = dependencyClasses.get(clazz.getImplementationName());

									boolean foundMethod = false;

									if (newDependencyClass != null)
									{
										String methodName = Util.getMethodName(dependency.getValue());

										String[] params = Util.getMethodParameters(dependency.getValue());

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

										if (methodMatched != null)
										{
											foundMethod = true;

											if (doesMethodChangeDataOrBehavior(newDependencyClass,
												clazz.getImplementationName() + "." + methodMatched.getSignature()))
											{
												ret = true;
											}
										}
									}

									if (!foundMethod)
									{
										// if the method is from the own class
										if (dependencyClass.getName().equals(methodClassName))
										{
											if (doesMethodChangeDataOrBehavior(dependencyClass, dependency.getValue()))
											{
												ret = true;
											}
										}
										else
										{
											newDependencyClass = dependencyClasses.get(methodClassName);
	
											if (doesMethodChangeDataOrBehavior(newDependencyClass, dependency.getValue()))
											{
												ret = true;
											}
										}
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

		return ret;
	}

	/**
	 * Starts the Function Point Analysis for Data Functions.
	 * 
	 * TODO: the methods extended should be analyzed
	 */
	public void startFunctionPointAnalysisDF()
	{
		Map<String, Entity> tempEntities = new HashMap<String, Entity>();
		tempEntities.putAll(entities);

		Map<String, DAO> tempDAOs = new HashMap<String, DAO>();
		tempDAOs.putAll(daos);

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
						if (!dependency.isFeature())
							continue;

						Util.println("\t\t" + dependency.getValue());

						String dependencyElementName = Util.getMethodClassName(dependency.getValue());

						Entity entity = tempEntities.get(dependencyElementName);

						// if the dependency is an entity
						if (entity != null)
						{
							String entityName = entity.getName();

							// if the dependency is related to the implementation name, then use it 
							if (dependencyElementName.equals(entity.getImplementationName()))
								entityName = entity.getImplementationName();

							boolean found = false;

							for (Iterator<Method> iEntityMethod = entity.getMethods().iterator(); iEntityMethod.hasNext() && !found; )
							{
								Method entityMethod = iEntityMethod.next();

								String signature = entityName + "." + entityMethod.getSignature();

								if (dependency.getValue().equals(signature) && entityMethod.isModifier())
								{
									Util.println("\t\t\tFound 1: " + signature);
									entity.setAsInternal();

									tempEntities.remove(entity.getName());
									tempEntities.remove(entity.getImplementationName());

									if (entity.getDao() != null)
									{
										tempDAOs.remove(entity.getDao().getName());
										tempDAOs.remove(entity.getDao().getImplementationName());
									}

									found = true;
								}
							}
						}
						else
						{
							DAO dao = tempDAOs.get(dependencyElementName);

							// if the dependency is a DAO
							if (dao != null)
							{
								String daoName = dao.getName();

								// if the dependency is related to the implementation name, then use it 
								if (dependencyElementName.equals(dao.getImplementationName()))
									daoName = dao.getImplementationName();

								boolean found = false;

								for (Iterator<DAOMethod> iDAOMethod = dao.getMethods().iterator(); iDAOMethod.hasNext() && !found; )
								{
									DAOMethod daoMethod = iDAOMethod.next();

									String signature = daoName + "." + daoMethod.getName();

									if (daoMethod.isDelete() && dependency.getValue().equals(signature))
									{
										Util.println("\t\t\tFound 2: " + signature);
										dao.getEntity().setAsInternal();

										tempEntities.remove(dao.getEntity().getName());
										tempEntities.remove(dao.getEntity().getImplementationName());

										tempDAOs.remove(dao.getName());
										tempDAOs.remove(dao.getImplementationName());

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

	/**
	 * Builds the report for Data Functions.
	 * 
	 * @param fpaConfig The configuration to be used.
	 * @return the report
	 */
	public FPAReport startFunctionPointAnalysisDFReport(FPAConfig fpaConfig)
	{
		if (fpaReport == null)
			fpaReport = new FPAReport();
		else
			fpaReport.clearTFReport();

		for (String key : entities.keySet())
		{
			Entity entity = entities.get(key);

			if (key.equals(entity.getName()))
			{
				ReportResult reportResult = new ReportResult();
				reportResult.setNamespace("");
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

				fpaReport.addDFReport(reportResult);
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

	/**
	 * Starts the Function Point Analysis for Transaction Functions.
	 */
	public void startFunctionPointAnalysisTF()
	{
		Util.println("\n-- TRANSACTION FUNCTION --");

		startFunctionPointAnalysisTF_UseCases();

		startFunctionPointAnalysisTF_WebServices();
	}

	/**
	 * Starts the Function Point Analysis for Transaction Functions.
	 * 
	 * Looks for Use Cases.
	 */
	private void startFunctionPointAnalysisTF_UseCases()
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

							// increasing the counter
							view.addNumberParameters();

							// if the parameter is an input field
							if (!param.isPlainText() && !param.isReadOnly() && !param.isHiddenField())
							{
								Util.println("\t\t\tINPUT");

								// increasing the counter
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
										// TODO: is it really necessary to verify a final state?

										/*
										 * Transitions between use cases using the name of a final state can only be
										 * used when linking use cases of the same web module. In order to link use
										 * cases of different web modules please use the tagged values for external hyperlinks.
										 */
//										if (action.getTarget().getName() != null)
//										{
											/*
											 * Each use-case must have a non-empty name that is unique among all use-cases.
											 * So it's possible to find the use case just by the name, disregarding the package.
											 * Considering the same module.
											 */
//											for (UseCase otherUseCase : useCases)
//											{
//												String otherUseCaseName = Util.getClassName(otherUseCase.getName());
//
//												if (otherUseCase.getModuleName().equals(useCase.getModuleName())
//													&& otherUseCaseName.equals(action.getTarget().getName()))
//												{
//													if (otherUseCase.isFirstState())
//													{
//														State otherFirstState = (State) otherUseCase.getFirst();
//														if (!otherFirstState.isFinalState())
//														{
//															classifyTF(otherUseCase, view, otherFirstState);
//														}
//													}
//
//													break;
//												}
//											}
//										}
//										else
//										{
											// verificar as validacoes de estados finais para saber quais
											// tags devem estar preenchidas.
//										}
									}
									else
									{
										Util.println("\t\t\t\t<Action>");

										BaseClass dependencyClass = dependencyClasses.get(useCase.getController().getImplementationName());

										if (dependencyClass == null)
										{
											Util.println("[ERROR] Could not find the dependency class: " + useCase.getController().getImplementationName());
											continue;
										}

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

											// used to avoid an infinite loop
											if (visitedMethods == null)
												visitedMethods = new HashSet<String>();
											else
												visitedMethods.clear();

											countedEntities = new HashSet<String>();

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

											view.setCountedEntities(countedEntities);
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
								// TODO: is it really necessary to verify a final state?
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

	/**
	 * Starts the Function Point Analysis for Transaction Functions.
	 * 
	 * Looks for Web Services.
	 */
	private void startFunctionPointAnalysisTF_WebServices()
	{
		for (Service service : services)
		{
			if (service.isWebService())
			{
				BaseClass dependencyClass = dependencyClasses.get(service.getImplementationName());

				if (dependencyClass == null)
				{
					Util.println("[ERROR] Could not find the dependency class: " + service.getImplementationName());
					continue;
				}

				Util.println("Service: " + service.getName());
				Util.println("  Impl: " + service.getImplementationName());

				for (Method method : service.getMethods())
				{
					String methodSignature = service.getImplementationName() + "." + method.getSignature();

					Util.println("\t " + method.getName());

					// used to avoid an infinite loop
					if (visitedMethods == null)
						visitedMethods = new HashSet<String>();
					else
						visitedMethods.clear();

					countedEntities = new HashSet<String>();

					// calling
					boolean ret = doesMethodChangeDataOrBehavior(dependencyClass, methodSignature);

					if (ret)
					{
						// TODO: classify if it is an EI or an EO 

						if (method.getReturnType().equals("void"))
						{
							Util.println("\t\t  Is an EI");
							method.setAsEI();
						}
						else
						{
							Util.println("\t\t  Is an EO");
							method.setAsEO();
						}
					}
					else
					{
						Util.println("\t\t  Is an EQ");
						method.setAsEQ1();
					}

					method.setCountedEntities(countedEntities);
				}
			}
		}
	}

	/**
	 * Builds the report for Transaction Functions.
	 * 
	 * @param fpaConfig The configuration to be used.
	 * @return the report
	 */
	public FPAReport startFunctionPointAnalysisTFReport(FPAConfig fpaConfig)
	{
		if (fpaReport == null)
			fpaReport = new FPAReport();
		else
			fpaReport.clearTFReport();

		for (UseCase useCase : useCases)
		{
			for (View view : useCase.getViews())
			{
				if (!view.isEQ2())
				{
					ReportResult reportResult = new ReportResult();
					reportResult.setNamespace(useCase.getName());

					int det = 0;
					int ftr = 0;
					int value = 0;
					String complexity = null;

					if (view.isEI())
					{
						det = view.getNumberInputParameters() + view.getNumberButtons();
						ftr = view.getCountedEntities().size();

						reportResult.setElement(view.getName());
						reportResult.setType(Constants.TF_EI);
						complexity = fpaConfig.getEIComplexity(ftr, det);
						value = fpaConfig.getEIComplexityValue(ftr, det);
					}
					else if (view.isEO())
					{
						det = view.getNumberParameters() + view.getNumberButtons() + view.getTotalColumns();
						ftr = view.getCountedEntities().size();

						reportResult.setElement(view.getName());
						reportResult.setType(Constants.TF_EO);
						complexity = fpaConfig.getEOComplexity(ftr, det);
						value = fpaConfig.getEOComplexityValue(ftr, det);
					}
					else if (view.isEQ1())
					{
						det = view.getNumberParameters() + view.getNumberButtons();

						StringBuilder element = new StringBuilder(view.getName());

						if (view.getResultView() != null)
						{
							element.append(" / ");
							element.append(view.getResultView().getName());
							det += view.getResultView().getNumberParameters() + view.getResultView().getNumberButtons() + view.getResultView().getTotalColumns();

							view.getCountedEntities().addAll(view.getResultView().getCountedEntities());
						}

						ftr = view.getCountedEntities().size();

						reportResult.setElement(element.toString());
						reportResult.setType(Constants.TF_EQ);
						complexity = fpaConfig.getEQComplexity(ftr, det);
						value = fpaConfig.getEQComplexityValue(ftr, det);
					}
					else
					{
						Util.println("\t[ERROR] Transaction Function Report.");
						Util.println("\t\tModule: " + useCase.getModuleName());
						Util.println("\t\tUseCase: " + useCase.getName());
						Util.println("\t\tView: " + view.getName());
						continue;
					}

					reportResult.setDet(det);
					reportResult.setRet_ftr(ftr);
					reportResult.setComplexity(complexity);
					reportResult.setComplexityValue(value);

					fpaReport.addTFReport(reportResult);
					fpaReport.addTFReportTotal(value);

					Util.println("\t" + reportResult.getType() + ": " + reportResult.getElement());
					Util.println("\t\tFTR: " + reportResult.getRet_ftr());
					Util.println("\t\tDET: " + reportResult.getDet());
					Util.println("\t\tComplexity: " + reportResult.getComplexity());
					Util.println("\t\tComplexity Value: " + reportResult.getComplexityValue());
				}
			}
		}

		for (Service service : services)
		{
			if (service.isWebService())
			{
				for (Method method : service.getMethods())
				{
					ReportResult reportResult = new ReportResult();
					reportResult.setNamespace(service.getName());

					reportResult.setElement(Util.getClassName(service.getName()) + "." + method.getName());

					int det = method.getParameters().size();
					int ftr = 0;
					int value = 0;
					String complexity = null;

					if (method.isEI())
					{
						reportResult.setType(Constants.TF_EI);
						complexity = fpaConfig.getEIComplexity(reportResult.getRet_ftr(), det);
						value = fpaConfig.getEIComplexityValue(reportResult.getRet_ftr(), det);
						ftr = method.getCountedEntities().size();
					}
					else if (method.isEO())
					{
						reportResult.setType(Constants.TF_EO);
						complexity = fpaConfig.getEOComplexity(reportResult.getRet_ftr(), det);
						value = fpaConfig.getEOComplexityValue(reportResult.getRet_ftr(), det);
						ftr = method.getCountedEntities().size();
					}
					else if (method.isEQ1())
					{
						reportResult.setType(Constants.TF_EQ);
						complexity = fpaConfig.getEQComplexity(reportResult.getRet_ftr(), det);
						value = fpaConfig.getEQComplexityValue(reportResult.getRet_ftr(), det);
						ftr = method.getCountedEntities().size();
					}
					else
					{
						Util.println("\t[ERROR] Transaction Function Report.");
						Util.println("\t\tService: " + service.getName());
						Util.println("\t\tMethod: " + method.getName());
						continue;
					}

					reportResult.setDet(det);
					reportResult.setRet_ftr(ftr);
					reportResult.setComplexity(complexity);
					reportResult.setComplexityValue(value);

					fpaReport.addTFReport(reportResult);
					fpaReport.addTFReportTotal(value);

					Util.println("\t" + reportResult.getType() + ": " + reportResult.getElement());
					Util.println("\t\tFTR: " + reportResult.getRet_ftr());
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

		if (visitedMethods != null)
			visitedMethods.clear();

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
		if (classUsages == null)
			throw new ExpressFPAException("Must load the Class Usage Report.");

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
