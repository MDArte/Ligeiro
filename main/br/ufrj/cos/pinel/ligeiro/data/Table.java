package br.ufrj.cos.pinel.ligeiro.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Table displayed in a View.
 * 
 * @author Roque Pinel
 *
 */
public class Table
{
	private String name;

	private Collection<String> columns;

	public Table()
	{
		columns = new ArrayList<String>();
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the columns
	 */
	public Collection<String> getColumns()
	{
		return columns;
	}

	/**
	 * @param columns the column to be added
	 */
	public void addColumn(String column)
	{
		this.columns.add(column);
	}
}
