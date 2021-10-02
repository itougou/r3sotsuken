package model;

import java.util.List;

import dao.AddressBookDAO;

/*
 * アドレス帳検索ロジック
 */
public class SearchAddressBookLogic {
	public List<Address>  execute(String key_word ) {
		AddressBookDAO dao = new AddressBookDAO();
		List<Address> addressList = dao.search(key_word);
		return addressList;
	}
}
