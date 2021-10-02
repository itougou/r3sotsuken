package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Address;
import model.GetAddressBookListLogic;
import model.PostAddressBookLogic;
import model.SearchAddressBookLogic;

/**
 *  0521課題　アドレス帳Webアプリ　コントローラ
 */
@WebServlet("/AddressBookController")
public class AddressBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * doGetメソッド
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPath = "";	//フォワード先

		String action = request.getParameter("action");	//動作ﾊﾟﾗﾒｰﾀ取得
		
		if( action == null ) {	//一覧表示のﾘｸｴｽﾄの場合
			forwardPath="/WEB-INF/jsp/list.jsp";
			//アドレス帳リストの取り出し＆設定
			GetAddressBookListLogic getAddressBookListLogic = new GetAddressBookListLogic();
			List<Address> addressList = getAddressBookListLogic.execute();
			request.setAttribute("addressList", addressList);
			//検索キーワード空欄設定
			request.setAttribute("key_word", "");
			
		}else if( action.equals("form") ) {	//入力画面表示のﾘｸｴｽﾄの場合
			forwardPath="/WEB-INF/jsp/form.jsp";
		}
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward( request, response );
	}
	/*
	 * doPostメソッド
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//リクエストパラメータ取り出し
		String name = request.getParameter("name");
		String addr = request.getParameter("address");
		String tel = request.getParameter("tel");
		String action = request.getParameter("action");
		String key_word = request.getParameter("key_word");
		
		List<Address> addressList;	//アドレス帳リスト
		
		if(action==null) {	//登録実行の場合
			//Addressﾋﾞｰﾝｽﾞ生成
			Address address = new Address(name,addr,tel);
			
			//DBへ1件登録する処理
			PostAddressBookLogic postAddressBookLogic = new PostAddressBookLogic();
			postAddressBookLogic.execute( address );
			//DBから全データを読みだす処理
			GetAddressBookListLogic getAddressBookListLogic = new GetAddressBookListLogic();
			addressList = getAddressBookListLogic.execute();
			//ﾘｸｴｽﾄｽｺｰﾌﾟへ全ｱﾄﾞﾚｽ帳ﾃﾞｰﾀを格納
			request.setAttribute( "addressList", addressList );
		}else if(action.equals("search")) {	//検索の実行の場合
			//検索を実行
			SearchAddressBookLogic searchAddressBookLogic = new SearchAddressBookLogic();
			addressList = searchAddressBookLogic.execute( key_word );
			//ﾘｸｴｽﾄｽｺｰﾌﾟへ検索されたｱﾄﾞﾚｽ帳ﾃﾞｰﾀを格納
			request.setAttribute( "addressList", addressList );
		}
		//ﾘｸｴｽﾄｽｺｰﾌﾟﾍｷｰﾜｰﾄﾞを格納
		request.setAttribute("key_word", key_word );
		
		String forwardPath="/WEB-INF/jsp/list.jsp";
		
		//ｱﾄﾞﾚｽ帳一覧表JSPへﾌｫﾜｰﾄﾞ
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward( request, response );
	}

}
