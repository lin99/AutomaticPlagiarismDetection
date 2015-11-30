import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Reports extends JFrame {
	public Reports(String data[][], String columns[]) {
		
		TableModel model = new DefaultTableModel(data, columns);
		JTable table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e){
				int i = table.getSelectedRow();
				int j = table.getSelectedColumn();
				System.out.println(i + " " + j);
				if( j <= 1 ){
					App.setSourceCode( Integer.parseInt( data[i][j] ) );
				}
			}
		});
		
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(table, BorderLayout.CENTER);
		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		add(tablePanel);
		setVisible(true);
		pack();
	}
}
