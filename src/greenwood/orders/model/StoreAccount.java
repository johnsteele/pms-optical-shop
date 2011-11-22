/**
 * @author John Steele
 * @file   Store.java
 * @date   07/22/2011
 * 
 * <p>A class to represent a store account.</p>
 */
package greenwood.orders.model;

/**
 * <p>A class to represent a store account.</p>
 */
public class StoreAccount {
	
	/**
	 * The account number for the store.
	 */
	private int my_accountNumber;
	
	/**
	 * The name of the account.
	 */
	private String my_accountName;
	
	/**
	 * The mailing address of the store.
	 */
	private String my_street;
	private String my_city;
	private String my_state;
	private int my_zip;
	
	
	/**
	 * Creates a StoreAccount with the specified values.
	 * 
	 * @param accountNum The account number.
	 * @param accountName The name on the account.
	 * @param street The street address of the store.
	 * @param city The city of the store address.
	 * @param state The state the city is located in.
	 * @param zip The store's zipcode.
	 */
	public StoreAccount (int accountNum, String accountName, String street, 
							String city, String state, int zip) {
		
		this.my_accountNumber = accountNum;
		this.my_accountName   = accountName;
		this.my_street        = street;
		this.my_city          = city;
		this.my_state         = state;
		this.my_zip           = zip;		
	}
	
	
	/**
	 * Returns the account number.
	 * 
	 * @return The account number.
	 */
	public int getAccountNumber () {
		return my_accountNumber;
	}
	
	
	/**
	 * Returns the account name.
	 * 
	 * @return The account name.
	 */
	public String getAccountName () {
		return my_accountName;
	}
	
	
	/**
	 * Returns the street address.
	 * 
	 * @return The street address.
	 */
	public String getStreetAddress () {
		return my_street;
	}
	
	
	/**
	 * Returns the address city.
	 * 
	 * @return The address city.
	 */
	public String getCity () {
		return my_city;
	}
	
	
	/**
	 * Returns the address state.
	 * 
	 * @return The address state.
	 */
	public String getState () {
		return my_state;
	}
	
	
	/**
	 * Returns the address zipcode.
	 * 
	 * @return The address zipcode.
	 */
	public int getZipcode () {
		return my_zip;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(my_accountName);
		buff.append(" - ");
		buff.append(my_street);		
		buff.append("  ");
		buff.append(my_city);
		buff.append(", ");
		buff.append(my_state);
		buff.append(" ");
		buff.append(my_zip);
		return buff.toString();
	}

}
