package LMS;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class BorrowerComponentTest {

    // ---------- Scenario Test 1 ----------
    @Test
    public void testAddThenRemoveBorrowedBook() {
        Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);
        Loan l1 = new Loan(b, null, null, null, new Date(), null, false);

        b.addBorrowedBook(l1);
        b.removeBorrowedBook(l1);

        assertEquals(0, b.getBorrowedBooks().size());
    }

    // ---------- Scenario Test 2 ----------
    @Test
    public void testBorrowedThenHold() {
        Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);
        Loan l1 = new Loan(b, null, null, null, new Date(), null, false);
        HoldRequest hr1 = new HoldRequest(b, null, new Date());

        b.addBorrowedBook(l1);
        b.addHoldRequest(hr1);

        assertEquals(1, b.getBorrowedBooks().size());
        assertEquals(1, b.getOnHoldBooks().size());
    }

    // ---------- Scenario Test 3 ----------
    @Test
    public void testHoldThenRemoveBorrowed() {
        Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);
        Loan l1 = new Loan(b, null, null, null, new Date(), null, false);
        HoldRequest hr1 = new HoldRequest(b, null, new Date());

        b.addHoldRequest(hr1);
        b.addBorrowedBook(l1);
        b.removeBorrowedBook(l1);

        assertEquals(0, b.getBorrowedBooks().size());
        assertEquals(1, b.getOnHoldBooks().size());
    }

    // ---------- Scenario Test 4 ----------
    @Test
    public void testDuplicateBorrowedThenRemoveOne() {
        Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);
        Loan l1 = new Loan(b, null, null, null, new Date(), null, false);

        b.addBorrowedBook(l1);
        b.addBorrowedBook(l1);
        b.removeBorrowedBook(l1);

        assertEquals(1, b.getBorrowedBooks().size());
    }

    // ---------- Scenario Test 5 ----------
    @Test
    public void testDifferentBorrowerObjects() {
        Borrower b1 = new Borrower(1, "Noura", "Riyadh", 12345);
        Borrower b2 = new Borrower(2, "Sara", "Jeddah", 99999);

        Loan foreignLoan = new Loan(b2, null, null, null, new Date(), null, false);
        HoldRequest foreignHr = new HoldRequest(b2, null, new Date());

        b1.addBorrowedBook(foreignLoan);
        b1.addHoldRequest(foreignHr);

        assertEquals(1, b1.getBorrowedBooks().size());
        assertEquals(1, b1.getOnHoldBooks().size());
    }
}