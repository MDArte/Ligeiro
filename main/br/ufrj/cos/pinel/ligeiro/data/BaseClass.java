package br.ufrj.cos.pinel.ligeiro.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Roque Pinel
 *
 */
public class BaseClass implements IBaseClass
{
	private String name;

	private String implementationName;

//	private String packageName;

	private String moduleName;

	private String extendsClass;

	private Collection<Attribute> attributes;

	private Collection<Method> methods;

	private Collection<Association> associations;

	/**
	 * Default constructor.
	 */
	public BaseClass ()
	{
		attributes = new ArrayList<Attribute>();
		methods = new ArrayList<Method>();
		associations = new ArrayList<Association>();
	}

	/**
	 * @param name the class name
	 */
	public BaseClass (String name)
	{
		this();

		this.name = name;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#getName()
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#setName(java.lang.String)
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
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#getPackageName()
	 */
//	public String getPackageName()
//	{
//		return packageName;
//	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#setPackageName(java.lang.String)
	 */
//	public void setPackageName(String packageName)
//	{
//		this.packageName = packageName;
//	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#getModuleName()
	 */
	public String getModuleName()
	{
		return moduleName;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#setModuleName(java.lang.String)
	 */
	public void setModuleName(String module)
	{
		this.moduleName = module;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#getExtendsClass()
	 */
	public String getExtendsClass()
	{
		return extendsClass;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#setExtendsClass(java.lang.String)
	 */
	public void setExtendsClass(String extendsClass)
	{
		this.extendsClass = extendsClass;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#getAttributes()
	 */
	public Collection<Attribute> getAttributes()
	{
		return attributes;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#addAttribute(br.ufrj.cos.pinel.ligeiro.data.Attribute)
	 */
	public void addAttribute(Attribute attribute)
	{
		attributes.add(attribute);
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#getMethods()
	 */
	public Collection<Method> getMethods()
	{
		return methods;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#addMethod(br.ufrj.cos.pinel.ligeiro.data.Method)
	 */
	public void addMethod(Method method)
	{
		methods.add(method);
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#getAssociations()
	 */
	public Collection<Association> getAssociations()
	{
		return associations;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#addAssociation(br.ufrj.cos.pinel.ligeiro.data.Association)
	 */
	public void addAssociation(Association association)
	{
		associations.add(association);
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.IBaseClass#getMethodsSignatures()
	 */
	public Set<String> getMethodsSignatures()
	{
		Set<String> methodSignatures = new HashSet<String>();

		for (Method method : getMethods())
		{
			String signature = getName() + "." + method.getSignature();

			methodSignatures.add(signature);
		}

		return methodSignatures;
	}
}
