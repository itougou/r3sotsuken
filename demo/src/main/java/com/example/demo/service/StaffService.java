package com.example.demo.service;
import org.springframework.ui.Model;

import com.example.demo.dto.LoginRequest;
/**
 * 顧客情報 サービス　クラス　用インターフェース
 */
public interface StaffService {

    boolean login( LoginRequest loginRequest , Model model );
    boolean logout( );
}