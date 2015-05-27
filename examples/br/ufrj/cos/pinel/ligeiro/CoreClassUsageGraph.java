package br.ufrj.cos.pinel.ligeiro;

import br.ufrj.cos.pinel.ligeiro.Core;
import br.ufrj.cos.pinel.ligeiro.common.Util;


/**
 * 
 * @author Roque Pinel
 *
 */
public class CoreClassUsageGraph
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Util.println("-- CoreClassUsageGraph --");

		Core core = new Core();

		try
		{
			Util.println(">> Report");
			core.loadClassUsageReport("data/AcademicSystem/ClassUsageReport.csv");

			Util.println(">> Starting...");

			core.generateClassUsageGraph();

			Util.println("Done.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
