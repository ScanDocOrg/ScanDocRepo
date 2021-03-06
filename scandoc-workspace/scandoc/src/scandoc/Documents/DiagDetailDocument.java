package scandoc.Documents;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import scandoc.*;
import scandoc.MainWindow;
import scandoc.Fichier.XConfigFile;
import scandoc.TypeDocument.*;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;

public class DiagDetailDocument extends Dialog {

	protected Object result;
	protected Shell shlDetailDocument;
	private Display display;
	
	
	private Text txtIdDocument;
	private String document_id;
	private String document_nom;
	
	private String individu_id;
	private Text txtNomTitulaire;
	private Text txtPrenomTitulaire;
	
	private int idDocumentType;
	private String nomDocumentType;
	private Text txtNomTypeDocument;
	
	
	Button btnSelectTypeDoc;
	Button btnChangeDocument;
	
	private ScrolledComposite scrCompImage;
	private Label lblImage;
	private Image image;
	
	private XConfigFile configFile;
	
	
	
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagDetailDocument(Shell parent, int style, String document_id, String individu_id) {
		super(parent, style);
		setText("D�tails Document");
		this.individu_id = individu_id;
		this.document_id = document_id;
		this.configFile =MainWindow.configFile;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlDetailDocument.open();
		shlDetailDocument.layout();
		display = getParent().getDisplay();
		while (!shlDetailDocument.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlDetailDocument = new Shell(getParent(), getStyle());
		shlDetailDocument.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		shlDetailDocument.setSize(800, 800);
		shlDetailDocument.setText("Detail Document");
		shlDetailDocument.setLayout(null);
		
		Group grpDocument = new Group(shlDetailDocument, SWT.BORDER | SWT.SHADOW_OUT);
		grpDocument.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		grpDocument.setText("Document");
		grpDocument.setBounds(10, 10, 774, 751);
		
		Label lblId = new Label(grpDocument, SWT.NONE);
		lblId.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblId.setBounds(10, 28, 20, 21);
	 	lblId.setText("Id :");
		
		txtIdDocument = new Text(grpDocument, SWT.BORDER);
		txtIdDocument.setEditable(false);
		txtIdDocument.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtIdDocument.setBounds(36, 28, 287, 21);
		
		Label lblType = new Label(grpDocument, SWT.NONE);
		lblType.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblType.setBounds(340, 28, 39, 21);
		lblType.setText("Type :");
		
		txtNomTitulaire = new Text(grpDocument, SWT.BORDER);
		txtNomTitulaire.setEditable(false);
		txtNomTitulaire.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtNomTitulaire.setBounds(444, 67, 320, 21);
		
		Label lblNomTitulaire = new Label(grpDocument, SWT.NONE);
		lblNomTitulaire.setText("Nom Titulaire :");
		lblNomTitulaire.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNomTitulaire.setBounds(340, 67, 98, 21);
		
		Label lblPrenomTitulaire = new Label(grpDocument, SWT.NONE);
		lblPrenomTitulaire.setText("Prenom Titulaire :");
		lblPrenomTitulaire.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblPrenomTitulaire.setBounds(10, 67, 118, 21);
		
		txtPrenomTitulaire = new Text(grpDocument, SWT.BORDER);
		txtPrenomTitulaire.setEditable(false);
		txtPrenomTitulaire.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPrenomTitulaire.setBounds(134, 67, 189, 21);
		
		Button btnEffacer = new Button(grpDocument, SWT.NONE);
		btnEffacer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnEffacer.setText("Effacer");
		btnEffacer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnEffacer.setBounds(34, 711, 153, 25);
		
		Button btnEditer = new Button(grpDocument, SWT.NONE);
		btnEditer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSelectTypeDoc.setEnabled(true);
				btnChangeDocument.setEnabled(true);
			}
		});
		btnEditer.setText("Editer");
		btnEditer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnEditer.setBounds(221, 711, 153, 25);
		
		Button btnSave = new Button(grpDocument, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSelectTypeDoc.setEnabled(false);
				btnSelectTypeDoc.setEnabled(false);
				updateDocument();
			}
		});
		btnSave.setText("Save");
		btnSave.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSave.setBounds(408, 711, 153, 25);
		
		btnChangeDocument = new Button(grpDocument, SWT.NONE);
		btnChangeDocument.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateDocument();
			}
		});
		btnChangeDocument.setText("Change Document");
		btnChangeDocument.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnChangeDocument.setBounds(595, 711, 142, 25);
		
		//ScrolledComposite scrCompImage = new ScrolledComposite(grpDocument, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrCompImage = new ScrolledComposite(grpDocument, SWT.H_SCROLL | SWT.V_SCROLL);
		scrCompImage.setAlwaysShowScrollBars(true);
		scrCompImage.setBounds(10, 94, 754, 611);
		//scrCompImage.setBounds(10, 94, 900,700);
		scrCompImage.setExpandHorizontal(true);
		scrCompImage.setExpandVertical(false);
		
		lblImage = new Label(scrCompImage, SWT.BORDER | SWT.CENTER);
		
		//scrCompImage.pack();
		scrCompImage.setMinSize(lblImage.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrCompImage.setContent(lblImage);
		
		txtNomTypeDocument = new Text(grpDocument, SWT.BORDER);
		txtNomTypeDocument.setEditable(false);
		txtNomTypeDocument.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtNomTypeDocument.setBounds(385, 24, 215, 25);
		
		btnSelectTypeDoc = new Button(grpDocument, SWT.NONE);
		btnSelectTypeDoc.setEnabled(false);
		btnSelectTypeDoc.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getFileTypes();
			}
		});
		btnSelectTypeDoc.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		btnSelectTypeDoc.setBounds(606, 24, 158, 25);
		btnSelectTypeDoc.setText("Select. un type de document");
		
		populateForm();
		getImage();

	}
	
	private void populateForm() {
		String query = "SELECT ELEVE.NOM AS ELEVE_NOM," +
						"ELEVE.PRENOM AS ELEVE_PRENOM, " +
						"TYPE_DOCUMENT.NOM AS TYPE_DOCUMENT_NOM, "+
						"DOCUMENT.ID_DOCUMENT AS ID_DOCUMENT " +
						"FROM ELEVE "+
						"INNER JOIN DOCUMENT ON DOCUMENT.ID_ELEVE = ELEVE.IDELEVE "+
						"INNER JOIN TYPE_DOCUMENT ON TYPE_DOCUMENT.TYPE_DOCUMENT_ID=DOCUMENT.ID_TYPE_DOCUMENT "+
						"WHERE DOCUMENT.ID_DOCUMENT=" +this.document_id +";";
		
		Connection conn = null;
		Statement stmt =null;
		try {
			conn = connect();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(conn != null) {
				while(rs.next()) {
					txtIdDocument.setText(this.document_id);
					txtPrenomTitulaire.setText(rs.getString("ELEVE_PRENOM"));
					txtNomTitulaire.setText(rs.getString("ELEVE_NOM"));
					txtNomTypeDocument.setText(rs.getString("TYPE_DOCUMENT_NOM"));
				}
				conn.close();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void getImage() {
		String query= "SELECT DOCUMENT.NOM AS NOM FROM DOCUMENT WHERE DOCUMENT.ID_DOCUMENT="+this.document_id+";";
		
		Connection conn = null;
		Statement stmt =null;
		try {
			conn = connect();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(conn != null) {
				while(rs.next()) {
					this.document_nom = rs.getString("NOM");
				}
				conn.close();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
		
		String url = this.document_nom;
		this.image =  new Image(display, url);
		Image image2 =getScaledImage(image);
		lblImage.setImage(image2);
	
		//lblImage.setLocation (clientArea.x, clientArea.y);
		lblImage.pack();
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void updateDocument() {
		String url = openFile();
		
		if(url != null && url != "") {
			String query = "UPDATE DOCUMENT SET " +
							"DOCUMENT.NOM = ? " +
							"WHERE DOCUMENT.ID_DOCUMENT="+this.document_id+";";
							
			try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(query)) {
	 
	            // set the corresponding param
	            pstmt.setString(1, url);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
			this.document_nom = url;
			this.image =  new Image(display, url);
			Image image2 =getScaledImage(image);
			lblImage.setImage(image2);
			lblImage.pack();
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void getFileTypes() {
		DiagGetFileTypes getFileTypes = new DiagGetFileTypes(shlDetailDocument, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		getFileTypes.open();
		this.nomDocumentType = getFileTypes.selectedNomDocumentType;
		this.idDocumentType = getFileTypes.selectedIdTypeDocument;
		this.txtNomTypeDocument.setText(this.nomDocumentType);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String openFile() {
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
		return originUrl;
		
		
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////

	private Image getScaledImage(Image image) {
		Image imageScaled = null;
		
		ImageData imgData = image.getImageData();
		Rectangle rect = image.getBounds();
		
		int width = rect.width;
		int height = rect.height;
		
		float scaleWidth = width/754;
		float scaleHeight = height/611;
		
		int newWidth = 0;
		int newHeight = 0;
		
		if(scaleWidth >= scaleHeight) {
			newWidth = Math.round(width/scaleWidth);
			newHeight = Math.round(height/scaleWidth);
		}else if (scaleWidth <= scaleHeight) {
			newWidth = Math.round(width/scaleHeight);
			newHeight = Math.round(width/scaleHeight);
		}
		/*else if (scaleWidth == scaleHeight) {
			newWidth = /scaleHeight;
			newHeight = /scaleHeight;
		}*/
		// To fit in 754 x 611
		imgData = imgData.scaledTo(newWidth, newHeight);
		imageScaled = new Image(display, imgData);
		
		return imageScaled;
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
}

//////////////////////////////////////////////////////////////////////////////////////////////////////
