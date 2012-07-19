package br.ufrj.cos.pinel.ligeiro.data;

import java.util.Collection;

/**
 * @author Roque Pinel
 *
 */
public class Entity extends BaseClass
{
	private static class FP_TYPE
	{
		public static int NONE = -1;
		public static int ILF = 0;
		public static int EIF = 1;
	}

	private int fpType;

	private DAO dao;

	/**
	 * Default constructor.
	 */
	public Entity()
	{
		super();

		this.fpType = FP_TYPE.NONE;
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
		fpType = FP_TYPE.ILF;
	}

	/**
	 * Defines as external data.
	 */
	public void setAsExternal()
	{
		fpType = FP_TYPE.ILF;
	}

	/**
	 * @return if it has no type.
	 */
	public boolean isNotDefined()
	{
		return fpType == FP_TYPE.NONE;
	}

	/**
	 * @return if it's an internal data
	 */
	public boolean isInternal()
	{
		return fpType == FP_TYPE.ILF;
	}

	/**
	 * @return if it's an external data
	 */
	public boolean isExternal()
	{
		return fpType == FP_TYPE.EIF;
	}

	/**
	 * @return the dao
	 */
	public DAO getDao()
	{
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(DAO dao)
	{
		this.dao = dao;
	}
}
