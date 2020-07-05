package model;

public class Log {

	private int id_Log;
	private String fileName;
	private String dataFileConfigId;
	private String fileStatus;
	private String stagingLoadCounT;
	private String timestampInsertDownload;
	private String timestampInsertStaging;
	private String timestampInsertDataWarehouse;

	public int getId_Log() {
		return id_Log;
	}

	public void setId_Log(int id_Log) {
		this.id_Log = id_Log;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDataFileConfigId() {
		return dataFileConfigId;
	}

	public void setDataFileConfigId(String dataFileConfigId) {
		this.dataFileConfigId = dataFileConfigId;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getStagingLoadCounT() {
		return stagingLoadCounT;
	}

	public void setStagingLoadCounT(String stagingLoadCounT) {
		this.stagingLoadCounT = stagingLoadCounT;
	}

	public String getTimestampInsertDownload() {
		return timestampInsertDownload;
	}

	public void setTimestampInsertDownload(String timestampInsertDownload) {
		this.timestampInsertDownload = timestampInsertDownload;
	}

	public String getTimestampInsertStaging() {
		return timestampInsertStaging;
	}

	public void setTimestampInsertStaging(String timestampInsertStaging) {
		this.timestampInsertStaging = timestampInsertStaging;
	}

	public String getTimestampInsertDataWarehouse() {
		return timestampInsertDataWarehouse;
	}

	public void setTimestampInsertDataWarehouse(String timestampInsertDataWarehouse) {
		this.timestampInsertDataWarehouse = timestampInsertDataWarehouse;
	}

}
