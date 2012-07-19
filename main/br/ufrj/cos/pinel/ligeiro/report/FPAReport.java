package br.ufrj.cos.pinel.ligeiro.report;

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
	 * @param reportResult the result to be added
	 */
	public void addDFReport(ReportResult reportResult)
	{
		dfReport.add(reportResult);
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
	 * @param reportResult the result to be added
	 */
	public void addTFReport(ReportResult reportResult)
	{
		tfReport.add(reportResult);
	}

	/**
	 * @return the tfReportTotal
	 */
	public int getTFReportTotal()
	{
		return tfReportTotal;
	}

	/**
	 * @param value value to be added
	 */
	public void addTFReportTotal(int value)
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
	 * @return the adjustedReportTotal
	 */
	public double getAdjustedReportTotal(double vaf)
	{
		return getReportTotal() * vaf;
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
	public void clear()
	{
		clearDFReport();
		clearTFReport();
	}
}
