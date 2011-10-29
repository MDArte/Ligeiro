package br.ufrj.coppe.pinel.express.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import br.ufrj.coppe.pinel.express.common.FPAConfig;
import br.ufrj.coppe.pinel.express.common.Util;
import br.ufrj.coppe.pinel.express.data.Attribute;
import br.ufrj.coppe.pinel.express.data.BaseClass;
import br.ufrj.coppe.pinel.express.data.Controller;
import br.ufrj.coppe.pinel.express.data.Dependency;
import br.ufrj.coppe.pinel.express.data.Entity;
import br.ufrj.coppe.pinel.express.data.Method;
import br.ufrj.coppe.pinel.express.data.Parameter;
import br.ufrj.coppe.pinel.express.data.Service;
import br.ufrj.coppe.pinel.express.data.UseCase;
import br.ufrj.coppe.pinel.express.xml.exception.ReadXMLException;
import br.ufrj.coppe.pinel.express.xml.exception.WriteXMLException;

/**
 * Holds functions related to XML.
 * 
 * @author Roque Pinel
 *
 */
public class JDOMUtil implements IXMLEngine
{
	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readStatisticType(java.lang.String)
	 */
	@Override
	public String readStatisticType (String fileName) throws ReadXMLException
	{
		Element statistics = readXMLDocument(fileName).getRootElement();

		return statistics.getChildText("type");
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readEntities(java.lang.String)
	 */
	@Override
	public Collection<Entity> readEntities(String fileName) throws ReadXMLException
	{
		Collection<Entity> entities = new LinkedList<Entity>();

		Element statistics = readXMLDocument(fileName).getRootElement();

		Util.println(statistics.getChildText("application"));

		if (statistics.getChild("entities") != null)
		{
			List<Element> entitiesElements = statistics.getChild("entities").getChildren();
			for (Element entityElement : entitiesElements)
			{
				Util.println("\t" + entityElement.getChildText("name"));

				Entity entity = new Entity(entityElement.getChildText("name"));
				entity.setImplementationName(entityElement.getChildText("implementationName"));

				if (entityElement.getChildText("extends") != null)
				{
					entity.setExtendsClass(entityElement.getChildText("extends"));
				}

				entity.setAttributes(readAttributes(entityElement));

				entity.setMethods(readMethods(entityElement));

				entities.add(entity);
			}
		}

		return entities;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readClasses(java.lang.String)
	 */
	@Override
	public Collection<BaseClass> readClasses(String fileName) throws ReadXMLException
	{
		Collection<BaseClass> classes = new LinkedList<BaseClass>();

		Element statistics = readXMLDocument(fileName).getRootElement();

		Util.println(statistics.getChildText("application"));

		if (statistics.getChild("modules") != null)
		{
			List<Element> moduleElements = statistics.getChild("modules").getChildren();
			for (Element moduleElement : moduleElements)
			{
				Util.println("\t" + moduleElement.getChildText("name"));

				List<Element> classesElements = moduleElement.getChild("classes").getChildren("class");
				for (Element classElement : classesElements)
				{
					Util.println("\t\t" + classElement.getChildText("name"));

					BaseClass clazz = new BaseClass(classElement.getChildText("name"));
					clazz.setModule(moduleElement.getChildText("name"));

					clazz.setMethods(readMethods(classElement));

					classes.add(clazz);
				}
			}
		}

		return classes;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readServices(java.lang.String)
	 */
	@Override
	public Collection<Service> readServices(String fileName) throws ReadXMLException
	{
		Collection<Service> services = new LinkedList<Service>();

		Element statistics = readXMLDocument(fileName).getRootElement();

		Util.println(statistics.getChildText("application"));

		if (statistics.getChild("modules") != null)
		{
			List<Element> moduleElements = statistics.getChild("modules").getChildren();
			for (Element moduleElement : moduleElements)
			{
				Util.println("\t" + moduleElement.getChildText("name"));

				List<Element> servicesElements = moduleElement.getChild("services").getChildren("service");
				for (Element serviceElement : servicesElements)
				{
					Util.println("\t\t" + serviceElement.getChildText("name"));

					Service service = new Service(serviceElement.getChildText("name"));
					service.setImplementationName(serviceElement.getChildText("implementationName"));
					service.setModule(moduleElement.getChildText("name"));

					List<Element> valuesElements = serviceElement.getChild("otherNames").getChildren("value");
					for (Element valueElement : valuesElements)
					{
						service.addOtherName(valueElement.getText());
					}

					service.setMethods(readMethods(serviceElement));

					services.add(service);
				}
			}
		}

		return services;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readUseCases(java.lang.String)
	 */
	@Override
	public Collection<UseCase> readUseCases(String fileName) throws ReadXMLException
	{
		Collection<UseCase> useCases = new LinkedList<UseCase>();

		Element statistics = readXMLDocument(fileName).getRootElement();

		Util.println(statistics.getChildText("application"));

		if (statistics.getChild("modules") != null)
		{
			List<Element> moduleElements = statistics.getChild("modules").getChildren();
			for (Element moduleElement : moduleElements)
			{
				Util.println("\t" + moduleElement.getChildText("name"));

				List<Element> useCasesElements = moduleElement.getChild("useCases").getChildren("useCase");
				for (Element useCaseElement : useCasesElements)
				{
					UseCase useCase = new UseCase(useCaseElement.getChildText("name"));
					Util.println("\t\t" + useCase.getName());

					Element controllerElement = useCaseElement.getChild("controller");

					Controller controller = new Controller(controllerElement.getChildText("name"));
					Util.println("\t\t\t" + controller.getName());

					controller.setImplementationName(controllerElement.getChildText("implementationName"));
					//controller.setUseCaseName(useCaseElement.getChildText("name"));

					controller.setMethods(readMethods(controllerElement));

					useCase.setController(controller);
				}
			}
		}

		return useCases;
	}

	/**
	 * Read a <code>Collection</code> of attributes.
	 * 
	 * @param classElement the class
	 * @return the <code>Collection</code> of attributes
	 */
	private static Collection<Attribute> readAttributes(Element classElement)
	{
		Collection<Attribute> attributes = new ArrayList<Attribute>();

		List<Element> attributesElements = classElement.getChild("attributes").getChildren("attribute");
		for (Element attributeElement : attributesElements)
		{
			Util.println("\t\t" + attributeElement.getChildText("name"));

			Attribute attribute = new Attribute(attributeElement.getChildText("name"), attributeElement.getChildText("type"));

			try
			{
				if (attributeElement.getAttribute("identifier") != null
					&& attributeElement.getAttribute("identifier").getBooleanValue())
				{
					Util.println("\t\t\t<<Identifier>>");
					attribute.setAsIdentifier();
				}
			}
			catch (DataConversionException e)
			{
				// suppressed
			}

			attributes.add(attribute);
		}

		return attributes;
	}

	/**
	 * Reads a <code>Collection</code> of methods.
	 * 
	 * @param classElement the class
	 * @return the <code>Collection</code> of methods
	 */
	private static Collection<Method> readMethods(Element classElement)
	{
		Collection<Method> methods = new ArrayList<Method>();

		List<Element> methodsElements = classElement.getChild("methods").getChildren("method");
		for (Element methodElement : methodsElements)
		{
			Util.println("\t\t\t" + methodElement.getChildText("name"));

			Method method = new Method(methodElement.getChildText("name"));

			method.setReturnType(methodElement.getChild("return").getChildText("type"));

			try
			{
				if (methodElement.getAttribute("modifier") != null
					&& methodElement.getAttribute("modifier").getBooleanValue())
				{
					Util.println("\t\t\t<<Modifier>>");
					method.setAsModifier();
				}
			}
			catch (DataConversionException e)
			{
				// suppressed
			}

			method.setParameters(readParameters(methodElement));

			methods.add(method);
		}

		return methods;
	}

	/**
	 * Reads a <code>Collection</code> of parameters.
	 * 
	 * @param methodElement the method
	 * @return the <code>Collection</code> of parameters
	 */
	private static Collection<Parameter> readParameters(Element methodElement)
	{
		Collection<Parameter> parameters = new ArrayList<Parameter>();

		List<Element> parametersElements = methodElement.getChild("parameters").getChildren("parameter");
		for (Element parameterElement : parametersElements)
		{
			Util.println("\t\t\t\t" + parameterElement.getChildText("type"));

			Parameter parameter = new Parameter(parameterElement.getChildText("name"), parameterElement.getChildText("type"));
			parameters.add(parameter);
		}

		return parameters;
	}

	/* -------------------    DEPENDENCY    ------------------- */

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readDependencies(java.lang.String)
	 */
	@Override
	public Collection<BaseClass> readDependencies(String fileName) throws ReadXMLException
	{
		Collection<BaseClass> classes = new LinkedList<BaseClass>();

		Element dependenciesElements = readXMLDocument(fileName).getRootElement();

		List<Element> packagesElements = dependenciesElements.getChildren();
		for (Element packageElement : packagesElements)
		{
//			Util.print(packageElement.getChildText("name"));

			List<Element> classesElements = packageElement.getChildren("class");
			for (Element classElement : classesElements)
			{
				if (classElement.getAttributeValue("confirmed").equals("no"))
					continue;

//				Util.print("\t" + classElement.getChildText("name"));

				BaseClass baseClass = new BaseClass(classElement.getChildText("name"));
				baseClass.setPackageName(packageElement.getChildText("name"));

				List<Element> featuresElements = classElement.getChildren("feature");
				for (Element featureElement : featuresElements)
				{
//					Util.print("\t\t" + featureElement.getChildText("name"));

					Method method = new Method(featureElement.getChildText("name"));

					List<Element> boundsElements = featureElement.getChildren("inbound");
					for (Element boundElement : boundsElements)
					{
//						Util.print("\t\t\t" + boundElement.getText());

						Dependency dependency = new Dependency(boundElement.getText());

						String type = boundElement.getAttributeValue("type");
						if (type.equals("class"))
							dependency.setAsClass();
						else if (type.equals("feature"))
							dependency.setAsFeature();

						method.addDependency(dependency);
					}

					boundsElements = featureElement.getChildren("outbound");
					for (Element boundElement : boundsElements)
					{
//						Util.print("\t\t\t" + boundElement.getText());

						Dependency dependency = new Dependency(boundElement.getText());

						String type = boundElement.getAttributeValue("type");
						if (type.equals("class"))
							dependency.setAsClass();
						else if (type.equals("feature"))
							dependency.setAsFeature();

						method.addDependency(dependency);
					}

					baseClass.addMethod(method);
				}

				classes.add(baseClass);
			}
		}

		return classes;
	}

	/* -------------------    CONFIGURATION    ------------------- */

	@Override
	public FPAConfig readFPAConfig(String fileName) throws ReadXMLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* -------------------    XML    ------------------- */

	/**
	 * Reads a XML document.
	 * 
	 * @param fileName the XML's filename
	 * @return the document read
	 * @throws ReadXMLException
	 */
	public static Document readXMLDocument(String fileName) throws ReadXMLException
	{
		File file = new File(fileName);

		if (!file.exists() || !file.canRead())
			throw new ReadXMLException("Could not read XML.");

		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = new Document();

		try
		{
			document = saxBuilder.build(file);
		}
		catch (JDOMException e)
		{
			throw new ReadXMLException("Could not parse XML.", e);
		}
		catch (IOException e)
		{
			throw new ReadXMLException("Could not read file.", e);
		}

		return document;
	}

	/**
	 * Writes a XML document.
	 * 
	 * @param fileName the XML's filename 
	 * @param document the document to be written
	 * @throws WriteXMLException
	 */
	public static void writeXMLDocument(String fileName, Document document) throws WriteXMLException
	{
		XMLOutputter xout = new XMLOutputter();

		try
		{
			FileWriter writer = new FileWriter(fileName);

			xout.output(document, writer);

			writer.close();
		}
		catch (IOException e)
		{
			throw new WriteXMLException("Could not write file.", e);
		}
	}
}
