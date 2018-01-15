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
package Images;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MouseHover {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setSize (100, 100);
	shell.addListener (SWT.MouseEnter, e -> System.out.println ("ENTER"));
	shell.addListener (SWT.MouseExit, e -> System.out.println ("EXIT"));
	shell.addListener (SWT.MouseHover, e -> System.out.println ("HOVER"));
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
