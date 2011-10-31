package br.ufrj.cos.pinel.ligeiro;

import br.ufrj.cos.pinel.ligeiro.Core;


/**
 * 
 * @author Roque Pinel
 *
 */
public class TestCoreClassUsageReport
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("-- TestCoreClassUsageReport --");

		Core core = new Core();

		try
		{
			System.out.println(">> Entities");
			core.readStatistics("data/statistics/statistics_entities.xml");

			System.out.println(">> Services");
			core.readStatistics("data/statistics/statistics_services.xml");

			System.out.println(">> Controllers");
			core.readStatistics("data/statistics/statistics_usecases.xml");

			System.out.println(">> ControleAcesso");
			core.readStatistics("data/statistics/statistics_controleacesso.xml");

			System.out.println(">> Dependencies");
//			core.readDependencies("data/dependency/as-common.xml");
//			core.readDependencies("data/dependency/as-core-cd.xml");
//			core.readDependencies("data/dependency/as-core-compartilhado.xml");
//			core.readDependencies("data/dependency/as-core-controleacesso.xml");
//			core.readDependencies("data/dependency/as-core-initial.xml");
//			core.readDependencies("data/dependency/as-core-shared.xml");
//			core.readDependencies("data/dependency/as-core-student.xml");
//			core.readDependencies("data/dependency/as-web-help.xml");
//			core.readDependencies("data/dependency/as-web-layout.xml");
//			core.readDependencies("data/dependency/as-web-system.xml");
			core.readDependencies("data/dependency/example.xml");

			System.out.println(">> Starting...");

			core.loadClassUsageReport();

			core.createClassUsageReport("data/ClassUsageReport.csv");

			System.out.println("Done.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
