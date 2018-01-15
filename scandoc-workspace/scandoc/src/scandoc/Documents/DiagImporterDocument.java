package scandoc.Documents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class DiagImporterDocument extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagImporterDocument(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
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
		shell.setSize(450, 579);
		shell.setText(getText());
		
		Group group = new Group(shell, SWT.NONE);
		group.setText("Document");
		group.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		group.setBounds(10, 0, 424, 540);
		
		Label label = new Label(group, SWT.NONE);
		label.setText("ID :");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setBounds(10, 28, 22, 21);
		
		text = new Text(group, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		text.setBounds(46, 28, 114, 21);
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setText("Type :");
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setBounds(178, 28, 39, 21);
		
		text_1 = new Text(group, SWT.BORDER);
		text_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		text_1.setBounds(119, 69, 295, 21);
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setText("Nom Titulaire :");
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_2.setBounds(10, 69, 103, 21);
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setText("Prenom Titulaire :");
		label_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_3.setBounds(10, 111, 118, 21);
		
		text_2 = new Text(group, SWT.BORDER);
		text_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		text_2.setBounds(134, 111, 280, 21);
		
		Label label_4 = new Label(group, SWT.NONE);
		label_4.setBounds(10, 138, 404, 361);
		
		Button button = new Button(group, SWT.NONE);
		button.setText("Effacer");
		button.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button.setBounds(13, 505, 75, 25);
		
		Button button_1 = new Button(group, SWT.NONE);
		button_1.setText("Editer");
		button_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button_1.setBounds(101, 505, 75, 25);
		
		Button button_2 = new Button(group, SWT.NONE);
		button_2.setText("Save");
		button_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button_2.setBounds(189, 505, 75, 25);
		
		Button button_3 = new Button(group, SWT.NONE);
		button_3.setText("Change Document");
		button_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button_3.setBounds(277, 505, 132, 25);
		
		Combo combo = new Combo(group, SWT.NONE);
		combo.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		combo.setBounds(223, 25, 162, 28);

	}
}
