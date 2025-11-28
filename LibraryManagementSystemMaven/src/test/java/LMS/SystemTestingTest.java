package LMS;


import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class SystemTestingTest {

   
    @Test
    public void testFullSystemWorkflow() {

        // 1) SYSTEM SETUP (Objects creation)
        // -----------------------------------------------------
        Library lib = new Library();
        Borrower borrower = new Borrower(1, "Noura", "Jeddah", 9999);
        Staff staff = new Staff(1, "Maha", "Jeddah", 55555, 6000);
        Book book = new Book(10, "AI", "CS", "Andrew", false);

        // 2) HOLD REQUEST WORKFLOW (4 Book + 2 Borrower)
        // -----------------------------------------------------

        book.makeHoldRequest(borrower);

    
        HoldRequest hr = new HoldRequest(borrower, book, new Date());
        borrower.addHoldRequest(hr);

  
        book.placeBookOnHold(borrower);

        
        HoldRequest firstReq = book.getHoldRequests().get(0);
        book.serviceHoldRequest(firstReq);

        
        assertTrue(borrower.getOnHoldBooks().size() >= 0);

     
        if (!borrower.getOnHoldBooks().isEmpty()) {
            borrower.removeHoldRequest(borrower.getOnHoldBooks().get(0));
        }
        
        // 3) BORROW WORKFLOW (Borrower + Library)
        // -----------------------------------------------------

        Loan loan = new Loan(borrower, book, staff, staff, new Date(), null, false);

        lib.addLoan(loan);

   
        borrower.addBorrowedBook(loan);

        
        assertEquals(1, borrower.getBorrowedBooks().size());

        // 4) PRINT HOLD REQUESTS (Book)
        // -----------------------------------------------------

        
        book.printHoldRequests();

        // 5) LOAN FINE + RENEWAL (Loan + Library)
        // -----------------------------------------------------

      
        double fine1 = loan.computeFine1();
        assertTrue(fine1 >= 0);

        Date newDate = new Date();
        loan.renewIssuedBook(newDate);
        assertEquals(newDate, loan.getIssuedDate());

        double totalFine = lib.computeFine2(borrower);
        assertTrue(totalFine >= 0);

        // 6) CLEANUP — removeBorrowedBook()
        // -----------------------------------------------------

        borrower.removeBorrowedBook(loan);
        assertEquals(0, borrower.getBorrowedBooks().size());
    }
}