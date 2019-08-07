package com.banling.oauth2server.web;
import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ResController {
	
	@RequestMapping("/res/getMsg")
	public String getMsg(String msg,Principal principal) {//principal中封装了客户端（用户，也就是clientDetails，区别于Security的UserDetails，其实clientDetails中也封装了UserDetails），不是必须的参数，除非你想得到用户信息，才加上principal。
		return "Get the msg: "+msg;
	}
}
