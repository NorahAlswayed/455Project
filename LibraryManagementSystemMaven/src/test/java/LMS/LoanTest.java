package LMS;

import java.util.Date;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoanTest {

//    //  small helper that gives us a past date (today - X days)
//    private Date daysAgo(int days) {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DAY_OF_YEAR, -days);
//        return cal.getTime();
//    }
//
//    // Same idea for future dates
//    private Date daysAfter(int days) {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DAY_OF_YEAR, days);
//        return cal.getTime();
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 1 — No fine (returned within deadline)
//    */
//    @Test
//    public void testReturnedBeforeDeadline() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(7);
//        lib.setFine(2);
//
//        Loan loan = new Loan(null, null, null, null, daysAgo(5), null, false);
//
//        assertEquals(0.0, loan.computeFine1(), 0.001);
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 2 — Returned on deadline
//   */
//    @Test
//    public void testReturnedOnDeadline() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(7);
//        lib.setFine(2);
//
//        Loan loan = new Loan(null, null, null, null, daysAgo(7), null, false);
//
//        assertEquals(0.0, loan.computeFine1(), 0.001);
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 3 — 1 day late
//      */
//    @Test
//    public void testOneDayLate() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(7);
//        lib.setFine(2);
//
//        Loan loan = new Loan(null, null, null, null, daysAgo(8), null, false);
//
//        assertEquals(2.0, loan.computeFine1(), 0.001);
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 4 — 3 days late
//     */
//    @Test
//    public void testThreeDaysLate() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(7);
//        lib.setFine(2);
//
//        Loan loan = new Loan(null, null, null, null, daysAgo(10), null, false);
//
//        assertEquals(6.0, loan.computeFine1(), 0.001);
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 5 — 30 days late (big delay)
//     */
//    @Test
//    public void testLargeDelay() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(7);
//        lib.setFine(2);
//
//        Loan loan = new Loan(null, null, null, null, daysAgo(37), null, false);
//
//        assertEquals(60.0, loan.computeFine1(), 0.001);
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 6 — Fine already paid
//   */
//    @Test
//    public void testFineAlreadyPaid() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(7);
//        lib.setFine(2);
//
//        Loan loan = new Loan(null, null, null, null, daysAgo(10), null, true);
//
//        assertEquals(0.0, loan.computeFine1(), 0.001);
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 7 — Zero per-day fine
//     */
//    @Test
//    public void testZeroFinePerDay() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(7);
//        lib.setFine(0);
//
//        Loan loan = new Loan(null, null, null, null, daysAgo(20), null, false);
//
//        assertEquals(0.0, loan.computeFine1(), 0.001);
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 8 — Future issued date
//   */
//    @Test
//    public void testFutureIssuedDate() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(7);
//        lib.setFine(2);
//
//        Loan loan = new Loan(null, null, null, null, daysAfter(5), null, false);
//
//        assertEquals(0.0, loan.computeFine1(), 0.001);
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 9 — issuedDate = null → should throw NPE
//     */
//    @Test(expected = NullPointerException.class)
//    public void testNullIssuedDate() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(5);
//        lib.setFine(2.0);
//
//        Loan loan = new Loan(null, null, null, null, null, null, false);
//
//        loan.computeFine1(); // triggers NPE
//    }
//
//    /* ---------------------------------------------------------
//     * Test Case 10 — Library values not set
//     */
//    @Test
//    public void testLibraryValuesNotSet() {
//        Library lib = Library.getInstance();
//        lib.setReturnDeadline(0);
//        lib.setFine(0);
//
//        Loan loan = new Loan(null, null, null, null, daysAgo(20), null, false);
//
//        assertEquals(0.0, loan.computeFine1(), 0.001);
//    }

    
    // Helper: create past date
    private Date daysAgo(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        return cal.getTime();
    }

    // Helper: create future date
    private Date daysAfter(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }


    /* ---------------------------------------------------------
     * Test Case 1 — Normal renew (everything valid)
 */
    @Test
    public void testNormalRenew() {
        Book book = new Book(-1, "Algorithms", "CS", "CLRS", false);
        Loan loan = new Loan(null, book, null, null, daysAgo(8), null, false);

        Date newDate = daysAfter(5);
        loan.renewIssuedBook(newDate);

        assertEquals(newDate, loan.getIssuedDate());
    }


    /* ---------------------------------------------------------
     * Test Case 2 — Renew with past date
     */
    @Test
    public void testRenewWithPastDate() {
        Book book = new Book(-1, "Math", "Sci", "Author", false);
        Loan loan = new Loan(null, book, null, null, daysAgo(10), null, false);

        Date pastDate = daysAgo(50);
        loan.renewIssuedBook(pastDate);

        assertEquals(pastDate, loan.getIssuedDate());
    }


    /* ---------------------------------------------------------
     * Test Case 3 — Renew with future date
    */
    @Test
    public void testRenewWithFutureDate() {
        Book book = new Book(-1, "Physics", "Sci", "Someone", false);
        Loan loan = new Loan(null, book, null, null, daysAgo(4), null, false);

        Date future = daysAfter(30);
        loan.renewIssuedBook(future);

        assertEquals(future, loan.getIssuedDate());
    }


    /* ---------------------------------------------------------
     * Test Case 4 — Book is null → should throw NPE
     */
    @Test(expected = NullPointerException.class)
    public void testRenewWithNullBook() {
        Loan loan = new Loan(null, null, null, null, daysAgo(4), null, false);

        loan.renewIssuedBook(daysAfter(3)); // should crash
    }


    /* ---------------------------------------------------------
     * Test Case 5 — new issued date = null
*/
    @Test
    public void testRenewWithNullDate() {
        Book book = new Book(-1, "Science", "Sci", "Author", false);
        Loan loan = new Loan(null, book, null, null, daysAgo(10), null, false);

        loan.renewIssuedBook(null);

        assertNull(loan.getIssuedDate());
    }


    /* ---------------------------------------------------------
     * Test Case 6 — Book title = null
     */
    @Test
    public void testRenewWithNullBookTitle() {
        Book book = new Book(-1, null, "CS", "Author", false);
        Loan loan = new Loan(null, book, null, null, daysAgo(3), null, false);

        Date newDate = daysAfter(1);
        loan.renewIssuedBook(newDate);

        assertEquals(newDate, loan.getIssuedDate());
    }


    /* ---------------------------------------------------------
     * Test Case 7 — Renew twice
     */
    @Test
    public void testRenewTwice() {
        Book book = new Book(-1, "Clean Code", "CS", "Uncle Bob", false);
        Loan loan = new Loan(null, book, null, null, daysAgo(15), null, false);

        Date first = daysAfter(2);
        Date second = daysAfter(10);

        loan.renewIssuedBook(first);
        loan.renewIssuedBook(second);

        assertEquals(second, loan.getIssuedDate());
    }


}
