package dataaccess;

import java.util.HashMap;

import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;
import business.Checkout;

public interface DataAccess {
	public HashMap<String, Book> readBooksMap();

	public HashMap<String, User> readUserMap();

	public HashMap<String, LibraryMember> readMemberMap();

	public void saveNewMember(LibraryMember member);

	public void updateMember(String memberId, LibraryMember member);

	// needed for task #4
	public void updateBook(String isbn, Book book);

	public Book searchBook(String isbn);

	// optional feature #3
	public Checkout getCheckout(String isbn, int copyNum);

}
