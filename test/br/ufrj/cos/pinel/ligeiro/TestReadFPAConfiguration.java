package br.ufrj.cos.pinel.ligeiro;

import br.ufrj.cos.pinel.ligeiro.common.FPAConfig;
import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.xml.XMLUtil;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

/**
 * 
 * @author Roque Pinel
 *
 */
public class TestReadFPAConfiguration
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			Util.println("-- TestReadFPAConfiguration --");

			FPAConfig fpaConfig = XMLUtil.readFPAConfiguration("conf/ExpressFPA.xml");

			fpaConfig.printAll();

			Util.println("\n" + fpaConfig.getIFLComplexityValue(7, 0));

			Util.println("Done.");
		}
		catch (ReadXMLException e)
		{
			e.printStackTrace();
		}
	}
}
