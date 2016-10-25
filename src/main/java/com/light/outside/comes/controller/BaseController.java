package com.light.outside.comes.controller;

import com.light.outside.comes.model.admin.UsersModel;
import com.light.outside.comes.qbkl.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * B3ST9U
 */
@SuppressWarnings("unchecked")
public class BaseController {
	
	@Autowired
	private HttpSession session;
	
	public static final String SESSION_KEY_USERINFO = "sys_user";
	public static final String SESSION_KEY_APP_USERINFO = "app_user";
	
	protected void setSession(String key, Object value){
		session.setAttribute(key, value);
	}
	
	protected Object getSession(String key){
		return session.getAttribute(key);
	}


	/**
	 *后端用户
	 */
	public UsersModel getUserInfo(){
		Object obj = session.getAttribute(SESSION_KEY_USERINFO);
		if(obj != null){
			return (UsersModel)obj;
		}else{
			return null;
		}
	}

	/**
	 * 前端登录用户
	 * @return
	 */
	public UserModel getAppUserInfo(){
		Object obj = session.getAttribute(SESSION_KEY_APP_USERINFO);
		if(obj != null){
			return (UserModel)obj;
		}else{
			return null;
		}
	}

	/**
	 *
	 * @param response
	 * @param strMsg
	 */
	protected void responseSendMsg(HttpServletResponse response, String strMsg) {
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(strMsg);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
