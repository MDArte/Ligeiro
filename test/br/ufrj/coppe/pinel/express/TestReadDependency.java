package br.ufrj.coppe.pinel.express;

import java.util.Collection;

import br.ufrj.coppe.pinel.express.common.Util;
import br.ufrj.coppe.pinel.express.data.BaseClass;
import br.ufrj.coppe.pinel.express.xml.XMLUtil;
import br.ufrj.coppe.pinel.express.xml.exception.ReadXMLException;

/**
 * @author Roque Pinel
 *
 */
public class TestReadDependency
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			Util.println("-- TestReadDependency --");

			Collection<BaseClass> classes = XMLUtil.readDependencies("data/dependency/as-core-student.xml");

			Util.println("Classes: " + classes.size());

			for (BaseClass clazz : classes)
			{
				Util.printClass(clazz);
			}

			Util.println("Done.");
		}
		catch (ReadXMLException e)
		{
			e.printStackTrace();
		}
	}
}
