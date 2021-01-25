package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.report.TransactionsReportInfo;
import com.yahya.growth.stockmanagementsystem.service.ItemTransactionService;
import com.yahya.growth.stockmanagementsystem.service.ReportService;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ItemTransactionService itemTransactionService;

    @Autowired
    public ReportServiceImpl(ItemTransactionService itemTransactionService) {
        this.itemTransactionService = itemTransactionService;
    }


    @SneakyThrows // TODO REMOVE THIS
    @Override
    public byte[] generateTransactionReport(TransactionsReportInfo transactionsReportInfo) {
        List<ItemTransaction> itemTransactions = itemTransactionService.findAll();

        InputStream transactionStream = getClass().getResourceAsStream("/reports/TransactionReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(transactionStream);
        jasperReport.setProperty("net.sf.jasperreports.compiler.xml.parser.cache.schemas", "false");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemTransactions);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, dataSource);
//        String path = "C:\\Users\\yahya\\OneDrive\\Desktop\\File.pdf";" WINDOWS
        String path = "/home/blackpillow/File.pdf"; // LINUX
        JasperExportManager.exportReportToPdfFile(print, path);
        return JasperRunManager.runReportToPdf(jasperReport, null, dataSource);
    }
}
