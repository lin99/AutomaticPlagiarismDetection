import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.BoxLayout;

public class MainFrame extends JFrame {
	JTextPane textPane;
	private JPanel contentPane;
	double points[][];
	int clusters[];
	Color colors[];
	double radius;
	ArrayList<Ellipse2D.Double> dataPoints;
	boolean alreadyComputed;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public static synchronized Color randomColor() {

		return new Color(0xff000000 + 256 * 256 * ThreadLocalRandom.current().nextInt(256)
				+ 256 * ThreadLocalRandom.current().nextInt(256) + ThreadLocalRandom.current().nextInt(256));
	}
	
	void getData() {
		App.computeModel();
		this.points = App.getPoints();
		this.clusters = App.getClusters();
		this.colors = new Color[this.clusters.length];
		this.radius = App.getRadius();
		for (int i = 0; i < colors.length; i++)
			colors[i] = randomColor();

		dataPoints.clear();
		for (int i = 0; i < clusters.length; i++) {
			Ellipse2D.Double sh = new Ellipse2D.Double(points[0][i] - radius + 15.0, points[1][i] - radius + 15.0,
					radius * 2, radius * 2);
			dataPoints.add(sh);
			
		}
	}
	
	Point2D buildPoint(Ellipse2D.Double circle) {
		return new Point2D.Double(circle.getCenterX(), circle.getCenterY());
	}


	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{241, 241, 241, 0};
		gbl_contentPane.rowHeights = new int[]{445, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		dataPoints = new ArrayList<>();
		alreadyComputed = false;
		
		JPanel clusterPanel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				if(alreadyComputed == false) return;
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
		};
		
		clusterPanel.addMouseListener(new MouseListener() {

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
					textPane.setText( App.getSourceCode(idx) );
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
		
		clusterPanel.setBackground(Color.WHITE);
		
		GridBagConstraints gbc_clusterPanel = new GridBagConstraints();
		gbc_clusterPanel.fill = GridBagConstraints.BOTH;
		gbc_clusterPanel.insets = new Insets(0, 0, 0, 5);
		gbc_clusterPanel.gridx = 0;
		gbc_clusterPanel.gridy = 0;
		contentPane.add(clusterPanel, gbc_clusterPanel);
		
		JPanel codePanel = new JPanel();
		codePanel.setBackground(Color.RED);
		GridBagConstraints gbc_codePanel = new GridBagConstraints();
		gbc_codePanel.fill = GridBagConstraints.BOTH;
		gbc_codePanel.insets = new Insets(0, 0, 0, 5);
		gbc_codePanel.gridx = 1;
		gbc_codePanel.gridy = 0;
		contentPane.add(codePanel, gbc_codePanel);
		codePanel.setLayout(new BoxLayout(codePanel, BoxLayout.X_AXIS));
		
		textPane = new JTextPane(){
			// Override getScrollableTracksViewportWidth
			  // to preserve the full width of the text
			  public boolean getScrollableTracksViewportWidth() {
			    Component parent = getParent();
			    ComponentUI ui = getUI();

			    return parent != null ? (ui.getPreferredSize(this).width <= parent
			        .getSize().width) : true;
			  }
		};
		
		
		textPane.setEditable(false);
		//codePanel.add(textPane);
	
		JScrollPane codeScroll = new JScrollPane(textPane);
		codePanel.add(codeScroll);
		
		JPanel optionsPanel = new JPanel();
		GridBagConstraints gbc_optionsPanel = new GridBagConstraints();
		gbc_optionsPanel.fill = GridBagConstraints.BOTH;
		gbc_optionsPanel.gridx = 2;
		gbc_optionsPanel.gridy = 0;
		contentPane.add(optionsPanel, gbc_optionsPanel);
		GridBagLayout gbl_optionsPanel = new GridBagLayout();
		gbl_optionsPanel.columnWidths = new int[]{0, 0};
		gbl_optionsPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_optionsPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_optionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		optionsPanel.setLayout(gbl_optionsPanel);
		
		JLabel folderPathLabel = new JLabel("Ruta carpeta:");
		folderPathLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_folderPathLabel = new GridBagConstraints();
		gbc_folderPathLabel.anchor = GridBagConstraints.WEST;
		gbc_folderPathLabel.insets = new Insets(0, 0, 5, 0);
		gbc_folderPathLabel.gridx = 0;
		gbc_folderPathLabel.gridy = 0;
		optionsPanel.add(folderPathLabel, gbc_folderPathLabel);
		
		JLabel pathLabel = new JLabel("Ruta");
		GridBagConstraints gbc_pathLabel = new GridBagConstraints();
		gbc_pathLabel.insets = new Insets(0, 0, 5, 0);
		gbc_pathLabel.anchor = GridBagConstraints.WEST;
		gbc_pathLabel.gridx = 0;
		gbc_pathLabel.gridy = 1;
		optionsPanel.add(pathLabel, gbc_pathLabel);
		
		JButton changeFolderButton = new JButton("Cambiar carpeta");
		GridBagConstraints gbc_changeFolderButton = new GridBagConstraints();
		gbc_changeFolderButton.insets = new Insets(0, 0, 5, 0);
		gbc_changeFolderButton.gridx = 0;
		gbc_changeFolderButton.gridy = 2;
		optionsPanel.add(changeFolderButton, gbc_changeFolderButton);
		
		JButton configurationButton = new JButton("Configuración");
		GridBagConstraints gbc_configurationButton = new GridBagConstraints();
		gbc_configurationButton.insets = new Insets(0, 0, 5, 0);
		gbc_configurationButton.gridx = 0;
		gbc_configurationButton.gridy = 3;
		optionsPanel.add(configurationButton, gbc_configurationButton);
		
		JLabel chooseMetricLabel = new JLabel("Seleccionar métrica:");
		GridBagConstraints gbc_chooseMetricLabel = new GridBagConstraints();
		gbc_chooseMetricLabel.anchor = GridBagConstraints.WEST;
		gbc_chooseMetricLabel.insets = new Insets(0, 0, 5, 0);
		gbc_chooseMetricLabel.gridx = 0;
		gbc_chooseMetricLabel.gridy = 4;
		optionsPanel.add(chooseMetricLabel, gbc_chooseMetricLabel);
		
		JComboBox metricComboBox = new JComboBox();
		metricComboBox.setModel(new DefaultComboBoxModel(new String[] {"Edit distance", "Jaro Winkler distance", "Longest common subsequence"}));
		GridBagConstraints gbc_metricComboBox = new GridBagConstraints();
		gbc_metricComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_metricComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_metricComboBox.gridx = 0;
		gbc_metricComboBox.gridy = 5;
		optionsPanel.add(metricComboBox, gbc_metricComboBox);
		
		JButton analyzeButton = new JButton("Analizar");
		analyzeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getData();
				App.selectMetric(metricComboBox.getSelectedIndex());
				alreadyComputed = true;
				App.repaintView();
				
			}
		});
		GridBagConstraints gbc_analyzeButton = new GridBagConstraints();
		gbc_analyzeButton.insets = new Insets(0, 0, 5, 0);
		gbc_analyzeButton.gridx = 0;
		gbc_analyzeButton.gridy = 6;
		optionsPanel.add(analyzeButton, gbc_analyzeButton);
		
		JLabel similarityPercentageLabel = new JLabel("Porcentaje de similitud:");
		similarityPercentageLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_similarityPercentageLabel = new GridBagConstraints();
		gbc_similarityPercentageLabel.insets = new Insets(0, 0, 5, 0);
		gbc_similarityPercentageLabel.anchor = GridBagConstraints.WEST;
		gbc_similarityPercentageLabel.gridx = 0;
		gbc_similarityPercentageLabel.gridy = 7;
		optionsPanel.add(similarityPercentageLabel, gbc_similarityPercentageLabel);
		
		JSpinner similarityPercentageSpinner = new JSpinner();
		GridBagConstraints gbc_similarityPercentageSpinner = new GridBagConstraints();
		gbc_similarityPercentageSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_similarityPercentageSpinner.gridx = 0;
		gbc_similarityPercentageSpinner.gridy = 8;
		optionsPanel.add(similarityPercentageSpinner, gbc_similarityPercentageSpinner);
		
		JButton createReportButton = new JButton("Generar reportes");
		GridBagConstraints gbc_createReportButton = new GridBagConstraints();
		gbc_createReportButton.gridx = 0;
		gbc_createReportButton.gridy = 9;
		optionsPanel.add(createReportButton, gbc_createReportButton);
	}

	

}
