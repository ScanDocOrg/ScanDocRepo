package scandoc.Eleves;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.*;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.*;
import java.sql.*;
import scandoc.*;
import scandoc.Fichier.XConfigFile;

public class CompChercherUnEleve extends Composite {
	public Text txtNom;
	public Text txtMatricule;
	//public Table table;
	public Table tblEleves;
	private XConfigFile configFile;
	//public String clickedMatricule;
	ResultSet rs;
	//public CompDetailEleve compDetailEleve;
	//compDetailEleve.setLayout(null);
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompChercherUnEleve(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		setLayout(null);
		configFile = MainWindow.configFile;
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
		label.setBounds(10, 22, 40, 21);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setText("Nom :");
		
		txtNom = new Text(grpChercherParNom, SWT.BORDER);
		txtNom.setBounds(59, 22, 327, 23);
		txtNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		
		//Button
		Button btnNomChercher = new Button(grpChercherParNom, SWT.NONE);
		btnNomChercher.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String textNomString = txtNom.getText().trim().toLowerCase();
				String sqlQuery = "Select * from ELEVE where ELEVE.NOM='" + textNomString +"'";
				selectEleve(sqlQuery);	
			}
		});
		btnNomChercher.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNomChercher.setBounds(168, 63, 75, 25);
		btnNomChercher.setText("Chercher");
		
		//
		//Chercher par Matricule
		//
		Group grpChercherParMatricule = new Group(this, SWT.BORDER | SWT.SHADOW_OUT);
		grpChercherParMatricule.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpChercherParMatricule.setText("Chercher par matricule");
		grpChercherParMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpChercherParMatricule.setBounds(10, 110, 440, 104);
		
		Label lblMatricule = new Label(grpChercherParMatricule, SWT.NONE);
		lblMatricule.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblMatricule.setText("Matricule :");
		lblMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblMatricule.setBounds(10, 22, 75, 21);
		
		txtMatricule = new Text(grpChercherParMatricule, SWT.BORDER);
		txtMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtMatricule.setBounds(91, 22, 295, 23);	
		
		//Button Chercher par matricule
		Button buttonMatriculeChercher = new Button(grpChercherParMatricule, SWT.NONE);
		buttonMatriculeChercher.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String textMatriculeString = txtMatricule.getText().trim().toLowerCase();
				String sqlQuery = "Select * from ELEVE where ELEVE.MATRICULE='" + textMatriculeString +"'";
				selectEleve(sqlQuery);	
			}
		});
		buttonMatriculeChercher.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		buttonMatriculeChercher.setText("Chercher");
		buttonMatriculeChercher.setBounds(168, 63, 75, 25);
		
		Group grpRsultats = new Group(this, SWT.BORDER | SWT.SHADOW_OUT);
		grpRsultats.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpRsultats.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpRsultats.setText("R\u00E9sultats");
		grpRsultats.setBounds(10, 220, 440, 270);
		
		
		tblEleves = new Table(grpRsultats, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tblEleves.setBounds(10, 22, 420, 238);
		tblEleves.setHeaderVisible(true);
		tblEleves.setLinesVisible(true);
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		tblEleves.setLayoutData(data);
		
		String[] titles = {"Matricule", "Nom", "Prenom"};
		
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (tblEleves, SWT.NONE);
			column.setText (titles [i]);
			tblEleves.getColumn (i).setWidth(147);
		}
	}
	
	public void selectEleve(String query) {
		Connection conn = null;
		Statement stmt =null;
		try {
			String url = "jdbc:sqlite:" + configFile.getFolderPath() + "/database/scandocdb.db";
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			if(conn != null) {
				int items = tblEleves.getItemCount();
				for(int i=items; i>0; i--) {
						tblEleves.remove(i-1);
				}
				tblEleves.clearAll();
				while(rs.next()) {
					TableItem item = new TableItem (tblEleves, SWT.NONE);
					rs.getInt("IDELEVE");
					item.setText (0, rs.getString("MATRICULE"));
					item.setText (1, rs.getString("NOM"));
					item.setText (2, rs.getString("PRENOM"));	
				}
				
				conn.close();
				tblEleves.getParent().layout();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
	}
	
	public void refresh(){
		txtMatricule.setText("");
		txtNom.setText("");
		
		int items = tblEleves.getItemCount();
		for(int i=items; i>0; i--) {
				tblEleves.remove(i-1);
		}
	}
	
	private Connection connect() {
        // SQLite connection string
		String url = "jdbc:sqlite:"+ configFile.getFolderPath() +"/database/scandocdb.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
