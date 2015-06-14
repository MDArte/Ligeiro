package br.ufrj.cos.pinel.ligeiro;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import br.ufrj.cos.pinel.ligeiro.common.FPAConfig;
import br.ufrj.cos.pinel.ligeiro.report.FPAReport;

/**
 * 
 * @author Roque Pinel
 * 
 */
public class CoreTest extends GenericTest
{
	@Test
	public void createClassUsageReport() throws Exception
	{
		Core core = new Core();
		core.readStatistics(entitiesFilename);
		core.readStatistics(servicesFilename);
		core.readStatistics(useCasesFilename);
		core.readStatistics(classesFilename);
		core.readDependencies(sampleDependencyFilename);
		core.loadClassUsageReport();
		
		File temp = null;
		BufferedReader br = null;

		try
		{
			temp = File.createTempFile("ClassUsageReport", ".csv.tmp");
			
			core.createClassUsageReport(temp.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(temp.getAbsolutePath()));
			
			assertEquals("Type;Element;Element Method;Dependency Type;Dependency", br.readLine());
			assertEquals("usecase;br.ufrj.coppe.system.uc.system.student.update.UpdateStudent;"
					+ "br.ufrj.coppe.system.uc.system.student.update.UpdateStudentControllerImpl.loadValues("
					+ "org.apache.struts.action.ActionMapping, br.ufrj.coppe.system.uc.system.student.update.LoadValuesForm,"
					+ " javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse);entity;br.ufrj.coppe.system.cd.Student",
					br.readLine());
		}
		finally
		{
			if (temp != null)
				temp.deleteOnExit();
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test
	public void startFunctionPointAnalysis() throws Exception
	{
		Core core = new Core();
		core.readStatistics(entitiesFilename);
		core.readStatistics(servicesFilename);
		core.readStatistics(useCasesFilename);
		core.readStatistics(classesFilename);
		core.readDependencies(sampleDependencyFilename);
		core.loadClassUsageReport();

		FPAConfig fpaConfig = core.readFPAConfiguration(configFilename);

		FPAReport fpaReport = core.startFunctionPointAnalysis(fpaConfig);

		assertEquals(94, fpaReport.getDFReportTotal());
		assertEquals(16, fpaReport.getTFReportTotal());
		assertEquals(110, fpaReport.getReportTotal());
	}
}
