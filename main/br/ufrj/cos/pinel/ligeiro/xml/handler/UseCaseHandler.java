package br.ufrj.cos.pinel.ligeiro.xml.handler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.data.Controller;
import br.ufrj.cos.pinel.ligeiro.data.Event;
import br.ufrj.cos.pinel.ligeiro.data.Method;
import br.ufrj.cos.pinel.ligeiro.data.Parameter;
import br.ufrj.cos.pinel.ligeiro.data.State;
import br.ufrj.cos.pinel.ligeiro.data.Table;
import br.ufrj.cos.pinel.ligeiro.data.UseCase;
import br.ufrj.cos.pinel.ligeiro.data.View;

/**
 * The listener used to read use cases from a XML.
 * 
 * @author Roque Pinel
 * 
 */
public class UseCaseHandler extends GenericHandler
{
	private List<UseCase> useCases = new LinkedList<UseCase>();

	private String tagName = null;
	private String valueNode = null;

	private String moduleName = null;

	private UseCase useCase = null;

	private View view = null;

	private Controller controller = null;
	private Method method = null;
	private Parameter parameter = null;

	private State state = null;
	private State target = null;
	private Event event = null;

	private boolean hasMethodReturn = false;

	private Table table = null;

	/**
	 * @return the useCases read.
	 */
	public Collection<UseCase> getUseCases()
	{
		return useCases;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.xml.handler.GenericHandler#endDocument()
	 */
	public void endDocument() throws SAXException
	{
		super.endDocument();

		// build the relationship between actions and states
		Util.println("Building relationship...");
		for (UseCase useCase : useCases)
		{
			Util.println("UseCase: " + useCase.getName());
			for (View view : useCase.getViews())
			{
				for (Method action : view.getMethods())
				{
					if (action.getTarget() != null && !action.getTarget().isFinalState() && action.getTarget().getName() != null)
					{
						for (State state : useCase.getStates())
						{
							if (state.getName().equals(action.getTarget().getName()))
							{
								Util.println("\tFrom: " + action.getName());
								Util.println("\tTo: " + state.getName());
								action.setTarget(state);
							}
						}
					}
				}
			}
		}
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
		else if (tagName.equals("useCase"))
		{
			useCase = new UseCase();
			useCases.add(useCase);
		}
		else if (useCase != null && tagName.equals("state"))
		{
			state = new State();
			useCase.addState(state);

			String firstStr = attributes.getValue("first");
			if (firstStr != null && firstStr.equals("true"))
			{
				state.setAsFirst();
				useCase.setFirst(state);
			}
		}
		else if (useCase != null && tagName.equals("view"))
		{
			view = new View();
			view.setUseCase(useCase);
			useCase.addView(view);

			String firstStr = attributes.getValue("first");
			if (firstStr != null && firstStr.equals("true"))
			{
				view.setAsFirst();
				useCase.setFirst(view);
			}
		}
		else if (useCase != null && tagName.equals("controller"))
		{
			controller = new Controller();
			controller.setUseCase(useCase);
			useCase.setController(controller);
		}
		else if (view != null && tagName.equals("action"))
		{
			method = new Method();
			view.addMethod(method);

			String lookupGridStr = attributes.getValue("lookupGrid");
			if (lookupGridStr != null && lookupGridStr.equals("true"))
			{
				method.setAsLookupGrid();
			}
			String tableLinkStr = attributes.getValue("tableLink");
			if (tableLinkStr != null && tableLinkStr.equals("true"))
			{
				method.setAsTableLink();
			}
		}
		else if (view != null && tagName.equals("variable"))
		{
			String tableStr = attributes.getValue("table");
			if (tableStr != null && tableStr.equals("true"))
			{
				table = new Table();
				view.addTable(table);
			}
		}
		else if (controller != null && tagName.equals("method"))
		{
			method = new Method();
			controller.addMethod(method);
		}
		else if (method != null && tagName.equals("return"))
		{
			hasMethodReturn = true;
		}
		else if (method != null && tagName.equals("parameter"))
		{
			parameter = new Parameter();
			method.addParameter(parameter);

			String plainTextStr = attributes.getValue("plainText");
			if (plainTextStr != null && plainTextStr.equals("true"))
			{
				parameter.setAsPlainText();
			}
			String readOnlyStr = attributes.getValue("readOnly");
			if (readOnlyStr != null && readOnlyStr.equals("true"))
			{
				parameter.setAsReadOnly();
			}
			String hiddenFieldStr = attributes.getValue("hiddenField");
			if (hiddenFieldStr != null && hiddenFieldStr.equals("true"))
			{
				parameter.setAsHiddenField();
			}
		}
		else if (method != null && tagName.equals("target"))
		{
			target = new State();
			method.setTarget(target);

			String hiddenFieldStr = attributes.getValue("finalState");
			if (hiddenFieldStr != null && hiddenFieldStr.equals("true"))
			{
				target.setAsFinalState();
			}
		}
		else if (state != null && tagName.equals("target"))
		{
			target = new State();
			state.setTarget(target);

			String hiddenFieldStr = attributes.getValue("finalState");
			if (hiddenFieldStr != null && hiddenFieldStr.equals("true"))
			{
				target.setAsFinalState();
			}
		}
		else if (state != null && tagName.equals("event"))
		{
			event = new Event();
			state.addEvent(event);
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String tag)
	{
		tagName = tag.trim();
		valueNode = null;

		if (tagName.equals("module")) 
		{
			moduleName = null;
		}
		else if (tagName.equals("useCase"))
		{
			useCase.setModuleName(moduleName);
			useCase = null;
		}
		else if (tagName.equals("view"))
		{
			view = null;
		}
		else if (tagName.equals("state"))
		{
			state = null;
		}
		else if (tagName.equals("controller"))
		{
			controller = null;
		}
		else if (tagName.equals("method") || tagName.equals("action"))
		{
			method = null;
		}
		else if (tagName.equals("variable"))
		{
			table = null;
		}
		else if (tagName.equals("return"))
		{
			hasMethodReturn = false;
		}
		else if (tagName.equals("parameter"))
		{
			parameter = null;
		}
		else if (tagName.equals("target"))
		{
			target = null;
		}
		else if (tagName.equals("event"))
		{
			event = null;
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
			if (useCase != null)
			{
				if (controller != null)
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
					else if (tagName.equals("name"))
					{
						controller.setName(valueNode);
					}
					else if (tagName.equals("implementationName"))
					{
						controller.setImplementationName(valueNode);
					}
				}
				else if (view != null)
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
						else if (target != null)
						{
							if (tagName.equals("name"))
							{
								target.setName(valueNode);
							}
							else if (tagName.equals("hyperlinkApplicationName"))
							{
								target.setHyperlinkApplicationName(valueNode);
							}
							else if (tagName.equals("hyperlinkModulo"))
							{
								target.setHyperlinkModulo(valueNode);
							}
							else if (tagName.equals("hyperlink"))
							{
								target.setHyperlink(valueNode);
							}
						}
						else if (tagName.equals("name"))
						{
							method.setName(valueNode);
						}
					}
					else if (table != null)
					{
						if (tagName.equals("name"))
						{
							table.setName(valueNode);
						}
						else if (tagName.equals("column"))
						{
							table.addColumn(valueNode);
						}
					}
					else if (tagName.equals("name"))
					{
						view.setName(valueNode);
					}
				}
				else if (state != null)
				{
					if (event != null)
					{
						if (tagName.equals("name"))
						{
							event.setName(valueNode);
						}
					}
					else if (target != null)
					{
						if (tagName.equals("name"))
						{
							target.setName(valueNode);
						}
						else if (tagName.equals("hyperlinkApplicationName"))
						{
							target.setHyperlinkApplicationName(valueNode);
						}
						else if (tagName.equals("hyperlinkModulo"))
						{
							target.setHyperlinkModulo(valueNode);
						}
						else if (tagName.equals("hyperlink"))
						{
							target.setHyperlink(valueNode);
						}
					}
					else if (tagName.equals("name"))
					{
						state.setName(valueNode);
					}
				}
				else if (tagName.equals("activityDiagram"))
				{
					useCase.setActivityDiagram(valueNode);
				}
				else if (tagName.equals("name"))
				{
					useCase.setName(valueNode);
				}
			}
			else if (tagName.equals("name"))
			{
				moduleName = valueNode;
			}
		}
	}
}
