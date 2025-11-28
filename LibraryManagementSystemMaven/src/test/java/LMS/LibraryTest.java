package LMS;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class LibraryTest {

//    /* ---------------------------------------------------------
//     * Helper */
//    private Loan makeLoan(Book b) {
//        return new Loan(null, b, null, null, new Date(), null, false);
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 1 — Add a valid Loan*/
//    @Test
//    public void testAddValidLoan() {
//        Library lib = Library.getInstance();
//
//        Book b = new Book(-1, "Algorithms", "CS", "CLRS", false);
//        Loan loan = makeLoan(b);
//
//        lib.addLoan(loan);
//
//        assertTrue(lib.getLoans().contains(loan));
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 2 — Add multiple loans */
//    @Test
//    public void testAddMultipleLoans() {
//        Library lib = Library.getInstance();
//        lib.getLoans().clear(); // reset
//
//        Book b1 = new Book(-1, "Math", "Sci", "AuthorA", false);
//        Book b2 = new Book(-1, "Physics", "Sci", "AuthorB", false);
//
//        Loan l1 = makeLoan(b1);
//        Loan l2 = makeLoan(b2);
//
//        lib.addLoan(l1);
//        lib.addLoan(l2);
//
//        assertEquals(2, lib.getLoans().size());
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 3 — Add same loan twice */
//    @Test
//    public void testAddDuplicateLoan() {
//        Library lib = Library.getInstance();
//        lib.getLoans().clear(); // reset
//
//        Book b = new Book(-1, "AI", "CS", "Author", false);
//        Loan loan = makeLoan(b);
//
//        lib.addLoan(loan);
//        lib.addLoan(loan); // duplicated loan
//
//        assertEquals(2, lib.getLoans().size());
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 4 — Add null loan → system fails silently
//    */
//    @Test
//    public void testAddNullLoan() {
//        Library lib = Library.getInstance();
//        lib.getLoans().clear(); // reset
//
//        lib.addLoan(null);
//
//        assertTrue(lib.getLoans().contains(null));
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 5 — Loan without Book object
//      */
//    @Test
//    public void testAddLoanWithoutBook() {
//        Library lib = Library.getInstance();
//        lib.getLoans().clear(); // reset
//
//        Loan loan = new Loan(null, null, null, null, new Date(), null, false);
//
//        lib.addLoan(loan);
//
//        assertTrue(lib.getLoans().contains(loan));
//    }
    
    
    // Helper — create past date
    private Date daysAgo(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        return cal.getTime();
    }

    // Helper — create future date
    private Date daysAfter(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    /* ---------------------------------------------------------
     * Test Case 1 — Borrower has no loans*/
    @Test
    public void testNoLoansFine() {
        Library lib = Library.getInstance();
        lib.getLoans().clear(); // reset

        Borrower b = new Borrower(-1, "Ali", "Jeddah", 1234);

        double result = lib.computeFine2(b);

        assertEquals(0.0, result, 0.001);
    }

    /* ---------------------------------------------------------
     * Test Case 2 — Borrower has loans but all returned on time */
    @Test
    public void testLoansReturnedOnTime() {
        Library lib = Library.getInstance();
        lib.getLoans().clear();

        lib.setReturnDeadline(10);
        lib.setFine(2.0); // 2 SR per day

        Borrower b = new Borrower(-1, "Reem", "Jeddah", 555);

        Book bk = new Book(-1, "OS", "CS", "Tanenbaum", false);

        Loan l = new Loan(b, bk, null, null, daysAgo(5), daysAgo(0), false);

        lib.addLoan(l);

        double result = lib.computeFine2(b);

        assertEquals(0.0, result, 0.001);
    }

    /* ---------------------------------------------------------
     * Test Case 3 — Borrower has one overdue loan*/
    @Test
    public void testSingleLoanOverdue() {
        Library lib = Library.getInstance();
        lib.getLoans().clear();

        lib.setReturnDeadline(7);  
        lib.setFine(1.5);         

        Borrower b = new Borrower(-1, "Mona", "Makkah", 789);

        Book bk = new Book(-1, "Math", "Sci", "Author", false);

        Loan l = new Loan(b, bk, null, null, daysAgo(12), daysAgo(0), false);

        lib.addLoan(l);

        double expected = 5 * 1.5;
        double result = lib.computeFine2(b);

        assertEquals(expected, result, 0.001);
    }

    /* ---------------------------------------------------------
     * Test Case 4 — Borrower has multiple overdue loans */
    @Test
    public void testMultipleLoansFine() {
        Library lib = Library.getInstance();
        lib.getLoans().clear();

        lib.setReturnDeadline(10);
        lib.setFine(2.0); 

        Borrower b = new Borrower(-1, "Sara", "TA", 333);

        Book b1 = new Book(-1, "AI", "CS", "Author1", false);
        Book b2 = new Book(-1, "Physics", "Sci", "Author2", false);
        
        Loan l1 = new Loan(b, b1, null, null, daysAgo(15), daysAgo(10), false);

        Loan l2 = new Loan(b, b2, null, null, daysAgo(13), daysAgo(0), false);

        lib.addLoan(l1);
        lib.addLoan(l2);

        double expected = (5 * 2.0) + (3 * 2.0);
        double result = lib.computeFine2(b);

        assertEquals(expected, result, 0.001);
    }
}
