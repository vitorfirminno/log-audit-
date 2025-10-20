package com.audit_log.service;

import com.audit_log.model.UserRequestDTO;
import com.audit_log.model.UserResponseDTO;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GenerateExcelUseCase implements GenerateExcelInputPort<UserResponseDTO>{

    @Override
    public byte[] generate(List<UserResponseDTO> userResponseDTO)  {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()){
             Sheet sheet = workbook.createSheet();
             createHeader(workbook, sheet);
             createBody(workbook, sheet, userResponseDTO);

             workbook.write(out);
             return out.toByteArray();
             }catch (IOException ioException){
            throw new RuntimeException(ioException.getMessage());
        }
    }

    private void createBody(Workbook workbook, Sheet sheet, List<UserResponseDTO> users){
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        int rowIndex = 1;

        for (UserResponseDTO user : users){
            Row row = sheet.createRow(rowIndex++);

            Cell idCell = row.createCell(0);
            idCell.setCellValue(user.getId());
            idCell.setCellStyle(style);

            Cell descCell = row.createCell(1);
            descCell.setCellValue(user.getName());
            descCell.setCellStyle(style);
        }
/*
        Row row = sheet.createRow(2);
        Cell cell = row.createCell(1);
        cell.setCellValue(cellValue);
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(20);
        cell.setCellStyle(style);*/
    }

    private void createHeader(Workbook workbook, Sheet sheet){
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1,4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("ID");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Descrição");
        headerCell.setCellStyle(headerStyle);
    }



}
