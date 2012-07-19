package br.ufrj.cos.pinel.ligeiro.graph;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
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
import br.ufrj.cos.pinel.ligeiro.data.ClassUsage;

/**
 * @author Roque Pinel
 *
 */
public class ClassUsageGraph extends GraphView
{
	private static final long serialVersionUID = 1L;

	private static JFrame staticFrame;

	private static Collection<ClassUsage> classUsages;

	public ClassUsageGraph(Graph g)
	{
		super(g, LABEL);
	}

	@Override
	public void restoreApplication()
	{
		setGraph(getClassUsageGraph(), LABEL);
	}

	@Override
	public void updateMethodsCombo(String elementName)
	{
		methodsCombo.removeAllItems();

		methodsCombo.addItem("");

		Set<String> methods = new HashSet<String>();

		for (ClassUsage classUsage : classUsages)
		{
			if (classUsage.getElement().equals(elementName)
				&& !methods.contains(classUsage.getMethod()))
			{
				methodsCombo.addItem(classUsage.getMethod());
				methods.add(classUsage.getMethod());
			}
		}
	}

	public void updateElementsCombo()
	{
		elementsCombo.removeAllItems();

		elementsCombo.addItem("");

		Set<String> elements = new HashSet<String>();

		for (ClassUsage classUsage : classUsages)
		{
			if (!elements.contains(classUsage.getElement()))
			{
				elementsCombo.addItem(classUsage.getElement());
				elements.add(classUsage.getElement());
			}
		}
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
		setGraph(getMethodUsageGraph(methodName), LABEL);
	}

	// ------------------------------------------------------------------------

	public static void generate(Collection<ClassUsage> classUsages)
	{
//		Map<String, Collection<ClassUsage>> classToMethod = new HashMap<String, Collection<ClassUsage>>();
//		Map<String, Collection<ClassUsage>> methodToClasses = new HashMap<String, Collection<ClassUsage>>();
//
//		for (ClassUsage classUsage : classUsages)
//		{
//			Collection<ClassUsage> classes;
//
//			/* class to methods */
//
//			if (classToMethod.containsKey(classUsage.getElement()))
//			{
//				classes = classToMethod.get(classUsage.getElement());
//			}
//			else
//			{
//				classes = new ArrayList<ClassUsage>();
//				classToMethod.put(classUsage.getElement(), classes);
//			}
//			classes.add(classUsage);
//
//			/* method to classes */
//
//			if (methodToClasses.containsKey(classUsage.getMethod()))
//			{
//				classes = methodToClasses.get(classUsage.getMethod());
//			}
//			else
//			{
//				classes = new ArrayList<ClassUsage>();
//				methodToClasses.put(classUsage.getMethod(), classes);
//			}
//			classes.add(classUsage);
//		}

		ClassUsageGraph.classUsages = classUsages;

		UILib.setPlatformLookAndFeel();

		final ClassUsageGraph view = new ClassUsageGraph(getClassUsageGraph());

		view.updateElementsCombo();

		staticFrame = new JFrame("Class Usage Graph");
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
		nodes.addColumn("methodName", String.class);
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

	private static Graph getClassUsageGraph()
	{
		Graph g = new Graph(buildNodes(), buildEdges(), true, "id1", "id2");

		Node root = g.addNode();
		root.set("id", root.getRow());
		root.set("name", "Application");
		root.set("methodName", null);
		root.set("color", getColor(null));
		root.set("label", "Application");

		Map<String, Integer> nameToId = new HashMap<String, Integer>();

		Set<Node> toRoot = new HashSet<Node>();

		for (ClassUsage classUsage : classUsages)
		{
			Node node1;

			if (nameToId.containsKey(classUsage.getElement()))
			{
				node1 = g.getNode(nameToId.get(classUsage.getElement()));
			}
			else
			{
				node1 = g.addNode();
				node1.set("id", node1.getRow());
				node1.set("name", classUsage.getElement());
				node1.set("methodName", null);
				node1.set("color", getColor(classUsage.getType()));
				node1.set("label", classUsage.getElementLabel());

				nameToId.put(classUsage.getElement(), node1.getRow());

				toRoot.add(node1);
			}

			Node node2;

			if (nameToId.containsKey(classUsage.getDependency()))
			{
				node2 = g.getNode(nameToId.get(classUsage.getDependency()));

				toRoot.remove(node2);
			}
			else
			{
				node2 = g.addNode();
				node2.set("id", node2.getRow());
				node2.set("name", classUsage.getDependency());
				node2.set("methodName", null);
				node2.set("color", getColor(classUsage.getDependencyType()));
				node2.set("label", classUsage.getDependencyLabel());

				nameToId.put(classUsage.getDependency(), node2.getRow());
			}

			g.addEdge(node1.getRow(), node2.getRow());
		}

		// fixing the graph edges
		for (Node node : toRoot)
		{
			g.addEdge(root.getRow(), node.getRow());
		}

		return g;
	}

	private static Graph getElementUsageGraph(String elementName, Map<String, Collection<ClassUsage>> classToMethod, Map<String, Collection<ClassUsage>> methodToClasses)
	{
		Graph g = new Graph(buildNodes(), buildEdges(), true, "id1", "id2");


		return g;
	}

	private static Graph getMethodUsageGraph(String methodName)
	{
		Graph g = new Graph(buildNodes(), buildEdges(), true, "id1", "id2");

		Node root = g.addNode();
		root.set("id", root.getRow());
		root.set("name", methodName);
		root.set("methodName", methodName);
		root.set("color", getColor(null));
		root.set("label", methodName);

		ClassUsage rootClassUsage = null;

		Map<String, Collection<ClassUsage>> classToMethod = new HashMap<String, Collection<ClassUsage>>();

		for (ClassUsage classUsage : classUsages)
		{
			Collection<ClassUsage> classes;

			/* class to methods */

			if (classToMethod.containsKey(classUsage.getElement()))
			{
				classes = classToMethod.get(classUsage.getElement());
			}
			else
			{
				classes = new ArrayList<ClassUsage>();
				classToMethod.put(classUsage.getElement(), classes);
			}
			classes.add(classUsage);

			/* method to classes */

			if (classUsage.getMethod().equals(methodName))
				rootClassUsage = classUsage;
		}

		Map<String, Integer> nameToId = new HashMap<String, Integer>();

		Queue<String> queue = new LinkedList<String>();
		queue.add(rootClassUsage.getElement());

		boolean isRoot = true;

		while (!queue.isEmpty())
		{
			String elementName = queue.remove();

			System.out.println(elementName);

			Node node1 = null;

			if (isRoot)
			{
				node1 = root;
				isRoot = false;
			}
			else if (nameToId.containsKey(elementName))
				node1 = g.getNode(nameToId.get(elementName));

			Collection<ClassUsage> classes = classToMethod.get(elementName);

			if (node1 != null && classes != null && rootClassUsage != null)
			{
				for (ClassUsage classUsage : classes)
				{
					if (isRoot && !classUsage.getMethod().equals(rootClassUsage.getMethod()))
						continue;

					Node node2;

					if (nameToId.containsKey(classUsage.getDependency()))
					{
						node2 = g.getNode(nameToId.get(classUsage.getDependency()));
					}
					else
					{
						node2 = g.addNode();
						node2.set("id", node2.getRow());
						node2.set("name", classUsage.getDependencyLabel());
						node2.set("methodName", null);
						node2.set("color", getColor(classUsage.getDependencyType()));
						node2.set("label", classUsage.getDependency());

						nameToId.put(classUsage.getDependency(), node2.getRow());
					}

					g.addEdge(node1.getRow(), node2.getRow());
					queue.add(classUsage.getDependency());
				}
			}
		}

		return g;
	}

	private static int getColor(String type)
	{
		int color = 0;

		if (type != null)
		{
			if (type.equals(ClassUsage.USE_CASE))
				color = 1;
			else if (type.equals(ClassUsage.SERVICE))
				color = 2;
			else if (type.equals(ClassUsage.CLASS))
				color = 3;
			else if (type.equals(ClassUsage.ENTITY))
				color = 4;
		}

		return color;
	}
}
