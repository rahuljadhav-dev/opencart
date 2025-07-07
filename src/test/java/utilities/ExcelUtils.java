package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static XSSFFont font;
	public static CellStyle style;
	String path;

	public ExcelUtils(String path) {
		this.path=path;
	}

	public int getTotalRows(String sheetName) throws IOException {
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		sheet=wb.getSheet(sheetName);
		int total=sheet.getLastRowNum();
		wb.close();
		fi.close();
		return total;
	}

	public int getTotalCells(String sheetName) throws IOException {
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		sheet=wb.getSheet(sheetName);
		row=sheet.getRow(0);
		wb.close();
		fi.close();
		int total=row.getLastCellNum();
		return total;
	}

	public String getAllCellData( String sheetName, int rowCount, int cellCount) throws IOException {
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		sheet=wb.getSheet(sheetName);

		StringBuilder data = new StringBuilder();

		for (int i = 0; i <= rowCount; i++) {
			XSSFRow row = sheet.getRow(i);
			if (row != null) {
				for (int j = 0; j < cellCount; j++) {
					XSSFCell cell = row.getCell(j);
					if (cell != null) {
						data.append(cell.toString()).append("  ");
					} else {
						data.append("NULL  ");
					}
				}
				data.append("\n");
			}
		}
		wb.close();
		fi.close();
		return data.toString();
	}

	public String getCellData(String sheetName,int rowCount,int cellCount) throws IOException {
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		sheet=wb.getSheet(sheetName);
		row=sheet.getRow(rowCount);
		cell=row.getCell(cellCount);
		String data;
		try {
			DataFormatter formatter=new DataFormatter();
			data=formatter.formatCellValue(cell);//returns the formatted value of a cell as string regardless of the string type
		} catch (Exception e) {
			data="";
		}
		wb.close();
		fi.close();
		return data;
	}

	//	public static String setCellData(String xlfile,String xlsheet,int rowCount,int cellCount,String data) throws IOException {
	//		fi=new FileInputStream(xlfile);
	//		wb=new XSSFWorkbook(fi);
	//		sheet=wb.getSheet(xlsheet);
	//		row=sheet.getRow(rowCount);
	//
	//		cell=row.createCell(cellCount);
	//		cell.setCellValue(data);
	//
	//		fo=new FileOutputStream(xlfile);
	//		wb.write(fo);
	//
	//		wb.close();
	//		fi.close();
	//		fo.close();
	//
	//		return "file written successfully";
	//	}

//	public String setCellData(String sheetName, int rowCount, int cellCount, String data) throws IOException {
//		fi=null;
//		fo=null;
//		wb = null;
//
//		try {
//			fi = new FileInputStream(path);
//			wb = new XSSFWorkbook(fi);
//		} catch (IOException e) {
//			wb = new XSSFWorkbook(); // Create new workbook if file doesn't exist
//		}
//
//		sheet= wb.getSheet(sheetName);
//		if (sheet == null) {
//			sheet = wb.createSheet(sheetName);
//		}
//
//		row = sheet.getRow(rowCount);
//		if (row == null) {
//			row = sheet.createRow(rowCount);
//		}
//
//		cell = row.createCell(cellCount);
//		cell.setCellValue(data);
//
//		fo = new FileOutputStream(path);
//		wb.write(fo);
//
//		// Cleanup
//		wb.close();
//		if (fi != null) fi.close();
//		fo.close();
//
//		return "File written successfully";
//	}
	
	public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {
	    File xlfile = new File(path);

	    if (!xlfile.exists()) {
	        // If file does not exist, create new file and workbook
	        wb = new XSSFWorkbook();
	        fo = new FileOutputStream(path);
	        wb.write(fo);
	        fo.close();
	    }

	    fi = new FileInputStream(path);
	    wb= new XSSFWorkbook(fi);

	    // If sheet doesn't exist, create it
	    if (wb.getSheetIndex(sheetName) == -1) {
	        wb.createSheet(sheetName);
	    }

	    sheet = wb.getSheet(sheetName);

	    // If row doesn't exist, create it
	    if (sheet.getRow(rownum) == null) {
	        sheet.createRow(rownum);
	    }

	    row = sheet.getRow(rownum);
	    cell = row.createCell(column);
	    cell.setCellValue(data);

	    fo = new FileOutputStream(path);
	    wb.write(fo);
	    wb.close();
	    fi.close();
	    fo.close();
	}



	public void fillGreenColour(String sheetName,int rowCount,int cellCount) throws IOException {
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		sheet=wb.getSheet(sheetName);
		row=sheet.getRow(rowCount);
		cell=row.getCell(cellCount);

		style=wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo=new FileOutputStream(path);
		wb.write(fo);

		wb.close();
		fi.close();
		fo.close();

	}

	public void fillRedColour(String sheetName,int rowCount,int cellCount) throws IOException {
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		sheet=wb.getSheet(sheetName);
		row=sheet.getRow(rowCount);
		cell=row.getCell(cellCount);

		style=wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo=new FileOutputStream(path);
		wb.write(fo);

		wb.close();
		fi.close();
		fo.close();

	}

	public void makeHeaderBold(String sheetName) throws IOException {
		fi = new FileInputStream(path);
		wb= new XSSFWorkbook(fi);
		sheet = wb.getSheet(sheetName);

		if (sheet != null) {
			row = sheet.getRow(0);
			if (row != null) {
				style = wb.createCellStyle();
				font = wb.createFont();
				font.setBold(true);
				style.setFont(font);
				style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

				for (Cell cell : row) {
					cell.setCellStyle(style);
				}
			}
		}

		fo = new FileOutputStream(path);
		wb.write(fo);
		fi.close();
		fo.close();
		wb.close();
	}

}

