package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private List<Checkout> checkouts;

	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.checkouts = new ArrayList<Checkout>();
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	public void addCheckout(Checkout checkout) {
		this.checkouts.add(checkout);
	}

	public List<Checkout> getCheckouts() {
		return Collections.unmodifiableList(checkouts);
	}
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
