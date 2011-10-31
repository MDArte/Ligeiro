package br.ufrj.cos.pinel.ligeiro.common;

/**
 * Holds the constants.
 * 
 * @author Roque Pinel
 *
 */
public class Constants
{
	/**
	 * Enables debug report.
	 */
	public static boolean DEBUG = true;

	/**
	 * XML Engine Type.
	 * 
	 * @author Roque Pinel
	 *
	 */
	public static class XML_ENGINE_TYPE
	{
		public static String JDOM = "JDOM";
		public static String SAX = "SAX";
	}

	/**
	 * XML Engine
	 */
	public static String XML_ENGINE = XML_ENGINE_TYPE.SAX;

	/**
	 * XML.
	 * 
	 * @author Roque Pinel
	 *
	 */
	public static class XML
	{
		public static String CLASSES = "classes";
		public static String ENTITY = "entities";
		public static String SERVICE = "services";
		public static String USE_CASE = "usecases";
	}

	/**
	 * CSV.
	 * 
	 * @author Roque Pinel
	 *
	 */
	public static class CSV
	{
		/**
		 * The caracter used to separe the value in a CSV file.
		 */
		public static char DELIMITER = ';';
	}

	/*
	 * Data Function.
	 */
	public static String DF_ILF = "ILF";
	public static String DF_EIF = "EIF";
	public static int DF_DEFAULT_RET = 1;

	/*
	 * Transaction Function.
	 */
	public static String TF_EI = "EI";
	public static String TF_EO = "EO";
	public static String TF_EQ = "EQ";
	public static int TF_DEFAULT_FTR = 1;
}
