package clustering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicClusteringWindow extends JFrame {
	
	double points[][];
	int clusters[];
	Color colors[];
	double radius;
	

	public BasicClusteringWindow(double[][] points, int[] clusters, int k, double radius) throws HeadlessException {
		this.points = points;
		this.clusters = clusters;
		this.colors = new Color[k];
		this.radius = radius;
		for(int i=0; i<colors.length; i++)
			colors[i] = randomColor();
		
		add( new ClusteringPainter() );
		
		setSize(new Dimension(800, 800));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			}
		}
	}
	
	
}
