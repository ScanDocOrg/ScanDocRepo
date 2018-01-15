/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package Menu;



import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class PopupMenu2 {
public static void main (String [] args) {
	final Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.addListener (SWT.MenuDetect, event -> {
		Menu menu = new Menu (shell, SWT.POP_UP);
		MenuItem item = new MenuItem (menu, SWT.PUSH);
		item.setText ("Menu Item");
		item.addListener (SWT.Selection, e -> System.out.println ("Item Selected"));
		menu.setLocation (event.x, event.y);
		menu.setVisible (true);
		while (!menu.isDisposed () && menu.isVisible ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		while (display.readAndDispatch()); // needed, to get the selection event, which is fired AFTER the menu is hidden
		menu.dispose ();
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
