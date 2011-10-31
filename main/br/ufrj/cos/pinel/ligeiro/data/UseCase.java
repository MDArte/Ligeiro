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

	private Controller controller;

	private Collection<View> views;

	private Collection<State> states;

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
}
