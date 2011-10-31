package br.ufrj.cos.pinel.ligeiro.data;

/**
 * @author Roque Pinel
 *
 */
public class Entity extends BaseClass
{
	private boolean internal;

	/**
	 * Default constructor.
	 */
	public Entity()
	{
		super();

		this.internal = false;
	}

	/**
	 * @param name The class name
	 */
	public Entity(String name)
	{
		this();

		super.setName(name);
	}

	/**
	 * Defines as internal data.
	 */
	public void setAsInternal()
	{
		internal = true;
	}

	/**
	 * @return if it's an internal data
	 */
	public boolean isInternal()
	{
		return internal;
	}
}
