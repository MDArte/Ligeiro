package br.ufrj.coppe.pinel.express.common;

/**
 * @author Roque Pinel
 *
 */
public class FPAConfig
{
	public static int NULL = 0;
	public static int INFINITY = -1;

	private static int MIN_COL = 0;
	private static int MAX_COL = 1;
	private static int COLUMNS_SIZE = 2;

	/**
	 * Default contructor.
	 */
	public FPAConfig()
	{
		// emtpy
	}

	/*
	 * Internal Logic File
	 */
	private int[][] ilf_ret_row;
	private int[][] ilf_det_col;
	private String[][] ilf_complexity;
	private String[] ilf_values_row;
	private int[] ilf_values;

	public void createIFLComplexity(int rows, int columns)
	{
		ilf_ret_row = new int[rows][COLUMNS_SIZE];
		ilf_det_col = new int[columns][COLUMNS_SIZE];
		ilf_complexity = new String[rows][columns];
	}

	public void setIFLComplexity(int i, int j, int retMin, int retMax, int detMin, int detMax, String complexity)
	{
		if (i < 0 || i >= ilf_ret_row.length || j < 0 || j >= ilf_det_col.length)
		{
			throw new RuntimeException("setIFLComplexity - index boundary error");
		}

		ilf_ret_row[i][MIN_COL] = retMin;
		ilf_ret_row[i][MAX_COL] = retMax;

		ilf_det_col[j][MIN_COL] = detMin;
		ilf_det_col[j][MAX_COL] = detMax;

		ilf_complexity[i][j] = complexity;
	}

	public void createIFLComplexityValues(int size)
	{
		ilf_values_row = new String[size];
		ilf_values = new int[size];
	}

	public void setIFLComplexityValue(int i, String complexity, int value)
	{
		if (i < 0 || i >= ilf_values_row.length || i >= ilf_values.length)
		{
			throw new RuntimeException("setIFLComplexityValue - index boundary error");
		}

		ilf_values_row[i] = complexity;
		ilf_values[i] = value;
	}

	public int getIFLComplexityValue(int ret, int det)
	{
		return getComplexityValue(ilf_ret_row, ilf_det_col, ilf_complexity, ilf_values_row, ilf_values, ret, det);
	}

	/*
	 * External Interface File
	 */
	private int[][] eif_ret_row;
	private int[][] eif_det_col;
	private String[][] eif_complexity;
	private String[] eif_values_row;
	private int[] eif_values;

	public void createEIFComplexity(int rows, int columns)
	{
		eif_ret_row = new int[rows][COLUMNS_SIZE];
		eif_det_col = new int[columns][COLUMNS_SIZE];
		eif_complexity = new String[rows][columns];
	}

	public void setEIFComplexity(int i, int j, int retMin, int retMax, int detMin, int detMax, String complexity)
	{
		if (i < 0 || i >= eif_ret_row.length || j < 0 || j >= eif_det_col.length)
		{
			throw new RuntimeException("setEIFComplexity - index boundary error - i: " + i + " j: " + j + " rows: " + eif_ret_row.length + " columns: " + eif_det_col.length);
		}

		eif_ret_row[i][MIN_COL] = retMin;
		eif_ret_row[i][MAX_COL] = retMax;

		eif_det_col[j][MIN_COL] = detMin;
		eif_det_col[j][MAX_COL] = detMax;

		eif_complexity[i][j] = complexity;
	}

	public void createEIFComplexityValues(int size)
	{
		eif_values_row = new String[size];
		eif_values = new int[size];
	}

	public void setEIFComplexityValue(int i, String complexity, int value)
	{
		if (i < 0 || i >= eif_values_row.length || i >= eif_values.length)
		{
			throw new RuntimeException("setEIFComplexityValue - index boundary error - i: " + i + " size: " + eif_values.length);
		}

		eif_values_row[i] = complexity;
		eif_values[i] = value;
	}

	public int getEIFComplexityValue(int ret, int det)
	{
		return getComplexityValue(eif_ret_row, eif_det_col, eif_complexity, eif_values_row, eif_values, ret, det);
	}

	/*
	 * External Input
	 */
	private int[][] ei_ftr_row;
	private int[][] ei_det_col;
	private String[][] ei_complexity;
	private String[] ei_values_row;
	private int[] ei_values;

	public void createEIComplexity(int rows, int columns)
	{
		ei_ftr_row = new int[rows][COLUMNS_SIZE];
		ei_det_col = new int[columns][COLUMNS_SIZE];
		ei_complexity = new String[rows][columns];
	}

	public void setEIComplexity(int i, int j, int ftrMin, int ftrMax, int detMin, int detMax, String complexity)
	{
		if (i < 0 || i >= ei_ftr_row.length || j < 0 || j >= ei_det_col.length)
		{
			throw new RuntimeException("setEIComplexity - index boundary error - i: " + i + " j: " + j + " rows: " + ei_ftr_row.length + " columns: " + ei_det_col.length);
		}

		ei_ftr_row[i][MIN_COL] = ftrMin;
		ei_ftr_row[i][MAX_COL] = ftrMax;

		ei_det_col[j][MIN_COL] = detMin;
		ei_det_col[j][MAX_COL] = detMax;

		ei_complexity[i][j] = complexity;
	}

	public void createEIComplexityValues(int size)
	{
		ei_values_row = new String[size];
		ei_values = new int[size];
	}

	public void setEIComplexityValue(int i, String complexity, int value)
	{
		if (i < 0 || i >= ei_values_row.length || i >= ei_values.length)
		{
			throw new RuntimeException("setEIComplexityValue - index boundary error - i: " + i + " size: " + ei_values.length);
		}

		ei_values_row[i] = complexity;
		ei_values[i] = value;
	}

	public int getEIComplexityValue(int ftr, int det)
	{
		return getComplexityValue(ei_ftr_row, ei_det_col, ei_complexity, ei_values_row, ei_values, ftr, det);
	}

	/*
	 * External Output
	 */
	private int[][] eo_ftr_row;
	private int[][] eo_det_col;
	private String[][] eo_complexity;
	private String[] eo_values_row;
	private int[] eo_values;

	public void createEOComplexity(int rows, int columns)
	{
		eo_ftr_row = new int[rows][COLUMNS_SIZE];
		eo_det_col = new int[columns][COLUMNS_SIZE];
		eo_complexity = new String[rows][columns];
	}

	public void setEOComplexity(int i, int j, int ftrMin, int ftrMax, int detMin, int detMax, String complexity)
	{
		if (i < 0 || i >= eo_ftr_row.length || j < 0 || j >= eo_det_col.length)
		{
			throw new RuntimeException("setEOComplexity - index boundary error - i: " + i + " j: " + j + " rows: " + eo_ftr_row.length + " columns: " + eo_det_col.length);
		}

		eo_ftr_row[i][MIN_COL] = ftrMin;
		eo_ftr_row[i][MAX_COL] = ftrMax;

		eo_det_col[j][MIN_COL] = detMin;
		eo_det_col[j][MAX_COL] = detMax;

		eo_complexity[i][j] = complexity;
	}

	public void createEOComplexityValues(int size)
	{
		eo_values_row = new String[size];
		eo_values = new int[size];
	}

	public void setEOComplexityValue(int i, String complexity, int value)
	{
		if (i < 0 || i >= eo_values_row.length || i >= eo_values.length)
		{
			throw new RuntimeException("setEOComplexityValue - index boundary error - i: " + i + " size: " + eo_values.length);
		}

		eo_values_row[i] = complexity;
		eo_values[i] = value;
	}

	public int getEOComplexityValue(int ftr, int det)
	{
		return getComplexityValue(eo_ftr_row, eo_det_col, eo_complexity, eo_values_row, eo_values, ftr, det);
	}

	/*
	 * External Inquiry
	 */
	private int[][] eq_ftr_row;
	private int[][] eq_det_col;
	private String[][] eq_complexity;
	private String[] eq_values_row;
	private int[] eq_values;

	public void createEQComplexity(int rows, int columns)
	{
		eq_ftr_row = new int[rows][COLUMNS_SIZE];
		eq_det_col = new int[columns][COLUMNS_SIZE];
		eq_complexity = new String[rows][columns];
	}

	public void setEQComplexity(int i, int j, int ftrMin, int ftrMax, int detMin, int detMax, String complexity)
	{
		if (i < 0 || i >= eq_ftr_row.length || j < 0 || j >= eq_det_col.length)
		{
			throw new RuntimeException("setEOComplexity - index boundary error - i: " + i + " j: " + j + " rows: " + eq_ftr_row.length + " columns: " + eq_det_col.length);
		}

		eq_ftr_row[i][MIN_COL] = ftrMin;
		eq_ftr_row[i][MAX_COL] = ftrMax;

		eq_det_col[j][MIN_COL] = detMin;
		eq_det_col[j][MAX_COL] = detMax;

		eq_complexity[i][j] = complexity;
	}

	public void createEQComplexityValues(int size)
	{
		eq_values_row = new String[size];
		eq_values = new int[size];
	}

	public void setEQComplexityValue(int i, String complexity, int value)
	{
		if (i < 0 || i >= eq_values_row.length || i >= eq_values.length)
		{
			throw new RuntimeException("setEOComplexityValue - index boundary error - i: " + i + " size: " + eq_values.length);
		}

		eq_values_row[i] = complexity;
		eq_values[i] = value;
	}

	public int getEQComplexityValue(int ftr, int det)
	{
		return getComplexityValue(eq_ftr_row, eq_det_col, eq_complexity, eq_values_row, eq_values, ftr, det);
	}

	/* GENERAL */

	private int getComplexityValue(int[][] rows, int[][] cols, String[][] complexity, String[] values_row, int[] values, int r, int c)
	{
		for (int i = 0; i < rows.length; i++)
		{
			if (rows[i][MIN_COL] <= r && (rows[i][MAX_COL] == INFINITY || r <= rows[i][MAX_COL]))
			{
				for (int j = 0; j < cols.length; j++)
				{
					if (cols[j][MIN_COL] <= c && (cols[j][MAX_COL] == INFINITY || c <= cols[j][MAX_COL]))
					{
						for (int k = 0; k < values_row.length; k++)
						{
							if (values_row[k].equals(complexity[i][j]))
								return values[k];
						}
					}
				}
			}
		}

		return NULL;
	}

	/* PRINTING */

	public void printAll()
	{
		Util.println("Internal Logic File");
		{
			Util.println("  Complexity");
			printComplexity(ilf_ret_row, ilf_det_col, ilf_complexity, "\t");
	
			Util.println("  ComplexityValue");
			printComplexityValue(ilf_values_row, ilf_values, "\t");
		}

		Util.println("External Interface File");
		{
			Util.println("  Complexity");
			printComplexity(eif_ret_row, eif_det_col, eif_complexity, "\t");
	
			Util.println("  ComplexityValue");
			printComplexityValue(eif_values_row, eif_values, "\t");
		}

		Util.println("External Input");
		{
			Util.println("  Complexity");
			printComplexity(ei_ftr_row, ei_det_col, ei_complexity, "\t");
	
			Util.println("  ComplexityValue");
			printComplexityValue(ei_values_row, ei_values, "\t");
		}

		Util.println("External Output");
		{
			Util.println("  Complexity");
			printComplexity(eo_ftr_row, eo_det_col, eo_complexity, "\t");
	
			Util.println("  ComplexityValue");
			printComplexityValue(eo_values_row, eo_values, "\t");
		}

		Util.println("External Inquiry");
		{
			Util.println("  Complexity");
			printComplexity(eq_ftr_row, eq_det_col, eq_complexity, "\t");
	
			Util.println("  ComplexityValue");
			printComplexityValue(eq_values_row, eq_values, "\t");
		}
	}

	private void printComplexity(int[][] row, int[][] col, String[][] matrix, String prefix)
	{
		for (int i = -1; i < row.length; i++)
		{
			for (int j = -1; j < col.length; j++)
			{
				if (i == -1 && j == -1)
					Util.print(prefix);
				else if (i == -1)
				{
					Util.print("\t" + col[j][MIN_COL] + " ~ " + col[j][MAX_COL]);
				}
				else if (j == -1)
				{
					Util.print(prefix + row[i][MIN_COL] + " ~ " + row[i][MAX_COL]);
				}
				else
				{
					Util.print("\t" + matrix[i][j]);
				}
			}
			Util.print("\n");
		}
	}

	private void printComplexityValue(String[] row, int[] array, String prefix)
	{
		for (int i = 0; i < ilf_values_row.length; i++)
		{
			Util.print(prefix + row[i] + "\t" + array[i] + "\n");
		}
	}
}
