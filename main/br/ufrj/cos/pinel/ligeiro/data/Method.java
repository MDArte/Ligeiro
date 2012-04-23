package br.ufrj.cos.pinel.ligeiro.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

	private static class FP_TYPE
	{
		public static int NONE = -1;
		public static int EI = 0;
		public static int EQ1 = 1;
		public static int EQ2 = 2;
		public static int EO = 3;
	}

	private int fpType;

	private Set<String> countedEntities;

	/**
	 * Default constructor.
	 */
	public Method()
	{
		this.modifier = false;

		parameters = new ArrayList<Parameter>();
		dependencies = new ArrayList<Dependency>();

		this.fpType = FP_TYPE.NONE;

		this.countedEntities = new HashSet<String>();
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
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#getImplementationName()
	 */
	public String getImplementationName()
	{
		return implementationName;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#setImplementationName(java.lang.String)
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
	 * @param parameter the parameter to be added
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
		this.tableLink = true;
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

	/**
	 * @return the countedEntities
	 */
	public Set<String> getCountedEntities()
	{
		return countedEntities;
	}

	/**
	 * @param countedEntities the countedEntities to set
	 */
	public void setCountedEntities(Set<String> countedEntities)
	{
		this.countedEntities = countedEntities;
	}
}
