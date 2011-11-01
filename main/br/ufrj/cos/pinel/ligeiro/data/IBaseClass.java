package br.ufrj.cos.pinel.ligeiro.data;

import java.util.Collection;
import java.util.Set;

/**
 * @author Roque Pinel
 *
 */
public interface IBaseClass
{
	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param nane the name to set
	 */
	public void setName(String name);

	/**
	 * @return the implementationName
	 */
	public String getImplementationName();

	/**
	 * @param implementationName the implementationName to set
	 */
	public void setImplementationName(String implementationName);

	/**
	 * @return the packageName
	 */
//	public String getPackageName();

	/**
	 * @param packageName the packageName to set
	 */
//	public void setPackageName(String packageName);

	/**
	 * @return the moduleName
	 */
	public String getModuleName();

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName);

	/**
	 * @return the extendsClass
	 */
	public String getExtendsClass();

	/**
	 * @param extendsClass the extendsClass to set
	 */
	public void setExtendsClass(String extendsClass);

	/**
	 * @return the attributes
	 */
	public Collection<Attribute> getAttributes();

	/**
	 * @param attribute the attribute to add
	 */
	public void addAttribute(Attribute attribute);

	/**
	 * @return the methods
	 */
	public Collection<Method> getMethods();

	/**
	 * @param method the method to add
	 */
	public void addMethod(Method method);

	/**
	 * @return the <code>Set</code> of methods' signatures
	 */
	public Set<String> getMethodsSignatures();
}