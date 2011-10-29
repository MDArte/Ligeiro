package br.ufrj.coppe.pinel.express.data;

/**
 * @author Roque Pinel
 *
 */
public class Attribute
{
	private String name;

	private String type;

	private boolean identifier;

	/**
	 * Default constructor.
	 */
	public Attribute ()
	{
		this.identifier = false;
	}

	/**
	 * @param name the attribute's name
	 * @param type the attribute's type
	 */
	public Attribute (String name, String type)
	{
		this();

		this.name = name;
		this.type = type;
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
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Defines as Identifier.
	 */
	public void setAsIdentifier()
	{
		identifier = true;
	}

	/**
	 * @return if it's Identifier
	 */
	public boolean isIdentifier()
	{
		return identifier;
	}
}
