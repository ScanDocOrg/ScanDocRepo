package scandoc.Documents;

import scandoc.MainWindow;
import scandoc.Fichier.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;


public class CompChercherUnDocument extends Composite {
	//private Text txtNom;
	//private Text txtMatricule;
	//public Table table;
	public XConfigFile configFile;
	public Text txtNom;
	public Text txtMatricule;
	public Table table;
	public Button rdProfesseur;
	public Button rdEleve;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompChercherUnDocument(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		setLayout(null);
		
		Group grpChoisirUnType = new Group(this, SWT.NONE);
		grpChoisirUnType.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpChoisirUnType.setText("Choisir un type d'individu");
		grpChoisirUnType.setBounds(10, 10, 440, 55);
		
		rdProfesseur = new Button(grpChoisirUnType, SWT.RADIO);
		rdProfesseur.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		rdProfesseur.setBounds(66, 26, 90, 26);
		rdProfesseur.setText("Professeur");
		
		rdEleve = new Button(grpChoisirUnType, SWT.RADIO);
		rdEleve.setText("El\u00E8ve");
		rdEleve.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		rdEleve.setBounds(270, 26, 90, 26);
		
		Group grpChercherParNom = new Group(this, SWT.NONE);
		grpChercherParNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpChercherParNom.setText("Chercher par nom");
		grpChercherParNom.setBounds(10, 71, 440, 87);
		
		Label lblNom = new Label(grpChercherParNom, SWT.NONE);
		lblNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNom.setBounds(10, 25, 40, 20);
		lblNom.setText("Nom :");
		
		txtNom = new Text(grpChercherParNom, SWT.BORDER);
		txtNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtNom.setBounds(56, 25, 374, 21);
		
		Button btnChercherNom = new Button(grpChercherParNom, SWT.NONE);
		btnChercherNom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtNom.getText() != "" && getRadioSelection() != "null") {
					selectDocumentsByNom(txtNom.getText(), getRadioSelection());;
				}
			}
		});
		btnChercherNom.setText("Chercher");
		btnChercherNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnChercherNom.setBounds(182, 52, 75, 25);
		
		Group grpChercherParMatripule = new Group(this, SWT.NONE);
		grpChercherParMatripule.setText("Chercher par matripule");
		grpChercherParMatripule.setBounds(10, 164, 440, 81);
		
		Label lblMatricule = new Label(grpChercherParMatripule, SWT.NONE);
		lblMatricule.setText("Matricule :");
		lblMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblMatricule.setBounds(10, 21, 73, 20);
		
		txtMatricule = new Text(grpChercherParMatripule, SWT.BORDER);
		txtMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtMatricule.setBounds(89, 21, 341, 21);
		
		Button btnChercherMatricule = new Button(grpChercherParMatripule, SWT.NONE);
		btnChercherMatricule.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtMatricule.getText() != "" && getRadioSelection() != "null") {
					selectDocumentsByMatricule(txtMatricule.getText(), getRadioSelection());;
				}
			}
		});
		btnChercherMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnChercherMatricule.setBounds(182, 48, 75, 25);
		btnChercherMatricule.setText("Chercher");
		
		Group grpRsultat = new Group(this, SWT.NONE);
		grpRsultat.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpRsultat.setText("R\u00E9sultat");
		grpRsultat.setBounds(10, 251, 440, 239);
		
		table = new Table(grpRsultat, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 25, 420, 204);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		configFile = MainWindow.configFile;
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		
		String[] titles = {"ID document", "Matricule", "Nom", "Type Document", "Document"};
		
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
			table.getColumn (i).setWidth(147);
		}
	}

	
	private void selectDocumentsByMatricule(String matricule,String type) {
		String query = "";
		if(type == "professeur") {
			query = "SELECT DOCUMENT.ID_DOCUMENT AS ID, "+
					"PROFESSEUR.MATRICULE AS MATRICULE, " + 
					"PROFESSEUR.NOM AS NOM, " + 
					"TYPE_DOCUMENT.NOM AS TYPE_DOCUMENT, " + 
					"DOCUMENT.NOM AS DOCUMENT " + 
					"FROM DOCUMENT " + 
					"INNER JOIN PROFESSEUR ON PROFESSEUR.IDPROFESSEUR = DOCUMENT.ID_PROFESSEUR " + 
					"INNER JOIN TYPE_DOCUMENT ON TYPE_DOCUMENT.TYPE_DOCUMENT_ID = DOCUMENT.ID_TYPE_DOCUMENT " + 
					"WHERE PROFESSEUR.MATRICULE='"+ matricule +"';"; 
		}else {
			query = "SELECT DOCUMENT.ID_DOCUMENT AS ID, " +
					"ELEVE.MATRICULE AS MATRICULE, " +
					"ELEVE.NOM AS NOM, " +
					"TYPE_DOCUMENT.NOM AS TYPE_DOCUMENT, " + 
					"DOCUMENT.NOM AS DOCUMENT " + 
					"FROM DOCUMENT " + 
					"INNER JOIN ELEVE ON ELEVE.IDELEVE = DOCUMENT.ID_ELEVE " + 
					"INNER JOIN TYPE_DOCUMENT ON TYPE_DOCUMENT.TYPE_DOCUMENT_ID = DOCUMENT.ID_TYPE_DOCUMENT " + 
					"WHERE ELEVE.MATRICULE='"+ matricule +"';";
		}
		
		selectDocument(query);
	}
	
	private void selectDocumentsByNom(String nom, String type) {
		String query = "";
		if(type == "professeur") {
			query = "SELECT DOCUMENT.ID_DOCUMENT AS ID,"+
					"PROFESSEUR.MATRICULE AS MATRICULE, " +
					"PROFESSEUR.NOM AS NOM," + 
					"TYPE_DOCUMENT.NOM AS TYPE_DOCUMENT, " + 
					"DOCUMENT.NOM AS DOCUMENT " + 
					"FROM DOCUMENT " + 
					"INNER JOIN PROFESSEUR ON PROFESSEUR.IDPROFESSEUR = DOCUMENT.ID_PROFESSEUR " + 
					"INNER JOIN TYPE_DOCUMENT ON TYPE_DOCUMENT.TYPE_DOCUMENT_ID = DOCUMENT.ID_TYPE_DOCUMENT " + 
					"WHERE PROFESSEUR.NOM='"+ nom +"';"; 
		}else {
			query = "SELECT DOCUMENT.ID_DOCUMENT AS ID, " +
					"ELEVE.MATRICULE AS MATRICULE, " +
					"ELEVE.NOM AS NOM, " +
					"TYPE_DOCUMENT.NOM AS TYPE_DOCUMENT, " + 
					"DOCUMENT.NOM AS DOCUMENT " +  
					"FROM DOCUMENT " + 
					"INNER JOIN ELEVE ON ELEVE.IDELEVE = DOCUMENT.ID_ELEVE " + 
					"INNER JOIN TYPE_DOCUMENT ON TYPE_DOCUMENT.TYPE_DOCUMENT_ID = DOCUMENT.ID_TYPE_DOCUMENT " + 
					"WHERE ELEVE.NOM='"+ nom +"';";
		}
		selectDocument(query);
	}
	
	private void selectDocument(String query){
		Connection conn = null;
		Statement stmt =null;
		
		try {
			conn = connect();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(conn != null) {
				int items = table.getItemCount();
				for(int i=items; i>0; i--) {
					table.remove(i-1);
				}
				table.clearAll();
				while(rs.next()) {
					TableItem item = new TableItem (table, SWT.NONE);
					item.setText (0, rs.getString("ID"));
					item.setText (1, rs.getString("MATRICULE"));
					item.setText (2, rs.getString("NOM"));
					item.setText (3, rs.getString("TYPE_DOCUMENT"));
					item.setText (4, rs.getString("DOCUMENT"));
				}
				
				conn.close();
				table.getParent().layout();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
			System.out.println("In document");
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
	
	private String getRadioSelection() {
		if (rdProfesseur.getSelection()) {
			return "professeur";
		}else if (rdEleve.getSelection()) {
			return "eleve";
		}else {
			return "null";
		}
	}
	
	public void refresh(){
		txtMatricule.setText("");
		txtNom.setText("");
		
		int items = table.getItemCount();
		for(int i=items; i>0; i--) {
			table.remove(i-1);
		}
		rdEleve.setSelection(false);
		rdProfesseur.setSelection(false);
	}
	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
