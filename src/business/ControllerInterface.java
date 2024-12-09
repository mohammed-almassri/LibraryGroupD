package business;

import java.util.List;

import dataaccess.User;

public interface ControllerInterface {
	void login(String id, String password) throws LoginException;
	void logout();
	List<String> allMemberIds();
	List<String> allBookIds();
	User getCurrentUser();
	void addNewMember(LibraryMember member) throws Exception;
}
