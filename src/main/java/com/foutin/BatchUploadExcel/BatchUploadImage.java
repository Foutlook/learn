package com.foutin.BatchUploadExcel;

import com.google.common.collect.Lists;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

/**
 * @author xingkai.fan
 * @description
 * @date 2019/7/15 18:00
 */
public class BatchUploadImage {
    private static final String SPU_SHEET_NAME = "SPU图片";
    private static final String SKU_SHEET_NAME = "SKU图片";
    public static final String TEMPLATE_TYPE = "批量维护图片";
/*
    public void uploadImg() {
        FileOutputStream outStream = null;
        FileInputStream fis = null;
        ByteArrayOutputStream out = null;
        try {
            fis = new FileInputStream("C:\\Users\\xingkai.fan\\Desktop\\维护商品图片.xlsx");
            Workbook workbook = createworkbook(fis);
            //设置错误格式
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setColor(HSSFColor.RED.index);
            style.setFont(font);

            int fail = 0;
            Workbook errorWorkBook = new XSSFWorkbook();
            Sheet sheetSpu = errorWorkBook.createSheet(SPU_SHEET_NAME);
            Sheet sheetSku = errorWorkBook.createSheet(SKU_SHEET_NAME);

            int readBeginRow = Integer.parseInt(rlExcelAttributeManager.getExcelAttributeValue
                    (TEMPLATE_TYPE, CommonIndustryDefinedCode.EXCEL_ATTRIBUTE_TYPE.READ_BEGIN_ROW));
            // 调用测试方法
            batchUploadImgConsumer.getFail(fail, workbook, style, errorWorkBook, sheetSpu, sheetSku, readBeginRow);

            *//*合并标题行*//*
            if (sheetSpu.getPhysicalNumberOfRows() > 0) {
                // 起始行, 终止行, 起始列, 终止列
                CellRangeAddress craSpu = new CellRangeAddress(0, 0, 0, 6);
                sheetSpu.addMergedRegion(craSpu);
            }

            if (sheetSku.getPhysicalNumberOfRows() > 0) {
                // 起始行, 终止行, 起始列, 终止列
                CellRangeAddress craSku = new CellRangeAddress(0, 0, 0, 1);
                sheetSku.addMergedRegion(craSku);
            }

            out = new ByteArrayOutputStream();
            errorWorkBook.write(out);

            File file = new File("C:\\Users\\xingkai.fan\\Desktop\\图片.xlsx");//文件路径（路径+文件名）
            if (!file.exists()) {   //文件不存在则创建文件，先创建目录
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            outStream = new FileOutputStream(file); //文件输出流将数据写入文件
            outStream.write(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fis != null;
                fis.close();
                assert out != null;
                out.close();
                assert outStream != null;
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Workbook createworkbook(InputStream inp) throws Exception {
        if (!inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(inp)) {
            return new HSSFWorkbook(inp);
        }
        if (POIXMLDocument.hasOOXMLHeader(inp)) {
            return new XSSFWorkbook(OPCPackage.open(inp));
        }
        throw new IllegalArgumentException("你的excel版本目前poi解析不了");
    }

    public int getFail(int fail, Workbook workbook, CellStyle style, Workbook errorWorkBook, Sheet sheetSpu, Sheet sheetSku, int readBeginRow) {
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //获取总行数
            int rowCount = sheet.getLastRowNum();
            int colNum = sheet.getRow(readBeginRow - 1).getLastCellNum();
            int errorRowCount = 0;
            //遍历正文每一行
            for (int originalRowIndex = 0; originalRowIndex <= rowCount; originalRowIndex++) {
                Row row = sheet.getRow(originalRowIndex);
                List<Integer> errorCellList = Lists.newArrayList();
                //复制错误标题行
                if (originalRowIndex == 0 || originalRowIndex == 1) {
                    errorRowCount = copyErrorRow(errorWorkBook, sheetSpu, sheetSku, sheet, errorRowCount, row, errorCellList);
                }

                //无正文内容，跳过
                if (originalRowIndex < readBeginRow) {
                    continue;
                }

                if (row == null || isNullRow(row)) {
                    continue;
                }
                // 所有单元格都设置成错误行
                Cell errorCell = row.createCell(colNum);
                createErrorCell(style, errorCellList, colNum, errorCell, ERROR_MSG_RELATION_IMG_FAILD);
                copyErrorRow(errorWorkBook, sheetSpu, sheetSku, sheet, errorRowCount, row, errorCellList);
                fail = rowCount;
            }
        }
        return fail;
    }

    public String getExcelAttributeValue(String templateType, CommonIndustryDefinedCode.EXCEL_ATTRIBUTE_TYPE excelAttributeType) throws CommonIndustryException {
        if (ObjectUtil.hasEmpty(templateType, excelAttributeType)) {
            throw new CommonIndustryException(CommonIndustryResultConstant.PARAMETER_ERROR);
        }

        //获取属性list集合
        List<RlExcelAttribute> rlExcelAttributeList = getRlExcelAttributes(templateType);
        if (ObjectUtil.hasEmpty(rlExcelAttributeList)){
            throw new CommonIndustryException(CommonIndustryResultConstant.RL_EXCEL_ATTRIBUTE_DATA_NULL);
        }
        //从list中获取返回结果数据,并且返回
        String rlExcelAttribute = getExcelAttributeValue(excelAttributeType, rlExcelAttributeList);
        return rlExcelAttribute;
    }*/

}
