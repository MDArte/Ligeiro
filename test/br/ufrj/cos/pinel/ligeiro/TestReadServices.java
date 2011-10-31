package br.ufrj.cos.pinel.ligeiro;

import java.util.Collection;

import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.data.Service;
import br.ufrj.cos.pinel.ligeiro.xml.XMLUtil;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

/**
 * 
 * @author Roque Pinel
 *
 */
public class TestReadServices
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			Util.println("-- TestReadServices --");

			Collection<Service> services = XMLUtil.readServices("data/statistics/statistics_services.xml");
			//Collection<Service> services = XMLUtil.readServices("data/statistics/statistics_controleacesso.xml");

			Util.println("Services: " + services.size());

			for (Service service : services)
			{
				Util.printClass(service);
			}

			Util.println("Done.");
		}
		catch (ReadXMLException e)
		{
			e.printStackTrace();
		}
	}
}
