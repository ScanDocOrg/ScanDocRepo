package scandoc.Fichier;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DiagInfo extends Dialog {

	protected Object result;
	protected Shell shlInfo;
	protected String message;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagInfo(Shell parent, int style, String windowTitle, String message) {
		super(parent, style);
		setText(windowTitle);
		this.message = message;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlInfo.open();
		shlInfo.layout();
		Display display = getParent().getDisplay();
		while (!shlInfo.isDisposed()) {
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
		shlInfo = new Shell(getParent(), getStyle());
		shlInfo.setSize(410, 168);
		shlInfo.setText(message);
		shlInfo.setLayout(null);
		
		Button btnConfirmer = new Button(shlInfo, SWT.NONE);
		btnConfirmer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlInfo.close();
			}
		});
		btnConfirmer.setBounds(164, 92, 75, 25);
		btnConfirmer.setText("OK");
		
		Label lblVoullezvousVraimentEffacer = new Label(shlInfo, SWT.NONE);
		lblVoullezvousVraimentEffacer.setAlignment(SWT.CENTER);
		lblVoullezvousVraimentEffacer.setBounds(36, 25, 331, 61);

	}
	

	
	
	
}
