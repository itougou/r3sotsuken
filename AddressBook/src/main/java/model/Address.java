package model;

import java.io.Serializable;
/*
 * アドレス帳ビーンズ
 */
public class Address implements Serializable{
	//コンストラクタ
	public Address() {}
	public Address( String name, String address, String tel ) {
		this.name = name;
		this.address = address;
		this.tel = tel;
	}
	
	private String name;	//名前
	private String address;	//住所
	private String tel;	//電話番号
	
	//ゲッター
	public String getName() { return name; 	}
	public String getAddress() { return address; }
	public String getTel() { return tel; }
	
	//セッター
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	

	
}
