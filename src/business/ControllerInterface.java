package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;

	public void logout();

	public List<String> allMemberIds();

	public List<String> allBookIds();

	public User getCurrentUser();

	// feature #4
	void addBookCopy(String isbn, int noOfCopies) throws LibrarySystemException;

	// optional: feature #3
	List<Checkout> searchOverdueBookCopies(String isbn) throws LibrarySystemException;
}
