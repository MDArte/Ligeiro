package br.ufrj.coppe.pinel.express;

import br.ufrj.coppe.pinel.express.common.Util;
import br.ufrj.coppe.pinel.express.xml.XMLUtil;
import br.ufrj.coppe.pinel.express.xml.exception.ReadXMLException;

/**
 * 
 * @author Roque Pinel
 *
 */
public class TestReadStatisticsType
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			Util.println("-- TestReadStatisticsType --");

			String statisticsType = XMLUtil.readStatisticType("data/statistics/statistics_services.xml");

			Util.println("StatisticsType: " + statisticsType);

			Util.println("Done.");
		}
		catch (ReadXMLException e)
		{
			e.printStackTrace();
		}
	}
}
