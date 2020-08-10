package utils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dto.Config;
import dto.Log;

public class LoadStaging {

	public static void loadStaging(Config config, Log log, Connection connection) throws Exception {
		if (log.getStatus().equalsIgnoreCase(Status.DOWNLOAD) || log.getStatus().equalsIgnoreCase(Status.ERROR)) {
			String nameDB = null;
			String tableDB = null;

			File file = new File(log.getUrlLocal());

			
			boolean loading = false;

			nameDB = config.getNameDbStaging();
			tableDB = config.getNameTbStaging();

			if (log.getName_log().equalsIgnoreCase(Status.LOAD_STUDENTS)) {
				loading = loadFileSinhVien(file, connection, nameDB, tableDB, log.getId_log());
			} else if (log.getName_log().equalsIgnoreCase(Status.LOAD_SUBJECTS)) {
				loading = loadFileMonHoc(file, connection, nameDB, tableDB, log.getId_log());
			} else if (log.getName_log().equalsIgnoreCase(Status.LOAD_CLASSESS)) {
				loading = loadFileLopHoc(file, connection, nameDB, tableDB, log.getId_log());
			} else if (log.getName_log().equals(Status.LOAD_REGISTER)) {
				loading = loadDangKy(file, connection, nameDB, tableDB, log.getId_log());
			}

			if (loading == true) {
				log.setStatus(Status.UPLOAD_STAGING);

				update(config, log, connection);

				SendMail.sendMail("hainguyen.70475@gmail.com", "Upload Staging Done", "Upload staging success");
			} else {
				log.setStatus(Status.ERROR);

				update(config, log, connection);

				SendMail.sendMail("hainguyen.70475@gmail.com", "Upload Staging Fail", "Upload staging fail");
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean loadFileSinhVien(File file, Connection connection, String nameDB, String nameTable, int idLog)
			throws Exception {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (Exception e) {
			return false;
		}

		PreparedStatement ps = null;
		String sql = "INSERT INTO " + nameDB + "." + nameTable
				+ "(MSSV, ho, ten, dob, lop, tenlop, sdt, email, quequan, ghichu, id_log) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();

		rowIterator.next();
		boolean isStop = false;
		int row_load = 0;
		int row_limit = 20;

		while (rowIterator.hasNext()) {
			if (isStop == true || row_limit < 0) {
				break;
			}
			Row row = rowIterator.next();
			ps = connection.prepareStatement(sql);
			ps.setString(1, "");
			ps.setString(2, "");
			ps.setString(3, "");
			ps.setString(4, "");
			ps.setString(5, "");
			ps.setString(6, "");
			ps.setString(7, "");
			ps.setString(8, "");
			ps.setString(9, "");
			ps.setString(10, "");
			int columnIndex = 0;
			// Lấy Iterator cho tất cả các cell của dòng hiện tại.
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				int mssvCheck = 0;
				if (columnIndex > 10)
					columnIndex = 0;

				Cell nextCell = cellIterator.next();
				if (columnIndex == 1) {
					if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
						mssvCheck = (int) nextCell.getNumericCellValue();
					} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
						mssvCheck = Integer.parseInt(nextCell.getStringCellValue());
					}

					if (mssvCheck == 0) {
						isStop = true;
						break;
					}
				} else if (isStop == true) {
					isStop = true;
					break;
				}
				switch (columnIndex) {
				case 1:

					int mssv = 0;
					if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
						mssv = (int) nextCell.getNumericCellValue();
					} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
						mssv = Integer.parseInt(nextCell.getStringCellValue());
					}

					if (mssv == 0) {
						isStop = true;
					} else {
						ps.setString(1, String.valueOf(mssv));
					}

					break;
				case 2:
					try {
						String ho = nextCell.getStringCellValue();
						ps.setString(2, ho);
					} catch (Exception e) {
						ps.setString(2, "");
					}
					break;
				case 3:
					try {
						String ten = nextCell.getStringCellValue();
						ps.setString(3, ten);
					} catch (Exception e) {
						ps.setString(3, "");
					}

					break;
				case 4:
					if (nextCell.getCellTypeEnum() == CellType.STRING) {

						try {
							String dob = nextCell.getStringCellValue();
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							Date d = df.parse(dob);
							ps.setString(4, getString(d));
						} catch (Exception e) {
							ps.setString(4, "");
						}

					} else {

						try {
							Date dOB = (Date) nextCell.getDateCellValue();
							ps.setString(4, getString(dOB));
						} catch (Exception e) {
							ps.setString(4, "");
						}
					}

					break;
				case 5:
					try {
						String lop = nextCell.getStringCellValue();
						ps.setString(5, lop);
					} catch (Exception e) {
						ps.setString(5, "");
					}

					break;
				case 6:
					try {
						String tenLop = nextCell.getStringCellValue();
						ps.setString(6, tenLop);
					} catch (Exception e) {
						ps.setString(6, "");
					}

					break;
				case 7:
					try {
						int sdt = 0;
						if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
							sdt = (int) nextCell.getNumericCellValue();
						} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
							sdt = Integer.parseInt(nextCell.getStringCellValue());
						}
						ps.setString(7, String.valueOf(sdt));
					} catch (Exception e) {
						ps.setString(7, String.valueOf(""));
					}

					break;
				case 8:
					try {
						String email = nextCell.getStringCellValue();
						ps.setString(8, email);
					} catch (Exception e) {
						ps.setString(8, "");
					}

					break;
				case 9:

					try {
						String queQuan = nextCell.getStringCellValue();
						ps.setString(9, queQuan);
					} catch (Exception e) {
						ps.setString(9, "");
					}

					break;
				case 10:
					try {
						String ghiChu = nextCell.getStringCellValue();
						ps.setString(10, ghiChu);
					} catch (Exception e) {
						ps.setString(9, "");
					}
					break;
				}
				columnIndex++;

			}

			if (isStop != true) {
				ps.setInt(11, idLog);
				ps.execute();
				row_load++;
				row_limit--;
			}

		}
		if (row_load == 0)
			return false;
		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean loadFileMonHoc(File file, Connection connection, String nameDB, String nameTable, int idLog)
			throws Exception {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (Exception e) {
			return false;
		}
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + nameDB + "." + nameTable
				+ "(STT, maMH, tenMH, TC, khoaBoMon, ghiChu, id_log) VALUES (?, ?, ?, ?, ?, ?, ?)";

		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		boolean isStop = false;
		int row_load = 0;
		int row_limit = 20;

		while (rowIterator.hasNext()) {
			if (isStop == true || row_limit < 0) {
				break;
			}
			Row row = rowIterator.next();
			ps = connection.prepareStatement(sql);
			ps.setString(1, "");
			ps.setString(2, "");
			ps.setString(3, "");
			ps.setString(4, "");
			ps.setString(5, "");
			ps.setString(6, "");
			int columnIndex = 0;
			// Lấy Iterator cho tất cả các cell của dòng hiện tại.
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				int sttCheck = 0;
				if (columnIndex > 7)
					columnIndex = 0;

				Cell nextCell = cellIterator.next();

				if (columnIndex == 0) {
					if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
						sttCheck = (int) nextCell.getNumericCellValue();
					} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
						sttCheck = Integer.parseInt(nextCell.getStringCellValue());
					}

					if (sttCheck == 0) {
						isStop = true;
						break;
					}
				} else if (isStop == true) {
					isStop = true;
					break;
				}
				switch (columnIndex) {
				case 0:
					int stt = 0;
					if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
						stt = (int) nextCell.getNumericCellValue();
					} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
						stt = Integer.parseInt(nextCell.getStringCellValue());
					}

					if (stt == 0) {
						isStop = true;
					} else {
						ps.setString(1, String.valueOf(stt));
					}
					break;
				case 1:
					try {
						int maMH = 0;
						if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
							maMH = (int) nextCell.getNumericCellValue();
						} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
							maMH = Integer.parseInt(nextCell.getStringCellValue());
						}
						ps.setString(2, String.valueOf(maMH));
					} catch (Exception e) {
						ps.setString(2, "");
					}
					break;
				case 2:
					try {
						String tenMH = nextCell.getStringCellValue();
						ps.setString(3, tenMH);
					} catch (Exception e) {
						ps.setString(3, "");
					}
					break;
				case 3:
					try {
						String TC = nextCell.getStringCellValue();
						ps.setString(4, TC);
					} catch (Exception e) {
						ps.setString(4, "");
					}
					break;
				case 4:
					try {
						String khoaBoMon = nextCell.getStringCellValue();
						ps.setString(5, khoaBoMon);
					} catch (Exception e) {
						ps.setString(5, "");
					}
					break;
				case 5:
					try {
						String ghiChu = nextCell.getStringCellValue();
						ps.setString(6, ghiChu);
					} catch (Exception e) {
						ps.setString(6, "");
					}
					break;
				}
				columnIndex++;
			}
			if (isStop != true) {
				ps.setInt(7, idLog);
				ps.execute();
				row_load++;
				row_limit--;
			}

		}
		if (row_load == 0)
			return false;
		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean loadFileLopHoc(File file, Connection connection, String nameDB, String nameTable, int idLog)
			throws Exception {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (Exception e) {
			return false;
		}
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + nameDB + "." + nameTable
				+ "(STT, maLopHoc, maMonHoc, namHoc, id_log) VALUES (?, ?, ?, ?, ?)";
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		boolean isStop = false;
		int row_load = 0;
		int row_limit = 20;

		while (rowIterator.hasNext()) {
			if (isStop == true || row_limit < 0) {
				break;
			}
			Row row = rowIterator.next();
			ps = connection.prepareStatement(sql);
			ps.setString(1, "");
			ps.setString(2, "");
			ps.setString(3, "");
			ps.setString(4, "");
			int columnIndex = 0;
			// Lấy Iterator cho tất cả các cell của dòng hiện tại.
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				int sttCheck = 0;
				if (columnIndex > 3)
					columnIndex = 0;

				Cell nextCell = cellIterator.next();

				if (columnIndex == 0) {
					if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
						sttCheck = (int) nextCell.getNumericCellValue();
					} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
						sttCheck = Integer.parseInt(nextCell.getStringCellValue());
					}

					if (sttCheck == 0) {
						isStop = true;
						break;
					}
				} else if (isStop == true) {
					isStop = true;
					break;
				}

				switch (columnIndex) {
				case 0:

					int stt = 0;
					if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
						stt = (int) nextCell.getNumericCellValue();
					} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
						stt = Integer.parseInt(nextCell.getStringCellValue());
					}

					if (stt == 0) {
						isStop = true;
					} else {
						ps.setString(1, String.valueOf(stt));
					}

					break;
				case 1:
					try {
						String maLopHoc = nextCell.getStringCellValue();
						ps.setString(2, maLopHoc);
					} catch (Exception e) {
						ps.setString(2, "");
					}
					break;
				case 2:
					try {
						int maMH = 0;
						if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
							maMH = (int) nextCell.getNumericCellValue();
						} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
							maMH = Integer.parseInt(nextCell.getStringCellValue());
						}
						ps.setString(3, String.valueOf(maMH));
					} catch (Exception e) {
						ps.setString(3, "");
					}

					break;

				case 3:
					try {
						int namHoc = 0;
						if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
							namHoc = (int) nextCell.getNumericCellValue();
						} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
							namHoc = Integer.parseInt(nextCell.getStringCellValue());
						}
						ps.setString(4, String.valueOf(namHoc));
					} catch (Exception e) {
						ps.setString(4, "");
					}

					break;
				}
				columnIndex++;

			}

			if (isStop != true) {
				ps.setInt(5, idLog);
				ps.execute();
				row_load++;
				row_limit--;
			}

		}
		if (row_load == 0)
			return false;
		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean loadDangKy(File file, Connection conn, String nameDB, String nameTableDB, int idLog)
			throws Exception {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (Exception e) {
			return false;
		}

		PreparedStatement ps = null;
		String sql = "INSERT INTO " + nameDB + "." + nameTableDB
				+ " (maDangKy, maSinhvien , maLophoc, thoigianDK, id_log) VALUES (?,?,?,?,?)";

		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		boolean isStop = false;
		int row_load = 0;
		int row_limit = 20;

		while (rowIterator.hasNext()) {
			if (isStop == true || row_limit < 0) {
				break;
			}
			Row row = rowIterator.next();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "");
			ps.setString(2, "");
			ps.setString(3, "");
			ps.setString(4, "");
			int columnIndex = 0;
			// Lấy Iterator cho tất cả các cell của dòng hiện tại.
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				int sttCheck = 0;
				if (columnIndex > 4)
					columnIndex = 0;

				Cell nextCell = cellIterator.next();

				if (columnIndex == 0) {
					if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
						sttCheck = (int) nextCell.getNumericCellValue();
					} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
						sttCheck = Integer.parseInt(nextCell.getStringCellValue());
					}

					if (sttCheck == 0) {
						isStop = true;
						break;
					}
				} else if (isStop == true) {
					isStop = true;
					break;
				}

				switch (columnIndex) {

				case 1:
					try {
						String maDangKy = nextCell.getStringCellValue();
						ps.setString(1, maDangKy);
					} catch (Exception e) {
						ps.setString(1, "");
					}
					break;
				case 2:
					try {
						int maSV = 0;
						if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
							maSV = (int) nextCell.getNumericCellValue();
						} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
							maSV = Integer.parseInt(nextCell.getStringCellValue());
						}
						ps.setString(2, String.valueOf(maSV));
					} catch (Exception e) {
						ps.setString(2, "");
					}

					break;

				case 3:
					try {
						int maLopHoc = 0;
						if (nextCell.getCellTypeEnum() == CellType.NUMERIC) {
							maLopHoc = (int) nextCell.getNumericCellValue();
						} else if (nextCell.getCellTypeEnum() == CellType.STRING) {
							maLopHoc = Integer.parseInt(nextCell.getStringCellValue());
						}
						ps.setString(3, String.valueOf(maLopHoc));
					} catch (Exception e) {
						ps.setString(3, "");
					}

					break;
				case 4:
					if (nextCell.getCellTypeEnum() == CellType.STRING) {

						try {
							String thoiGianDK = nextCell.getStringCellValue();
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							Date d = df.parse(thoiGianDK);
							ps.setString(4, getString(d));
						} catch (Exception e) {
							ps.setString(4, "");
						}

					} else {

						try {
							Date thoiGianDK = (Date) nextCell.getDateCellValue();
							ps.setString(4, getString(thoiGianDK));
						} catch (Exception e) {
							ps.setString(4, "");
						}
					}

					break;
				}
				columnIndex++;

			}

			if (isStop != true) {
				ps.setInt(5, idLog);
				ps.execute();
				row_load++;
				row_limit--;
			}

		}
		if (row_load == 0)
			return false;
		return true;
	}

	public static String getString(Date d) {
		return new SimpleDateFormat("yyyy-MM-dd").format(d);
	}

	private static void update(Config config, Log log, Connection conn) {
		String nameDB = config.getNameDbControl();
		String nameTableDB = config.getNameTbLog();
		String sql = "UPDATE " + nameDB + "." + nameTableDB + " SET status = ? WHERE id_log = ?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, log.getStatus());
			ps.setInt(2, log.getId_log());
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
