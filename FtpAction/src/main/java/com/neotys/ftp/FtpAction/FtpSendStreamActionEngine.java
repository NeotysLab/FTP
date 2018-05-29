package com.neotys.ftp.FtpAction;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.common.base.Strings;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public final class FtpSendStreamActionEngine implements ActionEngine {

	private String Hostname;
	private int port;
	private String Login;
	private String Password;
	private String FileName;
	private String FileContent;

	@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		final SampleResult sampleResult = new SampleResult();
		final StringBuilder requestBuilder = new StringBuilder();
		final StringBuilder responseBuilder = new StringBuilder();

		FTPClient ftpClient=null;
		String  Stport=null;
		boolean IsLogin=false;

		for(ActionParameter parameter:parameters) {
			switch(parameter.getName()) {


				case FtpSendStreamAction.Hostname:
					Hostname = parameter.getValue();
					break;
				case FtpSendStreamAction.Port:
					Stport = parameter.getValue();
					break;
				case FtpSendStreamAction.FileName:
					FileName = parameter.getValue();
					break;
				case FtpSendStreamAction.FileContent:
					FileContent = parameter.getValue();
					break;
				case FtpSendStreamAction.Login:
					Login = parameter.getValue();
					break;
				case FtpSendStreamAction.Password:
					Password = parameter.getValue();
					break;
			}
		}
		if (Strings.isNullOrEmpty(Hostname)) {
			return getErrorResult(context, sampleResult, "Invalid argument: Hostname cannot be null "
					+ FtpSendStreamAction.Hostname + ".", null);
		}
		if (Strings.isNullOrEmpty(Stport)) {
			return getErrorResult(context, sampleResult, "Invalid argument: Port cannot be null "
					+ FtpSendStreamAction.Port + ".", null);
		}
		else
		{
			try{
				port=Integer.parseInt(Stport);
			}
			catch(NumberFormatException e) {
				return getErrorResult(context, sampleResult, "Invalid argument: Port needs to be digit"
						+ FtpSendStreamAction.Port + ".", null);
			}
		}
		if (Strings.isNullOrEmpty(FileName)) {
			return getErrorResult(context, sampleResult, "Invalid argument: FileName cannot be null "
					+ FtpSendStreamAction.FileName + ".", null);
		}
		if (Strings.isNullOrEmpty(FileContent)) {
			return getErrorResult(context, sampleResult, "Invalid argument: FileContent cannot be null "
					+ FtpSendStreamAction.FileContent + ".", null);
		}
		if (!Strings.isNullOrEmpty(Login)) {
			IsLogin=true;

		}
		ftpClient = new FTPClient();
		try {
			sampleResult.sampleStart();

			ftpClient.connect(Hostname, port);
			if(IsLogin)
				ftpClient.login(Login, Password);

			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			InputStream stream = new ByteArrayInputStream(FileContent.getBytes(StandardCharsets.UTF_8));

			ftpClient.storeFile(FileName,stream);

			if(IsLogin)
				ftpClient.logout();


			appendLineToStringBuilder(responseBuilder, "FtpSendStream :."+FileName+" has been sent");
			// TODO perform execution.

			sampleResult.sampleEnd();
		}
		catch(Exception e)
		{
			return getErrorResult(context, sampleResult, "Technical Error : "
					+ e.getMessage() + ".", e);

		}
		finally {
			try {
				if (ftpClient != null) {
					if (ftpClient.isConnected())
						ftpClient.disconnect();
				}
			}
			 catch (IOException e) {
				 return getErrorResult(context, sampleResult, "Technical ftp Error : "
						 + e.getMessage() + ".", e);
			}
		}
		sampleResult.setRequestContent(requestBuilder.toString());
		sampleResult.setResponseContent(responseBuilder.toString());
		return sampleResult;
	}

	private void appendLineToStringBuilder(final StringBuilder sb, final String line){
		sb.append(line).append("\n");
	}

	/**
	 * This method allows to easily create an error result and log exception.
	 */
	private static SampleResult getErrorResult(final Context context, final SampleResult result, final String errorMessage, final Exception exception) {
		result.setError(true);
		result.setStatusCode("NL-FtpSendStream_ERROR");
		result.setResponseContent(errorMessage);
		if(exception != null){
			context.getLogger().error(errorMessage, exception);
		} else{
			context.getLogger().error(errorMessage);
		}
		return result;
	}

	@Override
	public void stopExecute() {
		// TODO add code executed when the test have to stop.
	}

}
