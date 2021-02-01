package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.report.TransactionsReportInfo;
import net.sf.jasperreports.engine.JRException;

public interface ReportService {
    byte[] generateTransactionReport(TransactionsReportInfo transactionsReportInfo) throws JRException;

    byte[] generateTransactionReportByType(TransactionsReportInfo info) throws JRException;

    byte[] generateItemTransactionSummaryReport(TransactionsReportInfo info) throws JRException;

    byte[] generateInvoice(Integer transactionId) throws JRException;
}
