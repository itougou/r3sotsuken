package model;

import java.util.List;

import dao.AddressBookDAO;

/*
 * アドレス帳取得ロジック
 */
public class GetAddressBookListLogic {
	public List<Address> execute(){
		AddressBookDAO dao=new AddressBookDAO();
		List<Address> addressBookList = dao.findAll();
		return addressBookList;
	}
}
