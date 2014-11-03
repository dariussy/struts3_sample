package fr.treeptik.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import fr.treeptik.entity.User;

@Action(value = "login", results = {
		@Result(name = "success", type = "redirectAction", location = "employee/listAction.action"),
		@Result(name = "input", location = "/login.jsp") })
public class LoginAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;

	User user = new User();

	@Override
	public String execute() throws Exception {
		System.out.println(user);
		session.put("user", user);
		return SUCCESS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
