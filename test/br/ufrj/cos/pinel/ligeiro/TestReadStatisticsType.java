package br.ufrj.cos.pinel.ligeiro;

import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.xml.XMLUtil;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

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

			String statisticsType = XMLUtil.readStatisticType("data/AcademicSystem/statistics/statistics_services.xml");

			Util.println("StatisticsType: " + statisticsType);

			Util.println("Done.");
		}
		catch (ReadXMLException e)
		{
			e.printStackTrace();
		}
	}
}
