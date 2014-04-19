package qinyuan.lib.file;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import qinyuan.lib.date.MyTime;

public class ExcelCreator {

	public static final short LEFT = 1, RIGHT = 2, TOP = 3, BOTTOM = 4,
			CENTER = 5, THICK = 6, THIN = 7;

	private int maxColWidth, borderStyle, align, verticalAlign;
	private String sheetHead;
	private List<String[]> strArrList;

	public ExcelCreator setAlign(int align) {
		this.align = align;
		return this;
	}

	public ExcelCreator setBorder(int borderStyle) {
		this.borderStyle = borderStyle;
		return this;
	}

	public ExcelCreator setMaxColWidth(int maxColWidth) {
		this.maxColWidth = maxColWidth;
		return this;
	}

	public ExcelCreator setSheetHead(String sheetHead) {
		this.sheetHead = sheetHead;
		return this;
	}

	public ExcelCreator setStrArrList(List<String[]> strArrList) {
		this.strArrList = strArrList;
		return this;
	}

	public ExcelCreator setVerticalAlign(int verticalAlign) {
		this.verticalAlign = verticalAlign;
		return this;
	}

	public boolean create(String fileName) {
		try (FileOutputStream fos = new FileOutputStream(fileName)) {
			// declare variables
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("sheet1");
			HSSFRow row;
			HSSFCellStyle cellStyle = createCellStyle(workbook, borderStyle,
					align, verticalAlign);
			int rowIndex = 0, maxColIndex = 0;

			if (sheetHead != null) {
				row = sheet.createRow(rowIndex);
				createSheetHead(workbook, row, sheetHead);
				rowIndex++;
			}

			// set data
			if (strArrList != null) {
				for (String[] strs : strArrList) {
					row = sheet.createRow(rowIndex);
					createCells(row, strs, cellStyle);
					maxColIndex = Math.max(row.getLastCellNum() - 1,
							maxColIndex);
					rowIndex++;
				}
			}

			if (sheetHead != null && maxColIndex > 0) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, maxColIndex));
			}

			// auto fit the column width
			for (int i = 0; i <= maxColIndex; i++) {
				sheet.autoSizeColumn(i);
			}

			// if there are columns wider than maxColWidth, set the width of
			// them to maxColWidth
			if (maxColWidth > 0) {
				for (int i = 0; i < maxColIndex; i++) {
					if (sheet.getColumnWidth(i) > maxColWidth)
						sheet.setColumnWidth(i, maxColWidth);
				}
			}

			// write excel content to output file
			workbook.write(fos);
			fos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void createCells(HSSFRow row, String[] strs,
			HSSFCellStyle style) {
		if (strs.length == 0)
			return;

		int colIndex = 0;
		HSSFCell cell;
		for (String str : strs) {
			cell = row.createCell(colIndex++);
			cell.setCellValue(str);
			cell.setCellStyle(style);
		}
	}

	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook,
			int borderStyle, int align, int verticalAlign) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);

		switch (borderStyle) {
		case THIN:
			setCellBorder(cellStyle, HSSFCellStyle.BORDER_THIN);
			break;
		case THICK:
			setCellBorder(cellStyle, HSSFCellStyle.BORDER_THICK);
			break;
		}

		switch (align) {
		case LEFT:
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			break;
		case RIGHT:
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			break;
		case CENTER:
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			break;
		}

		switch (verticalAlign) {
		case TOP:
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
			break;
		case CENTER:
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			break;
		case BOTTOM:
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
			break;
		}

		return cellStyle;
	}

	private static void createSheetHead(HSSFWorkbook workbook, HSSFRow row,
			String str) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 16);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(str);
		cell.setCellStyle(cellStyle);
	}

	private static void setCellBorder(HSSFCellStyle cellStyle, short border) {
		cellStyle.setBorderBottom(border);
		cellStyle.setBorderLeft(border);
		cellStyle.setBorderRight(border);
		cellStyle.setBorderTop(border);
	}

	public static void main(String[] args) {
		List<String[]> list = new ArrayList<String[]>();
		String[] strArr;
		for (int i = 0; i < 40; i++) {
			strArr = new String[3];
			strArr[0] = StringUtils.repeat("a", 5);
			strArr[1] = StringUtils.repeat("b", 50);
			strArr[2] = StringUtils.repeat("c", 100);
			list.add(strArr);
		}
		String fileName = "d:/" + new MyTime().toString().replace(":", "-")
				+ ".xls";
		ExcelCreator excel = new ExcelCreator();
		excel.setStrArrList(list).setBorder(ExcelCreator.THIN)
				.setMaxColWidth(10000).setVerticalAlign(ExcelCreator.TOP)
				.setAlign(ExcelCreator.LEFT).setSheetHead("this is sheet head")
				.create(fileName);
	}
}
