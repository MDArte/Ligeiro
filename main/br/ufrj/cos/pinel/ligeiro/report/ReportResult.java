package br.ufrj.cos.pinel.ligeiro.report;


/**
 * @author Roque Pinel
 *
 */
public class ReportResult
{
	private String element;

	private String type;

	private int det;

	private int ret_ftr;

	private String complexity;

	private int complexityValue;

	/**
	 * Default contructor.
	 */
	public ReportResult()
	{
		// empty
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
	 * @return the det
	 */
	public int getDet()
	{
		return det;
	}

	/**
	 * @param det the det to set
	 */
	public void setDet(int det)
	{
		this.det = det;
	}

	/**
	 * @return the ret_ftr
	 */
	public int getRet_ftr()
	{
		return ret_ftr;
	}

	/**
	 * @param ret_ftr the ret_ftr to set
	 */
	public void setRet_ftr(int ret_ftr)
	{
		this.ret_ftr = ret_ftr;
	}

	/**
	 * @return the complexity
	 */
	public String getComplexity()
	{
		return complexity;
	}

	/**
	 * @param complexity the complexity to set
	 */
	public void setComplexity(String complexity)
	{
		this.complexity = complexity;
	}

	/**
	 * @return the complexityValue
	 */
	public int getComplexityValue()
	{
		return complexityValue;
	}

	/**
	 * @param complexityValue the complexityValue to set
	 */
	public void setComplexityValue(int complexityValue)
	{
		this.complexityValue = complexityValue;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return element;
	}
}
