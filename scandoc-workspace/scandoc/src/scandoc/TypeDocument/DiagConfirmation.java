package scandoc.TypeDocument;



import javax.swing.event.EventListenerList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import scandoc.MainWindow;
import scandoc.Events.EleveErasedEvent;
import scandoc.Events.EleveErasedListener;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;

public class DiagConfirmation extends Dialog {

	protected Object result;
	protected Shell shlConfirmationEffacement;
	private EventListenerList eleveErasedListenerList = new EventListenerList();
	private String message;
	public Boolean confirmation;
	//private Display display;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DiagConfirmation(Shell parent, int style, String title, String message) {
		super(parent, style);
		setText(title);
		this.message = message;
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
		
		Display display = getParent().getDisplay();
		shlConfirmationEffacement = new Shell(getParent(), getStyle());
		
		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shlConfirmationEffacement.getBounds ();
		int x_main = (bounds.width-421)/2;
		int y_main = (bounds.height-215)/2;
		int x = x_main +(460-421)/2;
		int y = y_main + (500-215)/2;
		shlConfirmationEffacement.setBounds(x, y, 421, 215);
		
		
		shlConfirmationEffacement.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		shlConfirmationEffacement.setSize(353, 192);
		shlConfirmationEffacement.setText("Confirmation Effacement");
		shlConfirmationEffacement.setLayout(null);
		
		Group group = new Group(shlConfirmationEffacement, SWT.BORDER | SWT.SHADOW_OUT);
		group.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		group.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		group.setBounds(10, 0, 327, 153);
		
		Label lblVoullezvousVraimentEffacer = new Label(group, SWT.NONE);
		lblVoullezvousVraimentEffacer.setAlignment(SWT.CENTER);
		lblVoullezvousVraimentEffacer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblVoullezvousVraimentEffacer.setBounds(10, 46, 307, 25);
		lblVoullezvousVraimentEffacer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblVoullezvousVraimentEffacer.setText(message);
		
		Button btnConfirmer = new Button(group, SWT.CENTER);
		btnConfirmer.setGrayed(true);
		btnConfirmer.setAlignment(SWT.LEFT);
		btnConfirmer.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnConfirmer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnConfirmer.setBounds(193, 118, 75, 25);
		btnConfirmer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				confirmation = true;
				System.out.println("Confirmer");
				shlConfirmationEffacement.close();
			}
		});
		btnConfirmer.setText("Confirmer");
		
		Button btnAnnuler = new Button(group, SWT.CENTER);
		btnAnnuler.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAnnuler.setBounds(59, 118, 75, 25);
		btnAnnuler.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				confirmation = false;
				System.out.println("Annuler");
				shlConfirmationEffacement.close();
			}
		});
		btnAnnuler.setText("Annuler");

		//while (!shlConfirmationEffacement.isDisposed ()) {
		//	if (!display.readAndDispatch ()) display.sleep ();
		//}
	}
	
	public void addEleveErasedMessageListener(EleveErasedListener listener) {
        eleveErasedListenerList.add(EleveErasedListener.class, listener);
    }
    public void removeEleveErasedMessageListener(EleveErasedListener listener) {
    	eleveErasedListenerList.remove(EleveErasedListener.class, listener);
    }
    public void fireEvent(EleveErasedEvent evt) {
        Object[] listeners = eleveErasedListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == EleveErasedListener.class) {
                ((EleveErasedListener) listeners[i+1]).EleveErased(evt);
            }
        }
    }
}
