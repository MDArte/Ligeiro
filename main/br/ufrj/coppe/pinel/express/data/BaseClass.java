package br.ufrj.coppe.pinel.express.data;

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

	private String packageName;

	private String module;

	private String extendsClass;

	private Collection<Attribute> attributes;

	private Collection<Method> methods;

	/**
	 * Default constructor.
	 */
	public BaseClass ()
	{
		attributes = new ArrayList<Attribute>();
		methods = new ArrayList<Method>();
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
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#getName()
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#setName(java.lang.String)
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
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#getPackageName()
	 */
//	public String getPackageName()
//	{
//		return packageName;
//	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#setPackageName(java.lang.String)
	 */
	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#getModule()
	 */
	public String getModule()
	{
		return module;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#setModule(java.lang.String)
	 */
	public void setModule(String module)
	{
		this.module = module;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#getExtendsClass()
	 */
	public String getExtendsClass()
	{
		return extendsClass;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#setExtendsClass(java.lang.String)
	 */
	public void setExtendsClass(String extendsClass)
	{
		this.extendsClass = extendsClass;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#getAttributes()
	 */
	public Collection<Attribute> getAttributes()
	{
		return attributes;
	}

	/**
	 * @param attributes
	 */
	public void setAttributes(Collection<Attribute> attributes)
	{
		this.attributes = attributes;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#addAttribute(br.ufrj.coppe.pinel.express.data.Attribute)
	 */
	public void addAttribute(Attribute attribute)
	{
		attributes.add(attribute);
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#getMethods()
	 */
	public Collection<Method> getMethods()
	{
		return methods;
	}

	/**
	 * @param methods
	 */
	public void setMethods(Collection<Method> methods)
	{
		this.methods = methods;
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#addMethod(br.ufrj.coppe.pinel.express.data.Method)
	 */
	public void addMethod(Method method)
	{
		methods.add(method);
	}

	/**
	 * @see br.ufrj.coppe.pinel.express.data.IBaseClass#getMethodsSignatures()
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
