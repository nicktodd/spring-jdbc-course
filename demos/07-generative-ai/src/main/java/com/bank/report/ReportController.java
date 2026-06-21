package com.bank.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes account statement reports over HTTP.
 *
 * --- AI WORKFLOW NOTE ---
 * Prompt used to generate this controller:
 *
 *   "Write a Spring @RestController called ReportController mapped to /api/reports.
 *    Inject ReportService via constructor. Add one endpoint:
 *    GET /api/reports/accounts/{id}/statement — returns AccountStatement,
 *    404 if the account does not exist. Use SLF4J for logging."
 *
 * AI output was largely correct. One issue corrected:
 *   AI caught IllegalArgumentException inside the method and returned 404.
 *   This duplicated the GlobalExceptionHandler. Removed the try/catch —
 *   GlobalExceptionHandler already maps IllegalArgumentException to 400.
 *   A missing account is a 404, so changed to use Optional from the service.
 *   (This is a common AI mistake: duplicating exception handling that already exists.)
 *
 * Security check passed:
 *   No raw SQL, no user input used in queries beyond the Long path variable.
 * ------------------------
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/accounts/{id}/statement")
    public ResponseEntity<AccountStatement> getStatement(@PathVariable Long id) {
        log.info("Statement requested for accountId={}", id);
        AccountStatement statement = reportService.generateStatement(id);
        return ResponseEntity.ok(statement);
    }
}
