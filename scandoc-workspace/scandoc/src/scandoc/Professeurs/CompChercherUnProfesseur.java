package scandoc.Professeurs;

import java.sql.Connection;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.*;
import java.sql.*;
//import org.sqlite.*;

public class CompChercherUnProfesseur extends Composite {
	public Text textNom;
	public Text textMatricule;
	public Table table;
	ResultSet rs;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompChercherUnProfesseur(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		setLayout(null);

		////////////////////////////////////////////////////////////////////////////////////////
		//
		//Chercher par nom
		//
		Group grpChercherParNom = new Group(this, SWT.BORDER | SWT.SHADOW_OUT);
		grpChercherParNom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpChercherParNom.setText("Chercher par nom");
		grpChercherParNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpChercherParNom.setBounds(10, 10, 440, 98);
		
		Label label = new Label(grpChercherParNom, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		label.setText("Nom :");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setBounds(10, 22, 40, 21);
		
		textNom = new Text(grpChercherParNom, SWT.BORDER);
		textNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		textNom.setBounds(59, 22, 371, 23);
		
	////// Button Chercher par nom
		Button btnNomChercher = new Button(grpChercherParNom, SWT.NONE);
		btnNomChercher.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String textNomString = textNom.getText().trim().toLowerCase();
				String sqlQuery = "Select * from PROFESSEUR where PROFESSEUR.NOM='" + textNomString +"'";
				selectQuery(sqlQuery);	
			}
		});
		btnNomChercher.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNomChercher.setText("Chercher");
		btnNomChercher.setBounds(168, 63, 75, 25);
		
		Group grpChercherParMatricule = new Group(this, SWT.BORDER | SWT.SHADOW_OUT);
		grpChercherParMatricule.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpChercherParMatricule.setText("Chercher par matricule");
		grpChercherParMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpChercherParMatricule.setBounds(10, 120, 440, 98);
		
		Label label_2 = new Label(grpChercherParMatricule, SWT.NONE);
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		label_2.setText("Matricule :");
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_2.setBounds(10, 22, 75, 21);
		
		textMatricule = new Text(grpChercherParMatricule, SWT.BORDER);
		textMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		textMatricule.setBounds(91, 22, 339, 23);
		
		
	////// Button Chercher par Matricule
		Button buttonMatriculeChercher = new Button(grpChercherParMatricule, SWT.NONE);
		buttonMatriculeChercher.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String textMatriculeString = textMatricule.getText().trim().toLowerCase();
				String sqlQuery = "Select * from PROFESSEUR where PROFESSEUR.MATRICULE='" + textMatriculeString +"'";
				selectQuery(sqlQuery);	
			}
		});
		buttonMatriculeChercher.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		buttonMatriculeChercher.setText("Chercher");
		buttonMatriculeChercher.setBounds(168, 63, 75, 25);
		
		Group grpRsultat = new Group(this, SWT.BORDER | SWT.SHADOW_OUT);
		grpRsultat.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpRsultat.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpRsultat.setText("R\u00E9sultat");
		grpRsultat.setBounds(10, 224, 440, 266);
		
		////////////////////////////////////////////////////////////////////////////////////////
		//
		//Table
		//
		
		table = new Table (grpRsultat, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 23, 420, 233);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		
		String[] titles = {"Matricule", "Nom", "Prenom"};
		
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
			table.getColumn (i).setWidth(147);
		}
	}

	public void selectQuery(String query) {
		Connection conn = null;
		Statement stmt =null;
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
					rs.getInt("IDPROFESSEUR");
					item.setText (0, rs.getString("MATRICULE"));
					item.setText (1, rs.getString("NOM"));
					item.setText (2, rs.getString("PRENOM"));	
				}
				
				conn.close();
				table.getParent().layout();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
	}
		
	
	public void refresh() {
		int items = table.getItemCount();
		for(int i=items; i>0; i--) {
				table.remove(i-1);
		}
		textMatricule.setText("");
		textNom.setText("");
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
