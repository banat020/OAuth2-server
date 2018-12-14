package com.banling.oauth2server.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping("/user")
	public Principal user(Principal principal) {
		//principal在经过security拦截后，是org.springframework.security.authentication.UsernamePasswordAuthenticationToken
		//在经OAuth2拦截后，是OAuth2Authentication
	    return principal;
	}
	
}
