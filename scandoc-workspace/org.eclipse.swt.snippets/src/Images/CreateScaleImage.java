/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
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
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

public class CreateScaleImage {

public static void main (String [] args) {
	
		Display display = new Display ();
		Shell shell = new Shell (display);
		Scale scale = new Scale (shell, SWT.BORDER);
		Rectangle clientArea = shell.getClientArea ();
		scale.setBounds (clientArea.x, clientArea.y, 200, 64);
		scale.setMaximum (40);
		scale.setPageIncrement (5);
		shell.open ();
		
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		
		display.dispose ();
	}
}
