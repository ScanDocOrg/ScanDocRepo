/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package Browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class RenderHTML {

public static void main(String [] args) {
	/* Relative links: use the HTML base tag */
	String html = "<html><head>"+
		"<base href=\"http://www.eclipse.org/swt/\" >"+
		"<title>HTML Test</title></head>"+
		"<body><a href=\"faq.php\">local link</a></body></html>";

	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	Browser browser;
	try {
		browser = new Browser(shell, SWT.NONE);
	} catch (SWTError e) {
		System.out.println("Could not instantiate Browser: " + e.getMessage());
		display.dispose();
		return;
	}
	browser.setText(html);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
