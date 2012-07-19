package br.ufrj.cos.pinel.ligeiro.xml;

import java.util.Collection;

import br.ufrj.cos.pinel.ligeiro.common.Constants;
import br.ufrj.cos.pinel.ligeiro.common.FPAConfig;
import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.data.BaseClass;
import br.ufrj.cos.pinel.ligeiro.data.Entity;
import br.ufrj.cos.pinel.ligeiro.data.Service;
import br.ufrj.cos.pinel.ligeiro.data.UseCase;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

/**
 * Holds functions related to XML.
 * 
 * @author Roque Pinel
 *
 */
public class XMLUtil
{
	/**
	 * Chooses which XML Engine to use.
	 * 
	 * @param methodName
	 * @param fileName
	 * @return
	 */
	private static IXMLEngine getXMLEngine(String methodName, String fileName)
	{
		IXMLEngine xmlEngine = null;

		if (Constants.XML_ENGINE.equals(Constants.XML_ENGINE_TYPE_JDOM))
		{
			Util.println("Using JDOM (" + methodName + ") in " + fileName);
			xmlEngine = new JDOMUtil();
		}
		else if (Constants.XML_ENGINE.equals(Constants.XML_ENGINE_TYPE_SAX))
		{
			Util.println("Using SAX (" + methodName + ") in " + fileName);
			xmlEngine = new SAXUtil();
		}

		return xmlEngine;
	}

	/**
	 * Prints the size of the collection. If <code>null</code>, prints 0.
	 * 
	 * @param col the collection
	 */
	private static void printCollectionSize(Collection col)
	{
		int size = 0;

		if (col != null)
			size = col.size();

		Util.println("Number of elements read: " + size);
	}

	/**
	 * Reads the type of a statistic XML.
	 * 
	 * @param fileName the file name
	 * @return the type of the statistic XML
	 * @throws ReadXMLException
	 */
	public static String readStatisticType(String fileName) throws ReadXMLException
	{
		String statisticType = null;

		IXMLEngine xmlEngine = getXMLEngine("readStatisticType", fileName);

		if (xmlEngine != null)
			statisticType = xmlEngine.readStatisticType(fileName);

		return statisticType;
	}

	/**
	 * Read a <code>Collection</code> of entities.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of entities
	 * @throws ReadXMLException
	 */
	public static Collection<Entity> readEntities(String fileName) throws ReadXMLException
	{
		Collection<Entity> entities = null;

		IXMLEngine xmlEngine = getXMLEngine("readEntities", fileName);

		if (xmlEngine != null)
			entities = xmlEngine.readEntities(fileName);

		printCollectionSize(entities);

		return entities;
	}

	/**
	 * Reads a <code>Collection</code> of classes.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of classes
	 * @throws ReadXMLException
	 */
	public static Collection<BaseClass> readClasses(String fileName) throws ReadXMLException
	{
		Collection<BaseClass> classes = null;

		IXMLEngine xmlEngine = getXMLEngine("readClasses", fileName);

		if (xmlEngine != null)
			classes = xmlEngine.readClasses(fileName);

		printCollectionSize(classes);

		return classes;
	}

	/**
	 * Reads a <code>Collection</code> of services.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of services
	 * @throws ReadXMLException
	 */
	public static Collection<Service> readServices(String fileName) throws ReadXMLException
	{
		Collection<Service> services = null;

		IXMLEngine xmlEngine = getXMLEngine("readServices", fileName);

		if (xmlEngine != null)
			services = xmlEngine.readServices(fileName);

		printCollectionSize(services);

		return services;
	}

	/**
	 * Reads a <code>Collection</code> of use cases.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of use cases
	 * @throws ReadXMLException
	 */
	public static Collection<UseCase> readUseCases(String fileName) throws ReadXMLException
	{
		Collection<UseCase> useCases = null;

		IXMLEngine xmlEngine = getXMLEngine("readUseCases", fileName);

		if (xmlEngine != null)
			useCases = xmlEngine.readUseCases(fileName);

		printCollectionSize(useCases);

		return useCases;
	}

	/* -------------------    DEPENDENCY    ------------------- */

	/**
	 * Reads the dependencies from a XML and return as a <code>Collection</code>
	 * of <code>BaseClass</code>.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of classes and dependencies
	 * @throws ReadXMLException
	 */
	public static Collection<BaseClass> readDependencies(String fileName) throws ReadXMLException
	{
		Collection<BaseClass> classes = null;

		IXMLEngine xmlEngine = getXMLEngine("readDependencies", fileName);

		if (xmlEngine != null)
			classes = xmlEngine.readDependencies(fileName);

		printCollectionSize(classes);

		return classes;
	}

	/* -------------------    CONFIGURATION    ------------------- */

	/**
	 * Reads the FPA Configuration from a XML and return it.
	 * 
	 * @param fileName the file name
	 * @return the configuration
	 * @throws ReadXMLException
	 */
	public static FPAConfig readFPAConfiguration(String fileName) throws ReadXMLException
	{
		FPAConfig fpaConfig = null;

		IXMLEngine xmlEngine = getXMLEngine("readFPAConfiguration", fileName);

		if (xmlEngine != null)
			fpaConfig = xmlEngine.readFPAConfig(fileName);

		return fpaConfig;
	}
}
