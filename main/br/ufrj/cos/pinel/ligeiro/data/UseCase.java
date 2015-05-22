package br.ufrj.cos.pinel.ligeiro.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Roque Pinel
 *
 */
public class UseCase
{
	private String name;

	private String moduleName;

	private String activityDiagram;

	private Controller controller;

	private Collection<View> views;

	private Collection<State> states;

	// can be a View or State
	private Object first;

	/**
	 * Default contructor.
	 */
	public UseCase()
	{
		views = new ArrayList<View>();
		states = new ArrayList<State>();
	}

	/**
	 * @param name The use case name
	 */
	public UseCase(String name)
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
	 * @return the moduleName
	 */
	public String getModuleName()
	{
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	/**
	 * @return the activityDiagram
	 */
	public String getActivityDiagram()
	{
		return activityDiagram;
	}

	/**
	 * @param activityDiagram the activityDiagram to set
	 */
	public void setActivityDiagram(String activityDiagram)
	{
		this.activityDiagram = activityDiagram;
	}

	/**
	 * @return the controller
	 */
	public Controller getController()
	{
		return controller;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(Controller controller)
	{
		this.controller = controller;
	}

	public Collection<View> getViews()
	{
		return views;
	}

	public void addView(View view)
	{
		views.add(view);
	}

	public Collection<State> getStates()
	{
		return states;
	}

	public void addState(State state)
	{
		states.add(state);
	}

	/**
	 * @return the first
	 */
	public Object getFirst()
	{
		return first;
	}

	/**
	 * @param first the first to set
	 */
	public void setFirst(Object first)
	{
		this.first = first;
	}

	/**
	 * @return if the first is a view
	 */
	public boolean isFirstView()
	{
		return first instanceof View;
	}

	/**
	 * @return if the first is a state
	 */
	public boolean isFirstState()
	{
		return first instanceof State;
	}
}
