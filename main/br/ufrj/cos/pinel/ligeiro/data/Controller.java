package br.ufrj.cos.pinel.ligeiro.data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Roque Pinel
 *
 */
public class Controller extends BaseClass
{
	private UseCase useCase;

	/**
	 * Default constructor.
	 */
	public Controller()
	{
		super();
	}

	/**
	 * @param name The class name
	 */
	public Controller(String name)
	{
		this();
		super.setName(name);
	}

	/**
	 * @return the useCase
	 */
	public UseCase getUseCase()
	{
		return useCase;
	}

	/**
	 * @param useCase the useCase to set
	 */
	public void setUseCase(UseCase useCase)
	{
		this.useCase = useCase;
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
