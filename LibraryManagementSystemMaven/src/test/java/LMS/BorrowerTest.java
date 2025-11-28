package LMS;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class BorrowerTest {
    

    
    @Test
    public void testAddBorrowedBook() {
        Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

        Book book = new Book(10, "Java", "CS", "Author", false);
        Loan loan = new Loan(b, book, null, null, new Date(), null, false);

        b.addBorrowedBook(loan);

        assertEquals(1, b.getBorrowedBooks().size());
        assertEquals(loan, b.getBorrowedBooks().get(0));
    }
    
    
    
    @Test
public void testAddBorrowedBook_Null() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    b.addBorrowedBook(null);

    assertEquals(1, b.getBorrowedBooks().size());
    assertNull(b.getBorrowedBooks().get(0));
}


@Test
public void testAddBorrowedBook_Multiple() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    Loan l1 = new Loan(b, null, null, null, new Date(), null, false);
    Loan l2 = new Loan(b, null, null, null, new Date(), null, false);

    b.addBorrowedBook(l1);
    b.addBorrowedBook(l2);

    assertEquals(2, b.getBorrowedBooks().size());
    assertEquals(l1, b.getBorrowedBooks().get(0));
    assertEquals(l2, b.getBorrowedBooks().get(1));
}


@Test
public void testAddBorrowedBook_DifferentBorrowerLoan() {
    Borrower b1 = new Borrower(1, "Noura", "Riyadh", 12345);
    Borrower b2 = new Borrower(2, "Hanin", "Jeddah", 54321);

    Loan loan = new Loan(b2, null, null, null, new Date(), null, false);

    b1.addBorrowedBook(loan);

    assertEquals(1, b1.getBorrowedBooks().size());
    assertEquals(loan, b1.getBorrowedBooks().get(0));
}

@Test
public void testAddBorrowedBook_Duplicate() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    Loan loan = new Loan(b, null, null, null, new Date(), null, false);

    b.addBorrowedBook(loan);
    b.addBorrowedBook(loan);

    assertEquals(2, b.getBorrowedBooks().size());
    assertEquals(loan, b.getBorrowedBooks().get(0));
    assertEquals(loan, b.getBorrowedBooks().get(1));
}

//---------------------------------------------------------------------------------------
 

@Test
public void testRemoveBorrowedBook_Valid() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    Loan l1 = new Loan(b, null, null, null, new Date(), null, false);
    b.addBorrowedBook(l1);

    b.removeBorrowedBook(l1);

    assertEquals(0, b.getBorrowedBooks().size());
}

@Test
public void testRemoveBorrowedBook_Null() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    Loan l1 = new Loan(b, null, null, null, new Date(), null, false);
    b.addBorrowedBook(l1);

    b.removeBorrowedBook(null);

    // nothing removed
    assertEquals(1, b.getBorrowedBooks().size());
}


@Test
public void testRemoveBorrowedBook_NotInList() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    Loan existing = new Loan(b, null, null, null, new Date(), null, false);
    Loan notExisting = new Loan(b, null, null, null, new Date(), null, false);

    b.addBorrowedBook(existing);

    b.removeBorrowedBook(notExisting);

    assertEquals(1, b.getBorrowedBooks().size());
    assertEquals(existing, b.getBorrowedBooks().get(0));
}

@Test
public void testRemoveBorrowedBook_Duplicate() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    Loan loan = new Loan(b, null, null, null, new Date(), null, false);

    b.addBorrowedBook(loan);
    b.addBorrowedBook(loan);  // duplicate

    b.removeBorrowedBook(loan);

    assertEquals(1, b.getBorrowedBooks().size());
    assertEquals(loan, b.getBorrowedBooks().get(0));
}

@Test
public void testRemoveBorrowedBook_DifferentBorrowerLoan() {
    Borrower b1 = new Borrower(1, "Noura", "Riyadh", 12345);
    Borrower b2 = new Borrower(2, "Sara", "Jeddah", 88888);

    Loan otherLoan = new Loan(b2, null, null, null, new Date(), null, false);
    Loan myLoan = new Loan(b1, null, null, null, new Date(), null, false);

    b1.addBorrowedBook(myLoan);

    b1.removeBorrowedBook(otherLoan);

    assertEquals(1, b1.getBorrowedBooks().size());
    assertEquals(myLoan, b1.getBorrowedBooks().get(0));
}


////-------------------------------------------------------------------------------------
   

@Test
public void testAddHoldRequest_Valid() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    HoldRequest hr = new HoldRequest(b, null, new Date());

    b.addHoldRequest(hr);

    assertEquals(1, b.getOnHoldBooks().size());
    assertEquals(hr, b.getOnHoldBooks().get(0));
}

@Test
public void testAddHoldRequest_Null() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    b.addHoldRequest(null);

    assertEquals(1, b.getOnHoldBooks().size());
    assertNull(b.getOnHoldBooks().get(0));
}

@Test
public void testAddHoldRequest_Duplicate() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    HoldRequest hr = new HoldRequest(b, null, new Date());

    b.addHoldRequest(hr);
    b.addHoldRequest(hr);

    assertEquals(2, b.getOnHoldBooks().size());
    assertEquals(hr, b.getOnHoldBooks().get(0));
    assertEquals(hr, b.getOnHoldBooks().get(1));
}

@Test
public void testAddHoldRequest_DifferentBorrower() {
    Borrower b1 = new Borrower(1, "Noura", "Riyadh", 12345);
    Borrower b2 = new Borrower(2, "Sara", "Jeddah", 99999);

    HoldRequest hr = new HoldRequest(b2, null, new Date());

    b1.addHoldRequest(hr);

    assertEquals(1, b1.getOnHoldBooks().size());
    assertEquals(hr, b1.getOnHoldBooks().get(0));
}

@Test
public void testAddHoldRequest_Multiple() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    HoldRequest hr1 = new HoldRequest(b, null, new Date());
    HoldRequest hr2 = new HoldRequest(b, null, new Date());

    b.addHoldRequest(hr1);
    b.addHoldRequest(hr2);

    assertEquals(2, b.getOnHoldBooks().size());
    assertEquals(hr1, b.getOnHoldBooks().get(0));
    assertEquals(hr2, b.getOnHoldBooks().get(1));
}


//---------------------------------------------------------------------------------------
 

@Test
public void testRemoveHoldRequest_Valid() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    HoldRequest hr = new HoldRequest(b, null, new Date());
    b.addHoldRequest(hr);

    b.removeHoldRequest(hr);

    assertEquals(0, b.getOnHoldBooks().size());
}

@Test
public void testRemoveHoldRequest_Null() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    HoldRequest hr = new HoldRequest(b, null, new Date());
    b.addHoldRequest(hr);

    b.removeHoldRequest(null);

    assertEquals(1, b.getOnHoldBooks().size());
    assertEquals(hr, b.getOnHoldBooks().get(0));
}

@Test
public void testRemoveHoldRequest_NotInList() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    HoldRequest existing = new HoldRequest(b, null, new Date());
    HoldRequest notExisting = new HoldRequest(b, null, new Date());

    b.addHoldRequest(existing);

    b.removeHoldRequest(notExisting);

    assertEquals(1, b.getOnHoldBooks().size());
    assertEquals(existing, b.getOnHoldBooks().get(0));
}

@Test
public void testRemoveHoldRequest_Duplicate() {
    Borrower b = new Borrower(1, "Noura", "Riyadh", 12345);

    HoldRequest hr = new HoldRequest(b, null, new Date());

    b.addHoldRequest(hr);
    b.addHoldRequest(hr); // duplicate

    b.removeHoldRequest(hr);

    assertEquals(1, b.getOnHoldBooks().size());
    assertEquals(hr, b.getOnHoldBooks().get(0));
}

@Test
public void testRemoveHoldRequest_DifferentBorrower() {
    Borrower b1 = new Borrower(1, "Noura", "Riyadh", 12345);
    Borrower b2 = new Borrower(2, "Lama", "Jeddah", 55555);

    HoldRequest myRequest = new HoldRequest(b1, null, new Date());
    HoldRequest otherRequest = new HoldRequest(b2, null, new Date());

    b1.addHoldRequest(myRequest);

    b1.removeHoldRequest(otherRequest);

    assertEquals(1, b1.getOnHoldBooks().size());
    assertEquals(myRequest, b1.getOnHoldBooks().get(0));
}



}
