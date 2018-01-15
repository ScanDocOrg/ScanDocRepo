package scandoc.Documents;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DiagConfirmationEffacementDocument extends Dialog {

	protected Object result;
	protected Shell shlConfirmationEffacement;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagConfirmationEffacementDocument(Shell parent, int style) {
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
		shlConfirmationEffacement.setSize(308, 159);
		shlConfirmationEffacement.setText("Confirmation Effacement");
		shlConfirmationEffacement.setLayout(null);
		
		Button btnNewButton = new Button(shlConfirmationEffacement, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setBounds(52, 96, 75, 25);
		btnNewButton.setText("Annuler");
		
		Button btnConfirmer = new Button(shlConfirmationEffacement, SWT.NONE);
		btnConfirmer.setBounds(179, 96, 75, 25);
		btnConfirmer.setText("Confirmer");
		
		Label lblVoullezvousVraimentEffacer = new Label(shlConfirmationEffacement, SWT.NONE);
		lblVoullezvousVraimentEffacer.setBounds(32, 35, 238, 15);
		lblVoullezvousVraimentEffacer.setText("Voullez-vous vraiment effacer ce document ?");

	}
}
