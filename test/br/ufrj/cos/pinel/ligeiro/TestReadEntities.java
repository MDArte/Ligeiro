package br.ufrj.cos.pinel.ligeiro;

import java.util.Collection;

import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.data.Entity;
import br.ufrj.cos.pinel.ligeiro.xml.XMLUtil;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

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
