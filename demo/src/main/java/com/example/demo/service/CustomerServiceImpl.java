package com.example.demo.service;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.Customer;
import com.example.demo.mail.MailTool;
import com.example.demo.repository.CustomerMapper;

/**
 * 顧客情報 Service　（処理ロジック）　クラス　
 */
@Service
public class CustomerServiceImpl implements CustomerService{
    /**
     * 顧客情報 マッパー　クラス
     */
    @Autowired
    private CustomerMapper customerMapper;
    /**
     * メール　クラス
     */
    @Autowired
    private MailTool mailTool;
    //検索
    @Override
    //public Customer search( CustomerSearchRequest customerSearchRequest) {
    public Customer searchById( int id ) {
        //return customerMapper.search( customerSearchRequest );
        return customerMapper.searchById( id );
    }
    //検索( sessionid )
    @Override
    public Customer findBySession(String sessionId) {
        return customerMapper.findBySession( sessionId );
    }
    //データ1件追加
    @Override
    public void add( Customer customerAddRequest ) {
    	customerMapper.add( customerAddRequest );
    }
    
    //全件読み出し
    @Override
    public List<Customer> getCustomer(){
    	return customerMapper.findMany();
    }    
    
    //ログイン（ 成功時は、cookieとcustomerテーブルへセッションID登録）
    @Override
    public boolean login( LoginRequest loginRequest , HttpServletResponse response ){
    	Customer c = customerMapper.searchByIdPass( Integer.parseInt( loginRequest.getId() ), loginRequest.getPass() );
    						//customerテーブルから ID、Pass が一致するレコード取得
    	if( c != null ) {	//IDとパスワード一致するレコードありの場合
    		
    		String authCode = RandomStringUtils.randomNumeric(6);	//6桁のランダムな数字文字列生成
    		System.out.println( "login authCode:"+authCode );
    		//メール送信
    		mailTool.send("出席管理システム 認証コード："+authCode,"出席管理システム 認証コードは、\n"+authCode+"\nです。");
        	customerMapper.setAuthCode( Integer.parseInt( loginRequest.getId() ), authCode );//CUSTOMERテーブルにセッションID登録


        	return true;	//ログイン成功
    	}else {
    		return false;	//ログイン失敗
    	}

    }
    //認証コードチェック
    @Override
    public boolean authCheck( AuthRequest authRequest , HttpServletResponse response ){

    	Customer c = customerMapper.searchByIdAuthCode(Integer.parseInt( authRequest.getId()),authRequest.getAuthCode() );
    	System.out.println("authCheck customer="+c);
    	System.out.println("authCheck authRequest="+authRequest);
    	if( c != null ) {//IDと認証コードありの場合
			//System.out.println("login searchByIdPass=> ID:"+c.getId()+","+ "PASS"+c.getPass());
			//セッションIDをランダムに生成
			String rs = RandomStringUtils.randomAlphanumeric(32);	//32桁のランダムな英数文字列生成
			System.out.println( "authChec searchByIdPass=> random String:"+rs );
	    	String sessionId = "ATTENDANCE"+rs;	//セッションID文字列生成
		    Cookie cookie = new Cookie( "customerSessionId", sessionId );	//cookieにセッションIDを登録
		    cookie.setMaxAge( 365*24*60*60 );	//有効期限 365日の秒数 に設定
		    response.addCookie( cookie );	//cookie追加
	    	customerMapper.setSession( Integer.parseInt( authRequest.getId() ), sessionId );//CUSTOMERテーブルにセッションID登録
	    	return true;
    	}else {
    		return false;
    	}
    	
    }
    //ログアウト（ cookieとcustomerテーブルからセッションID削除 ）
    @Override
    public boolean logout( HttpServletRequest request , HttpServletResponse response ){
		String sessionId="";
	    Cookie cookies[] = request.getCookies();
	    for (Cookie cookie : cookies) {
	        if ( cookie.getName().equals("customerSessionId") ) {	//クッキーの中からcustomerSessionIdを探す
	             cookie.setMaxAge(0);//有効期限：0に設定　= すぐに消去される
	             response.addCookie(cookie);
	             sessionId = cookie.getValue();	//セッションIDを取り出す
	        }
	    }
	    System.out.println("★ Customer logout removeSession() sessionId（cookie）＝"+sessionId);
    	customerMapper.removeSession( sessionId );	//customerテーブルのセッションIDを消去
    	return true;
    }
}