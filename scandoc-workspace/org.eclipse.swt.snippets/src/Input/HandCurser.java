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
package Input;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class HandCurser {

public static void main (String [] args) {
	Display display = new Display ();
	final Cursor cursor = display.getSystemCursor(SWT.CURSOR_HAND);
	Shell shell = new Shell (display);
	shell.open ();
	final Button b = new Button (shell, 0);
	b.setText("Push to set cursor to hand");
	Rectangle clientArea = shell.getClientArea ();
	b.setBounds (clientArea.x + 10, clientArea.y + 10, 200, 200);
	b.addListener (SWT.Selection, e -> b.setCursor (cursor));
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
