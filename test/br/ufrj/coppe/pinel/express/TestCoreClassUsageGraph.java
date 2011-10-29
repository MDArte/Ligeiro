package br.ufrj.coppe.pinel.express;


/**
 * 
 * @author Roque Pinel
 *
 */
public class TestCoreClassUsageGraph
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("-- TestCoreClassUsageGraph --");

		Core core = new Core();

		try
		{
			System.out.println(">> Report");
			core.loadClassUsageReport("data/ClassUsageReport.csv");
			//core.loadClassUsageReport("data/SGDCP_ClassUsageReport.csv");

			System.out.println(">> Starting...");

			core.generateClassUsageGraph();

			System.out.println("Done.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
