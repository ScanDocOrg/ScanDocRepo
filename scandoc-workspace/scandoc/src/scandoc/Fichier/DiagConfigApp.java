package scandoc.Fichier;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import scandoc.MainWindow;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DiagConfigApp extends Dialog {

	protected Object result;
	protected Shell shlConfiguration;
	private Text txtDB;
	private Text txtBackupPath;
	public XConfigFile configFile;
	public Backup backup;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagConfigApp(Shell parent, int style) {
		super(parent, style);
		setText("Configuration");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlConfiguration.open();
		shlConfiguration.layout();
		Display display = getParent().getDisplay();
		while (!shlConfiguration.isDisposed()) {
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
		shlConfiguration = new Shell(getParent(), getStyle());
		shlConfiguration.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		shlConfiguration.setSize(508, 372);
		shlConfiguration.setText("Configuration");
		configFile = MainWindow.configFile;
		
		backup = MainWindow.backup;
		
		Group grpSlectionnerLemplacementDes = new Group(shlConfiguration, SWT.BORDER | SWT.SHADOW_OUT);
		grpSlectionnerLemplacementDes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpSlectionnerLemplacementDes.setText("S\u00E9lectionner l'emplacement des donn\u00E9es");
		grpSlectionnerLemplacementDes.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpSlectionnerLemplacementDes.setBounds(10, 10, 482, 295);
		
		txtDB = new Text(grpSlectionnerLemplacementDes, SWT.BORDER | SWT.READ_ONLY);
		txtDB.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtDB.setText("");
		txtDB.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtDB.setBounds(10, 51, 462, 20);
		
		Label lblEmplacementDeLa = new Label(grpSlectionnerLemplacementDes, SWT.NONE);
		lblEmplacementDeLa.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblEmplacementDeLa.setText("Emplacement de la base de donn\u00E9es");
		lblEmplacementDeLa.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblEmplacementDeLa.setBounds(10, 25, 420, 20);
		
		Button btnSelectBackupPath = new Button(grpSlectionnerLemplacementDes, SWT.NONE);
		btnSelectBackupPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String backupPath = SelectDirectory(shlConfiguration);
				txtBackupPath.setText(backupPath);
				configFile.WriteXConfigBackupPath(backupPath);
			}
		});
		btnSelectBackupPath.setText("Selectionner l'emplacement du backup");
		btnSelectBackupPath.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		btnSelectBackupPath.setBounds(10, 173, 462, 30);
		
		txtBackupPath = new Text(grpSlectionnerLemplacementDes, SWT.BORDER | SWT.READ_ONLY);
		txtBackupPath.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtBackupPath.setText("");
		txtBackupPath.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtBackupPath.setBounds(10, 147, 462, 20);
		
		Label lblEmplacementDuBackup = new Label(grpSlectionnerLemplacementDes, SWT.NONE);
		lblEmplacementDuBackup.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblEmplacementDuBackup.setText("Emplacement du backup");
		lblEmplacementDuBackup.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblEmplacementDuBackup.setBounds(10, 121, 171, 20);
		
		Label label_2 = new Label(grpSlectionnerLemplacementDes, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(10, 113, 462, 2);
		
		Button btnSlectionnerLeDossier = new Button(grpSlectionnerLemplacementDes, SWT.NONE);
		btnSlectionnerLeDossier.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String folderPath = SelectDirectory(shlConfiguration);
				txtDB.setText(folderPath);
				configFile.WriteXConfigFolderPath(folderPath);
			}
		});
		btnSlectionnerLeDossier.setText("S\u00E9lectionner l'emplacement de la base de donn\u00E9es");
		btnSlectionnerLeDossier.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		btnSlectionnerLeDossier.setBounds(10, 77, 462, 30);
		
		Label label_3 = new Label(grpSlectionnerLemplacementDes, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		label_3.setBounds(10, 209, 462, 2);
		
		Button btnSelectBackupZip = new Button(grpSlectionnerLemplacementDes, SWT.BORDER);
		btnSelectBackupZip.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String backupFile = SelectBackupFile();
				if(backup.getConfigFile().getIsFolderPathConfigured()) {
					backup.RestoreBackupFromExternalZip(backupFile);
				}
			}
		});
		btnSelectBackupZip.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnSelectBackupZip.setBounds(10, 221, 462, 64);
		btnSelectBackupZip.setText("Restaurer \u00E0 partir d'une archive externe (.zip)");
		btnSelectBackupZip.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		
		Button btnOk = new Button(shlConfiguration, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlConfiguration.close();
			}
		});
		btnOk.setBounds(213, 311, 75, 25);
		btnOk.setText("OK");
	}

	protected String SelectDirectory(Shell shell) {
		
		DirectoryDialog dialog = new DirectoryDialog (shell);
		String platform = SWT.getPlatform();
		dialog.setFilterPath (platform.equals("win32") ? "c:\\" : "/");
		return dialog.open ();
	}
	
	protected String SelectBackupFile() {
		FileDialog dialog = new FileDialog (shlConfiguration, SWT.SELECTED);
		String [] filterNames = new String [] {"Image Files"};
		String [] filterExtensions = new String [] {"*.zip"};
		String filterPath = "/";
		String platform = SWT.getPlatform();
		if (platform.equals("win32")) {
			filterNames = new String [] {"Zip Files"};
			filterExtensions = new String [] {"*.zip"};
			filterPath = "c:\\";
		}
		dialog.setFilterNames (filterNames);
		dialog.setFilterExtensions (filterExtensions);
		dialog.setFilterPath (filterPath);
		dialog.setFileName ("myfile");
		return dialog.open ();
	}
}
