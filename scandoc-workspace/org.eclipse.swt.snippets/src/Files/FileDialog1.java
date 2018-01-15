package Files;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class FileDialog1 {

  public static void main(String[] args) {
    Display display = new Display();
    final Shell shell = new Shell(display);

    FileDialog dlg = new FileDialog(shell, SWT.OPEN);

    dlg.setFilterNames(new String[] { "OpenOffice.org Spreadsheet Files (*.sxc)",
        "Microsoft Excel Spreadsheet Files (*.xls)", "Comma Separated Values Files (*.csv)",
        "All Files (*.*)" });

    dlg.setFilterExtensions(new String[] { "*.sxc", "*.xls", "*.csv", "*.*" });
    String fileName = dlg.open();
    if (fileName != null) {
      System.out.println(fileName);
    }

    display.dispose();

  }
}