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
	public static boolean DEBUG = false;

	/**
	 * XML Engine Type.
	 *
	 */
	public static String XML_ENGINE_TYPE_JDOM = "JDOM";
	public static String XML_ENGINE_TYPE_SAX = "SAX";

	/**
	 * XML Engine
	 */
	public static String XML_ENGINE = XML_ENGINE_TYPE_SAX;

	/**
	 * XML Types.
	 */
	public static String XML_CLASS = "classes";
	public static String XML_ENTITY = "entities";
	public static String XML_SERVICE = "services";
	public static String XML_USE_CASE = "usecases";
	public static String XML_DEPENDENCY = "dependencies";

	/**
	 * The caracter used to separe the value in a CSV file.
	 */
	public static char CSV_DELIMITER = ';';

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
