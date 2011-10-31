package br.ufrj.cos.pinel.ligeiro.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Holds the FPA Report.
 * 
 * @author Roque Pinel
 *
 */
public class FPAReport
{
	private Collection<ReportResult> dfReport;
	private int dfReportTotal;

	private Collection<ReportResult> tfReport;
	private int tfReportTotal;

	/**
	 * Default constructor.
	 */
	public FPAReport()
	{
		dfReport = new ArrayList<ReportResult>();
		dfReportTotal  = 0;

		tfReport = new ArrayList<ReportResult>();
		tfReportTotal  = 0;
	}

	/**
	 * @return the dfReport
	 */
	public Collection<ReportResult> getDFReport()
	{
		return dfReport;
	}

	/**
	 * @param result the result to be added
	 */
	public void setDFReport(ReportResult result)
	{
		dfReport.add(result);
	}

	/**
	 * @return the dfReportTotal
	 */
	public int getDFReportTotal()
	{
		return dfReportTotal;
	}

	/**
	 * @param value value to be added
	 */
	public void addDFReportTotal(int value)
	{
		dfReportTotal += value;
	}

	/**
	 * @return the tfReport
	 */
	public Collection<ReportResult> getTFReport()
	{
		return tfReport;
	}

	/**
	 * @param result the result to be added
	 */
	public void setTFReport(ReportResult result)
	{
		tfReport.add(result);
	}

	/**
	 * @return the tfReportTotal
	 */
	public int getTfReportTotal()
	{
		return tfReportTotal;
	}

	/**
	 * @param value value to be added
	 */
	public void addTfReportTotal(int value)
	{
		tfReportTotal += value;
	}

	/**
	 * @return the total
	 */
	public int getReportTotal()
	{
		return dfReportTotal + tfReportTotal;
	}

	/**
	 * Clears the DF Report.
	 */
	public void clearDFReport()
	{
		dfReport.clear();
		dfReportTotal  = 0;
	}

	/**
	 * Clears the TF Report.
	 */
	public void clearTFReport()
	{
		tfReport.clear();
		tfReportTotal  = 0;
	}

	/**
	 * Clears the all reports.
	 */
	public void clearAll()
	{
		clearDFReport();
		clearTFReport();
	}
}
