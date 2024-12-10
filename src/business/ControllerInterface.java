package business;

import java.util.List;

import dataaccess.User;

public interface ControllerInterface {
	void login(String id, String password) throws LoginException;
	void logout();
	List<String> allMemberIds();
	List<String> allBookIds();
	User getCurrentUser();
	void addNewMember(String memberId, String firstName, String lastName, String phone, String street,
					  String city, String state, String zip) throws LibrarySystemException;
	void addNewBook(Book book) throws Exception;
    void addBookCopy(String isbn, int noOfCopies) throws LibrarySystemException; // feature #4
    List<Checkout> searchOverdueBookCopies(String isbn) throws LibrarySystemException; // optional: feature #3
}
