package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Address;
/*
 * ADDRESSBOOKテーブルを担当するDAOｸﾗｽ
 */
public class AddressBookDAO {
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/example?useSSL=false";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";
	/*
	 * アドレス帳の全データを読み出す
	 */
	public List<Address> findAll(){
		List<Address> addressList = new ArrayList<>();
		
		try( Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			String sql = "SELECT ID,NAME,ADDRESS,TEL FROM ADDRESSBOOK ORDER BY ID DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();
			
			while( rs.next() ) {
				String name = rs.getString("NAME");
				String addr = rs.getString("ADDRESS");
				String tel = rs.getString("TEL");
				Address address = new Address(name,addr,tel);
				addressList.add(address);
			}
		}catch( SQLException e ) {
			e.printStackTrace();
			return null;
		}
		return addressList;
	}
	/*
	 * アドレス帳のデータを検索する
	 */
	public List<Address> search( String keyword ){
		List<Address> addressList = new ArrayList<>();
		
		try( Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			String sql = "SELECT ID,NAME,ADDRESS,TEL FROM ADDRESSBOOK WHERE NAME LIKE ? OR ADDRESS LIKE ? OR TEL LIKE ? ORDER BY ID DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, "%"+keyword+"%" );
			pStmt.setString(2, "%"+keyword+"%" );
			pStmt.setString(3, "%"+keyword+"%" );
			System.out.println ("★実行されるsql文"+pStmt.toString() );
			ResultSet rs = pStmt.executeQuery();
			
			while( rs.next() ) {
				String name = rs.getString("NAME");
				String addr = rs.getString("ADDRESS");
				String tel = rs.getString("TEL");
				Address address = new Address(name,addr,tel);
				addressList.add(address);
			}
		}catch( SQLException e ) {
			e.printStackTrace();
			return null;
		}
		return addressList;
	}
	/*
	 * アドレス帳データをテーブルへ1件追加
	 */
	public boolean create( Address address ) {
		try( Connection conn = DriverManager.getConnection( JDBC_URL, DB_USER, DB_PASS ) ){
			String sql = "INSERT INTO ADDRESSBOOK (NAME,ADDRESS,TEL) VALUES(?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, address.getName());
			pStmt.setString(2, address.getAddress());
			pStmt.setString(3, address.getTel());
			int result = pStmt.executeUpdate();
			if( result != 1 ) {
				return false;
			}
		}catch( SQLException e ) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
