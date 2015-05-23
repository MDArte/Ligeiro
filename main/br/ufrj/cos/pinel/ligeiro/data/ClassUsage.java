package br.ufrj.cos.pinel.ligeiro.data;

/**
 * @author Roque Pinel
 *
 */
public class ClassUsage
{
	public static final String CLASS = "class";
	public static final String ENTITY = "entity";
	public static final String SERVICE = "service";
	public static final String USE_CASE = "usecase";

	private String type;
	private String element;
	private String method;

	private String dependencyType;
	private String dependency;

	public ClassUsage()
	{
		// empty
	}

	public ClassUsage(String type, String element, String method, String dependencyType, String dependency)
	{
		this();

		this.type = type;
		this.element = element;
		this.method = method;
		this.dependencyType = dependencyType;
		this.dependency = dependency;
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
	 * @return the element
	 */
	public String getElement()
	{
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public void setElement(String element)
	{
		this.element = element;
	}

	/**
	 * @return the method
	 */
	public String getMethod()
	{
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method)
	{
		this.method = method;
	}

	/**
	 * @return the dependencyType
	 */
	public String getDependencyType()
	{
		return dependencyType;
	}

	/**
	 * @param dependencyType the dependencyType to set
	 */
	public void setDependencyType(String service)
	{
		this.dependencyType = service;
	}

	/**
	 * @return the dependency
	 */
	public String getDependency()
	{
		return dependency;
	}

	/**
	 * @param dependency the dependency to set
	 */
	public void setDependency(String entity)
	{
		this.dependency = entity;
	}

	public String getElementLabel()
	{
		int index = element.lastIndexOf(".");

		if (index < 0)
			return element;

		return element.substring(index + 1);
	}

	public String getDependencyLabel()
	{
		int index = dependency.lastIndexOf(".");

		if (index < 0)
			return dependency;

		return dependency.substring(index + 1);
	}
}
