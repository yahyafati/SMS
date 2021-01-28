package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.report.TransactionsReportInfo;

public interface ReportService {
    byte[] generateTransactionReport(TransactionsReportInfo transactionsReportInfo);

    byte[] generateItemList();
}
