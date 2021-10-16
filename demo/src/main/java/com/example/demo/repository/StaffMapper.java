package com.example.demo.repository;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Staff;
/**
 *スタッフ情報 Mapper  （STAFFテーブルのアクセスとJavaのロジックを結びつけるクラス）
 */
@Mapper
public interface StaffMapper {

    Staff findByIdPass( int id, String pass );

}