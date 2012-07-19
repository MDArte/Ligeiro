package br.ufrj.cos.pinel.ligeiro.data;

/**
 * @author Roque Pinel
 *
 */
public class Dependency
{
	private static int TYPE_CLASS = 1;
	private static int TYPE_FEATURE = 2;

	private String value;

	private int type;

	/**
	 * @param value the Dependency's value
	 */
	public Dependency(String value)
	{
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @return if it's class
	 */
	public boolean isClass()
	{
		return type == TYPE_CLASS;
	}

	/**
	 * Defines as Class.
	 */
	public void setAsClass()
	{
		type = TYPE_CLASS;
	}

	/**
	 * @return if it's feature
	 */
	public boolean isFeature()
	{
		return type == TYPE_FEATURE;
	}

	/**
	 * Defines as Feature.
	 */
	public void setAsFeature()
	{
		type = TYPE_FEATURE;
	}
}
