package scandoc.Professeurs;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DiagConfirmationEffacementProfesseur extends Dialog {

	protected Object result;
	protected Shell shlConfirmationEffacement;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagConfirmationEffacementProfesseur(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlConfirmationEffacement.open();
		shlConfirmationEffacement.layout();
		Display display = getParent().getDisplay();
		while (!shlConfirmationEffacement.isDisposed()) {
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
		shlConfirmationEffacement = new Shell(getParent(), getStyle());
		shlConfirmationEffacement.setSize(290, 168);
		shlConfirmationEffacement.setText("Confirmation Effacement");
		shlConfirmationEffacement.setLayout(null);
		
		Button btnNewButton = new Button(shlConfirmationEffacement, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setBounds(44, 96, 75, 25);
		btnNewButton.setText("Annuler");
		
		Button btnConfirmer = new Button(shlConfirmationEffacement, SWT.NONE);
		btnConfirmer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		btnConfirmer.setBounds(163, 96, 75, 25);
		btnConfirmer.setText("Confirmer");
		
		Label lblVoullezvousVraimentEffacer = new Label(shlConfirmationEffacement, SWT.NONE);
		lblVoullezvousVraimentEffacer.setAlignment(SWT.CENTER);
		lblVoullezvousVraimentEffacer.setBounds(22, 36, 238, 15);
		lblVoullezvousVraimentEffacer.setText("Voullez-vous vraiment effacer ce professeur ?");

	}
	
	
	
	
}
