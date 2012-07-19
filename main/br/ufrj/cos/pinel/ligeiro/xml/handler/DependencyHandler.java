package br.ufrj.cos.pinel.ligeiro.xml.handler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;

import br.ufrj.cos.pinel.ligeiro.data.BaseClass;
import br.ufrj.cos.pinel.ligeiro.data.Dependency;
import br.ufrj.cos.pinel.ligeiro.data.Method;

/**
 * The listener used to read dependencies from a XML.
 * 
 * @author Roque Pinel
 * 
 */
public class DependencyHandler extends GenericHandler
{
	private List<BaseClass> classes = new LinkedList<BaseClass>();

	private String tagName = null;
	private String tagType = null;
	private String valueNode = null;

	private BaseClass baseClass = null;
	private Method method = null;

	private boolean hasMethod = false;


	/**
	 * @return the classes read.
	 */
	public Collection<BaseClass> getClasses()
	{
		return classes;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String tag, Attributes attributes)
	{
		tagName = tag.trim();
		tagType = null;

		if (tagName.equals("class"))
		{
			String confirmedStr = attributes.getValue("confirmed");
			if (confirmedStr != null && confirmedStr.equals("yes"))
			{
				baseClass = new BaseClass();
				classes.add(baseClass);
			}
		}
		else if (tagName.equals("feature"))
		{
			hasMethod = true;

			String confirmedStr = attributes.getValue("confirmed");
			if (baseClass != null && confirmedStr != null && confirmedStr.equals("yes"))
			{
				method = new Method();
				baseClass.addMethod(method);
			}
		}
		else if (tagName.equals("inbound") || tagName.equals("outbound"))
		{
			tagType = attributes.getValue("type");
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String tag)
	{
		tagName = tag.trim();
		tagType = null;
		valueNode = null;

		if (tagName.equals("class"))
		{
			baseClass = null;
		}
		else if (tagName.equals("feature"))
		{
			method = null;
			hasMethod = false;
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int length)
	{
		StringBuffer content = new StringBuffer();
		content.append(ch, start, length);

		valueNode = content.toString().trim();

		if(valueNode == null || valueNode.length() <= 0)
		{ 
			return;
		}

		if (baseClass != null)
		{
			if (hasMethod)
			{
				if (method != null)
				{
					if (tagName.equals("name"))
					{
						method.setName(valueNode);
					}
					else if (tagName.equals("inbound") || tagName.equals("outbound"))
					{
						Dependency dependency = new Dependency(valueNode);
	
						if (tagType.equals("class"))
							dependency.setAsClass();
						else if (tagType.equals("feature"))
							dependency.setAsFeature();
	
						method.addDependency(dependency);
					}
				}
			}
			else if (tagName.equals("name"))
			{
				baseClass.setName(valueNode);
			}
		}
	}
}
