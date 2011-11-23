package br.ufrj.cos.pinel.ligeiro.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Roque Pinel
 *
 */
public class Service extends BaseClass
{
	private Collection<String> otherNames;

	private boolean webService;

	private static class FP_TYPE
	{
		public static int NONE = -1;
		public static int EI = 0;
		public static int EQ1 = 1;
		public static int EQ2 = 2;
		public static int EO = 3;
	}

	private int fpType;

	/**
	 * Default constructor.
	 */
	public Service()
	{
		super();

		otherNames = new ArrayList<String>();

		this.fpType = FP_TYPE.NONE;
	}

	/**
	 * @param name The class name
	 */
	public Service(String name)
	{
		this();
		super.setName(name);
	}

	/**
	 * @return the otherNames
	 */
	public Collection<String> getOtherNames()
	{
		return otherNames;
	}

	/**
	 * @param otherNames the otherNames to set
	 */
	public void setOtherNames(Collection<String> otherNames)
	{
		this.otherNames = otherNames;
	}

	/**
	 * @param otherName the otherName to add
	 */
	public void addOtherName(String otherName)
	{
		otherNames.add(otherName);
	}

	/**
	 * Defines as a WebService.
	 */
	public void setAsWebService()
	{
		webService = true;
	}

	/**
	 * @return if it's a WebService
	 */
	public boolean isWebService()
	{
		return webService;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.BaseClass#getMethodsSignatures()
	 */
	public Set<String> getMethodsSignatures()
	{
		Set<String> methodSignatures = new HashSet<String>();

		for (Method method : getMethods())
		{
			String signature = getImplementationName() + "." + method.getSignature();

			methodSignatures.add(signature);
		}

		return methodSignatures;
	}

	/**
	 * Defines FP Type as EI.
	 */
	public void setAsEI()
	{
		this.fpType = FP_TYPE.EI;
	}

	/**
	 * @return if it's EI
	 */
	public boolean isEI()
	{
		return this.fpType == FP_TYPE.EI;
	}

	/**
	 * Defines FP Type as EO.
	 */
	public void setAsEO()
	{
		this.fpType = FP_TYPE.EO;
	}

	/**
	 * @return if it's EO
	 */
	public boolean isEO()
	{
		return this.fpType == FP_TYPE.EO;
	}

	/**
	 * Defines FP Type as EQ1.
	 */
	public void setAsEQ1()
	{
		this.fpType = FP_TYPE.EQ1;
	}

	/**
	 * @return if it's EQ1
	 */
	public boolean isEQ1()
	{
		return this.fpType == FP_TYPE.EQ1;
	}

	/**
	 * Defines FP Type as EQ2.
	 */
	public void setAsEQ2()
	{
		this.fpType = FP_TYPE.EQ2;
	}

	/**
	 * @return if it's EQ2
	 */
	public boolean isEQ2()
	{
		return this.fpType == FP_TYPE.EQ2;
	}

	/**
	 * @return if it's EQ
	 */
	public boolean isEQ()
	{
		return this.fpType == FP_TYPE.EQ1 || this.fpType == FP_TYPE.EQ2;
	}
}
