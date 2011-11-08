package br.ufrj.cos.pinel.ligeiro;

import br.ufrj.cos.pinel.ligeiro.Core;
import br.ufrj.cos.pinel.ligeiro.common.FPAConfig;
import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

/**
 * 
 * @author Roque Pinel
 *
 */
public class TestCore
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Util.println("-- TestCore --");

		Core core = new Core();

		try
		{
			Util.println(">> Entities");
			core.readStatistics("data/statistics/statistics_entities.xml");

			Util.println(">> Services");
			core.readStatistics("data/statistics/statistics_services.xml");

			Util.println(">> Controllers");
			core.readStatistics("data/statistics/statistics_usecases.xml");

			Util.println(">> ControleAcesso");
			core.readStatistics("data/statistics/statistics_controleacesso.xml");

			Util.println(">> Dependencies");
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

			Util.println(">> Configuration...");
			FPAConfig fpaConfig = core.readFPAConfiguration("conf/LigeiroConfig.xml");

			Util.println(">> Starting...");
			core.startFunctionPointAnalysis(fpaConfig);

			Util.println("Done.");
		}
		catch (ReadXMLException e)
		{
			e.printStackTrace();
		}
	}
}
