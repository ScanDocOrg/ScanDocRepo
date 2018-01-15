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
package Files;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class PromptDirectory {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.open ();
	DirectoryDialog dialog = new DirectoryDialog (shell);
	String platform = SWT.getPlatform();
	dialog.setFilterPath (platform.equals("win32") ? "c:\\" : "/");
	System.out.println ("RESULT=" + dialog.open ());
	
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
