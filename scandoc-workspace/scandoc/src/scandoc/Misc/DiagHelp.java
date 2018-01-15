package scandoc.Misc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class DiagHelp extends Shell {


	/**
	 * Create the shell.
	 * @param display
	 */
	public DiagHelp(Display display) {
		super(display, SWT.SHELL_TRIM);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		this.setLayout(gridLayout);
		ToolBar toolbar = new ToolBar(this, SWT.NONE);
		ToolItem itemBack = new ToolItem(toolbar, SWT.PUSH);
		itemBack.setImage(SWTResourceManager.getImage(DiagHelp.class, "/scandoc/ressource/Backward.png"));
		itemBack.setToolTipText("Back");
		//itemBack.setImage(new Image(display,));
		ToolItem itemForward = new ToolItem(toolbar, SWT.PUSH);
		itemForward.setImage(SWTResourceManager.getImage(DiagHelp.class, "/scandoc/ressource/Forward.png"));
		//itemForward.setText("Forward");
		itemForward.setToolTipText("Forward");
		//itemForward.setImage(new Image(display,"Forward.png"));
		
		GridData data = new GridData();
		data.horizontalSpan = 3;
		toolbar.setLayoutData(data);

		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		
		final Browser browser;
		try {
			browser = new Browser(this, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		browser.setLayoutData(data);

		final Label status = new Label(this, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		status.setLayoutData(data);

		final ProgressBar progressBar = new ProgressBar(this, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		progressBar.setLayoutData(data);

		/* event handling */
		Listener listener = event -> {
			ToolItem item = (ToolItem) event.widget;
			String string = item.getToolTipText();
			if (string.equals("Back"))
				browser.back();
			else if (string.equals("Forward"))
				browser.forward();
		};
		
		browser.addProgressListener(new ProgressListener() {
			@Override
			public void changed(ProgressEvent event) {
					if (event.total == 0) return;
					int ratio = event.current * 100 / event.total;
					progressBar.setSelection(ratio);
			}
			@Override
			public void completed(ProgressEvent event) {
				progressBar.setSelection(0);
			}
		});
		browser.addStatusTextListener(event -> status.setText(event.text));
		
		itemBack.addListener(SWT.Selection, listener);
		itemForward.addListener(SWT.Selection, listener);
		

		browser.setUrl("http://eclipse.org");

		setText("HELP");
		setSize(900, 600);
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
