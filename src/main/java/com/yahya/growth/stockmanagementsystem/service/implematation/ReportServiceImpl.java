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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportServiceImpl implements ReportService {

    private final ItemTransactionService itemTransactionService;

    @Autowired
    public ReportServiceImpl(ItemTransactionService itemTransactionService) {
        this.itemTransactionService = itemTransactionService;
    }

    private List<ItemTransaction> filterItemTransactions(TransactionsReportInfo info) {
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
        return itemTransactionStream.collect(Collectors.toList());
    }

    @SneakyThrows // TODO Delete this
    private byte[] generateReport(String path, Map<String, Object> parameters, JRBeanCollectionDataSource dataSource) {
        InputStream transactionStream = getClass().getResourceAsStream(path);
//        System.out.println(transactionStream.available());
        JasperReport jasperReport = JasperCompileManager.compileReport(transactionStream);
        return JasperRunManager.runReportToPdf(jasperReport, parameters, dataSource);

    }

    @Override
    public byte[] generateTransactionReport(TransactionsReportInfo info) {
        List<ItemTransaction> itemTransactions = filterItemTransactions(info);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemTransactions);
        return generateReport("/reports/TransactionReport.jrxml", new HashMap<>(), dataSource);
    }

    @Override
    public byte[] generateTransactionReportByType(TransactionsReportInfo info) {
        List<ItemTransaction> itemTransactions = filterItemTransactions(info);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemTransactions);
        return generateReport("/reports/ItemByTransactionType.jrxml", new HashMap<>(), dataSource);
    }

}
