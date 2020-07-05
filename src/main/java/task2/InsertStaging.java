package task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Config;
import model.Log;
import utils.SendEmail;
import utils.SystemContain;

public class InsertStaging {

	@SuppressWarnings({ "incomplete-switch", "deprecation" })
	public static String insertStaging(Config config, Log log, Connection connection)
			throws IOException, SQLException, ParseException {

		String result = SystemContain.SUCCESS;
		// Đọc một file XSL.
		File file = new File(log.getFileName());
		FileInputStream inputStream = new FileInputStream(file);

		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		String sql = config.getQuerySQL();

		// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
		Iterator<Row> rowIterator = sheet.iterator();
		PreparedStatement ps = null;
		rowIterator.next();
		boolean isStop = false;
		while (rowIterator.hasNext()) {
			if (isStop == true) {
				break;
			}
			Row row = rowIterator.next();
			ps = connection.prepareStatement(sql);

			// Lấy Iterator cho tất cả các cell của dòng hiện tại.
			Iterator<Cell> cellIterator = row.cellIterator();
			int columnIndex = 0;
			while (cellIterator.hasNext()) {
				int sttCheck = 0;
				if (columnIndex > 10)
					columnIndex = 0;

				Cell nextCell = cellIterator.next();
				CellType cellType = nextCell.getCellTypeEnum();
				if (columnIndex == 0) {
					switch (cellType) {
					case NUMERIC:
						sttCheck = (int) nextCell.getNumericCellValue();
						break;
					case STRING:
						sttCheck = Integer.parseInt(nextCell.getStringCellValue());
						break;
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

					int mssv = 0;
					switch (cellType) {

					case NUMERIC:
						mssv = (int) nextCell.getNumericCellValue();
						ps.setInt(1, mssv);
						break;
					case STRING:
						mssv = Integer.parseInt(nextCell.getStringCellValue());
						ps.setInt(1, mssv);
						break;
					}

					if (mssv == 0) {
						isStop = true;
					}

					break;
				case 2:
					try {
						String ho = nextCell.getStringCellValue();
						ps.setString(2, ho);
					} catch (Exception e) {
						isStop = true;
						String mess = file.getName() + " file cannot uploadstaging";
						SendEmail.send("File ERROR", mess);
						result = SystemContain.ERROR;
						break;
					}
					break;
				case 3:
					try {
						String ten = nextCell.getStringCellValue();
						ps.setString(3, ten);
					} catch (Exception e) {
						isStop = true;
						String mess = file.getName() + " file cannot uploadstaging";
						SendEmail.send("File ERROR", mess);
						result = SystemContain.ERROR;
						break;
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
							isStop = true;
							String mess = file.getName() + " file cannot uploadstaging";
							SendEmail.send("File ERROR", mess);
							result = SystemContain.ERROR;
							break;
						}

					} else {

						try {
							Date dOB = (Date) nextCell.getDateCellValue();
							ps.setString(4, getString(dOB));
						} catch (Exception e) {
							isStop = true;
							String mess = file.getName() + " file cannot uploadstaging";
							SendEmail.send("File ERROR", mess);
							result = SystemContain.ERROR;
							break;
						}
					}

					break;
				case 5:
					try {
						String lop = nextCell.getStringCellValue();
						ps.setString(5, lop);
					} catch (Exception e) {
						isStop = true;
						String mess = file.getName() + " file cannot uploadstaging";
						SendEmail.send("File ERROR", mess);
						result = SystemContain.ERROR;
						break;
					}

					break;
				case 6:
					try {
						String tenLop = nextCell.getStringCellValue();
						ps.setString(6, tenLop);
					} catch (Exception e) {
						isStop = true;
						String mess = file.getName() + " file cannot uploadstaging";
						SendEmail.send("File ERROR", mess);
						result = SystemContain.ERROR;
						break;
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
						ps.setInt(7, sdt);
					} catch (Exception e) {
						isStop = true;
						String mess = file.getName() + " file cannot uploadstaging";
						SendEmail.send("File ERROR", mess);
						result = SystemContain.ERROR;
						break;
					}

					break;
				case 8:
					try {
						String email = nextCell.getStringCellValue();
						ps.setString(8, email);
					} catch (Exception e) {
						isStop = true;
						String mess = file.getName() + " file cannot uploadstaging";
						SendEmail.send("File ERROR", mess);
						result = SystemContain.ERROR;
						break;
					}

					break;
				case 9:

					try {
						String queQuan = nextCell.getStringCellValue();
						ps.setString(9, queQuan);
					} catch (Exception e) {
						isStop = true;
						String mess = file.getName() + " file cannot uploadstaging";
						SendEmail.send("File ERROR", mess);
						result = SystemContain.ERROR;
						break;
					}

					break;
				case 10:
					try {
						String ghiChu = nextCell.getStringCellValue();
						ps.setString(10, ghiChu);
					} catch (Exception e) {
						isStop = true;
						String mess = file.getName() + " file cannot uploadstaging";
						SendEmail.send("File ERROR", mess);
						result = SystemContain.ERROR;
						break;
					}
					break;
				}
				columnIndex++;
				if (columnIndex == 10)
					ps.setString(10, "");
			}
			if (isStop != true) {
				ps.setInt(11, log.getId_Log());
				
				ps.execute();
			}

		}
		return result;
	}

	public static String getString(Date d) {
		return new SimpleDateFormat("yyyy-MM-dd").format(d);
	}

	

}
