package scandoc.Eleves;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import java.sql.*;

public class DiagCreerUnEleve extends Dialog {

	protected Object result;
	protected Shell shell;
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
	public DiagCreerUnEleve(Shell parent, int style) {
		super(parent, style);
		setText("Créer un élève");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
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
		shell = new Shell(getParent(), getStyle());
		Display display = getParent().getDisplay();

		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shell.getBounds ();
		int x_main = (bounds.width-460)/2;
		int y_main = (bounds.height-500)/2;
		int x = x_main +(460-421)/2;
		int y = y_main + (500-215)/2;
		//shell.setLocation (x, y);
		shell.setBounds(x, y, 421, 215);
		
		//shell.setSize(421, 215);
		shell.setText(getText());
		
		Group group = new Group(shell, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		group.setBounds(10, 0, 396, 157);
		
		Label label = new Label(group, SWT.NONE);
		label.setText("Nom :");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setBounds(10, 57, 40, 21);
		
		txtNom = new Text(group, SWT.BORDER);
		txtNom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtNom.setBounds(56, 55, 330, 23);
		txtNom.setText("");
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setText("Prenom :");
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setBounds(10, 92, 58, 21);
		
		txtPrenom = new Text(group, SWT.BORDER);
		txtPrenom.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPrenom.setBounds(71, 90, 315, 23);
		txtPrenom.setText("");
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setText("Matricule :");
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_2.setBounds(10, 22, 69, 21);
		
		txtMatricule = new Text(group, SWT.BORDER);
		txtMatricule.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtMatricule.setBounds(85, 20, 301, 23);
/////////
		
		// Button Creer
		Button btnCreer = new Button(group, SWT.NONE);
		btnCreer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nom = txtNom.getText();
				prenom = txtPrenom.getText();
				matricule = txtMatricule.getText();
				insertQuery(matricule, nom, prenom);
				
				
				shell.close();
			}
		});
		btnCreer.setText("Cr\u00E9er");
		btnCreer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnCreer.setBounds(239, 129, 75, 25);
		
		// Button Annuler
		Button btnAnnuler = new Button(group, SWT.NONE);
		btnAnnuler.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nom = "";
				prenom = "";
				matricule = ""; 
				shell.close();
			}
		});
		btnAnnuler.setText("Annuler");
		btnAnnuler.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAnnuler.setBounds(82, 129, 75, 25);

	}

	private void insertQuery(String matricule, String nom, String prenom) {
		String query = "INSERT INTO ELEVE VALUES (?,?,?,?);" ;
		
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
