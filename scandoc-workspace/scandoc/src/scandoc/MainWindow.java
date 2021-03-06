package scandoc;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import scandoc.Documents.*;
import scandoc.Eleves.*;
import scandoc.Events.*;
import scandoc.Fichier.*;
import scandoc.Misc.*;
import scandoc.Professeurs.*;
import scandoc.TypeDocument.*;
import scandoc.Fichier.*;
import org.eclipse.wb.swt.SWTResourceManager;


public class MainWindow implements EleveErasedListener {

	static int pageNum = -1;
	static String matricule;
	static String prenom;
	static String nom;
	static String nomDocument;
	public static XConfigFile configFile;
	public static Backup backup;
	public static Shell shell;
	public static Display display;
	public static CompChercherUnDocument compChercherUnDocument;
	/**
	 * Launch the application.
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		//
		// Main Shell
		//
		display = Display.getDefault();
		shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setBackground(SWTResourceManager.getColor(199, 205, 209));
		
		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shell.getBounds ();
		int x = (bounds.width-460)/2;
		int y = (bounds.height-500)/2;
		//shell.setLocation (x, y);
		shell.setBounds(x, y,460, 500);
		
		//StackLayout handle dynamically created composite 
		final Composite contentPanel = new Composite(shell, SWT.BORDER);
		contentPanel.setForeground(SWTResourceManager.getColor(199, 205, 209));
		contentPanel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
	    contentPanel.setBounds(0, 0, 460, 500);
	    final StackLayout layout = new StackLayout();
	    contentPanel.setLayout(layout);
	    
			
		try {
			configFile = new XConfigFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(configFile.getConfigured()) {
			backup = new Backup(configFile);
		}
		
	    //
		//Initialisation Vues
		//
	    
	    // Fichiers
	    CompGererDB compGereDB = new CompGererDB(contentPanel, SWT.NONE);
	    compGereDB.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	    compGereDB.setLayout(null);
	    
	    // Documents
	    compChercherUnDocument = new CompChercherUnDocument(contentPanel, SWT.NONE);
	    compChercherUnDocument.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
	    compChercherUnDocument.setLayout(null);
	    DiagConfirmationEffacementDocument diagConfirmationEffacementDocument = new DiagConfirmationEffacementDocument(shell, SWT.NONE);
		//DiagDetailDocument diagDetailDocument = new DiagDetailDocument(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, );
		//diagDetailDocument.open();
		DiagImporterDocument diagImporterDocument = new DiagImporterDocument(shell, SWT.NONE);

		// El�ves
		CompChercherUnEleve compChercherUnEleve = new CompChercherUnEleve(contentPanel, SWT.NONE);
		compChercherUnEleve.setLayout(null);	
		//DiagConfirmation diagConfirmationEffacementEleve = new DiagConfirmation(shell, SWT.NONE);
		
		// Misc
		CompAccueil compAccueil = new CompAccueil(contentPanel, SWT.NONE);
		compAccueil.setLayout(null);
		
		// Professeurs
		CompChercherUnProfesseur compChercherUnProfesseur = new CompChercherUnProfesseur(contentPanel, SWT.NONE);
		compChercherUnProfesseur.setLayout(null);
		DiagConfirmationEffacementProfesseur diagConfirmationEffacementProfesseur = new DiagConfirmationEffacementProfesseur(shell, SWT.NONE);
		
		
		// TypeDocument
		CompChercherTypeDocument compChercherTypeDocument = new CompChercherTypeDocument(contentPanel, SWT.NONE);
		compChercherTypeDocument.setLayout(null);
		//DiagConfirmationEffacementTypeDocument diagConfirmationEffacementTypeDocument = new DiagConfirmationEffacementTypeDocument(shell,SWT.NONE );
		
		
		shell.setSize(476, 561);
		shell.setText("Scandoc");
		shell.setLayout(null);
		
		//
		// MENU
		//
		Menu menu_1 = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu_1);
		
//////////
		// Fichiers
//////////
		MenuItem mntmFiles = new MenuItem(menu_1, SWT.CASCADE);
		mntmFiles.setText("Fichiers");
		
		Menu menuFichiers = new Menu(mntmFiles);
		mntmFiles.setMenu(menuFichiers);
		
		// G�rer les DB
		MenuItem mntmGererDB = new MenuItem(menuFichiers, SWT.NONE);
		mntmGererDB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu Creer Nouvelle BD s�lectionn�.");
				layout.topControl = compGereDB;
				contentPanel.layout();
			}
		});
		mntmGererDB.setText("Configurer et restaurer une base de donn\u00E9es.");
		
		// Quitter
		MenuItem mntmQuitter = new MenuItem(menuFichiers, SWT.NONE);
		mntmQuitter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu Quitter");
				shell.close(); 
			}
		});
		mntmQuitter.setText("Quitter");
		
//////////
		// Professeurs
//////////
		MenuItem mntmProfesseurs = new MenuItem(menu_1, SWT.CASCADE);
		mntmProfesseurs.setText("Professeurs");
		
		Menu menuProfesseurs = new Menu(mntmProfesseurs);
		mntmProfesseurs.setMenu(menuProfesseurs);
		
		// Chercher un professeur
		MenuItem mntmChercherProfesseur = new MenuItem(menuProfesseurs, SWT.NONE);
		mntmChercherProfesseur.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu Chercher Professeur");
				layout.topControl = compChercherUnProfesseur; 
				contentPanel.layout();
				compChercherUnProfesseur.refresh();
				
				compChercherUnProfesseur.table.addListener (SWT.Selection, event -> {
					//String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
					TableItem current = (TableItem)event.item;
					
					matricule = current.getText();
					
					CompDetailProfesseur compDetailProfesseur = new CompDetailProfesseur(contentPanel, SWT.NONE, shell, matricule);
					compDetailProfesseur.setLayout(null);
					System.out.println (current.getText());
					layout.topControl = compDetailProfesseur;
					contentPanel.layout();
				});
			}
		});
		mntmChercherProfesseur.setText("Chercher un professeur");
		
		//Cr�er un professeur
		MenuItem mntmCreerProfesseur = new MenuItem(menuProfesseurs, SWT.NONE);
		mntmCreerProfesseur.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu Creer Professeur");
				DiagCreerUnProfesseur diagCreerUnProfesseur = new DiagCreerUnProfesseur(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);							
				diagCreerUnProfesseur.open();
				
				matricule = diagCreerUnProfesseur.matricule;
				nom = diagCreerUnProfesseur.nom;
				prenom = diagCreerUnProfesseur.prenom;
				
			}
		});
		mntmCreerProfesseur.setText("Cr\u00E9er un professeur");
		
//////////
		// El�ves
//////////
		MenuItem mntmElves = new MenuItem(menu_1, SWT.CASCADE);
		mntmElves.setText("El\u00E8ves");
		
		Menu menuEleves = new Menu(mntmElves);
		mntmElves.setMenu(menuEleves);
		
		// Chercher un �l�ve
		MenuItem mntmChercherEleve = new MenuItem(menuEleves, SWT.NONE);
		mntmChercherEleve.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu Chercher El�ve");
				layout.topControl = compChercherUnEleve;
				contentPanel.layout();
				compChercherUnEleve.refresh();
				
				compChercherUnEleve.tblEleves.addListener (SWT.Selection, event -> {
					String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
					TableItem current = (TableItem)event.item;
					matricule = current.getText();
					
					CompDetailEleve compDetailEleve = new CompDetailEleve(contentPanel, SWT.NONE, shell, matricule);
					compDetailEleve.setLayout(null);
					System.out.println (current.getText());
					layout.topControl = compDetailEleve;
					contentPanel.layout();
				});
			}
		});
		mntmChercherEleve.setText("Chercher un \u00E9l\u00E8ve");
		
		// Creer un �l�ve
		MenuItem mntmCreerEleve = new MenuItem(menuEleves, SWT.NONE);
		mntmCreerEleve.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu Creer Eleve");
				DiagCreerUnEleve diagCreerUnEleve = new DiagCreerUnEleve(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL); 
				diagCreerUnEleve.open();
				matricule = diagCreerUnEleve.matricule;
				nom = diagCreerUnEleve.nom;
				prenom = diagCreerUnEleve.prenom;
				
				// Do Stuffs...
			}
		});
		mntmCreerEleve.setText("Cr\u00E9er un \u00E9l\u00E8ve");
		

//////////
		// Document
//////////
		MenuItem mntmDocuments = new MenuItem(menu_1, SWT.CASCADE);
		mntmDocuments.setText("Documents");
		
		Menu menuDocuments = new Menu(mntmDocuments);
		mntmDocuments.setMenu(menuDocuments);
		
		// Chercher un document
		MenuItem mntmChercherDocument = new MenuItem(menuDocuments, SWT.NONE);
		mntmChercherDocument.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu Chercher Document");
				layout.topControl= compChercherUnDocument;
				contentPanel.layout();
				compChercherUnDocument.refresh();
				
				
				compChercherUnDocument.table.addListener (SWT.Selection, event -> {
					TableItem current = (TableItem)event.item;
					System.out.println (current.getText());
				});
			}
		});
		mntmChercherDocument.setText("Chercher un document");
		
		new MenuItem(menuDocuments, SWT.SEPARATOR);
		
//////////
		// TypeDocument
//////////
		MenuItem mntmTypesDocument = new MenuItem(menuDocuments, SWT.CASCADE);
		mntmTypesDocument.setText("Types Document");
		
		Menu menuTypeDocument = new Menu(mntmTypesDocument);
		mntmTypesDocument.setMenu(menuTypeDocument);
		
		// Chercher un type de document
		MenuItem mntmChercherTypeDocu = new MenuItem(menuTypeDocument, SWT.NONE);
		mntmChercherTypeDocu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu Chercher Type Document");
				layout.topControl=compChercherTypeDocument;
				contentPanel.layout();
				
				compChercherTypeDocument.table.addListener (SWT.Selection, event -> {
					TableItem current = (TableItem)event.item;
					System.out.println (current.getText());
					int id = Integer.parseInt(current.getText());
					DiagDetailTypeDocument diagUpdate = new DiagDetailTypeDocument(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, id); 
					diagUpdate.open();
				});	
			}
		});
		mntmChercherTypeDocu.setText("Chercher un type de document");
		
		// Cr�er type document
		MenuItem mntmCreerTypeDocu = new MenuItem(menuTypeDocument, SWT.NONE);
		mntmCreerTypeDocu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu Cr�er Type Document");
				DiagCreerTypeDocument diagCreerTypeDocument = new DiagCreerTypeDocument(shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				diagCreerTypeDocument.open();
				nomDocument = diagCreerTypeDocument.nomDocument;
				
				// Do Stuff.
			}
		});
		mntmCreerTypeDocu.setText("Cr\u00E9er un type de document");
		
		
//////////
		// ?
//////////
		MenuItem menuItem = new MenuItem(menu_1, SWT.CASCADE);
		menuItem.setText("?");
		
		Menu menuAide = new Menu(menuItem);
		menuItem.setMenu(menuAide);
		
		// Aide
		MenuItem mntmAide = new MenuItem(menuAide, SWT.NONE);
		mntmAide.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DiagHelp diagHelp = new DiagHelp(display);
				diagHelp.open();
				diagHelp.layout();
				
				while (!diagHelp.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
		mntmAide.setText("Aide");
		
		// A propos
		MenuItem mntmAPropos = new MenuItem(menuAide, SWT.NONE);
		mntmAPropos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Menu A propos");
			}
		});
		mntmAPropos.setText("A propos...");
//////////
		// Fin Menu
//////////
		
		shell.open();
		shell.layout();
		//diagDetailDocument.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		System.exit(0);
	}
	
	@Override
	public void EleveErased(EleveErasedEvent e) {
		// TODO Auto-generated method stub
		
	}
}
