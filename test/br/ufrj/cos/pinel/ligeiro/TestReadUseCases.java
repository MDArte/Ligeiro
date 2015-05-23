package br.ufrj.cos.pinel.ligeiro;

import java.util.Collection;

import br.ufrj.cos.pinel.ligeiro.common.Util;
import br.ufrj.cos.pinel.ligeiro.data.Event;
import br.ufrj.cos.pinel.ligeiro.data.State;
import br.ufrj.cos.pinel.ligeiro.data.Table;
import br.ufrj.cos.pinel.ligeiro.data.UseCase;
import br.ufrj.cos.pinel.ligeiro.data.View;
import br.ufrj.cos.pinel.ligeiro.xml.XMLUtil;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

/**
 * 
 * @author Roque Pinel
 *
 */
public class TestReadUseCases
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			Util.println("-- TestReadUseCases --");

			Collection<UseCase> useCases = XMLUtil.readUseCases("data/AcademicSystem/statistics/statistics_usecases.xml");
			Util.println("UseCases: " + useCases.size());

			for (UseCase useCase : useCases)
			{
				Util.println("UseCase name: " + useCase.getName());
				Util.println("UseCase module: " + useCase.getModuleName());

				Util.println("--> Views");
				for (View view : useCase.getViews())
				{
					Util.printClass(view);

					Util.println("\t--> Tables");
					for (Table table : view.getTables())
					{
						Util.println("\t\tName: " + table.getName());
						for (String column : table.getColumns())
						{
							Util.println("\t\t\t" + column);
						}
					}
				}

				Util.println("--> States");
				for (State state : useCase.getStates())
				{
					Util.println("\tState name: " + state.getName());
					Util.println("\t  FINAL: " + state.isFinalState());

					if (state.getTarget() != null)
					{
						Util.println("\t\tTarget: " + state.getTarget().getName());
						Util.println("\t\t  FINAL: " + state.getTarget().isFinalState());

						if (state.getTarget().isFinalState())
						{
							Util.println("\t\t  hyperlinkApplicationName: " + state.getTarget().getHyperlinkApplicationName());
							Util.println("\t\t  hyperlinkModulo: " + state.getTarget().getHyperlinkModulo());
							Util.println("\t\t  hyperlink: " + state.getTarget().getHyperlink());
						}

						for (Event event : state.getTarget().getEvents())
						{
							Util.println("\t\t  * " + event.getName());
						}
					}
				}

				Util.println("--> Controller");

				if (useCase.getController() != null)
					Util.printClass(useCase.getController());
			}

			Util.println("Done.");
		}
		catch (ReadXMLException e)
		{
			e.printStackTrace();
		}
	}
}
