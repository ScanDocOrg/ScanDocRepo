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
package Dialog;



import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class PrintSelectedItem {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	final List list = new List (shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	for (int i=0; i<128; i++) list.add ("Item " + i);
	Rectangle clientArea = shell.getClientArea ();
	list.setBounds (clientArea.x, clientArea.y, 100, 100);
	list.addListener (SWT.Selection, e -> {
		String string = "";
		int [] selection = list.getSelectionIndices ();
		for (int i=0; i<selection.length; i++) string += selection [i] + " ";
		System.out.println ("Selection={" + string + "}");
	});
	list.addListener (SWT.DefaultSelection, e -> {
		String string = "";
		int [] selection = list.getSelectionIndices ();
		for (int i=0; i<selection.length; i++) string += selection [i] + " ";
		System.out.println ("DefaultSelection={" + string + "}");
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
