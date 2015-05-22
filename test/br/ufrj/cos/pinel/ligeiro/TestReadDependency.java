package br.ufrj.cos.pinel.ligeiro;

import java.util.Collection;

import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.data.BaseClass;
import br.ufrj.cos.pinel.ligeiro.xml.XMLUtil;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

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

			Collection<BaseClass> classes = XMLUtil.readDependencies("data/AcademicSystem/dependency/as-core-student.xml");

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
