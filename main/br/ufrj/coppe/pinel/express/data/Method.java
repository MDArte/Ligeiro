package br.ufrj.coppe.pinel.express.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Roque Pinel
 *
 */
public class Method
{
	private String name;

	private String implementationName;

	private String returnType;

	private Collection<Parameter> parameters;

	private Collection<Dependency> dependencies;

	private boolean modifier;

	private boolean lookupGrid;
	private boolean tableLink;

	private State target;

	/**
	 * Default constructor.
	 */
	public Method()
	{
		this.modifier = false;

		parameters = new ArrayList<Parameter>();
		dependencies = new ArrayList<Dependency>();
	}

	/**
	 * @param name the method's name
	 */
	public Method(String name)
	{
		this();

		this.name = name;
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
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#getImplementationName()
	 */
	public String getImplementationName()
	{
		return implementationName;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#setImplementationName(java.lang.String)
	 */
	public void setImplementationName(String implementationName)
	{
		this.implementationName = implementationName;
	}

	/**
	 * @return the returnType
	 */
	public String getReturnType()
	{
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType)
	{
		this.returnType = returnType;
	}

	/**
	 * @return the parameters
	 */
	public Collection<Parameter> getParameters()
	{
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Collection<Parameter> parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * @param parameter the parameter to add
	 */
	public void addParameter(Parameter parameter)
	{
		parameters.add(parameter);
	}

	/**
	 * @return the dependencies
	 */
	public Collection<Dependency> getDependencies()
	{
		return dependencies;
	}

	/**
	 * @param parameter the parameter to add
	 */
	public void addDependency(Dependency dependency)
	{
		dependencies.add(dependency);
	}

	/**
	 * Defines as Modifier.
	 */
	public void setAsModifier()
	{
		modifier = true;
	}

	/**
	 * @return if it's Modifier
	 */
	public boolean isModifier()
	{
		return modifier;
	}

	/**
	 * Defines as LookupGrid.
	 */
	public void setAsLookupGrid()
	{
		lookupGrid = true;
	}

	/**
	 * @return if it's LookupGrid
	 */
	public boolean isLookupGrid()
	{
		return lookupGrid;
	}

	/**
	 * Defines as TableLink.
	 */
	public void setAsTableLink()
	{
		tableLink = true;
	}

	/**
	 * @return if it's TableLink
	 */
	public boolean isTableLink()
	{
		return tableLink;
	}

	/**
	 * @return the method's signature
	 */
	public String getSignature()
	{
		String methodName;

		if (getImplementationName() != null)
			methodName = getImplementationName();
		else
			methodName = getName();

		String signature = methodName + "(";

		int i = 0;
		for (Parameter parameter : getParameters())
		{
			signature += parameter.getType();

			if (++i < getParameters().size())
				signature += ", ";
		}

		return signature += ")";
	}

	/**
	 * @return the target
	 */
	public State getTarget()
	{
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(State target)
	{
		this.target = target;
	}
}
