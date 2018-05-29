package com.neotys.ftp.FtpAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import com.google.common.base.Optional;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;


public final class FtpSendStreamAction implements Action{
	private static final String BUNDLE_NAME = "com.neotys.ftp.FtpAction.bundle";
	private static final String DISPLAY_NAME = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayName");
	private static final String DISPLAY_PATH = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayPath");
	public static final String Hostname="Hostname";
	public static final String Login="Login";
	public static final String Port="Port";
	public static final String Password="Password";
	public static final String FileName="FileName";
	public static final String FileContent="FileContent";
	@Override
	public String getType() {
		return "FtpSendStream";
	}

	@Override
	public List<ActionParameter> getDefaultActionParameters() {
		final List<ActionParameter> parameters = new ArrayList<ActionParameter>();
		parameters.add(new ActionParameter(Hostname,"Host"));
		parameters.add(new ActionParameter(Port,"21"));
		parameters.add(new ActionParameter(FileName,"file.xml"));
		parameters.add(new ActionParameter(FileContent,"<xml></xml>"));

		return parameters;
	}

	@Override
	public Class<? extends ActionEngine> getEngineClass() {
		return FtpSendStreamActionEngine.class;
	}

	@Override
	public Icon getIcon() {
		// TODO Add an icon
		return null;
	}

	@Override
	public boolean getDefaultIsHit(){
		return false;
	}

	@Override
	public String getDescription() {
		final StringBuilder description = new StringBuilder();
		// TODO Add description
		description.append("FtpSendStream is uploading a file to the FTP repository\n");
		description.append("\tHostname : Host of the xmpp server");
		description.append("\tPort : Port of the xmpp server");
		description.append("\tFileName :  Xmpp Domain ");
		description.append("\tLogin : Optionnal login to connect to the ftp server ");
		description.append("\tPassword : Optionnal password to connect to the ftp server ");
		return description.toString();
	}

	@Override
	public String getDisplayName() {
		return DISPLAY_NAME;
	}

	@Override
	public String getDisplayPath() {
		return DISPLAY_PATH;
	}

	@Override
	public Optional<String> getMinimumNeoLoadVersion() {
		return Optional.absent();
	}

	@Override
	public Optional<String> getMaximumNeoLoadVersion() {
		return Optional.absent();
	}
}
