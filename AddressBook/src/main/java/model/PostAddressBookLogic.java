package model;

import dao.AddressBookDAO;

/*
 * アドレス帳登録ロジック
 */
public class PostAddressBookLogic {
	public void execute(Address address ) {
		AddressBookDAO dao = new AddressBookDAO();
		dao.create(address);
	}
}
