package model;

public class Config {
	private int id;
	private String nameDatabase;
	private String userNameDatabase;
	private String passWordDatabse;
	private String querySQL;
	private String userNameData;
	private String passwordData;
	private String fileFormat;
	private String remoteDir;
	private String localDir;

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getRemoteDir() {
		return remoteDir;
	}

	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}

	public String getLocalDir() {
		return localDir;
	}

	public void setLocalDir(String localDir) {
		this.localDir = localDir;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameDatabase() {
		return nameDatabase;
	}

	public void setNameDatabase(String nameDatabase) {
		this.nameDatabase = nameDatabase;
	}

	public String getUserNameDatabase() {
		return userNameDatabase;
	}

	public void setUserNameDatabase(String userNameDatabase) {
		this.userNameDatabase = userNameDatabase;
	}

	public String getPassWordDatabse() {
		return passWordDatabse;
	}

	public void setPassWordDatabse(String passWordDatabse) {
		this.passWordDatabse = passWordDatabse;
	}

	public String getUserNameData() {
		return userNameData;
	}

	public void setUserNameData(String userNameData) {
		this.userNameData = userNameData;
	}

	public String getPasswordData() {
		return passwordData;
	}

	public void setPasswordData(String passwordData) {
		this.passwordData = passwordData;
	}

	public String getQuerySQL() {
		return querySQL;
	}

	public void setQuerySQL(String querySQL) {
		this.querySQL = querySQL;
	}
	

}
