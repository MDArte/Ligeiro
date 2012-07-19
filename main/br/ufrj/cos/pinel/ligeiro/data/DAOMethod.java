package br.ufrj.cos.pinel.ligeiro.data;

/**
 * DAO Method
 * 
 * @author Roque Pinel
 *
 */
public class DAOMethod
{
	private String name;

	private boolean modifier;

	private boolean delete;

	public DAOMethod()
	{
		// empty
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
	 * Defines as Modifier.
	 */
	public void setAsModifier()
	{
		this.modifier = true;
	}

	/**
	 * @return if it's Modifier
	 */
	public boolean isModifier()
	{
		return modifier;
	}

	/**
	 * Defines as Delete.
	 */
	public void setAsDelete()
	{
		this.delete = true;
	}

	/**
	 * @return the delete
	 */
	public boolean isDelete()
	{
		return delete;
	}
}
