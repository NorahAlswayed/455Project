package LMS;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class LibraryComponentTest {

    // ---------- Scenario Test 1 ----------
    @Test
    public void testAddLoanAndComputeTotalFine() {

        Library lib = new Library();

        Borrower b = new Borrower(1, "Noura", "Jeddah", 1111);
        Staff st = new Staff(1, "Maha", "Jeddah", 12345, 5000);

        Book book1 = new Book(1, "AI", "CS", "Smith", false);
        Book book2 = new Book(2, "ML", "CS", "Andrew", false);

        Loan l1 = new Loan(b, book1, st, st, 
            new Date(System.currentTimeMillis() - 5L*24*60*60*1000), null, false);

        Loan l2 = new Loan(b, book2, st, st, 
            new Date(System.currentTimeMillis() - 3L*24*60*60*1000), null, false);

        lib.addLoan(l1);
        lib.addLoan(l2);

        assertEquals(2, lib.getLoans().size());

        double totalFine = lib.computeFine2(b);

        assertTrue(totalFine >= 0);
    }

    // ---------- Scenario Test 2 ----------
    @Test
    public void testDuplicateLoanInsertion() {

        Library lib = new Library();
        Borrower b = new Borrower(1, "Noura", "Jeddah", 1111);
        Staff st = new Staff(1, "Maha", "Jeddah", 12345, 5000);
        Book book = new Book(1, "AI", "CS", "Smith", false);

        Loan l1 = new Loan(b, book, st, st, new Date(), null, false);

        lib.addLoan(l1);
        lib.addLoan(l1); 

       
        assertEquals(2, lib.getLoans().size());
    }
}