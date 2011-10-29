package br.ufrj.coppe.pinel.express.data;

/**
 * @author Roque Pinel
 *
 */
public class Event
{
	private String name;

	/**
	 * Default constructor.
	 */
	public Event()
	{
		// empty
	}

	/**
	 * @param name the method's name
	 */
	public Event(String name)
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
}
