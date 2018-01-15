package scandoc.Fichier;

import scandoc.MainWindow;
import scandoc.Fichier.*;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CompGererDB extends Composite {
	private Text txtEmplacementBackup;
	private Text txtEmplacementBase;
	public DiagConfigApp diagConfigApp;
	public Backup backup;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompGererDB(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(204, 204, 204));
		setLayout(null);
		
		backup = MainWindow.backup;
	
		
		Group grpConfigurerApplication = new Group(this, SWT.BORDER | SWT.SHADOW_OUT);
		grpConfigurerApplication.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpConfigurerApplication.setText("Configurer l'application");
		grpConfigurerApplication.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpConfigurerApplication.setBounds(10, 10, 440, 232);
		
		txtEmplacementBase = new Text(grpConfigurerApplication, SWT.BORDER | SWT.READ_ONLY);
		txtEmplacementBase.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		txtEmplacementBase.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtEmplacementBase.setBounds(10, 51, 420, 27);
		
		Label lblEmplacementDeLa = new Label(grpConfigurerApplication, SWT.NONE);
		lblEmplacementDeLa.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblEmplacementDeLa.setText("Emplacement de la base de donn\u00E9es");
		lblEmplacementDeLa.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblEmplacementDeLa.setBounds(10, 25, 420, 20);
		
		Group grpRestaurerUnBackup = new Group(this, SWT.BORDER | SWT.SHADOW_OUT);
		grpRestaurerUnBackup.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		grpRestaurerUnBackup.setText("Restaurer / faire un backup de la base de donn\u00E9es");
		grpRestaurerUnBackup.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		grpRestaurerUnBackup.setBounds(10, 248, 440, 242);
		
		Button btnConfigurerApplication = new Button(grpConfigurerApplication, SWT.NONE);
		btnConfigurerApplication.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		btnConfigurerApplication.setBounds(10, 171, 420, 51);
		btnConfigurerApplication.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				diagConfigApp = new DiagConfigApp(getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				diagConfigApp.open();
				UpdateEmplacementFolderTxt();
				UpdateEmplacementBackupTxt();
			}
		});
		btnConfigurerApplication.setText("Configurer l'application");
		btnConfigurerApplication.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		
		txtEmplacementBackup = new Text(grpConfigurerApplication, SWT.BORDER | SWT.READ_ONLY);
		txtEmplacementBackup.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtEmplacementBackup.setBounds(9, 128, 420, 29);
		txtEmplacementBackup.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		
		Label lblEmplacementDuBackup = new Label(grpConfigurerApplication, SWT.NONE);
		lblEmplacementDuBackup.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblEmplacementDuBackup.setBounds(10, 102, 420, 20);
		lblEmplacementDuBackup.setText("Emplacement du backup");
		lblEmplacementDuBackup.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		
		Label label = new Label(grpConfigurerApplication, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 94, 420, 2);
		
		Button btnRestaurerBackup = new Button(grpRestaurerUnBackup, SWT.NONE);
		btnRestaurerBackup.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnRestaurerBackup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(backup.getConfigFile().getConfigured()) {
					backup.RestoreBackup();
				}else {
					DiagInfo diagInfo = new DiagInfo(getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, "Info", "Votre application n'est pas encore configurée.\n\nVeuillez-d'abord configurer le backup et/ou la base de donnée.");
				}
			}
		});
		btnRestaurerBackup.setText("Restaurer la base de donn\u00E9es");
		btnRestaurerBackup.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		btnRestaurerBackup.setBounds(10, 134, 420, 98);
		
		Button btnFaireUnBackup = new Button(grpRestaurerUnBackup, SWT.NONE);
		btnFaireUnBackup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(backup.getConfigFile().getConfigured()) {
					try {
						backup.CreateBackup();
					}catch(IOException ex){
						System.out.println(ex.getMessage());
					}
				}else {
					DiagInfo diagInfo = new DiagInfo(getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, "Info", "Votre application n'est pas encore configurée.\n\nVeuillez-d'abord configurer le backup et/ou la base de donnée.");
				}
			}
		});
		btnFaireUnBackup.setText("Faire un backup de la base de donn\u00E9es");
		btnFaireUnBackup.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		btnFaireUnBackup.setBounds(10, 30, 420, 98);

		
		UpdateEmplacementFolderTxt();
		UpdateEmplacementBackupTxt();
	}

	@Override
	protected void checkSubclass() {
		
	}
	
	protected void UpdateEmplacementFolderTxt() {	
		if(backup.getConfigFile().getIsFolderPathConfigured()) {
			txtEmplacementBase.setText(backup.getConfigFile().getFolderPath());
		}else{
			txtEmplacementBase.setText("");
		};
	}
	
	protected void UpdateEmplacementBackupTxt() {
		if(backup.getConfigFile().getIsBackupPathConfigured()) {
			txtEmplacementBackup.setText(backup.getConfigFile().getBackupPath());
		}else {
			txtEmplacementBackup.setText("");
		}
	}
}
