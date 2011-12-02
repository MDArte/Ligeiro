package br.ufrj.cos.pinel.ligeiro.data;

/**
 * Association.
 * 
 * @author Roque Pinel
 *
 */
public class Association
{
	private static class TYPE
	{
		public static int NONE = -1;
		public static int ONE_TO_ONE = 0;
		public static int ONE_TO_MANY = 1;
		public static int MANY_TO_ONE = 2;
		public static int MANY_TO_MANY = 3;
	}

	private String name;

	private int type;

	/**
	 * Default constructor.
	 */
	public Association()
	{
		this.type = TYPE.NONE;
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
	 * Defines the type as One to One.
	 */
	public void setAsOneToOne()
	{
		this.type = TYPE.ONE_TO_ONE;
	}

	/**
	 * @return if it's One to One
	 */
	public boolean isOneToOne()
	{
		return this.type == TYPE.ONE_TO_ONE;
	}

	/**
	 * Defines the type as One to Many.
	 */
	public void setAsOneToMany()
	{
		this.type = TYPE.ONE_TO_MANY;
	}

	/**
	 * @return if it's One to Many
	 */
	public boolean isOneToMany()
	{
		return this.type == TYPE.ONE_TO_MANY;
	}

	/**
	 * Defines the type as Many to One.
	 */
	public void setAsManyToOne()
	{
		this.type = TYPE.MANY_TO_ONE;
	}

	/**
	 * @return if it's Many to One
	 */
	public boolean isManyToOne()
	{
		return this.type == TYPE.MANY_TO_ONE;
	}

	/**
	 * Defines the type as Many To Many.
	 */
	public void setAsManyToMany()
	{
		this.type = TYPE.MANY_TO_MANY;
	}

	/**
	 * @return if it's Many To Many
	 */
	public boolean isManyToMany()
	{
		return this.type == TYPE.MANY_TO_MANY;
	}
}
