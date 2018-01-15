package scandoc.Professeurs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

public class DiagCreerUnProfesseur extends Dialog {

	protected Object result;
	protected Shell shlCrerUnProfesseur;
	private Text txtNom;
	private Text txtPrenom;
	private Text txtMatricule;
	
	public String nom;
	public String prenom;
	public String matricule;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagCreerUnProfesseur(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlCrerUnProfesseur.open();
		shlCrerUnProfesseur.layout();
		Display display = getParent().getDisplay();
		while (!shlCrerUnProfesseur.isDisposed()) {
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
		shlCrerUnProfesseur = new Shell(getParent(), getStyle());
		
		Display display = getParent().getDisplay();

		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shlCrerUnProfesseur.getBounds ();
		int x_main = (bounds.width-460)/2;
		int y_main = (bounds.height-500)/2;
		int x = x_main +(460-423)/2;
		int y = y_main + (500-218)/2;
		//shell.setLocation (x, y);
		shlCrerUnProfesseur.setBounds(x, y, 423, 218);
		
		//shlCrerUnProfesseur.setSize(423, 218);
		shlCrerUnProfesseur.setText("Cr\u00E9er un professeur");
		
		Group group = new Group(shlCrerUnProfesseur, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		group.setBounds(10, 10, 396, 167);
		
		Label label = new Label(group, SWT.NONE);
		label.setText("Nom :");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setBounds(10, 57, 40, 21);
		
		txtNom = new Text(group, SWT.BORDER);
		txtNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtNom.setBounds(56, 55, 330, 23);
		
		Label lblPrenom = new Label(group, SWT.NONE);
		lblPrenom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblPrenom.setBounds(10, 92, 58, 21);
		lblPrenom.setText("Prenom :");
		
		txtPrenom = new Text(group, SWT.BORDER);
		txtPrenom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPrenom.setBounds(71, 90, 315, 23);
		
		Label lblMatricule = new Label(group, SWT.NONE);
		lblMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblMatricule.setBounds(10, 22, 69, 21);
		lblMatricule.setText("Matricule :");
		
		txtMatricule = new Text(group, SWT.BORDER);
		txtMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtMatricule.setBounds(85, 20, 301, 23);
		
		
////////// Buttons
		Button btnCreer = new Button(group, SWT.NONE);
		btnCreer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				matricule = txtMatricule.getText();
				nom = txtNom.getText();
				prenom = txtPrenom.getText();
				insertQuery(matricule, nom, prenom);
				shlCrerUnProfesseur.close();
				
			}
		});
		btnCreer.setText("Cr\u00E9er");
		btnCreer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnCreer.setBounds(239, 129, 75, 25);
		
		Button btnAnnuler = new Button(group, SWT.NONE);
		btnAnnuler.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlCrerUnProfesseur.close();
			}
		});
		btnAnnuler.setText("Annuler");
		btnAnnuler.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAnnuler.setBounds(82, 129, 75, 25);

	}

	private void insertQuery(String matricule, String nom, String prenom) {
		String query = "INSERT INTO PROFESSEUR VALUES (?,?,?,?);" ;
		
		Connection conn = null;
		PreparedStatement stmt =null;
		try {
			String url = "jdbc:sqlite:V:/Code/test/test-folder-2/database/scandocdb.db";
			conn = DriverManager.getConnection(url);
			
			if(conn != null) {
				//conn.setAutoCommit(false);
				stmt = conn.prepareStatement(query);
				stmt.setNull(1, java.sql.Types.INTEGER);
				stmt.setString(2, matricule);
				stmt.setString(3, nom);
				stmt.setString(4, prenom);
				stmt.executeUpdate();
				conn.close();
			}	
				
		}catch(SQLException ex1) {
			System.out.println(ex1.getMessage());
		} 
	}
}
