package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;

import controller.App;

public class BasicClusteringWindow extends JFrame {

	double points[][];
	int clusters[];
	Color colors[];
	double radius;
	boolean alreadyAnalyzed;
	SettingsFrame settingsFrame;
	ClusteringPainter clusteringPainter;

	public BasicClusteringWindow() throws HeadlessException {
		super("Automatic Plagiarism Detection");
		alreadyAnalyzed = false;
		addJMenuBar();
		clusteringPainter = new ClusteringPainter();
		add(clusteringPainter);
		setSize(new Dimension(600, 600));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	void toogleSettingsFrame() {
		if (settingsFrame == null)
			settingsFrame = new SettingsFrame();

		settingsFrame.setVisible(true);
	}

	class SourceCodeFrame extends JFrame {
		public SourceCodeFrame(String title, String code) {
			super(title);
			setSize(600, 700);
			setVisible(true);
			JTextPane textArea = new JTextPane(){
				// Override getScrollableTracksViewportWidth
				  // to preserve the full width of the text
				  public boolean getScrollableTracksViewportWidth() {
				    Component parent = getParent();
				    ComponentUI ui = getUI();

				    return parent != null ? (ui.getPreferredSize(this).width <= parent
				        .getSize().width) : true;
				  }
			};
			//textArea.setContentType("text/html"); -> If you want to use html formatting 
			textArea.setText( code );
		    
			textArea.setBounds(0, 0, getWidth(), getHeight());
			
			JScrollPane scrollPane = new JScrollPane(textArea);

			// In order to ensure the scroll Pane object appears in your window, 
			// set a preferred size to it!
			scrollPane.setPreferredSize(new Dimension(380, 100));

			// Lines will be wrapped if they are too long to fit within the 
			// allocated width
//			textArea.setLineWrap(false);

			// Lines will be wrapped at word boundaries (whitespace) if they are 
			// too long to fit within the allocated width
//			textArea.setWrapStyleWord(true);

			// Assuming this is the chat client's window where we read text sent out 
			// and received, we don't want our Text Area to be editable!
			textArea.setEditable(false);

			// We also want a vertical scroll bar on our pane, as text is added to it
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			//adds and centers the scroll pane to the frame
			this.add(scrollPane, SwingConstants.CENTER);
			textArea.repaint();
		}
	}

	class SettingsFrame extends JFrame {
		JTextField clustersField;

		public SettingsFrame() {
			super("Settings");
			setSize(300, 400);
			setVisible(true);

			JPanel parentPanel = new JPanel();
			parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));

			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());

			panel.add(new JLabel("Clusters: "), BorderLayout.WEST);
			clustersField = new JTextField(5);
			panel.add(clustersField, BorderLayout.EAST);
			parentPanel.add(panel);
			JButton acceptButton = new JButton("Accept");
			acceptButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					App.setClusters(Integer.parseInt(getClusteringField()));
					setVisible(false);

					new Thread() {
						@Override
						public void run() {
							getData();
							repaint();
						}
					}.start();
				}
			});
			parentPanel.add(acceptButton);

			add(parentPanel);
		}

		String getClusteringField() {
			return clustersField.getText();
		}
	}

	void getData() {
		App.computeModel();
		this.points = App.getPoints();
		this.clusters = App.getClusters();
		this.colors = new Color[this.clusters.length];
		this.radius = App.getRadius();
		for (int i = 0; i < colors.length; i++)
			colors[i] = randomColor();

		alreadyAnalyzed = true;
		clusteringPainter.clear();
		for (int i = 0; i < clusters.length; i++) {
			// g.setColor(colors[ clusters[i] ]);
			Ellipse2D.Double sh = new Ellipse2D.Double(points[0][i] - radius + 15.0, points[1][i] - radius + 15.0,
					radius * 2, radius * 2);
			clusteringPainter.addPoint(sh);
			// ((Graphics2D)g).fill(sh);
			// ((Graphics2D)g).drawString(i+"", (int)points[0][i],
			// (int)points[1][i]);
		}
	}

	void addJMenuBar() {
		JMenu jmFile = new JMenu("File");
		jmFile.add(new AbstractAction("Analyze") {

			@Override
			public void actionPerformed(ActionEvent ae) {

				new Thread() {
					@Override
					public void run() {
						getData();
						repaint();
					}
				}.start();

			}
		});

		jmFile.add(new AbstractAction("Settings") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				toogleSettingsFrame();
			}
		});

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(jmFile);
		add(menuBar, BorderLayout.NORTH);
	}

	public static synchronized Color randomColor() {

		return new Color(0xff000000 + 256 * 256 * ThreadLocalRandom.current().nextInt(256)
				+ 256 * ThreadLocalRandom.current().nextInt(256) + ThreadLocalRandom.current().nextInt(256));
	}

	class ClusteringPainter extends JPanel {

		ArrayList<Ellipse2D.Double> dataPoints;

		Point2D buildPoint(Ellipse2D.Double circle) {
			return new Point2D.Double(circle.getCenterX(), circle.getCenterY());
		}

		public ClusteringPainter() {
			dataPoints = new ArrayList<>();
			addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					Point p = arg0.getPoint();
					int idx = -1;
					for (int i = 0; i < dataPoints.size(); i++) {
						if (dataPoints.get(i).contains(p)
								&& (idx == -1 || p.distance(buildPoint(dataPoints.get(idx))) > p
										.distance(buildPoint(dataPoints.get(i))))) {
							idx = i;
						}
					}
					if( idx != -1 ){
						new SourceCodeFrame("Source code", App.getSourceCode(idx) );
					}
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
			
			
			setBackground(Color.WHITE);
		}

		public void clear() {
			dataPoints.clear();
		}

		public void addPoint(Ellipse2D.Double circle) {
			dataPoints.add(circle);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (alreadyAnalyzed == false)
				return;
			Graphics2D g2 = (Graphics2D) g;
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHints(rh);

			for (int i = 0; i < dataPoints.size(); i++) {
				g.setColor(colors[clusters[i]]);
				Ellipse2D.Double sh = dataPoints.get(i);
				((Graphics2D) g).fill(sh);
				((Graphics2D) g).drawString(i + "", (int) points[0][i], (int) points[1][i]);
			}
		}
	}

}
