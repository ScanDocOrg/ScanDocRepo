package scandoc.TypeDocument;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import scandoc.MainWindow;
import scandoc.Fichier.XConfigFile;
import scandoc.Professeurs.CompDetailProfesseur;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import hirondelle.date4j.*;

public class DiagGetFileTypes extends Dialog {

	protected Object result;
	protected Shell shlDocumentType;
	private Text txtNom;
	private Table table;
	public String selectedNomDocumentType;
	private XConfigFile configFile;
	public int selectedIdTypeDocument;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagGetFileTypes(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlDocumentType.open();
		shlDocumentType.layout();
		Display display = getParent().getDisplay();
		while (!shlDocumentType.isDisposed()) {
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
		shlDocumentType = new Shell(getParent(), getStyle());
		Display display = getParent().getDisplay();
		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shlDocumentType.getBounds ();
		int x_main = (bounds.width-351)/2;
		int y_main = (bounds.height-488)/2;
		int x = x_main +(460-421)/2;
		int y = y_main + (500-215)/2;
		
		shlDocumentType.setBounds(x, y, 351, 488);
		//shlDocumentType = new Shell(getParent(), getStyle());
		//shlDocumentType.setSize(351, 488);
		shlDocumentType.setText("Document Type");
		
		configFile = MainWindow.configFile;
		
		Group grpSlectionnerUnType = new Group(shlDocumentType, SWT.NONE);
		grpSlectionnerUnType.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpSlectionnerUnType.setText("S\u00E9lectionner un type de document");
		grpSlectionnerUnType.setBounds(10, 10, 325, 335);
			
		Group grpCreateNewDocument = new Group(shlDocumentType, SWT.NONE);
		grpCreateNewDocument.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpCreateNewDocument.setText("Cr\u00E9er un nouveau type de document");
		grpCreateNewDocument.setBounds(10, 351, 325, 98);
		
		Label lblNewLabel = new Label(grpCreateNewDocument, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel.setBounds(10, 26, 43, 20);
		lblNewLabel.setText("Nom :");
		
		txtNom = new Text(grpCreateNewDocument, SWT.BORDER);
		txtNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtNom.setBounds(59, 23, 256, 26);
		
		Button btnCreer = new Button(grpCreateNewDocument, SWT.NONE);
		btnCreer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedNomDocumentType = txtNom.getText();
				if(selectedNomDocumentType != "" && selectedNomDocumentType != null) {
					insertDocumentType(selectedNomDocumentType);
					getIdDocumentType(selectedNomDocumentType);
				}
				shlDocumentType.close();
			}
		});
		btnCreer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnCreer.setBounds(92, 63, 140, 25);
		btnCreer.setText("Cr\u00E9er et s\u00E9lectioner");

		table = new Table(grpSlectionnerUnType, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 22, 305, 303);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = {"ID", "Document Type"};
		
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
			table.getColumn (i).setWidth(152);
		}
		
		table.addListener (SWT.Selection, event -> {
			//String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
			TableItem current = (TableItem)event.item;
			selectedIdTypeDocument = Integer.parseInt(current.getText(0));
			selectedNomDocumentType = current.getText(1);
			shlDocumentType.close();
		});
		
		populateDocumentTypeTable();
	}
	
	private void populateDocumentTypeTable() {
		Connection conn = null;
		Statement stmt =null;
		String query = "SELECT * FROM TYPE_DOCUMENT";
		
		try {
			conn = connect();
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
					item.setText(0, String.valueOf(rs.getInt("TYPE_DOCUMENT_ID")));
					item.setText (1, rs.getString("NOM"));	
				}
				
				conn.close();
				table.getParent().layout();
			}
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
	}
	
	private void insertDocumentType(String nom_document) {
		String query = "INSERT INTO TYPE_DOCUMENT (TYPE_DOCUMENT_ID, NOM,ERASED) VALUES (?,?,?);" ;
		
		Connection conn = null;
		PreparedStatement stmt =null;
		try {
			conn =connect();	
			if(conn != null) {
				stmt = conn.prepareStatement(query);
				stmt.setNull(1, java.sql.Types.INTEGER);
				stmt.setString(2, nom_document);
				stmt.setInt(3, 0);
				stmt.executeUpdate();
				conn.close();
			}	
				
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
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
	
	private void getIdDocumentType(String DocumentType) {
		String query = "SELECT TYPE_DOCUMENT_ID FROM TYPE_DOCUMENT WHERE NOM='"+ DocumentType +"';";
		
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs;

		try {
			conn =connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);	
			if(conn != null) {
				this.selectedIdTypeDocument = rs.getInt("TYPE_DOCUMENT_ID");
				conn.close();
			}		
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
		
		
	}
}
