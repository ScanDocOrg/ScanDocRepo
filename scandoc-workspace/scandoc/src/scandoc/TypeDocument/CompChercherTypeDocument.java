package scandoc.TypeDocument;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import java.sql.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class CompChercherTypeDocument extends Composite {
	public Table table; 
	private ResultSet rs;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompChercherTypeDocument(Composite parent, int style) {
		super(parent, style);
		setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		
		Group group = new Group(this, SWT.NONE);
		group.setText("Chercher par type d'individu");
		group.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		group.setBounds(10, 10, 440, 480);
		
		//
		// Table pre-rendering
		//
		table = new Table (group, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = {"ID", "Nom"};
		
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
			table.getColumn (i).setWidth(215);
		}
		populateDocumentTypeTable();
		table.setBounds(10, 20, 420, 450);
	}

	private void populateDocumentTypeTable() {
		Connection conn = null;
		Statement stmt =null;
		String query = "SELECT * FROM TYPE_DOCUMENT";
		try {
			String url = "jdbc:sqlite:V:/Code/test/test-folder-2/database/scandocdb.db";
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			if(conn != null) {
				
				int items = table.getItemCount();
				for(int i=items; i>0; i--) {
						table.remove(i-1);
				}
				table.clearAll();
				
				while(rs.next()) {
					TableItem item = new TableItem (table, SWT.NONE);
					item.setText (0, String.valueOf(rs.getInt("TYPE_DOCUMENT_ID")));
					item.setText (1, rs.getString("NOM"));	
				}
				
				conn.close();
				table.getParent().layout();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
		
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
