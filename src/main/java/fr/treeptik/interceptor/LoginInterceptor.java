package fr.treeptik.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import fr.treeptik.entity.User;

public class LoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		Map<String, Object> session = invocation.getInvocationContext()
				.getSession();

		User user = (User) session.get("user");

		if (user == null) {
			return "login";
		}
		System.out.println("invoke interceptor : " + user);
		return invocation.invoke();
	}

}
