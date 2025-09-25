package com.example.BSS.service;

import com.example.BSS.callback.UserRepository;
import com.example.BSS.entity.ContractEntity;
import com.example.BSS.entity.ServiceEntity;
import com.example.BSS.entity.UserEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public ByteArrayInputStream exportUserToUsers(List<ServiceEntity> listService, List<UserEntity> userEntityList, List<ContractEntity> listContract) throws IOException {
        String[] columns = {"STT", "Tên dịch vụ", "Người tạo hợp đồng", "Ngày hết hạn hợp đồng", "Họ và tên", "Email", "Địa chỉ", "Ngày tạo"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Thống kê");

            // Tạo style cho ngày (dd/MM/yyyy)
            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

            // Header
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowIdx = 1;
            int stt = 1;
            for (ServiceEntity service : listService) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(stt++); // STT
                row.createCell(1).setCellValue(service.getServiceName());

                for (ContractEntity contract : listContract) {
                    if (contract.getIdContract().equals(service.getIdContract())) {
                        row.createCell(2).setCellValue(contract.getUserCreated());

                        // Ngày hết hạn hợp đồng
                        Cell expiredCell = row.createCell(3);
                        if (contract.getExpiredDate() != null) {
                            expiredCell.setCellValue(contract.getExpiredDate());
                            expiredCell.setCellStyle(dateCellStyle);
                        }

                        for (UserEntity user : userEntityList) {
                            if (contract.getUserCode().equals(user.getIdUser())) {
                                row.createCell(4).setCellValue(user.getName());
                                row.createCell(5).setCellValue(user.getEmail());
                                row.createCell(6).setCellValue(user.getAddress());

                                // Ngày tạo
                                Cell createCell = row.createCell(7);
                                if (user.getCreateAt() != null) {
                                    createCell.setCellValue(user.getCreateAt());
                                    createCell.setCellStyle(dateCellStyle);
                                }
                            }
                        }
                    }
                }
            }

            // Auto-size cho tất cả cột
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
