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

public class MenuAcceleratorMnemonic {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Menu bar = new Menu (shell, SWT.BAR);
	shell.setMenuBar (bar);
	MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
	fileItem.setText ("&File");
	Menu submenu = new Menu (shell, SWT.DROP_DOWN);
	fileItem.setMenu (submenu);
	MenuItem item = new MenuItem (submenu, SWT.PUSH);
	item.addListener (SWT.Selection, e -> System.out.println ("Select All"));
	item.setText ("Select &All\tCtrl+A");
	item.setAccelerator (SWT.MOD1 + 'A');
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
