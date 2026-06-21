package com.bank.report;

public interface ReportService {

    /**
     * Generate a full account statement for the given account,
     * ordered chronologically with a running balance on each entry.
     *
     * @throws IllegalArgumentException if the account does not exist
     */
    AccountStatement generateStatement(Long accountId);
}
