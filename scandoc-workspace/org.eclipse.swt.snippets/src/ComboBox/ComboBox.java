/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package ComboBox;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ComboBox {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Combo combo = new Combo (shell, SWT.READ_ONLY);
	combo.setItems ("Alpha", "Bravo", "Charlie");
	Rectangle clientArea = shell.getClientArea ();
	combo.setBounds (clientArea.x, clientArea.y, 200, 200);
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
