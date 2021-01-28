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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportServiceImpl implements ReportService {

    private final ItemTransactionService itemTransactionService;

    @Autowired
    public ReportServiceImpl(ItemTransactionService itemTransactionService) {
        this.itemTransactionService = itemTransactionService;
    }


    @SneakyThrows // TODO REMOVE THIS
    @Override
    public byte[] generateTransactionReport(TransactionsReportInfo info) {
        List<ItemTransaction> itemTransactions = itemTransactionService.findAll();
        Stream<ItemTransaction> itemTransactionStream = itemTransactions.stream();
        if (info.getCustomer().getId() != 0) {
            itemTransactionStream = itemTransactionStream.filter(itemTransaction -> info.getCustomer().getId() == itemTransaction.getTransaction().getCustomer().getId());
        }
        if (info.getItem().getId() != 0) {
            itemTransactionStream = itemTransactionStream.filter(itemTransaction -> info.getItem().getId() == itemTransaction.getItem().getId());
        }
        if (!info.getFromBeginning()) {
            itemTransactionStream = itemTransactionStream.filter(itemTransaction -> itemTransaction.getTransaction().getTransactionDate().isAfter(info.getStartDate().minusDays(1)));
        }
        if (!info.getToLastDate()) {
            itemTransactionStream = itemTransactionStream.filter(itemTransaction ->  itemTransaction.getTransaction().getTransactionDate().isBefore(info.getFinalDate().plusDays(1)));
        }
        if (!info.getBothTypes()) {
            itemTransactionStream = itemTransactionStream.filter(itemTransaction -> itemTransaction.getTransaction().getType() == info.getType());
        }
        itemTransactions = itemTransactionStream.collect(Collectors.toList());
        InputStream transactionStream = getClass().getResourceAsStream("/reports/TransactionReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(transactionStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemTransactions);

        return JasperRunManager.runReportToPdf(jasperReport, null, dataSource);
    }

    @SneakyThrows // TODO Remove This
    @Override
    public byte[] generateItemList() {
        List<ItemTransaction> itemTransactions = itemTransactionService.findAll();
        InputStream itemsStream = getClass().getResourceAsStream("/reports/ItemTransasactionSummary.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(itemsStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemTransactions);
        return JasperRunManager.runReportToPdf(jasperReport, null, dataSource);
    }
}
