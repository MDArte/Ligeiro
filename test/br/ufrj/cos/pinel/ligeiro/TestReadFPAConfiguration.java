package br.ufrj.cos.pinel.ligeiro;

import br.ufrj.coppe.pinel.express.common.FPAConfig;
import br.ufrj.coppe.pinel.express.common.Util;
import br.ufrj.coppe.pinel.express.xml.XMLUtil;
import br.ufrj.coppe.pinel.express.xml.exception.ReadXMLException;

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

			Util.println("\n" + fpaConfig.getIFLComplexityValue(2, 2));

			Util.println("Done.");
		}
		catch (ReadXMLException e)
		{
			e.printStackTrace();
		}
	}
}
