package scandoc.TypeDocument;


import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import scandoc.Fichier.XConfigFile;
import scandoc.*;
import scandoc.Eleves.DiagConfirmation;

public class DiagDetailTypeDocument extends Dialog {

	protected Object result;
	protected Shell shlCrerUnType;
	public String nomDocument;
	public String typeDocument;
	private int typeIndividuToInt; 
	private Text txtTypeDocument;
	private List listIndividu;
	public int typeDocumentId;
	private XConfigFile configFile;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagDetailTypeDocument(Shell parent, int style, int id) {
		super(parent, style);
		setText("Cr�er un Type de Document");
		configFile = MainWindow.configFile;
		typeDocumentId = id;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlCrerUnType.open();
		shlCrerUnType.layout();
		Display display = getParent().getDisplay();
		while (!shlCrerUnType.isDisposed()) {
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
		shlCrerUnType = new Shell(getParent(), getStyle());
		shlCrerUnType.setSize(421, 80);
		
		shlCrerUnType.setText("Cr\u00E9er un type de document");
		
		Display display = getParent().getDisplay();

		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shlCrerUnType.getBounds ();
		int x_main = (bounds.width-460)/2;
		int y_main = (bounds.height-500)/2;
		int x = x_main +(460-421)/2;
		int y = y_main + (500-193)/2;
		shlCrerUnType.setBounds(x, y, 421, 153);
		
		Group group = new Group(shlCrerUnType, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		group.setBounds(10, 10, 396, 104);
		
		Label label = new Label(group, SWT.NONE);
		label.setText("Nom :");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setBounds(10, 28, 40, 21);
		
		txtTypeDocument = new Text(group, SWT.BORDER);
		txtTypeDocument.setEditable(false);
		txtTypeDocument.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtTypeDocument.setBounds(59, 28, 327, 23);
		
//////////	Buttons	
		Button btnUpdate = new Button(group, SWT.NONE);
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtTypeDocument.getText()!= "") {
					typeDocument = txtTypeDocument.getText();
					if(openConfirmation("Confirmation modifification Type Document", "Voulez-vous vraiment modifier ce Type de Document ?")) {
						updateQuery(typeDocument);
					}
					txtTypeDocument.setEditable(false);
				}
			}
		});
		btnUpdate.setText("Sauver");
		btnUpdate.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnUpdate.setBounds(301, 69, 75, 25);
		
		Button btnAnnuler = new Button(group, SWT.NONE);
		btnAnnuler.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nomDocument = "";
				shlCrerUnType.close();
			}
		});
		btnAnnuler.setText("Annuler");
		btnAnnuler.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAnnuler.setBounds(19, 69, 75, 25);
		
		Button btnEffacer = new Button(group, SWT.NONE);
		btnEffacer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(openConfirmation("Confirmation Effacement Type Document", "Voulez-vous vraiment effacer ce Type de Document ?")) {
					deleteTypeDocument();
				}
			}
		});
		btnEffacer.setText("Effacer");
		btnEffacer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnEffacer.setBounds(113, 69, 75, 25);
		
		Button btnEditer = new Button(group, SWT.NONE);
		btnEditer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtTypeDocument.setEditable(true);
			}
		});
		btnEditer.setText("Editer");
		btnEditer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnEditer.setBounds(207, 69, 75, 25);
		
		onLoadFill();
	}
	
	private void updateQuery(String nom_document) {
		String query = "UPDATE TYPE_DOCUMENT SET " +
						"NOM = ?, " +
						"ERASED = ? " +
						"WHERE TYPE_DOCUMENT_ID = ?;";
		
		try (Connection conn = this.connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {
			if(conn != null) {
				stmt.setString(1, nom_document);
				stmt.setNull(2, java.sql.Types.INTEGER);
				stmt.setInt(3, typeDocumentId);
				stmt.executeUpdate();
				conn.close();
			}				
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
	}
	
	protected void onLoadFill() {
		
		String query = "SELECT NOM FROM TYPE_DOCUMENT WHERE TYPE_DOCUMENT_ID=" + typeDocumentId + ";";
		
		Connection conn = null;
		Statement stmt =null;
		try {
			String url = "jdbc:sqlite:" + configFile.getFolderPath() + "/database/scandocdb.db";
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				txtTypeDocument.setText(rs.getString("NOM"));
			}
			
			conn.close();
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
		
		
	}
	
	private void deleteTypeDocument(){
		
		String query = "DELETE FROM TYPE_DOCUMENT WHERE TYPE_DOCUMENT_ID=?;";
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
 
            // set the corresponding param
            pstmt.setInt(1, typeDocumentId);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public Boolean openConfirmation(String title, String message) {
	Shell shellopen = new Shell();
	Boolean confirmation = false;
	DiagConfirmation diag = new DiagConfirmation(shellopen, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, title, message);
	diag.open();
	confirmation = diag.confirmation;
	return confirmation;
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
	
}
