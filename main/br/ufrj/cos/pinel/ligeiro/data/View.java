package br.ufrj.cos.pinel.ligeiro.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Roque Pinel
 *
 */
public class View extends BaseClass
{
	private UseCase useCase;

	private static class FP_TYPE
	{
		public static int NONE = -1;
		public static int EI = 0;
		public static int EQ1 = 1;
		public static int EQ2 = 2;
		public static int EO = 3;
	}

	private int fpType;

	private int numberParameters;

	private int numberInputParameters;

	private int numberButtons;

	private View resultView;

	private boolean first;

	private Collection<Table> tables;

	private Set<String> countedEntities;

	/**
	 * Default constructor.
	 */
	public View()
	{
		super();

		this.fpType = FP_TYPE.NONE;

		this.numberParameters = 0;
		this.numberInputParameters = 0;
		this.numberButtons = 0;

		this.first = false;

		this.tables = new ArrayList<Table>();

		this.countedEntities = new HashSet<String>();
	}

	/**
	 * @param name The class name
	 */
	public View(String name)
	{
		this();
		super.setName(name);
	}

	/**
	 * @return the useCase
	 */
	public UseCase getUseCase()
	{
		return useCase;
	}

	/**
	 * @param useCase the useCase to set
	 */
	public void setUseCase(UseCase useCase)
	{
		this.useCase = useCase;
	}

	/**
	 * Defines FP Type as EI.
	 */
	public void setAsEI()
	{
		this.fpType = FP_TYPE.EI;
	}

	/**
	 * @return if it's EI
	 */
	public boolean isEI()
	{
		return this.fpType == FP_TYPE.EI;
	}

	/**
	 * Defines FP Type as EO.
	 */
	public void setAsEO()
	{
		this.fpType = FP_TYPE.EO;
	}

	/**
	 * @return if it's EO
	 */
	public boolean isEO()
	{
		return this.fpType == FP_TYPE.EO;
	}

	/**
	 * Defines FP Type as EQ1.
	 */
	public void setAsEQ1()
	{
		this.fpType = FP_TYPE.EQ1;
	}

	/**
	 * @return if it's EQ1
	 */
	public boolean isEQ1()
	{
		return this.fpType == FP_TYPE.EQ1;
	}

	/**
	 * Defines FP Type as EQ2.
	 */
	public void setAsEQ2()
	{
		this.fpType = FP_TYPE.EQ2;
	}

	/**
	 * @return if it's EQ2
	 */
	public boolean isEQ2()
	{
		return this.fpType == FP_TYPE.EQ2;
	}

	/**
	 * @return if it's EQ
	 */
	public boolean isEQ()
	{
		return this.fpType == FP_TYPE.EQ1 || this.fpType == FP_TYPE.EQ2;
	}

	/**
	 * @return the numberParameters
	 */
	public int getNumberParameters()
	{
		return numberParameters;
	}

	/**
	 * numberParameters incremental.
	 */
	public void addNumberParameters()
	{
		this.numberParameters++;
	}

	/**
	 * @return the numberInputParameters
	 */
	public int getNumberInputParameters()
	{
		return numberInputParameters;
	}

	/**
	 * numberInputParameters incremental.
	 */
	public void addNumberInputParameters()
	{
		this.numberInputParameters++;
	}

	/**
	 * @return the numberButtons
	 */
	public int getNumberButtons()
	{
		return numberButtons;
	}

	/**
	 * numberButtons incremental.
	 */
	public void addNumberButtons()
	{
		this.numberButtons++;
	}

	/**
	 * @return the resultView
	 */
	public View getResultView()
	{
		return resultView;
	}

	/**
	 * @param resultView the resultView to set
	 */
	public void setResultView(View resultView)
	{
		this.resultView = resultView;
	}

	/**
	 * @see br.ufrj.cos.pinel.ligeiro.data.BaseClass#getMethodsSignatures()
	 */
	public Set<String> getMethodsSignatures()
	{
		Set<String> methodSignatures = new HashSet<String>();

		for (Method method : getMethods())
		{
			String signature = getImplementationName() + "." + method.getSignature();

			methodSignatures.add(signature);
		}

		return methodSignatures;
	}

	/**
	 * @return the first
	 */
	public boolean isFirst()
	{
		return first;
	}

	/**
	 * Set as first.
	 */
	public void setAsFirst()
	{
		this.first = true;
	}

	/**
	 * @return the tables
	 */
	public Collection<Table> getTables()
	{
		return tables;
	}

	/**
	 * @param tables the table to be added
	 */
	public void addTable(Table table)
	{
		this.tables.add(table);
	}

	/**
	 * @return The total of columns of all tables.
	 */
	public int getTotalColumns()
	{
		int total = 0;

		for (Table table : this.getTables())
		{
			total += table.getColumns().size();
		}

		return total;
	}

	/**
	 * @return the countedEntities
	 */
	public Set<String> getCountedEntities()
	{
		return countedEntities;
	}

	/**
	 * @param countedEntities the countedEntities to set
	 */
	public void setCountedEntities(Set<String> countedEntities)
	{
		this.countedEntities = countedEntities;
	}
}
