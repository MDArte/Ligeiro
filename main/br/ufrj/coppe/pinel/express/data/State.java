package br.ufrj.coppe.pinel.express.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Roque Pinel
 *
 */
public class State
{
	private String name;

	private String targetName;

	private String hyperlinkApplicationName;
	private String hyperlinkModulo;
	private String hyperlink;

	private boolean finalState;

	Collection<Event> events;

	private State target;

	/**
	 * Default constructor.
	 */
	public State()
	{
		this.finalState = false;

		events = new ArrayList<Event>();
	}

	/**
	 * @param name the method's name
	 */
	public State(String name)
	{
		this();

		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the targetName
	 */
	public String getTargetName()
	{
		return targetName;
	}

	/**
	 * @param targetName the targetName to set
	 */
	public void setTargetName(String targetName)
	{
		this.targetName = targetName;
	}

	/**
	 * @return the hyperlinkApplicationName
	 */
	public String getHyperlinkApplicationName()
	{
		return hyperlinkApplicationName;
	}

	/**
	 * @param hyperlinkApplicationName the hyperlinkApplicationName to set
	 */
	public void setHyperlinkApplicationName(String hyperlinkApplicationName)
	{
		this.hyperlinkApplicationName = hyperlinkApplicationName;
	}

	/**
	 * @return the hyperlinkModulo
	 */
	public String getHyperlinkModulo()
	{
		return hyperlinkModulo;
	}

	/**
	 * @param hyperlinkModulo the hyperlinkModulo to set
	 */
	public void setHyperlinkModulo(String hyperlinkModulo)
	{
		this.hyperlinkModulo = hyperlinkModulo;
	}

	/**
	 * @return the hyperlink
	 */
	public String getHyperlink()
	{
		return hyperlink;
	}

	/**
	 * @param hyperlink the hyperlink to set
	 */
	public void setHyperlink(String hyperlink)
	{
		this.hyperlink = hyperlink;
	}

	/**
	 * Defines as FinalState.
	 */
	public void setAsFinalState()
	{
		finalState = true;
	}

	/**
	 * @return if it's FinalState
	 */
	public boolean isFinalState()
	{
		return finalState;
	}

	/**
	 * @return the events
	 */
	public Collection<Event> getEvents()
	{
		return events;
	}

	/**
	 * @param event the event to added
	 */
	public void addEvent(Event event)
	{
		events.add(event);
	}

	/**
	 * @return the target
	 */
	public State getTarget()
	{
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(State target)
	{
		this.target = target;
	}
}
