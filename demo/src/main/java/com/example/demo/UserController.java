package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/")
	public String index(Model model) {
		List<Map<String,Object>> list;	//キー（String型）と値（Object型）をセットで持つMAP型　を複数入れる　List型　
		list = jdbcTemplate.queryForList("select * from employee");
		model.addAttribute("users", list);
		return "users";
	}
}
