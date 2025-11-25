package LMS;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class BookComponentTest {

    // ---------- Scenario Test 1 — Full Hold Workflow ----------
    @Test
    public void testHoldWorkflow() {

        Book book = new Book(1, "Java Programming", "CS", "Author", false);
        Borrower b1 = new Borrower(1, "Noura", "Jeddah", 1111);
        Borrower b2 = new Borrower(2, "Renas", "KAU", 2222);

    
        book.makeHoldRequest(b1);
        assertEquals(1, book.getHoldRequests().size());

        book.makeHoldRequest(b2);
        assertEquals(2, book.getHoldRequests().size());

        
        HoldRequest hr1 = book.getHoldRequests().get(0);
        book.serviceHoldRequest(hr1);
        assertEquals(1, book.getHoldRequests().size());
        assertEquals(b2, book.getHoldRequests().get(0).getBorrower());
        assertEquals(0, b1.getOnHoldBooks().size());

        
        HoldRequest hr2 = book.getHoldRequests().get(0);
        book.serviceHoldRequest(hr2);
        assertEquals(0, book.getHoldRequests().size());
        assertEquals(0, b2.getOnHoldBooks().size());
    }

    // ---------- Scenario Test 2 — Print Component ----------
    @Test
    public void testPrintHoldRequests() {
        Book book = new Book(100, "Networks", "CS", "Kurose", false);

        Borrower b1 = new Borrower(1, "Noura", "Jeddah", 555);
        Borrower b2 = new Borrower(2, "Renas", "KAU", 666);

        book.placeBookOnHold(b1);
        book.placeBookOnHold(b2);

        assertEquals(2, book.getHoldRequests().size());

        book.printHoldRequests();
    }

    // ---------- Scenario Test 3 — Fine and Renewal ----------
    @Test
    public void testFineAndRenew() {
        Borrower b = new Borrower(1, "Noura", "Jeddah", 1111);
        Staff s1 = new Staff(1, "Maha", "Jeddah", 12345, 5000);
        Staff s2 = new Staff(2, "Sara", "Jeddah", 99999, 6000);

        Book book = new Book(10, "OS", "CS", "Tanenbaum", false);

        Date issued = new Date(System.currentTimeMillis() - (5L * 24*60*60*1000));
        Loan loan = new Loan(b, book, s1, s2, issued, null, false);

        double fine = loan.computeFine1();
        assertTrue(fine >= 0);

        Date newDate = new Date();
        loan.renewIssuedBook(newDate);

        assertEquals(newDate, loan.getIssuedDate());
    }
}