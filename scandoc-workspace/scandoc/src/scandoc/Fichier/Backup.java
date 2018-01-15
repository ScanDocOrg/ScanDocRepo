package scandoc.Fichier;			


import java.io.*;
import scandoc.tools.*;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.IOException;
import java.nio.file.*;

import org.eclipse.swt.widgets.MessageBox;


public class Backup {

	private XConfigFile configFile;
	
	//
	// Constructors
	//
	public Backup(XConfigFile configFile) {
		if(configFile.getConfigured() && configFile.getFileExist()) {
			this.setConfigFile(configFile);		
		}else {
			return;
		}
	}

	//
	// Getters & Setters
	//
	public XConfigFile getConfigFile() {
		return configFile;
	}

	public void setConfigFile(XConfigFile configFile) {
		this.configFile = configFile;
	}

	public String getBackupPath() {
		if(this.getConfigFile().getConfigured()) {
			return this.getConfigFile().getBackupPath();
		}else {
			return "null";
		}
	}

	public String getFolderPath() {
		if(this.getConfigFile().getConfigured()) {
			return this.getConfigFile().getFolderPath();
		}else {
			return "null";
		}
	}

	//
	// Create backup
	//
	public void CreateBackup() throws IOException {
		File backup = new File(this.getConfigFile().getBackupPath());
		if(getConfigFile().getConfigured()) {
			if(this.getConfigFile().getIsBackupPathConfigured()) {
				backup.delete();
			}
			try {
				ZipFile zip = new ZipFile(configFile.getBackupPath());
				ZipParameters parameters = new ZipParameters();
				parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
				parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
				zip.addFolder(configFile.getFolderPath(), parameters);	
			}catch (ZipException e) {
				e.printStackTrace();
			}
		}
	}
	
	//
	// Restaure backup à la position actuelle de la base de données.
	//
	public void RestoreBackup() {	
		try {
			ZipFile zipFile = new ZipFile(configFile.getBackupPath());
			zipFile.extractAll(configFile.getFolderPath());
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
	public void RestoreBackupFromExternalZip(String backupZip) {	
		try {
			ZipFile zipFile = new ZipFile(backupZip);
			zipFile.extractAll(configFile.getFolderPath());
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
}
