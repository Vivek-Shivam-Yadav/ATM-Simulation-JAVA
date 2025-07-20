package com.atm;

import static org.junit.jupiter.api.Assertions.*;

import com.atm.model.Transaction;
import com.atm.service.impl.AccountServiceImpl;
import com.atm.service.impl.UserServiceImpl;
import com.atm.util.DatabaseUtil;
import org.junit.jupiter.api.*; // Import all annotations for clarity

import java.util.List;

/**
 * A comprehensive but simplified test class for the ATM application.
 * This class focuses on all core user functionalities.
 */
@DisplayName("ATM Core Functionality Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Allows non-static @BeforeAll and @AfterAll methods
public class AppTest {

    // Services needed for the tests
    private UserServiceImpl userService;
    private AccountServiceImpl accountService;

    // Test data constants for known users in the database
    private static final String TEST_CARD_NUMBER = "1234567890123456"; // Belongs to John Doe
    private static final String TEST_PIN = "1234";
    private static final String TEST_ACCOUNT_NUMBER = "ACC123";

    private static final String RECIPIENT_ACCOUNT_NUMBER = "ACC456"; // Belongs to Jane Smith
    private static final String NEW_PIN = "5678";

    /**
     * Initializes the database and services and prints a report header.
     */
    @BeforeAll
    void setupAll() {
        System.out.println("\n==============================================");
        System.out.println("  ATM SIMULATOR TEST SUITE - INITIALIZING");
        System.out.println("==============================================");
        DatabaseUtil.initializeDatabase();
        userService = new UserServiceImpl();
        accountService = new AccountServiceImpl();
        System.out.println("✅ Setup complete. Starting tests...\n");
    }

    /**
     * Prints a message before each test begins.
     */
    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("▶️  Running Test: " + testInfo.getDisplayName());
    }

    /**
     * Prints a success message after each test completes.
     */
    @AfterEach
    void afterEach(TestInfo testInfo) {
        System.out.println("✅  PASSED: " + testInfo.getDisplayName() + "\n");
    }

    /**
     * Cleans up resources and prints a final summary report.
     */
    @AfterAll
    void tearDownAll() {
        DatabaseUtil.closeConnection();
        System.out.println("==============================================");
        System.out.println("     ALL TESTS COMPLETED SUCCESSFULLY");
        System.out.println("==============================================");
    }

    // --- ATM FUNCTIONALITY TESTS ---

    @Test
    @DisplayName("User Login")
    void testUserAuthentication() {
        assertTrue(userService.validateUser(TEST_CARD_NUMBER, TEST_PIN),
                "Login should be successful with correct credentials.");
        assertFalse(userService.validateUser(TEST_CARD_NUMBER, "9999"),
                "Login should fail with incorrect credentials.");
    }
    
    @Test
    @DisplayName("Check Balance")
    void testCheckBalance() {
        double balance = accountService.getBalance(TEST_ACCOUNT_NUMBER);
        assertTrue(balance >= 0, "Account balance should be a positive value.");
    }

    @Test
    @DisplayName("Cash Withdrawal")
    void testWithdrawal() {
        double initialBalance = accountService.getBalance(TEST_ACCOUNT_NUMBER);
        double withdrawalAmount = 100.0;

        if (initialBalance < withdrawalAmount) {
            userService.deposit(TEST_ACCOUNT_NUMBER, 200.0);
            initialBalance = accountService.getBalance(TEST_ACCOUNT_NUMBER);
        }

        boolean success = userService.withdraw(TEST_ACCOUNT_NUMBER, withdrawalAmount);
        assertTrue(success, "Withdrawal should be successful.");

        double balanceAfterWithdrawal = accountService.getBalance(TEST_ACCOUNT_NUMBER);
        assertEquals(initialBalance - withdrawalAmount, balanceAfterWithdrawal,
                "Balance should be reduced by the withdrawal amount.");
    }

    @Test
    @DisplayName("Cash Deposit")
    void testDeposit() {
        double initialBalance = accountService.getBalance(TEST_ACCOUNT_NUMBER);
        double depositAmount = 200.0;

        boolean success = userService.deposit(TEST_ACCOUNT_NUMBER, depositAmount);
        assertTrue(success, "Deposit should be successful.");

        double newBalance = accountService.getBalance(TEST_ACCOUNT_NUMBER);
        assertEquals(initialBalance + depositAmount, newBalance,
                "Balance should be increased by the deposit amount.");
    }

    @Test
    @DisplayName("Fund Transfer")
    void testFundTransfer() {
        double fromInitialBalance = accountService.getBalance(TEST_ACCOUNT_NUMBER);
        double toInitialBalance = accountService.getBalance(RECIPIENT_ACCOUNT_NUMBER);
        double transferAmount = 150.0;

        if (fromInitialBalance < transferAmount) {
            userService.deposit(TEST_ACCOUNT_NUMBER, transferAmount * 2);
            fromInitialBalance = accountService.getBalance(TEST_ACCOUNT_NUMBER);
        }

        boolean success = userService.transferFunds(TEST_ACCOUNT_NUMBER, RECIPIENT_ACCOUNT_NUMBER, transferAmount);
        assertTrue(success, "Fund transfer should be successful.");

        // Verify the balances are updated correctly
        assertEquals(fromInitialBalance - transferAmount, accountService.getBalance(TEST_ACCOUNT_NUMBER));
        assertEquals(toInitialBalance + transferAmount, accountService.getBalance(RECIPIENT_ACCOUNT_NUMBER));
    }

    @Test
    @DisplayName("Change PIN")
    void testPinChange() {
        // 1. Change the PIN to a new one
        boolean changeSuccess = userService.changePin(TEST_CARD_NUMBER, TEST_PIN, NEW_PIN);
        assertTrue(changeSuccess, "PIN change should be successful.");

        // 2. Verify login works with the new PIN
        assertTrue(userService.validateUser(TEST_CARD_NUMBER, NEW_PIN), "Login with the new PIN should succeed.");

        // 3. IMPORTANT: Change the PIN back to the original to not affect other tests
        userService.changePin(TEST_CARD_NUMBER, NEW_PIN, TEST_PIN);
    }


    @Test
    @DisplayName("Mini Statement (Last 5 Transactions)")
    void testMiniStatement() {
        userService.deposit(TEST_ACCOUNT_NUMBER, 50.0);
        List<Transaction> miniStatement = userService.getMiniStatement(TEST_ACCOUNT_NUMBER);

        assertNotNull(miniStatement, "Mini statement should not be null.");
        assertFalse(miniStatement.isEmpty(), "Mini statement should have transactions.");
        assertTrue(miniStatement.size() <= 5, "Mini statement should contain 5 or fewer transactions.");
    }
    
    @Test
    @DisplayName("Full Transaction History")
    void testTransactionHistory() {
        // Perform a few transactions to ensure history exists
        userService.deposit(TEST_ACCOUNT_NUMBER, 10.0);
        userService.withdraw(TEST_ACCOUNT_NUMBER, 10.0);

        // Retrieve the full transaction history
        List<Transaction> history = userService.getTransactionHistory(TEST_ACCOUNT_NUMBER);

        assertNotNull(history, "Transaction history should not be null.");
        assertFalse(history.isEmpty(), "Transaction history should not be empty.");
    }
}