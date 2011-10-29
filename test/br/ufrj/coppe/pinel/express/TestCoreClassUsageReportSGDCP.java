package br.ufrj.coppe.pinel.express;


/**
 * 
 * @author Roque Pinel
 *
 */
public class TestCoreClassUsageReportSGDCP
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("-- TestCoreClassUsageReportSGDCP --");

		Core core = new Core();

		try
		{
			System.out.println(">> Entities");
			core.readStatistics("data/sgdcp/statistics/statistics_entities-dominio.xml");
			core.readStatistics("data/sgdcp/statistics/statistics_entities-uc-mrd.xml");

			System.out.println(">> Services");
			core.readStatistics("data/sgdcp/statistics/statistics_services-dominio.xml");

			System.out.println(">> Controllers");
			core.readStatistics("data/sgdcp/statistics/statistics_usecases-Piloto-Principal.xml");
			core.readStatistics("data/sgdcp/statistics/statistics_usecases-uc-configuracao.xml");
			core.readStatistics("data/sgdcp/statistics/statistics_usecases-uc-controlemensagens.xml");
			core.readStatistics("data/sgdcp/statistics/statistics_usecases-uc-empresa.xml");
			core.readStatistics("data/sgdcp/statistics/statistics_usecases-uc-mrd.xml");
			core.readStatistics("data/sgdcp/statistics/statistics_usecases-uc-soc.xml");
			core.readStatistics("data/sgdcp/statistics/statistics_usecases-uc-ssr.xml");
			core.readStatistics("data/sgdcp/statistics/statistics_usecases-uc-tir.xml");

			System.out.println(">> ControleAcesso");
			core.readStatistics("data/statistics/statistics_controleacesso.xml");

			System.out.println(">> Dependencies");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cd.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-common.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-buscaTextual.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-compartilhado.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-configuracao.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-controleMensagens.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-empresa.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-initial.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-mrd.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-qualificadoresReferencia.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-recebimento.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-rotinaConfiguravel.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-soc.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-ssr.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-cs-tir.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-uc-controleMensagens.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-uc-empresa.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-uc-layout.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-uc-mrd.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-uc-principal.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-uc-soc.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-uc-ssr.xml");
			core.readDependencies("data/sgdcp/dependency/sgdcp-uc-tir.xml");

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
