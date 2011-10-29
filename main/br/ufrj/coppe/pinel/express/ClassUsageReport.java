package br.ufrj.coppe.pinel.express;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.ufrj.coppe.pinel.express.common.Constants;
import br.ufrj.coppe.pinel.express.common.Util;
import br.ufrj.coppe.pinel.express.data.BaseClass;
import br.ufrj.coppe.pinel.express.data.ClassUsage;
import br.ufrj.coppe.pinel.express.data.Controller;
import br.ufrj.coppe.pinel.express.data.Dependency;
import br.ufrj.coppe.pinel.express.data.Entity;
import br.ufrj.coppe.pinel.express.data.IBaseClass;
import br.ufrj.coppe.pinel.express.data.Method;
import br.ufrj.coppe.pinel.express.data.Service;
import br.ufrj.coppe.pinel.express.exception.ExpressFPAException;

/**
 * @author Roque Pinel
 *
 */
public class ClassUsageReport
{
	private String fileName;

	private Collection<ClassUsage> classUsages = new ArrayList<ClassUsage>();

	/**
	 * @param fileName the report's file name
	 */
	public ClassUsageReport(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * @param classUsages
	 */
	public ClassUsageReport(String fileName, Collection<ClassUsage> classUsages)
	{
		this(fileName);

		this.classUsages = classUsages;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * Loads the ClassUsage from dependencies.
	 * 
	 * @param entities
	 * @param allClasses
	 * @param dependencyClasses
	 * @return the class usages
	 * @throws ExpressFPAException
	 */
	public Collection<ClassUsage> loadClassUsage(Map<String, Entity> entities, Map<String, IBaseClass> allClasses, Collection<BaseClass> dependencyClasses) throws ExpressFPAException
	{
		for (BaseClass dependencyClass : dependencyClasses)
		{
			IBaseClass clazz1 = allClasses.get(dependencyClass.getName());
			if (clazz1 == null)
				continue;

			Set<String> methodSignatures = clazz1.getMethodsSignatures();

			Set<String> dependenciesAlreadyFound = new HashSet<String>();

			String type = "";

			if (clazz1 instanceof Controller)
				type = ClassUsage.USE_CASE;
			else if (clazz1 instanceof Service)
				type = ClassUsage.SERVICE;
			else if (clazz1 instanceof BaseClass)
				type = ClassUsage.CLASS;
			else
				throw new ExpressFPAException("Could not find class's type.");

			for (Method method : dependencyClass.getMethods())
			{
				// double check the method
				if (methodSignatures.contains(method.getName()))
				{
					if (!type.equals(ClassUsage.USE_CASE))
						dependenciesAlreadyFound = new HashSet<String>();

					for (Dependency dependency : method.getDependencies())
					{
						ClassUsage classUsage = new ClassUsage();

						classUsage.setType(type);

						// if it's a controller, use the use case's name
						if (type.equals(ClassUsage.USE_CASE))
							classUsage.setElement(((Controller) clazz1).getUseCase().getName());
						else
							classUsage.setElement(clazz1.getName());

						classUsage.setMethod(method.getName());

						IBaseClass clazz2 = allClasses.get(dependency.getValue());

						if (clazz2 != null && !dependenciesAlreadyFound.contains(clazz2.getName()))
						{
							// depends on a service
							if (clazz2 instanceof Service)
							{
								classUsage.setDependencyType(ClassUsage.SERVICE);
								classUsage.setDependency(clazz2.getName());

								classUsages.add(classUsage);
							}

							dependenciesAlreadyFound.add(clazz2.getName());
						}
						else
						{
							clazz2 = entities.get(dependency.getValue());

							// depends on a entity
							if (clazz2 != null && !dependenciesAlreadyFound.contains(clazz2.getName()))
							{
								classUsage.setDependencyType(ClassUsage.ENTITY);
								classUsage.setDependency(clazz2.getName());

								classUsages.add(classUsage);

								dependenciesAlreadyFound.add(clazz2.getName());
							}
						}
					}
				}
			}
		}

		return classUsages;
	}

	/**
	 * Load the class usages from the CSV.
	 * 
	 * @return the class usages
	 * @throws ExpressFPAException
	 */
	public Collection<ClassUsage> loadCSV() throws ExpressFPAException
	{
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(fileName));

			boolean isHeader = true;
			
			while (in.ready())
			{
				String[] columns = Util.getCSVColumns(in.readLine(), Constants.CSV.DELIMITER);

				ClassUsage classUsage = new ClassUsage(columns[0], columns[1], columns[2], columns[3], columns[4]);

				Util.println(classUsage.getType() + " - " + classUsage.getElement() + " - "
						+ classUsage.getMethod() + " - " + classUsage.getDependencyType() + " - "
						+ classUsage.getDependency());

				if (isHeader)
					isHeader = false;
				else
					classUsages.add(classUsage);
			}

			in.close();
		}
		catch (FileNotFoundException e)
		{
			throw new ExpressFPAException("Could not find file: " + fileName, e);
		}
		catch (IOException e)
		{
			throw new ExpressFPAException("Could not read file: " + fileName, e);
		}

		return classUsages;
	}

	/**
	 * Creates the CSV report.
	 * 
	 * @throws ExpressFPAException
	 */
	public void createCSV() throws ExpressFPAException
	{
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

			out.write("Type" + Constants.CSV.DELIMITER + "Element" + Constants.CSV.DELIMITER
					+ "Element Method" + Constants.CSV.DELIMITER + "Dependency Type" + Constants.CSV.DELIMITER
					+ "Dependency" + "\n");
	
			for (ClassUsage classUsage : classUsages)
			{
				out.write(classUsage.getType() + Constants.CSV.DELIMITER);

				out.write(classUsage.getElement() + Constants.CSV.DELIMITER);

				out.write(classUsage.getMethod() + Constants.CSV.DELIMITER);

				out.write(classUsage.getDependencyType() + Constants.CSV.DELIMITER);

				out.write(classUsage.getDependency() + "\n");
			}

			out.close();
		}
		catch (IOException e)
		{
			throw new ExpressFPAException("Could not write file: " + fileName, e);
		}
	}
}
