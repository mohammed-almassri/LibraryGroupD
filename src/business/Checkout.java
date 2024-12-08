package business;

import java.io.Serializable;
import java.time.LocalDate;

public class Checkout implements Serializable {
    private LibraryMember member;
    private BookCopy bookCopy;
    private LocalDate checkoutDate;
    private LocalDate returnDate;


    public Checkout(LibraryMember member, BookCopy bookCopy, LocalDate checkoutDate, LocalDate returnDate) {
        this.member = member;
        this.bookCopy = bookCopy;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
    }

    public LibraryMember getMember() {
        return member;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
