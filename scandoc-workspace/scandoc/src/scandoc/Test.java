package scandoc;
import java.io.IOException;

import scandoc.Fichier.*;
import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import scandoc.Documents.*;

import hirondelle.date4j.DateTime;

public class Test {

	public static XConfigFile xConfigFile;
	
	
	
	
	public static void main(String[] args) {
		/*
		// TODO Auto-generated method stub
		try {
			xConfigFile = new XConfigFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		//xConfigFile.WriteXConfigBackupPath("kaboum");
		//xConfigFile.WriteXConfigFolderPath("ppppppp");
		Display display = Display.getDefault();
		Shell shell = new Shell(SWT.CLOSE | SWT.TITLE );
		//DiagDetailDocument diagDetailDocument = new DiagDetailDocument(shell,SWT.CLOSE | SWT.TITLE );
		//diagDetailDocument.open();
		
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		System.exit(0);
		
		
		//xConfigFile.WriteXConfigLastBackupDate(DateTime.today(TimeZone.getDefault()));
		
		//DateTime c = DateTime.today(TimeZone.getDefault());
		
		//System.out.println(c.toString());
		
		//DateTime f = new DateTime(c.toString());
		
		//System.out.println(f.toString());
		
	}

}
