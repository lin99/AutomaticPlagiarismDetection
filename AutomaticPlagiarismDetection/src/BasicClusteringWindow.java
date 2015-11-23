

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BasicClusteringWindow extends JFrame {
	
	double points[][];
	int clusters[];
	Color colors[];
	double radius;
	boolean alreadyAnalyzed;
	SettingsFrame settingsFrame;

	public BasicClusteringWindow() throws HeadlessException {
		super("Automatic Plagiarism Detection");
		alreadyAnalyzed = false;
		addJMenuBar();
		add( new ClusteringPainter() );
		setSize(new Dimension(600, 600));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	void toogleSettingsFrame(){
		if( settingsFrame == null )
			settingsFrame = new SettingsFrame();
		
		settingsFrame.setVisible(true);
	}
	
	class SettingsFrame extends JFrame{
		JTextField clustersField;
		
		public SettingsFrame(){
			super("Settings");
			setSize(300, 400);
			setVisible(true);
			
			JPanel parentPanel = new JPanel();
			parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			
			panel.add(new JLabel("Clusters: "),BorderLayout.WEST);
			clustersField = new JTextField(5);
			panel.add(clustersField,BorderLayout.EAST);
			parentPanel.add(panel);
			
//			panel = new JPanel();
//			panel.setLayout(new BorderLayout());
//			panel.add(new JLabel("Otra cosa: "),BorderLayout.WEST);
//			JTextField field = new JTextField(5);
//			panel.add(field,BorderLayout.EAST);
//			parentPanel.add(panel);
			
			JButton acceptButton = new JButton("Accept");
			acceptButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					App.setClusters( Integer.parseInt( getClusteringField() ) );
					setVisible(false);
				}
			});
			parentPanel.add(acceptButton);
			
			
			add(parentPanel);
		}
		
		String getClusteringField(){
			return clustersField.getText();
		}
	}
	
	void getData(){
		App.computeModel();
		this.points = App.getPoints();
		this.clusters = App.getClusters();
		this.colors = new Color[this.clusters.length];
		this.radius = App.getRadius();
		for(int i=0; i<colors.length; i++)
			colors[i] = randomColor();
		
		alreadyAnalyzed = true;
	}
	
	void addJMenuBar(){
		JMenu jmFile = new JMenu( "File" );
		jmFile.add( new AbstractAction( "Analyze" ) {
			
			@Override
			public void actionPerformed( ActionEvent ae ) {
				getData();
				repaint();
			}
		});
		
		jmFile.add(new AbstractAction("Settings") {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				toogleSettingsFrame();
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add( jmFile );
		add( menuBar, BorderLayout.NORTH );
	}
	
	public static synchronized Color randomColor() {
	
	    return new Color( 0xff000000 + 256 * 256 * ThreadLocalRandom.current().nextInt(256) + 
	    		256 * ThreadLocalRandom.current().nextInt(256)
	            + ThreadLocalRandom.current().nextInt(256) );
	}



	class ClusteringPainter extends JPanel{
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if( alreadyAnalyzed == false ) return;
			Graphics2D g2 = (Graphics2D)g;
		    RenderingHints rh = new RenderingHints(
		             RenderingHints.KEY_ANTIALIASING,
		             RenderingHints.VALUE_ANTIALIAS_ON);
		    g2.setRenderingHints(rh);
			
			for(int i=0; i<clusters.length; i++){
				g.setColor(colors[ clusters[i] ]);
				Ellipse2D.Double sh = new Ellipse2D.Double(
						points[0][i] - radius + 15.0,
						points[1][i] - radius+ 15.0,
						radius*2,
						radius*2
						);
				((Graphics2D)g).fill(sh);
				((Graphics2D)g).drawString(i+"", (int)points[0][i], (int)points[1][i]);
			}
		}
	}
	
	
}
