package br.ufrj.cos.pinel.ligeiro.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.ufrj.cos.pinel.ligeiro.common.Util;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.force.ForceSimulator;
import prefuse.util.ui.JForcePanel;
import prefuse.util.ui.JValueSlider;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

/**
 * @author Roque Pinel
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public abstract class GraphView extends JPanel
{
	protected static final String graph = "graph";
	protected static final String nodes = "graph.nodes";
	protected static final String edges = "graph.edges";

	protected static final String LABEL = "label";

	protected Graph g;

	protected Visualization m_vis;

	protected JComboBox elementsCombo = new JComboBox();
	protected JComboBox methodsCombo = new JComboBox();

	public GraphView(Graph g, String label)
	{
		super(new BorderLayout());

		// create a new, empty visualization for our data
		m_vis = new Visualization();

		this.g = g;

		// --------------------------------------------------------------------
		// set up the renderers

		LabelRenderer tr = new LabelRenderer();
		tr.setRoundedCorner(8, 8);
		m_vis.setRendererFactory(new DefaultRendererFactory(tr));

		// --------------------------------------------------------------------
		// register the data with a visualization

		// adds graph to visualization and sets renderer label field
		setGraph(g, label);

		// fix selected focus nodes
		TupleSet focusGroup = m_vis.getGroup(Visualization.FOCUS_ITEMS);
		focusGroup.addTupleSetListener(new TupleSetListener()
		{
			public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem)
			{
				for (int i = 0; i < rem.length; ++i)
					((VisualItem) rem[i]).setFixed(false);
				for (int i = 0; i < add.length; ++i)
				{
					((VisualItem) add[i]).setFixed(false);
					((VisualItem) add[i]).setFixed(true);
				}
				if (ts.getTupleCount() == 0)
				{
					ts.addTuple(rem[0]);
					((VisualItem) rem[0]).setFixed(false);
				}
				m_vis.run("draw");
			}
		});

		// --------------------------------------------------------------------
		// create actions to process the visual data

		int hops = 30;
		final GraphDistanceFilter filter = new GraphDistanceFilter(graph, hops);

		int[] palette = new int[5];
		palette[0] = ColorLib.rgb(249, 235, 39); // yellow
		palette[1] = ColorLib.rgb(0, 134, 139); // blue 
		palette[2] = ColorLib.rgb(35, 142, 35); // green 
		palette[3] = ColorLib.rgb(159, 121, 138); // purple
		palette[4] = ColorLib.rgb(119, 136, 153); // snow

		DataColorAction fill = new DataColorAction(nodes, "color",Constants.NUMERICAL, VisualItem.FILLCOLOR, palette);
		fill.add(VisualItem.FIXED, ColorLib.rgb(255, 100, 100));
		fill.add(VisualItem.HIGHLIGHT, ColorLib.rgb(255, 200, 125));

		ActionList draw = new ActionList();
		draw.add(filter);
		draw.add(fill);
		draw.add(new ColorAction(nodes, VisualItem.STROKECOLOR, 0));
		draw.add(new ColorAction(nodes, VisualItem.TEXTCOLOR, ColorLib.rgb(0, 0, 0)));
		draw.add(new ColorAction(edges, VisualItem.FILLCOLOR, ColorLib.gray(200)));
		draw.add(new ColorAction(edges, VisualItem.STROKECOLOR, ColorLib.gray(200)));

		ActionList animate = new ActionList(Activity.INFINITY);
		animate.add(new ForceDirectedLayout(graph));
		animate.add(fill);
		animate.add(new RepaintAction());

		// finally, we register our ActionList with the Visualization.
		// we can later execute our Actions by invoking a method on our
		// Visualization, using the name we've chosen below.
		m_vis.putAction("draw", draw);
		m_vis.putAction("layout", animate);

		m_vis.runAfter("draw", "layout");

		// --------------------------------------------------------------------
		// set up a display to show the visualization

		Display display = new Display(m_vis);
		display.setSize(700, 700);
		display.pan(350, 350);
		display.setForeground(Color.GRAY);
		display.setBackground(Color.WHITE);

		// main display controls
		display.addControlListener(new FocusControl(1));
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new ZoomToFitControl());
		display.addControlListener(new NeighborHighlightControl());

		// overview display
		// Display overview = new Display(vis);
		// overview.setSize(290,290);
		// overview.addItemBoundsListener(new FitOverviewListener());

		display.setForeground(Color.GRAY);
		display.setBackground(Color.WHITE);

		// --------------------------------------------------------------------
		// launch the visualization

		// create a panel for editing force values
		ForceSimulator fsim = ((ForceDirectedLayout) animate.get(0)).getForceSimulator();
		JForcePanel fpanel = new JForcePanel(fsim);

		// JPanel opanel = new JPanel();
		// opanel.setBorder(BorderFactory.createTitledBorder("Overview"));
		// opanel.setBackground(Color.WHITE);
		// opanel.add(overview);

		final JValueSlider slider = new JValueSlider("Distance", 0, hops, hops);
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				filter.setDistance(slider.getValue().intValue());
				m_vis.run("draw");
			}
		});
		slider.setBackground(Color.WHITE);
		slider.setPreferredSize(new Dimension(300, 30));
		slider.setMaximumSize(new Dimension(300, 30));

		Box cf = new Box(BoxLayout.Y_AXIS);
		cf.add(slider);
		cf.setBorder(BorderFactory.createTitledBorder("Connectivity Filter"));
		fpanel.add(cf);

		elementsCombo.setName("false");
		elementsCombo.setBackground(Color.WHITE);
		elementsCombo.setPreferredSize(new Dimension(400,50));
		elementsCombo.addActionListener(new ElementsComboListener());
		elementsCombo.setMaximumSize(new Dimension(400,50));
		elementsCombo.setBorder(BorderFactory.createTitledBorder("Elements"));
		elementsCombo.setFont(new Font("Tahoma", Font.PLAIN, 9));
		fpanel.add(elementsCombo);

		methodsCombo.setName("false");
		methodsCombo.setBackground(Color.WHITE);
		methodsCombo.setPreferredSize(new Dimension(400,50));
		methodsCombo.addActionListener(new MethodsComboListener());
		methodsCombo.setMaximumSize(new Dimension(400,50));
		methodsCombo.setBorder(BorderFactory.createTitledBorder("Methods"));
		methodsCombo.setFont(new Font("Tahoma", Font.PLAIN, 9));
		fpanel.add(methodsCombo);

		display.addControlListener(new ControlAdapter()
		{
			public void itemPressed(VisualItem item, java.awt.event.MouseEvent e)
			{
				String nodeName = item.getString("name");
				updateMethodsCombo(nodeName);
			}

			public void itemEntered(VisualItem item, MouseEvent e)
			{
				// empty
			}

			public void itemExited(VisualItem item, MouseEvent e)
			{
				// empty
			}
		});

		final JButton button = new JButton("Restore Graph");
		button.setEnabled(true);
		button.setToolTipText("Restore graph to application structrure");
		button.setPreferredSize(new Dimension(110, 20));
		button.setMaximumSize(new Dimension(110, 50));
		button.setBackground(Color.WHITE);
		button.setHorizontalAlignment(fpanel.getX());
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restoreApplication();
			}
		});
		fpanel.add(button);

		// fpanel.add(opanel);

		fpanel.add(Box.createVerticalGlue());

		// create a new JSplitPane to present the interface
		JSplitPane split = new JSplitPane();
		split.setLeftComponent(display);
		split.setRightComponent(fpanel);
		split.setOneTouchExpandable(true);
		split.setContinuousLayout(false);
		split.setDividerLocation(700);

		// now we run our action list
		m_vis.run("draw");

		add(split);
	}

	public abstract void restoreApplication();

	public abstract void updateMethodsCombo(String elementName);

	public abstract void createElementGraph(String elementName);
	public abstract void createMethodGraph(String methodName);

	protected void setGraph(Graph g, String label)
	{
		// update labeling
		DefaultRendererFactory drf = (DefaultRendererFactory) m_vis.getRendererFactory();
		((LabelRenderer) drf.getDefaultRenderer()).setTextField(label);

		// update graph
		m_vis.removeGroup(graph);
		VisualGraph vg = m_vis.addGraph(graph, g);
		m_vis.setValue(edges, null, VisualItem.INTERACTIVE, Boolean.FALSE);
		VisualItem f = (VisualItem) vg.getNode(0);
		m_vis.getGroup(Visualization.FOCUS_ITEMS).setTuple(f);
		f.setFixed(false);
	}

	public class ElementsComboListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JComboBox combo = (JComboBox) e.getSource();
			String elementName = (String) combo.getSelectedItem();

			if(!Util.isEmptyOrNull(elementName))
			{
				createElementGraph(elementName);
			}
		}
	}

	public class MethodsComboListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JComboBox combo = (JComboBox) e.getSource();
			String methodName = (String) combo.getSelectedItem();

			if(!Util.isEmptyOrNull(methodName))
			{
				createMethodGraph(methodName);
			}
		}
	}
}
