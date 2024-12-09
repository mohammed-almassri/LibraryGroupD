package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static User currentUser = null;

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentUser = map.get(id);
		currentAuth = currentUser.getAuthorization();
	}

	public void logout() {
		currentAuth = null;
		currentUser = null;
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public User getCurrentUser() {
		return currentUser;
	}

	@Override
	public void addBookCopy(String isbn, int numCopies) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		Book book = da.searchBook(isbn);

		if (book == null) {
			throw new LibrarySystemException("Book with ISBN " + isbn + " not found");
		}

		if (numCopies <= 0) {
			throw new LibrarySystemException("Number of copies must be positive");
		}

		for (int i = 0; i < numCopies; i++) {
			book.addCopy();
		}
		System.out.println("Copies: " + book.getCopies().length);

		da.updateBook(isbn, book);
	}

	@Override
	public List<Checkout> searchOverdueBookCopies(String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		Book book = da.searchBook(isbn);

		if (book == null) {
			throw new LibrarySystemException("Book with ISBN " + isbn + " not found");
		}

		List<Checkout> overdueCheckouts = new ArrayList<>();

		// Check each copy of the book
		for (BookCopy copy : book.getCopies()) {
			if (!copy.isAvailable()) {
				Checkout checkout = da.getCheckout(isbn, copy.getCopyNum());
				if (checkout != null && checkout.getReturnDate().isBefore(LocalDate.now())) {
					overdueCheckouts.add(checkout);
				}
			}
		}

		return overdueCheckouts;
	}
}
