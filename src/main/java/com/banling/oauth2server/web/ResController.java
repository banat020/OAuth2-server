package com.banling.oauth2server.web;
import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作为OAuth2的资源服务时，不能在Controller(或者RestController)注解上写上URL，因为这样不会被识别，会报404错误。<br>
 *<br> {
 *<br>     "timestamp": 1544580859138,
 *<br>     "status": 404,
 *<br>     "error": "Not Found",
 *<br>     "message": "No message available",
 *<br>     "path": "/res/getMsg"
 *<br> }
 *<br>
 * @author LLB
 *
 */
@RestController()//作为资源服务时，不能带上url，@RestController("/res")是错的，无法识别。只能在方法上注解全路径
public class ResController {
	
	@RequestMapping("/res/getMsg")
	public String getMsg(String msg,Principal principal) {//principal中封装了客户端（用户，也就是clientDetails，区别于Security的UserDetails，其实clientDetails中也封装了UserDetails），不是必须的参数，除非你想得到用户信息，才加上principal。
		return "Get the msg: "+msg;
	}
}
