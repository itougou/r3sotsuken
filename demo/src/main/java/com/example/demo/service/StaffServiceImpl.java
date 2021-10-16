package com.example.demo.service;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.Staff;
import com.example.demo.repository.StaffMapper;

/**
 * 顧客情報 Service　（処理ロジック）　クラス　
 */
@Service
public class StaffServiceImpl implements StaffService{
    /**
     * 顧客情報 サービス　クラス
     */
    @Autowired
    private StaffMapper staffMapper;
    
	@Autowired
	HttpSession session;  
    
    //ログイン（ 成功時は、セッションスコープへログイン情報登録）
    @Override
    public boolean login( LoginRequest loginRequest , Model model ){
		Staff staff = staffMapper.findByIdPass( Integer.parseInt(loginRequest.getId()), loginRequest.getPass() );	//顧客ログイン（セッション登録処理）ロジック
		if( staff!=null ) {
			System.out.println("staffMapper findByIdPass result staff="+staff.getId()+" , "+staff.getPass());
		    session.setAttribute( "loginInfo", loginRequest );	//セッションスコープへログイン情報セット
		    return true;
		}else {
		    return false;
		}

    }
    //ログアウト（セッションスコープ削除）
    @Override
    public boolean logout( ){
		session.invalidate();
    	return true;
    }
}