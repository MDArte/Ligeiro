package br.ufrj.cos.pinel.ligeiro.xml.handler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;

import br.ufrj.cos.pinel.ligeiro.data.Attribute;
import br.ufrj.cos.pinel.ligeiro.data.BaseClass;
import br.ufrj.cos.pinel.ligeiro.data.Method;
import br.ufrj.cos.pinel.ligeiro.data.Parameter;

/**
 * The listener used to read classes from a XML.
 * 
 * @author Roque Pinel
 * 
 */
public class ClassHandler extends GenericHandler
{
	private List<BaseClass> classes = new LinkedList<BaseClass>();

	private String tagName = null;
	private String valueNode = null;

	private BaseClass baseClass = null;
	private Method method = null;
	private Attribute attribute = null;
	private Parameter parameter = null;

	private boolean hasMethodReturn = false;


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

		if (tagName.equals("class"))
		{
			baseClass = new BaseClass();
			classes.add(baseClass);
		}
		else if (baseClass != null && tagName.equals("method"))
		{
			method = new Method();
			baseClass.addMethod(method);

			String modifierStr = attributes.getValue("modifier");
			if (modifierStr != null && Boolean.valueOf(modifierStr))
				method.setAsModifier();
		}
		else if (method != null && tagName.equals("return"))
		{
			hasMethodReturn = true;
		}
		else if (method != null && tagName.equals("parameter"))
		{
			parameter = new Parameter();
			method.addParameter(parameter);
		}
		else if (baseClass != null && tagName.equals("attribute"))
		{
			attribute = new Attribute();
			baseClass.addAttribute(attribute);

			String identifierStr = attributes.getValue("identifier");
			if (identifierStr != null && Boolean.valueOf(identifierStr))
				attribute.setAsIdentifier();
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String tag)
	{
		tagName = tag.trim();
		valueNode = null;

		if (tagName.equals("class"))
		{
			baseClass = null;
		}
		else if (tagName.equals("method"))
		{
			method = null;
		}
		else if (tagName.equals("return"))
		{
			hasMethodReturn = false;
		}
		else if (tagName.equals("parameter"))
		{
			parameter = null;
		}
		else if (tagName.equals("attribute"))
		{
			attribute = null;
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
			if (method != null)
			{
				if (hasMethodReturn)
				{
					if (tagName.equals("type"))
					{
						method.setReturnType(valueNode);
					}
				}
				else if (parameter != null)
				{
					if (tagName.equals("name"))
					{
						parameter.setName(valueNode);
					}
					else if (tagName.equals("type"))
					{
						parameter.setType(valueNode);
					}
				}
				else if (tagName.equals("name"))
				{
					method.setName(valueNode);
				}
			}
			else if (attribute != null)
			{
				if (tagName.equals("name"))
				{
					attribute.setName(valueNode);
				}
				else if (tagName.equals("type"))
				{
					attribute.setType(valueNode);
				}
			}
			else if (tagName.equals("name"))
			{
				baseClass.setName(valueNode);
			}
		}
	}
}
