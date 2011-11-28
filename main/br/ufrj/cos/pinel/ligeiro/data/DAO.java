package br.ufrj.cos.pinel.ligeiro.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The DAO of an Entity.
 * 
 * @author Roque Pinel
 *
 */
public class DAO
{
	private String name;

	private String implementationName;

	private Collection<DAOMethod> methods;

	private Entity entity;

	/**
	 * Default constructor.
	 */
	public DAO()
	{
		methods = new ArrayList<DAOMethod>();
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
	 * @return the implementationName
	 */
	public String getImplementationName()
	{
		return implementationName;
	}

	/**
	 * @param implementationName the implementationName to set
	 */
	public void setImplementationName(String implementationName)
	{
		this.implementationName = implementationName;
	}

	/**
	 * @return the methods
	 */
	public Collection<DAOMethod> getMethods()
	{
		return methods;
	}

	/**
	 * @param method the method to be added
	 */
	public void addMethod(DAOMethod method)
	{
		this.methods.add(method);
	}

	/**
	 * @return the entity
	 */
	public Entity getEntity()
	{
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
}
