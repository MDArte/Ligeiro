package br.ufrj.cos.pinel.ligeiro.xml.handler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;

import br.ufrj.cos.pinel.ligeiro.data.Method;
import br.ufrj.cos.pinel.ligeiro.data.Parameter;
import br.ufrj.cos.pinel.ligeiro.data.Service;

/**
 * The listener used to read services from a XML.
 * 
 * @author Roque Pinel
 * 
 */
public class ServiceHandler extends GenericHandler
{
	private List<Service> services = new LinkedList<Service>();

	private String tagName = null;
	private String valueNode = null;

	private String moduleName = null;

	private Service service = null;
	private Method method = null;
	private Parameter parameter = null;

	private boolean hasMethodReturn = false;

	private boolean hasOtherNames = false;

	/**
	 * @return the services read.
	 */
	public Collection<Service> getServices()
	{
		return services;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String tag, Attributes attributes)
	{
		tagName = tag.trim();

		if (tagName.equals("module"))
		{
			moduleName = new String();
		}
		else if (tagName.equals("service"))
		{
			service = new Service();
			services.add(service);
		}
		else if (service != null && tagName.equals("method"))
		{
			method = new Method();
			service.addMethod(method);
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
		else if (service != null && tagName.equals("otherNames"))
		{
			hasOtherNames = true;
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String tag)
	{
		tagName = null;
		valueNode = null;

		if (tag.equals("module"))
		{
			moduleName = null;
		}
		else if (tag.equals("service"))
		{
			service.setModuleName(moduleName);
			service = null;
		}
		else if (tag.equals("method"))
		{
			method = null;
		}
		else if (tag.equals("return"))
		{
			hasMethodReturn = false;
		}
		else if (tag.equals("parameter"))
		{
			parameter = null;
		}
		else if (tag.equals("otherNames"))
		{
			hasOtherNames = false;
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

		if (moduleName != null)
		{
			if (service != null)
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
					else if (tagName.equals("implementationName"))
					{
						method.setImplementationName(valueNode);
					}
				}
				else if (hasOtherNames)
				{
					if (tagName.equals("value"))
					{
						service.addOtherName(valueNode);
					}
				}
				else if (tagName.equals("name"))
				{
					service.setName(valueNode);
				}
				else if (tagName.equals("implementationName"))
				{
					service.setImplementationName(valueNode);
				}
			}
			else if (tagName.equals("name"))
			{
				moduleName = valueNode;
			}
		}
	}
}
