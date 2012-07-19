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

	/**
	 * Default constructor.
	 */
	public Service()
	{
		super();

		otherNames = new ArrayList<String>();
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
}
