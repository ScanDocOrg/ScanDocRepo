package Dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TransparentShell {

public static void main(String[] args) {
	Display display = new Display();
	final Image image = display.getSystemImage(SWT.ICON_WARNING);
	//Shell must be created with style SWT.NO_TRIM
	final Shell shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);
	shell.setBackground(display.getSystemColor(SWT.COLOR_RED));
	//define a region
	Region region = new Region();
	Rectangle pixel = new Rectangle(0, 0, 1, 1);
	for (int y = 0; y < 200; y+=2) {
			for (int x = 0; x < 200; x+=2) {
				pixel.x = x;
				pixel.y = y;
				region.add(pixel);
			}
		}
	//define the shape of the shell using setRegion
	shell.setRegion(region);
	shell.addPaintListener(e -> {
		Rectangle bounds = image.getBounds();
		Point size = shell.getSize();
		e.gc.drawImage(image, 0, 0, bounds.width, bounds.height, 10, 10, size.x-20, size.y-20);
	});
	shell.addListener(SWT.KeyDown, e -> {
		if (e.character == SWT.ESC) {
			shell.dispose();
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	region.dispose();
	display.dispose();
}
}