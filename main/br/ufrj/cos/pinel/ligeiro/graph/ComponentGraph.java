package br.ufrj.cos.pinel.ligeiro.graph;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;

import prefuse.Visualization;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.expression.parser.ExpressionParser;
import prefuse.data.tuple.TupleSet;
import prefuse.util.ui.UILib;
import br.ufrj.cos.pinel.ligeiro.data.State;
import br.ufrj.cos.pinel.ligeiro.data.UseCase;
import br.ufrj.cos.pinel.ligeiro.data.View;

/**
 * @author Roque Pinel
 * @author Marcio Antelio
 * 
 */
public class ComponentGraph extends GraphView
{
	private static final long serialVersionUID = 1L;

	private static final String CUSTOM_LABEL = "Component Graph";

	public static final int USE_CASE = 1;
	public static final int ACTIVITY_DIAGRAM = 2;
	public static final int VIEW = 3;
	public static final int CONTROLLER = 4;
	public static final int INITIAL_STATE = 5;
	public static final int FINAL_STATE = 6;
	public static final int OTHER_STATE = 7;
	public static final int SIGNAL = 8;

	private static Collection<UseCase> useCases;

	private static JFrame staticFrame;

	public ComponentGraph(Graph g)
	{
		super(g, LABEL);
	}

	@Override
	public void restoreApplication()
	{
		setGraph(getComponentGraph(), LABEL);
	}

	@Override
	public void updateMethodsCombo(String elementName)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createElementGraph(String elementName)
	{
		TupleSet focusGroup = m_vis.getFocusGroup(Visualization.FOCUS_ITEMS);

		Iterator iter = m_vis.getGroup(nodes).tuples(ExpressionParser.predicate("name == '" + elementName + "'"));

		if (iter.hasNext())
		{
			Tuple tuple = (Tuple) iter.next();
			focusGroup.setTuple(tuple);

			updateMethodsCombo(elementName);
		}
	}

	@Override
	public void createMethodGraph(String methodName)
	{
		// TODO Auto-generated method stub
	}

	// ------------------------------------------------------------------------

	public static void generate(Collection<UseCase> useCases)
	{
		ComponentGraph.useCases = useCases;

		UILib.setPlatformLookAndFeel();

		final ComponentGraph view = new ComponentGraph(getComponentGraph());

		staticFrame = new JFrame(ComponentGraph.CUSTOM_LABEL);
		prepareFrame(staticFrame, view);
	}

	private static void prepareFrame(JFrame frame, final GraphView view)
	{
		frame.setContentPane(view);
		frame.pack();
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter()
		{
			public void windowActivated(WindowEvent e)
			{
				view.m_vis.run("layout");
			}

			public void windowDeactivated(WindowEvent e)
			{
				view.m_vis.cancel("layout");
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static Table buildNodes()
	{
		Table nodes = new Table();
		nodes.addColumn("id", int.class);
		nodes.addColumn("name", String.class);
		nodes.addColumn("color", int.class);
		nodes.addColumn("ip", String.class);
		nodes.addColumn("group", String.class);
		nodes.addColumn("label", String.class);

		return nodes;
	}

	private static Table buildEdges()
	{
		Table edges = new Table();
		edges.addColumn("id1", int.class);
		edges.addColumn("id2", int.class);

		return edges;
	}

	private static Graph getComponentGraph()
	{
		Graph g = new Graph(buildNodes(), buildEdges(), true, "id1", "id2");

		Node root = g.addNode();
		root.set("id", root.getRow());
		root.set("name", "Application");
		root.set("color", getColor(null));
		root.set("label", "Application");

		Set<Node> toRoot = new HashSet<Node>();

		for (UseCase useCase : useCases)
		{
			Node ucNode = g.addNode();
			ucNode.set("id", ucNode.getRow());
			ucNode.set("name", useCase.getName());
			ucNode.set("color", getColor(USE_CASE));
			ucNode.set("label", useCase.getName());

			toRoot.add(ucNode);

			Node adNode = g.addNode();
			adNode.set("id", adNode.getRow());
			adNode.set("name", useCase.getActivityDiagram());
			adNode.set("color", getColor(ACTIVITY_DIAGRAM));
			adNode.set("label", useCase.getActivityDiagram());

			g.addEdge(ucNode.getRow(), adNode.getRow());

			if (useCase.getController() != null)
			{
				Node controllerNode = g.addNode();
				controllerNode.set("id", controllerNode.getRow());
				controllerNode.set("name", useCase.getController().getName());
				controllerNode.set("color", getColor(CONTROLLER));
				controllerNode.set("label", useCase.getController().getName());

				g.addEdge(adNode.getRow(), controllerNode.getRow());
			}

			for (View view : useCase.getViews())
			{
				Node viewNode = g.addNode();
				viewNode.set("id", viewNode.getRow());
				viewNode.set("name", view.getName());
				viewNode.set("color", getColor(VIEW));
				viewNode.set("label", view.getName());

				g.addEdge(adNode.getRow(), viewNode.getRow());
			}

			for (State state : useCase.getStates())
			{
				Node stateNode = g.addNode();
				stateNode.set("id", stateNode.getRow());
				stateNode.set("name", state.getName());
				stateNode.set("label", state.getName());

				if (state.isFirst())
					stateNode.set("color", getColor(INITIAL_STATE));
				else if (state.isFinalState())
					stateNode.set("color", getColor(FINAL_STATE));
				else
					stateNode.set("color", getColor(OTHER_STATE));

				g.addEdge(adNode.getRow(), stateNode.getRow());
			}
		}

		// fixing the graph edges
		for (Node node : toRoot)
		{
			g.addEdge(root.getRow(), node.getRow());
		}

		return g;
	}

	private static int getColor(Integer type)
	{
		int color = 0;

		if (type != null)
		{
			if (type == USE_CASE)
				color = 1;
			else if (type == ACTIVITY_DIAGRAM)
				color = 2;
			else if (type == VIEW)
				color = 3;
			else if (type == CONTROLLER)
				color = 4;
			else if (type == INITIAL_STATE)
				color = 5;
			else if (type == FINAL_STATE)
				color = 6;
			else if (type == OTHER_STATE)
				color = 7;
			else if (type == SIGNAL)
				color = 8;
		}

		return color;
	}
}
