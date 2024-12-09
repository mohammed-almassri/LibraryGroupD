package dataaccess;

import java.util.HashMap;

import business.Book;
import business.Checkout;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;
import business.Checkout;

public interface DataAccess { 
	HashMap<String,Book> readBooksMap();
	HashMap<String,User> readUserMap();
	HashMap<String, LibraryMember> readMemberMap();
	void saveNewMember(LibraryMember member) throws Exception;
    public void updateMember(String memberId, LibraryMember member);
    public void updateBook(String isbn, Book book);    // needed for task #4
    public Book searchBook(String isbn);
    public Checkout getCheckout(String isbn, int copyNum);     // optional feature #3
}
