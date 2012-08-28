package br.ufrj.cos.pinel.ligeiro;

import br.ufrj.cos.pinel.ligeiro.Core;
import br.ufrj.cos.pinel.ligeiro.common.Util;


/**
 * 
 * @author Roque Pinel
 *
 */
public class TestCoreComponentGraph extends Test
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Util.println("-- TestCoreComponentGraph --");

		Core core = new Core();

		try
		{
			Util.println(">> Entities");
			core.readStatistics("data/AcademicSystem/statistics/statistics_entities.xml");

			Util.println(">> Services");
			core.readStatistics("data/AcademicSystem/statistics/statistics_services.xml");

			Util.println(">> Controllers");
			core.readStatistics("data/AcademicSystem/statistics/statistics_usecases.xml");

			Util.println(">> ControleAcesso");
			core.readStatistics("data/AcademicSystem/statistics/statistics_controleacesso.xml");

			Util.println(">> Dependencies");
//			core.readDependencies("data/AcademicSystem/dependency/as-common.xml");
//			core.readDependencies("data/AcademicSystem/dependency/as-core-cd.xml");
//			core.readDependencies("data/AcademicSystem/dependency/as-core-compartilhado.xml");
//			core.readDependencies("data/AcademicSystem/dependency/as-core-controleacesso.xml");
//			core.readDependencies("data/AcademicSystem/dependency/as-core-initial.xml");
//			core.readDependencies("data/AcademicSystem/dependency/as-core-shared.xml");
//			core.readDependencies("data/AcademicSystem/dependency/as-core-student.xml");
//			core.readDependencies("data/AcademicSystem/dependency/as-web-help.xml");
//			core.readDependencies("data/AcademicSystem/dependency/as-web-layout.xml");
//			core.readDependencies("data/AcademicSystem/dependency/as-web-system.xml");
			core.readDependencies("data/AcademicSystem/dependency/amostra.xml");

			Util.println(">> Starting...");

			core.generateComponentGraph();

			Util.println("Done.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
