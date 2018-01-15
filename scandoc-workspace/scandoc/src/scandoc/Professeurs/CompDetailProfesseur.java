package scandoc.Professeurs;



import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.wb.swt.SWTResourceManager;

import hirondelle.date4j.DateTime;
import scandoc.MainWindow;
import scandoc.Eleves.DiagConfirmation;
import scandoc.Fichier.XConfigFile;
import scandoc.TypeDocument.DiagGetFileTypes;


public class CompDetailProfesseur extends Composite {
	public String id_professeur;
	private Text txtNom;
	public String nom;
	private Text txtPrenom;
	public String prenom;
	private Text txtMatricule;
	public String matricule;
	private XConfigFile configFile;
	public Shell shlConfirmationEffacement;
	public Table tblDocument;
	private Table table;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompDetailProfesseur(Composite parent, int style, Shell shell, String m) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		setLayout(null);
		
		configFile = MainWindow.configFile;
		this.matricule = m;
		
		Group grpProfesseurDtail = new Group(this, SWT.BORDER | SWT.SHADOW_OUT);
		grpProfesseurDtail.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		grpProfesseurDtail.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpProfesseurDtail.setText("D\u00E9tail Professeur ");
		grpProfesseurDtail.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpProfesseurDtail.setBounds(10, 10, 440, 480);
		
		Label label = new Label(grpProfesseurDtail, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		label.setText("Nom :");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setBounds(10, 59, 46, 21);
		
		txtNom = new Text(grpProfesseurDtail, SWT.BORDER);
		txtNom.setEditable(false);
		txtNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtNom.setBounds(59, 59, 371, 23);
		
		//
		// Boutton Editer
		//
		Button btnEditer = new Button(grpProfesseurDtail, SWT.NONE);
		btnEditer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtNom.setEditable(true);
				txtPrenom.setEditable(true);
				txtMatricule.setEditable(true);
			}
		});
		btnEditer.setText("Editer");
		btnEditer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnEditer.setBounds(95, 445, 75, 25);
		
		Label lblPrenom = new Label(grpProfesseurDtail, SWT.NONE);
		lblPrenom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblPrenom.setText("Prenom :");
		lblPrenom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblPrenom.setBounds(10, 98, 58, 21);
		
		txtPrenom = new Text(grpProfesseurDtail, SWT.BORDER);
		txtPrenom.setEditable(false);
		txtPrenom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPrenom.setBounds(74, 96, 356, 23);
		
		txtMatricule = new Text(grpProfesseurDtail, SWT.BORDER);
		txtMatricule.setEditable(false);
		txtMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtMatricule.setBounds(86, 26, 344, 23);
		
		Label lblMatricule = new Label(grpProfesseurDtail, SWT.NONE);
		lblMatricule.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblMatricule.setText("Matricule :");
		lblMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblMatricule.setBounds(10, 28, 70, 21);
		
		Label label_1 = new Label(grpProfesseurDtail, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(10, 135, 420, 2);
		
		Label lblDocuments = new Label(grpProfesseurDtail, SWT.NONE);
		lblDocuments.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblDocuments.setText("Documents");
		lblDocuments.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblDocuments.setBounds(10, 143, 82, 21);
		
		//
		// Boutton Effacer
		//
		Button btnEffacer = new Button(grpProfesseurDtail, SWT.NONE);
		btnEffacer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(openConfirmation("Confirmation effacement", "Voulez-vous vraiment effacer ce Professeur ?")) {
					deleteProfesseur(Integer.parseInt(id_professeur));
				}
			}
		});
		btnEffacer.setText("Effacer");
		btnEffacer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnEffacer.setBounds(10, 445, 75, 25);
		
		//
		// Boutton Save
		//
		Button btnSave = new Button(grpProfesseurDtail, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(openConfirmation("Confirmation modifification", "Voulez-vous vraiment modifier ce professeur ?")) {
					nom = txtNom.getText();
					matricule = txtMatricule.getText();
					prenom = txtPrenom.getText();
					
					updateProfesseur(matricule, nom, prenom);
				}
				
				txtNom.setEditable(false);
				txtPrenom.setEditable(false);
				txtMatricule.setEditable(false);
			}
		});
		btnSave.setText("Save");
		btnSave.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSave.setBounds(180, 445, 75, 25);
		
		//
		// Boutton Creer Document
		//
		Button btnCreerDocument = new Button(grpProfesseurDtail, SWT.NONE);
		btnCreerDocument.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveDocument();
			}
		});
		btnCreerDocument.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		btnCreerDocument.setText("Cr\u00E9er Document");
		btnCreerDocument.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnCreerDocument.setBounds(261, 445, 169, 25);
		
		tblDocument = new Table (grpProfesseurDtail, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);	
		//tblDocument = new org.eclipse.swt.widgets.Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		tblDocument.setHeaderVisible(true);
		tblDocument.setLinesVisible(true);
		tblDocument.setLinesVisible (true);
		tblDocument.setHeaderVisible (true);
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		tblDocument.setLayoutData(data);
		
		String[] titles = {"Id", "Matricule prof" ,"Nom", "Type"};
		
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (tblDocument, SWT.NONE);
			column.setText (titles [i]);
			tblDocument.getColumn (i).setWidth(105);
		}
		tblDocument.setBounds(10, 170, 420, 265);
		tblDocument.setHeaderVisible(true);
		tblDocument.setLinesVisible(true);
		
		selectProfesseur(matricule);
		selectDocuments(matricule);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////:

	// Select Professeur
	public void selectProfesseur(String matricule) {

		String query = "SELECT * FROM PROFESSEUR WHERE PROFESSEUR.MATRICULE='" + matricule + "';";
		
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs;
		
		try {
			conn = connect();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);	
			
			if(conn != null) {
				rs.next();
				id_professeur = String.valueOf(rs.getInt("IDPROFESSEUR"));
				txtMatricule.setText(rs.getString("MATRICULE"));
				this.matricule = rs.getString("MATRICULE");
				txtNom.setText(rs.getString("NOM")); 
				nom = rs.getString("NOM");
				txtPrenom.setText(rs.getString("PRENOM"));
				prenom = rs.getString("PRENOM");
				conn.close();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
			System.out.println("in Professeur");
		} 
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////:

	// Table Document select query & print 
	public void selectDocuments(String matricule) {
		String query = "SELECT DOCUMENT.ID_DOCUMENT AS ID,"+
						"PROFESSEUR.MATRICULE AS MATRICULE," + 
						"DOCUMENT.NOM AS NOM," + 
						"TYPE_DOCUMENT.NOM AS TYPE_DOCUMENT " + 
						"FROM DOCUMENT " + 
						"INNER JOIN PROFESSEUR ON PROFESSEUR.IDPROFESSEUR = DOCUMENT.ID_PROFESSEUR " + 
						"INNER JOIN TYPE_DOCUMENT ON TYPE_DOCUMENT.TYPE_DOCUMENT_ID = DOCUMENT.ID_TYPE_DOCUMENT " + 
						"WHERE MATRICULE='"+ matricule +"';";
		
		Connection conn = null;
		Statement stmt =null;
		
		try {
			conn = connect();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(conn != null) {
				int items = tblDocument.getItemCount();
				for(int i=items; i>0; i--) {
					tblDocument.remove(i-1);
				}
				tblDocument.clearAll();
				while(rs.next()) {
					TableItem item = new TableItem (tblDocument, SWT.NONE);
					item.setText (0, rs.getString("ID"));
					item.setText (1, rs.getString("MATRICULE"));
					item.setText (2, rs.getString("NOM"));
					item.setText (3, rs.getString("TYPE_DOCUMENT"));	
				}
				
				conn.close();
				tblDocument.getParent().layout();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
			System.out.println("In document");
		} 
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void deleteProfesseur(int id_professeur) {
		
		deleteDocuments(id_professeur);
		
		String query = "DELETE FROM PROFESSEUR WHERE IDPROFESSEUR= ?";
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
 
            // set the corresponding param
            pstmt.setInt(1, id_professeur);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
		this.txtMatricule.setText("");
		this.txtNom.setText("");
		this.txtPrenom.setText("");
		this.matricule = "";
		this.nom = "";
		this.prenom = "";
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void deleteDocuments(int id_professeur){
		
		String query = "DELETE FROM DOCUMENT WHERE ID_PROFESSEUR=?";
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
 
            // set the corresponding param
            pstmt.setInt(1, id_professeur);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void updateProfesseur(String matricule, String nom, String prenom) {
		String query = "UPDATE PROFESSEUR SET MATRICULE = ? , " +
                		"NOM = ? , " +
                		"PRENOM = ? " +
                		"WHERE IDPROFESSEUR = ?;";
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
 
            // set the corresponding param
            pstmt.setString(1, matricule);
            pstmt.setString(2, nom);
            pstmt.setString(3, prenom);
            pstmt.setInt(4, Integer.parseInt(id_professeur));
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
		this.matricule = txtMatricule.getText();
		this.nom = txtNom.getText();
		this.prenom = txtPrenom.getText();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

	protected void saveDocument() {
		Shell shellopen = new Shell();
		FileDialog dialog = new FileDialog (shellopen, SWT.SELECTED);
		dialog.setText("Choisissez un document (jpg, png, pdf)");
		String [] filterNames = new String [] {"Image Files", "PDF Files"};
		String [] filterExtensions = new String [] {"*.jpg","*.png","*.pdf"};
		String filterPath = "/";
		String platform = SWT.getPlatform();
	
		if (platform.equals("win32")) {
			filterNames = new String [] {"JPG Files (*.jpg)","PNG Files (*.png)", "PDF Files (*.pdf)"};
			filterExtensions = new String [] {"*.jpg","*.png","*.pdf"};
			filterPath = "c:\\";
		}
		
		dialog.setFilterNames (filterNames);
		dialog.setFilterExtensions (filterExtensions);
		dialog.setFilterPath (filterPath);
		dialog.setFileName ("myfile");
		String originUrl = dialog.open ();
		File originFile= new File(originUrl);
		String originFileName = dialog.getFileName();
		String fileExtention = originFileName.substring(originFileName.length() - 4);
		DiagGetFileTypes getFileTypes = new DiagGetFileTypes(shellopen, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		getFileTypes.open();
		String nomDocumentType = getFileTypes.selectedNomDocumentType;
		int idDocumentType = getFileTypes.selectedIdTypeDocument;
		String newFilePath = getSavedFilePath(nomDocumentType, fileExtention);
		originFile.renameTo(new File(newFilePath));
		
		String query = "INSERT INTO DOCUMENT VALUES (?,?,?,?,?,?,?);"; 
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setString(2, newFilePath);
			pstmt.setString(3, "professeur");
			pstmt.setNull(4, java.sql.Types.INTEGER);
			pstmt.setInt(5, Integer.parseInt(this.id_professeur));
            pstmt.setInt(6, idDocumentType);
            pstmt.setInt(7, 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected String getSavedFilePath(String nomDocumentType, String fileExtention) {
		String str = "";
		String path = configFile.getFolderPath() + "\\files\\";
		String matriculeNomPrenom = this.matricule +"$"+this.nom+"$"+this.prenom+"$";
		String documentType = nomDocumentType+"$";
		String date = DateTime.today(TimeZone.getDefault()).toString();
		str = path + matriculeNomPrenom + documentType + date + fileExtention;
		//str = str.replace("\\s+","-");
		return str;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////

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
	
//////////////////////////////////////////////////////////////////////////////////////////////////////:

	
	// Delete onfirmation
	public boolean openConfirmation(String title, String message) {
		
		Boolean confirmation = false;
		DiagConfirmation diag = new DiagConfirmation(MainWindow.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, title, message);
		diag.open();
		confirmation = diag.confirmation;
		return confirmation;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	
	
}
