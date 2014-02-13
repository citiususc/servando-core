package es.usc.citius.servando.android.httpServer;

import java.io.File;

import android.content.Context;

public class WebServerConfig {

	private int serverPort;
	private File rootDirectory;
	private boolean showHiddenFiles;
	private boolean downloadEnabled;
	private boolean authenticationRequired;
	private int invalidAccessLimit;
	private Context context;
	private String fileServerPath;

	public WebServerConfig()
	{
	}

	public int getServerPort()
	{
		return serverPort;
	}

	public File getRootDirectory()
	{
		return rootDirectory;
	}

	public boolean showHiddenFiles()
	{
		return showHiddenFiles;
	}

	public boolean isDownloadEnabled()
	{
		return downloadEnabled;
	}

	public boolean isAuthenticationRequired()
	{
		return authenticationRequired;
	}

	public Context getContext()
	{
		return context;
	}

	public static class Builder {

		private int serverPort;
		private File rootDirectory;
		private String fileServerPath;
		private boolean showHiddenFiles;
		private boolean downloadEnabled;
		private boolean authenticationRequired;
		private Context context;

		public Builder(Context context, File root)
		{
			this.context = context;
			rootDirectory = root;
			fileServerPath = ServerDefaults.CONTEXT_PATH;
			showHiddenFiles = ServerDefaults.SHOW_HIDDEN_FILES;
			downloadEnabled = ServerDefaults.ENABLE_DOWNLOADS;
			authenticationRequired = ServerDefaults.AUNTHENTICATION_REQUIRED;
			serverPort = ServerDefaults.PORT;
		}

		public Builder authenticationRequired(boolean authenticationRequired)
		{
			this.authenticationRequired = authenticationRequired;
			return this;
		}

		public Builder showHiddenFiles(boolean showHiddenFiles)
		{
			this.showHiddenFiles = showHiddenFiles;
			return this;
		}

		public Builder downloadEnabled(boolean showHiddelFiles)
		{
			showHiddenFiles = showHiddelFiles;
			return this;
		}

		public Builder rootDirectory(File rootDirectory)
		{
			this.rootDirectory = rootDirectory;
			return this;
		}

		public Builder serverPort(int serverPort)
		{
			this.serverPort = serverPort;
			return this;
		}

		public Builder fileServerPath(String fileServerPath)
		{
			this.fileServerPath = fileServerPath;
			return this;
		}

		public WebServerConfig build()
		{
			WebServerConfig cfg = new WebServerConfig();
			cfg.context = context;
			cfg.fileServerPath = fileServerPath;
			cfg.serverPort = serverPort;
			cfg.rootDirectory = rootDirectory;
			cfg.authenticationRequired = authenticationRequired;
			cfg.downloadEnabled = downloadEnabled;
			cfg.showHiddenFiles = showHiddenFiles;
			return cfg;
		}
	}

	public int getInvalidAccessLimit()
	{
		return invalidAccessLimit;
	}

	public void setInvalidAccessLimit(int invalidAccessLimit)
	{
		this.invalidAccessLimit = invalidAccessLimit;
	}

	/**
	 * @return the contextPath
	 */
	public String getFileServerPath()
	{
		return fileServerPath;
	}

	/**
	 * @param contextPath the contextPath to set
	 */
	public void setContextPath(String contextPath)
	{
		fileServerPath = contextPath;
	}

}
