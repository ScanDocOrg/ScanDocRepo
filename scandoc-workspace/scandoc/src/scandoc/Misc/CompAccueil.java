package scandoc.Misc;



import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class CompAccueil extends Composite {
	
	

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompAccueil(Composite parent, int style) {
		super(parent, SWT.EMBEDDED);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		setLayout(null);
		this.setSize(460,500);
		
		Label lblCeciEstLaccueil = new Label(this, SWT.NONE);
		lblCeciEstLaccueil.setFont(SWTResourceManager.getFont("Segoe UI", 27, SWT.BOLD));
		lblCeciEstLaccueil.setBounds(10, 88, 440, 55);
		lblCeciEstLaccueil.setText("Bienvenue sur Scandoc");
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		label_2.setBounds(675, 481, -67, 64);
		
		Label lblLapplicationQuiVous = new Label(this, SWT.CENTER);
		lblLapplicationQuiVous.setText("L'application qui vous aide \u00E0 vous en sortir dans votre paperasse");
		lblLapplicationQuiVous.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.BOLD));
		lblLapplicationQuiVous.setBounds(10, 161, 440, 112);
		
		Label lblPourCommencerChoisissez = new Label(this, SWT.CENTER);
		lblPourCommencerChoisissez.setText("        Pour commencer,          choisissez un \u00E9l\u00E9ment du menu.");
		lblPourCommencerChoisissez.setFont(SWTResourceManager.getFont("Segoe UI", 19, SWT.BOLD));
		lblPourCommencerChoisissez.setBounds(10, 333, 440, 79);
	}

	@Override
	public void setSize(int width, int height) {
		// TODO Auto-generated method stub
		super.setSize(width, height);
	} 
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
