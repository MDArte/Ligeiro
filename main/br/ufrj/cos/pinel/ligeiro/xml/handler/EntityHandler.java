package br.ufrj.cos.pinel.ligeiro.xml.handler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;

import br.ufrj.cos.pinel.ligeiro.data.Attribute;
import br.ufrj.cos.pinel.ligeiro.data.DAO;
import br.ufrj.cos.pinel.ligeiro.data.DAOMethod;
import br.ufrj.cos.pinel.ligeiro.data.Entity;
import br.ufrj.cos.pinel.ligeiro.data.Method;
import br.ufrj.cos.pinel.ligeiro.data.Parameter;

/**
 * The listener used to read entities from a XML.
 * 
 * @author Roque Pinel
 * 
 */
public class EntityHandler extends GenericHandler
{
	private List<Entity> entities = new LinkedList<Entity>();

	private String tagName = null;
	private String valueNode = null;

	private Entity entity = null;
	private Method method = null;
	private Attribute attribute = null;
	private Parameter parameter = null;

	private boolean hasMethodReturn = false;

	private DAO dao = null;
	private DAOMethod daoMethod = null;

	/**
	 * @return the entities read.
	 */
	public Collection<Entity> getEntities()
	{
		return entities;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String tag, Attributes attributes)
	{
		tagName = tag.trim();

		if (tagName.equals("entity"))
		{
			entity = new Entity();
			entities.add(entity);
		}
		else if (entity != null && tagName.equals("method"))
		{
			method = new Method();
			entity.addMethod(method);

			String modifierStr = attributes.getValue("modifier");
			if (modifierStr != null && Boolean.valueOf(modifierStr))
				method.setAsModifier();
		}
		else if (tagName.equals("return"))
		{
			hasMethodReturn = true;
		}
		else if (method != null && tagName.equals("parameter"))
		{
			parameter = new Parameter();
			method.addParameter(parameter);
		}
		else if (entity != null && tagName.equals("attribute"))
		{
			attribute = new Attribute();
			entity.addAttribute(attribute);

			String identifierStr = attributes.getValue("identifier");
			if (identifierStr != null && Boolean.valueOf(identifierStr))
				attribute.setAsIdentifier();
		}
		else if (entity != null && tagName.equals("dao"))
		{
			dao = new DAO();
			dao.setEntity(entity);

			entity.setDao(dao);
		}
		else if (dao != null && tagName.equals("methodName"))
		{
			daoMethod = new DAOMethod();
			dao.addMethod(daoMethod);

			String modifierStr = attributes.getValue("modifier");
			if (modifierStr != null && Boolean.valueOf(modifierStr))
				daoMethod.setAsModifier();

			String deleteStr = attributes.getValue("delete");
			if (deleteStr != null && Boolean.valueOf(deleteStr))
				daoMethod.setAsDelete();
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String tag)
	{
		tagName = null;
		valueNode = null;

		if (tag.equals("entity"))
		{
			entity = null;
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
		else if (tag.equals("attribute"))
		{
			attribute = null;
		}
		else if (tag.equals("dao"))
		{
			dao = null;
		}
		else if (tag.equals("methodName"))
		{
			daoMethod = null;
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

		if (entity != null)
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
			else if (dao != null)
			{
				if (daoMethod != null)
				{
					if (tagName.equals("methodName"))
					{
						daoMethod.setName(valueNode);
					}
				}
				else if (tagName.equals("name"))
				{
					dao.setName(valueNode);
				}
				else if (tagName.equals("implementationName"))
				{
					dao.setImplementationName(valueNode);
				}
				
			}
			else if (tagName.equals("name"))
			{
				entity.setName(valueNode);
			}
			else if (tagName.equals("implementationName"))
			{
				entity.setImplementationName(valueNode);
			}
			else if (tagName.equals("extends"))
			{
				entity.setExtendsClass(valueNode);
			}
		}
	}
}
