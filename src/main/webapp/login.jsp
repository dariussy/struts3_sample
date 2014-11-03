<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    	<s:head/>
    <body>
        <h1>Login</h1>
       
       <s:actionerror/>
       <s:form action="/login">
       		<s:textfield name="user.name" label="User name "/>
       		<s:password name="user.password" label="User password "/>
       		<s:submit/>
       
       </s:form>
        
    </body>
</html>
