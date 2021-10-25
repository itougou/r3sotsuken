package com.example.demo.service;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    public boolean login( LoginRequest loginRequest , HttpServletRequest request, HttpServletResponse response ){
    	Customer c = customerMapper.searchByIdPass( Integer.parseInt( loginRequest.getId() ), loginRequest.getPass() );
    						//customerテーブルから ID、Pass が一致するレコード取得
    	if( c != null ) {	//IDとパスワード一致するレコードありの場合
    		
    		String authCode = RandomStringUtils.randomNumeric(6);	//6桁のランダムな数字文字列生成
    		System.out.println( "login authCode:"+authCode );
    		//メール送信
    		String title="出席管理システム 認証コード："+authCode;
    		String body="出席管理システム 認証コードは、\n"+authCode+"\nです。";
    		mailTool.send( title, body );
    		customerMapper.setAuthCode( Integer.parseInt( loginRequest.getId() ), authCode );//CUSTOMERテーブルに認証コード登録
    		Date authTime = new Date();
    		customerMapper.setAuthTime( Integer.parseInt( loginRequest.getId() ), authTime );//CUSTOMERテーブルに認証時刻登録
    		
    		HttpSession session = request.getSession(true);	//セッションスコープ生成
    		System.out.println("★CustomerServiceImpl login() session:"+session);
    		session.setAttribute("loginCustomer", c );   	//セッションスコープにログイン成功した顧客のエンティティ保存	

    		return true;	//ログイン成功
    	}else {
    		return false;	//ログイン失敗
    	}

    }
    //認証コードチェック
    @Override
    public boolean authCheck( AuthRequest authRequest , HttpServletRequest request, HttpServletResponse response ){

		HttpSession session = request.getSession(false);	//セッションスコープ取り出し
		System.out.println("★CustomerServiceImpl auｔｈCheck() session:"+session);
   		if( session == null ) {
   			return false;	//処理エラー（セッション切れなど）
   		}
		Customer authCustomer = (Customer)session.getAttribute("loginCustomer" );   	//セッションスコープからログイン中の顧客エンティティ取り出し	
		
		Customer c = customerMapper.searchByIdPassAuthCode( authCustomer.getId(), authCustomer.getPass(), authRequest.getAuthCode() );
						//セッションスコープの「ID」、「パスワード」、入力した「認証コード」で顧客テーブル検索
		System.out.println("authCheck customer="+c);
		System.out.println("authCheck authRequest="+authRequest);
		if( c != null ) {//ID、パスワード、認証コード一致するレコードありの場合
			//System.out.println("login searchByIdPass=> ID:"+c.getId()+","+ "PASS"+c.getPass());
			
			Date nowTime = new Date();	//現在時刻巣h得
			Date authTime = c.getAuthTime();	//Customerテーブルから認証コード送信時刻取り出し
			long diff = nowTime.getTime() - authTime.getTime() ;//認証コードmail送信後、現在までのミリ秒数算出 
			if ( diff > 3*60*1000 ){	//3分を超えている場合タイムアウト
				System.out.println("タイムアウト："+diff);
				customerMapper.setAuthCode( Integer.parseInt( authRequest.getId() ), "" );//CUSTOMERテーブルの認証コード消去
				customerMapper.setAuthTime( Integer.parseInt( authRequest.getId() ), null );//CUSTOMERテーブルの認証時刻消去
				return false;	//認証失敗
			}

			//セッションIDをランダムに生成
			String rs = RandomStringUtils.randomAlphanumeric(32);	//32桁のランダムな英数文字列生成
			System.out.println( "authChec searchByIdPass=> random String:"+rs );
			String sessionId = "ATTENDANCE"+rs;	//セッションID文字列生成
			Cookie cookie = new Cookie( "customerSessionId", sessionId );	//cookieにセッションIDを登録
			cookie.setMaxAge( 365*24*60*60 );	//有効期限 365日の秒数 に設定
			response.addCookie( cookie );	//cookie追加
			customerMapper.setSession( Integer.parseInt( authRequest.getId() ), sessionId );//CUSTOMERテーブルにセッションID登録
			customerMapper.setAuthTime( Integer.parseInt( authRequest.getId() ), authTime );//CUSTOMERテーブルに認証時刻登録
			customerMapper.setAuthCode( Integer.parseInt( authRequest.getId() ), "" );//CUSTOMERテーブルの認証コード消去
			return true;	//認証成功
		}else {
			return false;	//認証失敗
		}
    	
   	}
 	//ログアウト（ cookieとcustomerテーブルからセッションID削除 ）
  	@Override
   	public boolean logout( HttpServletRequest request , HttpServletResponse response ){
		String sessionId="";
		Cookie cookies[] = request.getCookies();
		if( cookies == null ) {	//cokkie存在しない場合
			return true;	//何もせず終了
		}
		for ( Cookie cookie : cookies ) {
			if ( cookie.getName().equals("customerSessionId") ) {	//クッキーの中からcustomerSessionIdを探す
				sessionId = cookie.getValue();	//セッションIDを取り出す
				customerMapper.removeSession( sessionId );	//customerテーブルのセッションID,認証時刻を消去
				System.out.println("★ Customer logout removeSession() sessionId（cookie）＝"+sessionId);
				cookie.setMaxAge(0);//有効期限：0に設定　= すぐに消去される
				response.addCookie(cookie);
			}
		}
		HttpSession session = request.getSession(false);	//セッションスコープ取り出し
		if(session != null) session.invalidate();	//セッションスコープ破棄
		return true;
    }
}