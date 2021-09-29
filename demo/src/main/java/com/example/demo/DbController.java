package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/jdbc/sample")
public class DbController {

   @Autowired
   JdbcTemplate jdbcTemplate;

   @RequestMapping(path="/users", method=RequestMethod.GET)
   public String index() {	//インデックス
       List<Map<String,Object>> list;
       list = jdbcTemplate.queryForList("select * from employee");
       return list.toString();//戻り値返却
   }

   @RequestMapping(path="/users/{id}", method=RequestMethod.GET)
   public String read(@PathVariable String id) {
       List<Map<String,Object>> list;
       list = jdbcTemplate.queryForList("select * from employee where id = ?", id);
       return list.toString();
   }
}