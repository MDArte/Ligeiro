package br.ufrj.coppe.pinel.express.xml;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import br.ufrj.coppe.pinel.express.common.FPAConfig;
import br.ufrj.coppe.pinel.express.data.BaseClass;
import br.ufrj.coppe.pinel.express.data.Entity;
import br.ufrj.coppe.pinel.express.data.Service;
import br.ufrj.coppe.pinel.express.data.UseCase;
import br.ufrj.coppe.pinel.express.xml.exception.ReadXMLException;
import br.ufrj.coppe.pinel.express.xml.handler.ClassHandler;
import br.ufrj.coppe.pinel.express.xml.handler.DependencyHandler;
import br.ufrj.coppe.pinel.express.xml.handler.EntityHandler;
import br.ufrj.coppe.pinel.express.xml.handler.FPAConfigHandler;
import br.ufrj.coppe.pinel.express.xml.handler.ServiceHandler;
import br.ufrj.coppe.pinel.express.xml.handler.UseCaseHandler;

/**
 * Holds functions related to XML.
 * 
 * @author Roque Pinel
 *
 */
public class SAXUtil implements IXMLEngine
{
	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readStatisticType(java.lang.String)
	 */
	public String readStatisticType (String fileName) throws ReadXMLException
	{
		try
		{
			return XPathFactory.newInstance().newXPath().evaluate("/statistics/type", new InputSource(fileName));
		}
		catch (XPathExpressionException e)
		{
			throw new ReadXMLException("Could not parse XML.", e);
		}
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readEntities(java.lang.String)
	 */
	public Collection<Entity> readEntities(String fileName) throws ReadXMLException
	{
		EntityHandler xmlHandler = getXMLHandler(fileName, EntityHandler.class);

		return xmlHandler.getEntities();
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readClasses(java.lang.String)
	 */
	public Collection<BaseClass> readClasses(String fileName) throws ReadXMLException
	{
		ClassHandler xmlHandler = getXMLHandler(fileName, ClassHandler.class);

		return xmlHandler.getClasses();
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readServices(java.lang.String)
	 */
	public Collection<Service> readServices(String fileName) throws ReadXMLException
	{
		ServiceHandler xmlHandler = getXMLHandler(fileName, ServiceHandler.class);

		return xmlHandler.getServices();
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readUseCases(java.lang.String)
	 */
	public Collection<UseCase> readUseCases(String fileName) throws ReadXMLException
	{
		UseCaseHandler xmlHandler = getXMLHandler(fileName, UseCaseHandler.class);

		return xmlHandler.getUseCases();
	}

	/* -------------------    DEPENDENCY    ------------------- */

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readDependencies(java.lang.String)
	 */
	public Collection<BaseClass> readDependencies(String fileName) throws ReadXMLException
	{
		DependencyHandler xmlHandler = getXMLHandler(fileName, DependencyHandler.class);

		return xmlHandler.getClasses();
	}

	/* -------------------    CONFIGURATION    ------------------- */

	/**
	 * @see br.ufrj.coppe.pinel.express.xml.IXMLEngine#readFPAConfig(String)
	 */
	public FPAConfig readFPAConfig(String fileName) throws ReadXMLException
	{
		FPAConfigHandler xmlHandler = getXMLHandler(fileName, FPAConfigHandler.class);

		return xmlHandler.getFPAConfig();
	}

	/* -------------------    XML    ------------------- */

	/**
	 * Return the XML Handler.
	 * 
	 * @param fileName the XML's filename
	 * @param handlerClass the XML Handler's class
	 * @return the XML Handler
	 * @throws ReadXMLException
	 */
	public static <T> T getXMLHandler(String fileName, Class<T> handlerClass) throws ReadXMLException
	{
		InputSource input = new InputSource(fileName);

		java.lang.reflect.Constructor<T> clazz = null;

		try
		{
			clazz = handlerClass.getConstructor();
		}
		catch (SecurityException e)
		{
			throw new ReadXMLException("Could not create handler class.", e);
		}
		catch (NoSuchMethodException e)
		{
			throw new ReadXMLException("Could not create handler class.", e);
		}

		T handler = null;

		try
		{
			handler = clazz.newInstance();
		}
		catch (IllegalArgumentException e)
		{
			throw new ReadXMLException("Could not create handler instance.", e);
		}
		catch (InstantiationException e)
		{
			throw new ReadXMLException("Could not create handler instance.", e);
		}
		catch (IllegalAccessException e)
		{
			throw new ReadXMLException("Could not create handler instance.", e);
		}
		catch (InvocationTargetException e)
		{
			throw new ReadXMLException("Could not create handler instance.", e);
		}

		try
		{
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();

			parser.parse(input, (DefaultHandler) handler);
		}
		catch (ParserConfigurationException e)
		{
			throw new ReadXMLException("Could not parse XML.", e);
		}
		catch (SAXException e)
		{
			throw new ReadXMLException("Could not parse XML.", e);
		}
		catch (IOException e)
		{
			throw new ReadXMLException("Could not read file.", e);
		}

		return handler;
	}
}
