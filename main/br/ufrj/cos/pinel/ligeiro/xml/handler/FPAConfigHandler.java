package br.ufrj.cos.pinel.ligeiro.xml.handler;

import org.xml.sax.Attributes;

import br.ufrj.cos.pinel.ligeiro.common.FPAConfig;

/**
 * The listener used to read entities from a XML.
 * 
 * @author Roque Pinel
 * 
 */
public class FPAConfigHandler extends GenericHandler
{
	private FPAConfig fpaConfig = new FPAConfig();

	private String tagName = null;
	private String valueNode = null;

	private static class FPA_TYPE
	{
		public static int NONE = -1;
		public static int ILF = 0;
		public static int EIF = 1;
		public static int EI = 2;
		public static int EQ = 3;
		public static int EO = 4;
	}
	private int fpaType = FPA_TYPE.NONE;

	private static class TABLE_TYPE
	{
		public static int NONE = -1;
		public static int COMPLEXITY = 0;
		public static int COMPLEXITY_VALUES = 1;
	}
	private int tableType = TABLE_TYPE.NONE;

	private int rows = -1;
	private int columns = -1;

	private int size = -1;

	private int retMin = -1;
	private int retMax = -1;
	private int detMin = -1;
	private int detMax = -1;
	private int ftrMin = -1;
	private int ftrMax = -1;

	private String type = null;

	private int counter = -1;

	/**
	 * @return the FPA Configuration read.
	 */
	public FPAConfig getFPAConfig()
	{
		return fpaConfig;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String tag, Attributes attributes)
	{
		tagName = tag.trim();

		if (tagName.equals("internalLogicalFile"))
		{
			fpaType = FPA_TYPE.ILF;
		}
		else if (tagName.equals("externalInterfaceFile"))
		{
			fpaType = FPA_TYPE.EIF;
		}
		else if (tagName.equals("externalInput"))
		{
			fpaType = FPA_TYPE.EI;
		}
		else if (tagName.equals("externalOutput"))
		{
			fpaType = FPA_TYPE.EO;
		}
		else if (tagName.equals("externalInquiry"))
		{
			fpaType = FPA_TYPE.EQ;
		}
		else if (fpaType != FPA_TYPE.NONE && tagName.equals("complexity"))
		{
			tableType = TABLE_TYPE.COMPLEXITY;

			rows = Integer.parseInt(attributes.getValue("rows"));
			columns = Integer.parseInt(attributes.getValue("columns"));

			if (fpaType == FPA_TYPE.ILF)
			{
				fpaConfig.createIFLComplexity(rows, columns);
			}
			else if (fpaType == FPA_TYPE.EIF)
			{
				fpaConfig.createEIFComplexity(rows, columns);
			}
			else if (fpaType == FPA_TYPE.EI)
			{
				fpaConfig.createEIComplexity(rows, columns);
			}
			else if (fpaType == FPA_TYPE.EO)
			{
				fpaConfig.createEOComplexity(rows, columns);
			}
			else if (fpaType == FPA_TYPE.EQ)
			{
				fpaConfig.createEQComplexity(rows, columns);
			}
		}
		else if (fpaType != FPA_TYPE.NONE && tagName.equals("complexityValues"))
		{
			tableType = TABLE_TYPE.COMPLEXITY_VALUES;

			size = Integer.parseInt(attributes.getValue("size"));

			if (fpaType == FPA_TYPE.ILF)
			{
				fpaConfig.createIFLComplexityValues(size);
			}
			else if (fpaType == FPA_TYPE.EIF)
			{
				fpaConfig.createEIFComplexityValues(size);
			}
			else if (fpaType == FPA_TYPE.EI)
			{
				fpaConfig.createEIComplexityValues(size);
			}
			else if (fpaType == FPA_TYPE.EO)
			{
				fpaConfig.createEOComplexityValues(size);
			}
			else if (fpaType == FPA_TYPE.EQ)
			{
				fpaConfig.createEQComplexityValues(size);
			}
		}
		else if ((fpaType == FPA_TYPE.ILF || fpaType == FPA_TYPE.EIF) && tableType == TABLE_TYPE.COMPLEXITY && tagName.equals("value"))
		{
			// <value retMin="1" retMax="1" detMin="1" detMax="19">low</value>
			retMin = Integer.parseInt(attributes.getValue("retMin"));

			if (attributes.getValue("retMax").equals("*"))
				retMax = FPAConfig.INFINITY;
			else
				retMax = Integer.parseInt(attributes.getValue("retMax"));

			detMin = Integer.parseInt(attributes.getValue("detMin"));

			if (attributes.getValue("detMax").equals("*"))
				detMax = FPAConfig.INFINITY;
			else
				detMax = Integer.parseInt(attributes.getValue("detMax"));

			counter++;
		}
		else if ((fpaType == FPA_TYPE.EI || fpaType == FPA_TYPE.EO || fpaType == FPA_TYPE.EQ) && tableType == TABLE_TYPE.COMPLEXITY && tagName.equals("value"))
		{
			// <value ftrMin="0" ftrMax="1" detMin="1" detMax="4">low</value>
			ftrMin = Integer.parseInt(attributes.getValue("ftrMin"));

			if (attributes.getValue("ftrMax").equals("*"))
				ftrMax = FPAConfig.INFINITY;
			else
				ftrMax = Integer.parseInt(attributes.getValue("ftrMax"));

			detMin = Integer.parseInt(attributes.getValue("detMin"));

			if (attributes.getValue("detMax").equals("*"))
				detMax = FPAConfig.INFINITY;
			else
				detMax = Integer.parseInt(attributes.getValue("detMax"));

			counter++;
		}
		else if (fpaType != FPA_TYPE.NONE && tableType == TABLE_TYPE.COMPLEXITY_VALUES && tagName.equals("value"))
		{
			// <value type="low">7</value>
			type = attributes.getValue("type");

			counter++;
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String tag)
	{
		tagName = tag.trim();
		valueNode = null;

		if (tagName.equals("internalLogicalFile")
			|| tagName.equals("externalInterfaceFile")
			|| tagName.equals("externalInput")
			|| tagName.equals("externalOutput")
			|| tagName.equals("externalInquiry"))
		{
			fpaType = FPA_TYPE.NONE;
		}
		else if (tagName.equals("complexity") || tagName.equals("complexityValues"))
		{
			tableType = TABLE_TYPE.NONE;
			rows = -1;
			columns = -1;
			size = -1;
			counter = -1;
		}
		else if (tagName.equals("value"))
		{
			retMin = -1;
			retMax = -1;
			detMin = -1;
			detMax = -1;
			ftrMin = -1;
			ftrMax = -1;
			type = null;
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int length)
	{
		StringBuffer content = new StringBuffer();
		content.append(ch, start, length);

		valueNode = content.toString().trim();

		if(valueNode == null || valueNode.length() <= 0)
		{ 
			return;
		}

		if (tableType == TABLE_TYPE.COMPLEXITY && tagName.equals("value"))
		{
			int i = counter / columns;
			int j = counter % columns;

			if (fpaType == FPA_TYPE.ILF)
			{
				fpaConfig.setIFLComplexity(i, j, retMin, retMax, detMin, detMax, valueNode);
			}
			else if (fpaType == FPA_TYPE.EIF)
			{
				fpaConfig.setEIFComplexity(i, j, retMin, retMax, detMin, detMax, valueNode);
			}
			else if (fpaType == FPA_TYPE.EI)
			{
				fpaConfig.setEIComplexity(i, j, ftrMin, ftrMax, detMin, detMax, valueNode);
			}
			else if (fpaType == FPA_TYPE.EO)
			{
				fpaConfig.setEOComplexity(i, j, ftrMin, ftrMax, detMin, detMax, valueNode);
			}
			else if (fpaType == FPA_TYPE.EQ)
			{
				fpaConfig.setEQComplexity(i, j, ftrMin, ftrMax, detMin, detMax, valueNode);
			}
		}
		else if (tableType == TABLE_TYPE.COMPLEXITY_VALUES && tagName.equals("value"))
		{
			if (fpaType == FPA_TYPE.ILF)
			{
				fpaConfig.setIFLComplexityValue(counter, type, Integer.parseInt(valueNode));
			}
			else if (fpaType == FPA_TYPE.EIF)
			{
				fpaConfig.setEIFComplexityValue(counter, type, Integer.parseInt(valueNode));
			}
			else if (fpaType == FPA_TYPE.EI)
			{
				fpaConfig.setEIComplexityValue(counter, type, Integer.parseInt(valueNode));
			}
			else if (fpaType == FPA_TYPE.EO)
			{
				fpaConfig.setEOComplexityValue(counter, type, Integer.parseInt(valueNode));
			}
			else if (fpaType == FPA_TYPE.EQ)
			{
				fpaConfig.setEQComplexityValue(counter, type, Integer.parseInt(valueNode));
			}
		}
	}
}
