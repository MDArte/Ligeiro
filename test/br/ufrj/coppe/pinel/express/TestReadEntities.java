package br.ufrj.coppe.pinel.express;

import java.util.Collection;

import br.ufrj.coppe.pinel.express.common.Util;
import br.ufrj.coppe.pinel.express.data.Entity;
import br.ufrj.coppe.pinel.express.xml.XMLUtil;
import br.ufrj.coppe.pinel.express.xml.exception.ReadXMLException;

/**
 * 
 * @author Roque Pinel
 *
 */
public class TestReadEntities
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			Util.println("-- TestReadEntities --");

			Collection<Entity> entities = XMLUtil.readEntities("data/statistics/statistics_entities.xml");
			Util.println("Entities: " + entities.size());

			for (Entity entity : entities)
			{
				Util.printClass(entity);
			}

			Util.println("Done.");
		}
		catch (ReadXMLException e)
		{
			e.printStackTrace();
		}
	}
}
