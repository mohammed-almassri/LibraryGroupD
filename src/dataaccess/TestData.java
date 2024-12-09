package dataaccess;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import business.Address;
import business.Author;
import business.Book;
import business.LibraryMember;
import business.Checkout;
import business.BookCopy;

/**
 * This class loads data into the data repository and also
 * sets up the storage units that are used in the application.
 * The main method in this class must be run once (and only
 * once) before the rest of the application can work properly.
 * It will create three serialized objects in the dataaccess.storage
 * folder.
 * 
 *
 */
public class TestData {
	
	
	public static void main(String[] args) {
		// Create storage directory first
		createStorageDirectory();

		TestData td = new TestData();
		td.bookData();
		td.libraryMemberData();
		td.userData();
		td.addTestCheckouts();
		DataAccess da = new DataAccessFacade();
		System.out.println(da.readBooksMap());
		System.out.println(da.readUserMap());
		System.out.println(da.readMemberMap());
	}

	///create books
	public void bookData() {
		allBooks.get(0).addCopy();
		allBooks.get(0).addCopy();
		allBooks.get(1).addCopy();
		allBooks.get(3).addCopy();
		allBooks.get(2).addCopy();
		allBooks.get(2).addCopy();
		DataAccessFacade.loadBookMap(allBooks);
	}

	public void userData() {
		DataAccessFacade.loadUserMap(allUsers);
	}

	// create library members
	public void libraryMemberData() {
		LibraryMember libraryMember = new LibraryMember("1001", "Andy", "Rogers", "641-223-2211", addresses.get(4));
		members.add(libraryMember);
		libraryMember = new LibraryMember("1002", "Drew", "Stevens", "702-998-2414", addresses.get(5));
		members.add(libraryMember);

		libraryMember = new LibraryMember("1003", "Sarah", "Eagleton", "451-234-8811", addresses.get(6));
		members.add(libraryMember);

		libraryMember = new LibraryMember("1004", "Ricardo", "Montalbahn", "641-472-2871", addresses.get(7));
		members.add(libraryMember);

		DataAccessFacade.loadMemberMap(members);
	}

	///////////// DATA //////////////
	List<LibraryMember> members = new ArrayList<LibraryMember>();
	@SuppressWarnings("serial")

	List<Address> addresses = new ArrayList<Address>() {
		{
			add(new Address("101 S. Main", "Fairfield", "IA", "52556"));
			add(new Address("51 S. George", "Georgetown", "MI", "65434"));
			add(new Address("23 Headley Ave", "Seville", "Georgia", "41234"));
			add(new Address("1 N. Baton", "Baton Rouge", "LA", "33556"));
			add(new Address("5001 Venice Dr.", "Los Angeles", "CA", "93736"));
			add(new Address("1435 Channing Ave", "Palo Alto", "CA", "94301"));
			add(new Address("42 Dogwood Dr.", "Fairfield", "IA", "52556"));
			add(new Address("501 Central", "Mountain View", "CA", "94707"));
		}
	};
	@SuppressWarnings("serial")
	public List<Author> allAuthors = new ArrayList<Author>() {
		{
			add(new Author("Joe", "Thomas", "641-445-2123", addresses.get(0), "A happy man is he."));
			add(new Author("Sandra", "Thomas", "641-445-2123", addresses.get(0), "A happy wife is she."));
			add(new Author("Nirmal", "Pugh", "641-919-3223", addresses.get(1), "Thinker of thoughts."));
			add(new Author("Andrew", "Cleveland", "976-445-2232", addresses.get(2), "Author of childrens' books."));
			add(new Author("Sarah", "Connor", "123-422-2663", addresses.get(3), "Known for her clever style."));
		}
	};

	@SuppressWarnings("serial")
	List<Book> allBooks = new ArrayList<Book>() {
		{
			add(new Book("23-11451", "The Big Fish", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
			add(new Book("28-12331", "Antartica", 7, Arrays.asList(allAuthors.get(2))));
			add(new Book("99-22223", "Thinking Java", 21, Arrays.asList(allAuthors.get(3))));
			add(new Book("48-56882", "Jimmy's First Day of School", 7, Arrays.asList(allAuthors.get(4))));
		}
	};

	@SuppressWarnings("serial")
	List<User> allUsers = new ArrayList<User>() {
		{
			add(new User("101", "xyz", Auth.LIBRARIAN));
			add(new User("102", "abc", Auth.ADMIN));
			add(new User("103", "111", Auth.BOTH));
		}
	};

	/**
	 * Creates the storage directory if it doesn't exist.
	 * I added this because I was getting an error when running the test.
	 */
	private static void createStorageDirectory() {
		File directory = new File(DataAccessFacade.OUTPUT_DIR);
		if (!directory.exists()) {
			boolean created = directory.mkdirs();
			if (created) {
				System.out.println("Storage directory created at: " + directory.getAbsolutePath());
			} else {
				System.err.println("Failed to create storage directory at: " + directory.getAbsolutePath());
			}
		} else {
			System.out.println("Storage directory already exists at: " + directory.getAbsolutePath());
		}
	}

	// feature #3: testing checkouts
	public void addTestCheckouts() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		HashMap<String, Book> books = da.readBooksMap();

		// Checkout for the first book (overdue)
		LibraryMember member1 = members.get("1001"); // Andy Rogers
		Book book1 = books.get("23-11451"); // The Big Fish

		if (member1 != null && book1 != null && book1.getCopies().length > 0) {
			BookCopy copy1 = book1.getCopy(1); // Get the first copy
			if (copy1 != null) {
				copy1.changeAvailability(); // Mark as checked out

				// Create an overdue checkout
				LocalDate checkoutDate1 = LocalDate.now().minusDays(21); // 3 weeks ago
				LocalDate dueDate1 = checkoutDate1.plusDays(7); // Due after 7 days
				Checkout checkout1 = new Checkout(member1, copy1, checkoutDate1, dueDate1);

				// Add to member's checkouts
				member1.addCheckout(checkout1);
				// Update member in storage
				da.updateMember(member1.getMemberId(), member1);
				System.out
						.println("Overdue checkout added for member " + member1.getMemberId() + " and book " + book1.getIsbn());
			}
		}

		// Checkout for the second book (not overdue)
		LibraryMember member2 = members.get("1002"); // Drew Stevens
		Book book2 = books.get("28-12331"); // Antartica

		if (member2 != null && book2 != null && book2.getCopies().length > 0) {
			BookCopy copy2 = book2.getCopy(1); // Get the first copy
			if (copy2 != null) {
				copy2.changeAvailability(); // Mark as checked out

				// Create a checkout that is not overdue
				LocalDate checkoutDate2 = LocalDate.now().minusDays(3); // 3 days ago
				LocalDate dueDate2 = checkoutDate2.plusDays(7); // Due in 7 days
				Checkout checkout2 = new Checkout(member2, copy2, checkoutDate2, dueDate2);

				// Add to member's checkouts
				member2.addCheckout(checkout2);
				// Update member in storage
				da.updateMember(member2.getMemberId(), member2);
				System.out.println("Checkout added for member " + member2.getMemberId() + " and book " + book2.getIsbn());
			}
		}
	}

}
