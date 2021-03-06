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
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import scandoc.Fichier.XConfigFile;
import scandoc.*;

public class DiagCreerTypeDocument extends Dialog {

	protected Object result;
	protected Shell shlCrerUnType;
	public String nomDocument;
	private String typeIndividu;
	private int typeIndividuToInt; 
	private Text txtNom;
	private List listIndividu;
	private XConfigFile configFile;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagCreerTypeDocument(Shell parent, int style) {
		super(parent, style);
		setText("Cr�er un Type de Document");
		configFile = MainWindow.configFile;
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
		//shell.setLocation (x, y);
		shlCrerUnType.setBounds(x, y, 421, 153);
		
		Group group = new Group(shlCrerUnType, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		group.setBounds(10, 10, 396, 104);
		
		Label label = new Label(group, SWT.NONE);
		label.setText("Nom :");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setBounds(10, 28, 40, 21);
		
		txtNom = new Text(group, SWT.BORDER);
		txtNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtNom.setBounds(59, 28, 327, 23);
		String items[] = {"Professeur", "Eleve"};
		
//////////	Buttons	
		Button btnCreer = new Button(group, SWT.NONE);
		btnCreer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nomDocument = txtNom.getText();
				if(nomDocument != "") {
					insertQuery(nomDocument);
					shlCrerUnType.close();
				}
			}
		});
		btnCreer.setText("Cr\u00E9er");
		btnCreer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnCreer.setBounds(239, 69, 75, 25);
		
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
		btnAnnuler.setBounds(82, 69, 75, 25);
	}
	
	
	
	private void insertQuery(String nom_document) {
		String query = "INSERT INTO TYPE_DOCUMENT (TYPE_DOCUMENT_ID, NOM,ERASED) VALUES (?,?,?);" ;
		
		Connection conn = null;
		PreparedStatement stmt =null;
		try {
			String url = "jdbc:sqlite:" + configFile.getFolderPath()+"/database/scandocdb.db";
			conn = DriverManager.getConnection(url);
			
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
	
}
