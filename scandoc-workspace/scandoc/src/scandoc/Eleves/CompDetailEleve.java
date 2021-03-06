package scandoc.Eleves;


import org.eclipse.swt.events.*;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import scandoc.Fichier.XConfigFile;
import scandoc.TypeDocument.DiagGetFileTypes;
import scandoc.Documents.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.wb.swt.SWTResourceManager;
import static java.nio.file.StandardCopyOption.*;
import hirondelle.date4j.DateTime;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import scandoc.MainWindow;
import scandoc.Documents.*;

public class CompDetailEleve extends Composite {
	public String eleve_id;
	public Text txtNom;
	public String nom;
	public Text txtPrenom;
	public String prenom;
	public Text txtMatricule;
	public String matricule;
	public Shell parentShell;
	public XConfigFile configFile;
	public Shell shlConfirmationEffacement;
	public Table tblDocument;
	public DiagDetailDocument diagDetailDocument;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompDetailEleve(Composite parent, int style, Shell parentshell, String m) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		//diagConfirmationEffacementEleve = pDiagConfirmationEffacementEleve;
		this.parentShell = parentshell;
		
		configFile = MainWindow.configFile;
		this.matricule = m;
		
		Group grpDtailElve = new Group(this, SWT.BORDER | SWT.SHADOW_OUT);
		grpDtailElve.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpDtailElve.setText("D\u00E9tail El\u00E8ve");
		grpDtailElve.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpDtailElve.setBounds(10, 10, 440, 480);
		
		Label label = new Label(grpDtailElve, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		label.setText("Nom :");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setBounds(10, 59, 46, 21);
		
		txtNom = new Text(grpDtailElve, SWT.BORDER);
		txtNom.setEditable(false);
		txtNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtNom.setBounds(59, 59, 371, 23);
		
		
		
		Label label_1 = new Label(grpDtailElve, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		label_1.setText("Prenom :");
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setBounds(10, 98, 58, 21);
		
		txtPrenom = new Text(grpDtailElve, SWT.BORDER);
		txtPrenom.setEditable(false);
		txtPrenom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPrenom.setBounds(74, 96, 356, 23);
		
		txtMatricule = new Text(grpDtailElve, SWT.BORDER);
		txtMatricule.setEditable(false);
		txtMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtMatricule.setBounds(86, 26, 344, 23);
		
		Label label_2 = new Label(grpDtailElve, SWT.NONE);
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		label_2.setText("Matricule :");
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_2.setBounds(10, 28, 70, 21);
		
		Label label_3 = new Label(grpDtailElve, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(10, 135, 420, 2);
		
		Label lblDocuments = new Label(grpDtailElve, SWT.NONE);
		lblDocuments.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblDocuments.setText("Documents");
		lblDocuments.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblDocuments.setBounds(10, 143, 82, 21);
		
		//
		// Boutton Effacer
		//
		Button btnEffacer = new Button(grpDtailElve, SWT.NONE);
		btnEffacer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(openConfirmation("Confirmation effacement �l�ve", "Voulez-vous vraiment effacer cet �leve ?")) {
					deleteEleve(Integer.parseInt(eleve_id));
				}
			}
		});
		btnEffacer.setText("Effacer");
		btnEffacer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnEffacer.setBounds(12, 445, 75, 25);
		
		//
		// Boutton Editer
		//
		Button btnEditer = new Button(grpDtailElve, SWT.NONE);
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
		btnEditer.setBounds(97, 445, 75, 25);
		
		//
		// Boutton Save
		//
		Button btnSave = new Button(grpDtailElve, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(txtNom.getText()!= "") {
					if(openConfirmation("Confirmation modifification �l�ve", "Voulez-vous vraiment modifier cet �leve ?")) {
						nom = txtNom.getText();
						matricule = txtMatricule.getText();
						prenom = txtPrenom.getText();
						updateEleve(matricule, nom, prenom);
					}
					
					txtNom.setEditable(false);
					txtPrenom.setEditable(false);
					txtMatricule.setEditable(false);
				}
			}
		});
		btnSave.setText("Save");
		btnSave.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSave.setBounds(182, 445, 75, 25);
		
		//
		// Cr�er un document
		//
		Button btnCreerDocument = new Button(grpDtailElve, SWT.NONE);
		btnCreerDocument.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveDocument();
				
			}
		});
		btnCreerDocument.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		btnCreerDocument.setText("Cr\u00E9er Document");
		btnCreerDocument.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnCreerDocument.setBounds(267, 445, 163, 25);
		
		// Table document
		tblDocument = new Table (grpDtailElve, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tblDocument.setBounds(10, 170, 420, 269);
		tblDocument.setLinesVisible(true);
		tblDocument.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		tblDocument.setLayoutData(data);
		String[] titles = {"Id", "Matricule eleve", "Nom", "Type"};
		
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (tblDocument, SWT.NONE);
			column.setText (titles [i]);
			tblDocument.getColumn (i).setWidth(105);
		}
		
		selectEleve(matricule);
		selectDocuments(matricule);
		
		tblDocument.addListener (SWT.Selection, event -> {
			String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
			TableItem current = (TableItem)event.item;
			String document_id = current.getText();
			
			diagDetailDocument = new DiagDetailDocument(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, document_id, eleve_id);
			diagDetailDocument.open();
			
		});
	
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////
	// Select Eleve & print
	public void selectEleve(String matricule) {
		
		String query = "SELECT * FROM ELEVE WHERE ELEVE.MATRICULE='" + matricule + "';";
		
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
				eleve_id = String.valueOf(rs.getInt("IDELEVE"));
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
		} 
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////:

	//Select Document Query & print table
	public void selectDocuments(String matricule) {
		String query = "SELECT DOCUMENT.ID_DOCUMENT AS ID, " +
						"ELEVE.MATRICULE AS MATRICULE, " +
						"DOCUMENT.NOM AS NOM, " + 	 
						"TYPE_DOCUMENT.NOM AS TYPE_DOCUMENT " + 
						"FROM DOCUMENT " + 
						"INNER JOIN ELEVE ON ELEVE.IDELEVE = DOCUMENT.ID_ELEVE " + 
						"INNER JOIN TYPE_DOCUMENT ON TYPE_DOCUMENT.TYPE_DOCUMENT_ID = DOCUMENT.ID_TYPE_DOCUMENT " + 
						"WHERE ELEVE.MATRICULE='"+ matricule +"';";
		
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
					item.setText (0, String.valueOf(rs.getInt("ID")));
					item.setText (1, rs.getString("MATRICULE"));
					item.setText (2, rs.getString("NOM"));
					item.setText (3, rs.getString("TYPE_DOCUMENT"));
					//item.getT
				}
				
				conn.close();
				tblDocument.getParent().layout();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
	}
		
//////////////////////////////////////////////////////////////////////////////////////////////////////

	public void deleteEleve(int id_eleve) {
		
		deleteDocuments(id_eleve);
		
		String query = "DELETE FROM ELEVE WHERE IDELEVE= ?";
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
 
            // set the corresponding param
            pstmt.setInt(1, id_eleve);
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

	private void deleteDocuments(int id_eleve){
		
		String query = "DELETE FROM DOCUMENT WHERE ID_ELEVE=?";
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
 
            // set the corresponding param
            pstmt.setInt(1, id_eleve);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////

	private void updateEleve(String matricule, String nom, String prenom) {
		String query = "UPDATE ELEVE SET MATRICULE = ? , " +
                		"NOM = ? , " +
                		"PRENOM = ? " +
                		"WHERE IDELEVE = ?;";

		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
 
            // set the corresponding param
            pstmt.setString(1, matricule);
            pstmt.setString(2, nom);
            pstmt.setString(3, prenom);
            pstmt.setInt(4, Integer.parseInt(eleve_id));
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
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Boolean openConfirmation(String title, String message) {
		Shell shellopen = new Shell();
		Boolean confirmation = false;
		DiagConfirmation diag = new DiagConfirmation(shellopen, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, title, message);
		diag.open();
		confirmation = diag.confirmation;
		return confirmation;
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
			pstmt.setString(3, "eleve");
			pstmt.setInt(4, Integer.parseInt(this.eleve_id));
			pstmt.setNull(5, java.sql.Types.INTEGER);
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
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
 	protected String getDocumentFolderPath() {
		return configFile.getFolderPath() + "/files/";
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
