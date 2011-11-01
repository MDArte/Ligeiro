package br.ufrj.cos.pinel.ligeiro.report;

/**
 * Report of files loaded.
 * 
 * @author Roque Pinel
 *
 */
public class LoadReport
{
	private String fileName;

	private String type;

	private int elementsRead;

	/**
	 * @param fileName the name of the file
	 */
	public LoadReport(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
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
	 * @return the elementsRead
	 */
	public int getElementsRead()
	{
		return elementsRead;
	}

	/**
	 * @param elementsRead the elementsRead to set
	 */
	public void setElementsRead(int elementsRead)
	{
		this.elementsRead = elementsRead;
	}
}
