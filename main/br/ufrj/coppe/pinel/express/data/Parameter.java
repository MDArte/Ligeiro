package br.ufrj.coppe.pinel.express.data;

/**
 * @author Roque Pinel
 *
 */
public class Parameter
{
	private String name;

	private String type;

	private boolean readOnly;
	private boolean plainText;
	private boolean hiddenField;

	/**
	 * Default constructor.
	 */
	public Parameter()
	{
		this.readOnly = false;
		this.plainText = false;
		this.hiddenField = false;
	}

	/**
	 * @param name the parameter's name
	 * @param type the parameter's type
	 */
	public Parameter(String name, String type)
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
	 * Defines as ReadOnly.
	 */
	public void setAsReadOnly()
	{
		readOnly = true;
	}

	/**
	 * @return if it's ReadOnly
	 */
	public boolean isReadOnly()
	{
		return readOnly;
	}

	/**
	 * Defines as PlainText.
	 */
	public void setAsPlainText()
	{
		plainText = true;
	}

	/**
	 * @return if it's PlainText
	 */
	public boolean isPlainText()
	{
		return plainText;
	}

	/**
	 * Defines as HiddenField.
	 */
	public void setAsHiddenField()
	{
		hiddenField = true;
	}

	/**
	 * @return if it's HiddenField
	 */
	public boolean isHiddenField()
	{
		return hiddenField;
	}
}
