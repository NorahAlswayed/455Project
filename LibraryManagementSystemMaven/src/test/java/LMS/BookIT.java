package LMS;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BookIT {

    private Book book;
    private Borrower borrower;

    @Before
public void setUp() {
    book = new Book(-1, "Test Title", "Test Subject", "Test Author", false);

    borrower = new Borrower(1, "Test Borrower", "Test Address", 123456789);    
    
}

    @After
    public void tearDown() {
        book = null;
        borrower = null;
    }
    
    // Mokito subclass
    private class TestBook extends Book {
        boolean placeCalled = false;

        public TestBook(int id, String t, String s, String a, boolean issued) {
            super(id, t, s, a, issued);
        }

        @Override
        public void placeBookOnHold(Borrower b) {
            placeCalled = true; // نعلّم إنه تم استدعاء الدالة
        }
    }

  // makeHoldRequest() Test  
    
@Test
public void testMakeHoldRequest_FirstTime_AddsHoldRequest() {
    assertTrue(book.getHoldRequests().isEmpty());

    book.makeHoldRequest(borrower);

    assertEquals(1, book.getHoldRequests().size());
    assertEquals(borrower, book.getHoldRequests().get(0).getBorrower());
}

@Test
public void testMakeHoldRequest_Duplicate_DoesNotAddSecondTime() {

    book.makeHoldRequest(borrower);
    int sizeBefore = book.getHoldRequests().size();

    book.makeHoldRequest(borrower);
    int sizeAfter = book.getHoldRequests().size();

    assertEquals(sizeBefore, sizeAfter);
    assertEquals(1, sizeAfter);
}

@Test(expected = IllegalArgumentException.class)
public void testMakeHoldRequest_NullBorrower_ThrowsException() {
    book.makeHoldRequest(null);
}


@Test
public void testMakeHoldRequest_BorrowerAlreadyBorrowedBook_NoHoldCreated() {

    Loan loan = new Loan(borrower, book, null, null, new Date(), null, false);
    borrower.getBorrowedBooks().add(loan);

    assertTrue(book.getHoldRequests().isEmpty());

    book.makeHoldRequest(borrower);

    assertTrue(book.getHoldRequests().isEmpty());
}


 @Test
public void testMakeHoldRequest_OtherBorrowerAlreadyHasHold_NewHoldIsAdded() {
    Borrower otherBorrower = new Borrower(2, "Other", "Other Address", 987654321);

    HoldRequest hr1 = new HoldRequest(otherBorrower, book, new Date());
    book.getHoldRequests().add(hr1);

    assertEquals(1, book.getHoldRequests().size());

    book.makeHoldRequest(borrower);

    assertEquals(2, book.getHoldRequests().size());

    boolean found = false;
    for (HoldRequest hr : book.getHoldRequests()) {
        if (hr.getBorrower().equals(borrower)) {
            found = true;
            break;
        }
    }
    assertTrue(found);
}

@Test
public void testMakeHoldRequest_BorrowerBorrowedAnotherBook_StillAllowed() {
    Book anotherBook = new Book(-1, "Other", "Other", "Other", false);
    Loan loan = new Loan(borrower, anotherBook, null, null, new Date(), null, false);
    borrower.getBorrowedBooks().add(loan);

    book.makeHoldRequest(borrower);

    assertEquals(1, book.getHoldRequests().size());
}

// Mokito Test

 @Test
    public void testMakeHoldRequest_CallsPlaceBookOnHold() {
        TestBook tb = new TestBook(-1, "T", "S", "A", false);
        Borrower br = new Borrower(1, "Test", "Address", 12345);

        tb.makeHoldRequest(br);

        assertTrue(tb.placeCalled);
    }
    
    
    // placeBookOnHold() Test 
    
    @Test
public void testPlaceBookOnHold_Valid_AddsHoldForBorrower() {
    assertTrue(book.getHoldRequests().isEmpty());

    book.placeBookOnHold(borrower);

    assertEquals(1, book.getHoldRequests().size());
    HoldRequest hr = book.getHoldRequests().get(0);
    assertEquals(borrower, hr.getBorrower());
    assertEquals(book, hr.getBook());
}

@Test
public void testPlaceBookOnHold_MultipleCalls_AddMultipleRequests() {
    
    book.placeBookOnHold(borrower);
    book.placeBookOnHold(borrower);

    assertEquals(2, book.getHoldRequests().size());
}

@Test(expected = NullPointerException.class)
public void testPlaceBookOnHold_NullBorrower_ThrowsNullPointerException() {
    book.placeBookOnHold(null);
}

@Test
public void testPlaceBookOnHold_PrintsSuccessMessage() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(baos));

    try {
        book.placeBookOnHold(borrower);
    } finally {
        System.setOut(originalOut); 
    }

    String output = baos.toString();
    assertTrue(output.contains("has been successfully placed on hold"));
    assertTrue(output.contains(book.getTitle()));
    assertTrue(output.contains(borrower.getName()));
}

// serviceHoldRequest() TEST

@Test
public void testServiceHoldRequest_RemovesFirstHoldRequest() {
    book.placeBookOnHold(borrower);
    HoldRequest hr = book.getHoldRequests().get(0);

    assertEquals(1, book.getHoldRequests().size());
    assertEquals(1, borrower.getOnHoldBooks().size());

    book.serviceHoldRequest(hr);

    assertEquals(0, book.getHoldRequests().size());
    assertEquals(0, borrower.getOnHoldBooks().size());
}

@Test
public void testServiceHoldRequest_RemovesOnlyFirstWhenMultipleExist() {
    Borrower b2 = new Borrower(2, "Other", "Other Addr", 9999);

    // first request
    book.placeBookOnHold(borrower);
    HoldRequest hr1 = book.getHoldRequests().get(0);

    // second request
    book.placeBookOnHold(b2);
    HoldRequest hr2 = book.getHoldRequests().get(1);

    assertEquals(2, book.getHoldRequests().size());

    book.serviceHoldRequest(hr1);

    assertEquals(1, book.getHoldRequests().size());
    assertTrue(book.getHoldRequests().contains(hr2));
    assertFalse(book.getHoldRequests().contains(hr1));

    assertFalse(borrower.getOnHoldBooks().contains(hr1));
    assertTrue(b2.getOnHoldBooks().contains(hr2));
}

@Test(expected = NullPointerException.class)
public void testServiceHoldRequest_NullHR_ThrowsException() {
    book.serviceHoldRequest(null);
}

@Test(expected = NullPointerException.class)
public void testServiceHoldRequest_NullBorrowerInsideHR_ThrowsException() {
    HoldRequest badHR = new HoldRequest(null, book, new Date());
    book.getHoldRequests().add(badHR);

    book.serviceHoldRequest(badHR);
}

@Test
public void testServiceHoldRequest_RemovesFromBorrowerAfterBook() {
    book.placeBookOnHold(borrower);
    HoldRequest hr = book.getHoldRequests().get(0);

    assertEquals(1, borrower.getOnHoldBooks().size());
    assertEquals(1, book.getHoldRequests().size());

    book.serviceHoldRequest(hr);

    assertEquals(0, book.getHoldRequests().size());   
    assertEquals(0, borrower.getOnHoldBooks().size());  
}

// printHoldRequests() TEST

@Test
public void testPrintHoldRequests_NoHoldRequests_PrintsNoHoldRequestsMessage() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(baos));

    try {
        book.printHoldRequests();
    } finally {
        System.setOut(originalOut);
    }

    String output = baos.toString();

    assertTrue(output.contains("No Hold Requests."));
    assertFalse(output.contains("Hold Requests are: "));
}

@Test
public void testPrintHoldRequests_OneHoldRequest_PrintsHeaderAndSingleRequest() {

    HoldRequest hr = new HoldRequest(borrower, book, new Date());
    book.getHoldRequests().add(hr);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(baos));

    try {
        book.printHoldRequests();
    } finally {
        System.setOut(originalOut);
    }

    String output = baos.toString();

    assertTrue(output.contains("Hold Requests are: "));
    assertTrue(output.contains("Book's Title"));
    assertTrue(output.contains("Borrower's Name"));

    assertTrue(output.contains("0-\t\t"));
    assertTrue(output.contains(book.getTitle()));
    assertTrue(output.contains(borrower.getName()));
}

@Test
public void testPrintHoldRequests_MultipleHoldRequests_PrintsAllWithIndices() {
    Borrower otherBorrower = new Borrower(2, "Other Borrower", "Other Address", 5555);

    HoldRequest hr1 = new HoldRequest(borrower, book, new Date());
    HoldRequest hr2 = new HoldRequest(otherBorrower, book, new Date());

    book.getHoldRequests().add(hr1);
    book.getHoldRequests().add(hr2);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(baos));

    try {
        book.printHoldRequests();
    } finally {
        System.setOut(originalOut);
    }

    String output = baos.toString();

    assertTrue(output.contains("Hold Requests are: "));

    assertTrue(output.contains("0-\t\t"));
    assertTrue(output.contains("1-\t\t"));

    assertTrue(output.contains(borrower.getName()));
    assertTrue(output.contains(otherBorrower.getName()));
}

@Test
public void testPrintHoldRequests_AfterRemovingOne_RequestListUpdates() {
    Borrower otherBorrower = new Borrower(2, "Other Borrower", "Other Address", 5555);

    HoldRequest hr1 = new HoldRequest(borrower, book, new Date());
    HoldRequest hr2 = new HoldRequest(otherBorrower, book, new Date());

    book.getHoldRequests().add(hr1);
    book.getHoldRequests().add(hr2);

    book.getHoldRequests().remove(0);  

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(baos));

    try {
        book.printHoldRequests();
    } finally {
        System.setOut(originalOut);
    }

    String output = baos.toString();

    assertTrue(output.contains("0-\t\t"));
    assertFalse(output.contains("1-\t\t"));

    assertFalse(output.contains(borrower.getName()));
    assertTrue(output.contains(otherBorrower.getName()));
}

@Test(expected = NullPointerException.class)
public void testPrintHoldRequests_NullBorrowerInRequest_ThrowsException() {
    HoldRequest badHR = new HoldRequest(null, book, new Date());
    book.getHoldRequests().add(badHR);

    book.printHoldRequests(); 
}

}
