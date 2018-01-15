/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This Example Content is intended to demonstrate
 * usage of Eclipse technology. It is provided to you under the terms and
 * conditions of the Eclipse Distribution License v1.0 which is available
 * at http://www.eclipse.org/org/documents/edl-v10.php
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package Images;

/*
 * draw an image scaled to half size and double size
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class scaledImage {
	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display, SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);
		
		final Image image = display.getSystemImage (SWT.ICON_QUESTION);
		
		//Image image =new Image(display,);
		
		shell.addListener (SWT.Paint, e -> {
			Rectangle rect = image.getBounds ();
			GC gc = e.gc;
			
			int width = rect.width;
			int height = rect.height;
			
			int position_x = 180; 
			int position_y = 80;
			
			gc.drawImage (image, 0, 0, width, height, position_x, position_y, width*2, height*2);
			//gc.drawImage (image, 0, 0, width, height, x+width, y, (int)Math.round(width * 0.5), (int)Math.round(height * 0.5));
			//gc.drawImage (image, 0, 0, width, height, x+width+(int)Math.round(width * 0.5), y, width * 2, height * 2);
		});
		
		shell.setSize (600, 400);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) {
				display.sleep ();
			}
		}
		display.dispose ();
	}
}