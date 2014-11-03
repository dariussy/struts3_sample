Template de projet : Struts 2 / Spring
============================

Début de projet avec Struts et spring

##Configuration pom.xml : 
```
 <dependencies>
    <dependency>
      <groupId>org.apache.struts</groupId>
      <artifactId>struts2-core</artifactId>
      <version>2.3.15.1</version>
      <scope>compile</scope>
    </dependency>

<!--Gestion des annotations-->
    <dependency>
      <groupId>org.apache.struts</groupId>
      <artifactId>struts2-convention-plugin</artifactId>
      <version>2.3.15.1</version>
      <scope>compile</scope>
    </dependency>
 <dependencies>
```
##Integration dans web.xml :

```

	<filter>
		<filter-name>struts2</filter-name>
		<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
	</filter>

	<filter-mapping>
		<filter-name>struts2</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```

→ Emplacement fichier struts.xml (configuration) sur emplacement par défaut 

##Configuration Struts : src/main/resources/struts.xml : 
```
<struts>

<!-- 	Utilisation de constantes struts :
	configuration dev activé-->
 	<constant name="struts.devMode" value="true" />

	<!-- configuration de l internationalisation-->
 	<constant name="struts.custom.i18n.resources" value="i18n/messages" />
        
<!--         <package name="default" namespace="/pages" extends="struts-default"> -->
            
<!--             employeeAction is a spring bean -->
<!--             <action name="addEmployee" class="employeeAction" method="addEmployee" > -->
<!--                 <result name="success">/pages/success.jsp?action=addemployee</result> -->
<!--                 <result name="input">/pages/AddEmployee.jsp</result> -->
<!--             </action> -->
<!--             <action name="listEmployees" class="employeeAction" method="listEmployees"> -->
<!--                 <result name="success">/pages/AddEmployee.jsp</result> -->
<!--             </action> -->
            
<!--             Handles localisation changes -->
<!--             <action name="locale" class="s2sh.action.LocaleAction"> -->
<!--                 <result name="redirect">${url}</result> -->
<!--             </action> -->
            
<!--         </package> -->
	
</struts>
```
##Configuration par annotations des controlleur Struts : 
```
@Component(value = "employeeAction")
// l'objet est créé à chaque appel → liée struts attributs avec données membres
@Scope("prototype")
@Namespace("/employee")
public class EmployeeAction extends ActionSupport implements
		ModelDriven<Employee> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmployeeService employeeService;
	

	// Données membres
	private Employee employee = new Employee();
	private List<Employee> employees = new ArrayList<>();

	@Override
	public Employee getModel() {
		return employee;
	}

	public void validate() {

		if (getEmployee().getFirstname().trim().length() == 0) {
			addFieldError("firstname", "First name is required.");
		}

	}
	//	Configuration des urls
	@Action(value = "addAction" ,results = {
			@Result(name = "success", type = "redirectAction", location = "listAction.action"),
			@Result(name = "input", location = "/employee/add.jsp") })
	public String addEmployee() throws Exception {
		System.out.println("ADD EMPLOYEE");

		System.out.println(employee);
		
		employeeService.add(employee);

		return "success";
	}
```
#Taglib et JSP
```
<%@ taglib prefix="s" uri="/struts-tags" %>
```

###list.jsp : 
```
	<table border="1px" cellpadding="8px">
		<tr>
			<th>ID</th>
			<th>First name</th>
			<th>Last name</th>
			<th>Email</th>
			<th>Telephone</th>
		</tr>
		<s:iterator value="employees">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="firstname" /></td>
				<td><s:property value="lastname" /></td>
				<td><s:property value="email" /></td>
				<td><s:property value="telephone" /></td>
			</tr>
		</s:iterator>
	</table>
```
###add.jsp : 
```
 	<s:actionerror/>
        
        <s:form action="addAction" >
            <s:textfield name="firstname" label="First Name"  />
            <s:textfield name="lastname" label="Last Name"  />
            <s:textfield name="email" label="Email" />
            <s:textfield name="telephone" label="Telephone"  />
            <s:submit />
        </s:form>
```

Dans les controlleurs si la methode validates est déclaré elle est applé sur tous les fonctions du dit controlleur d'ou l'annotation pour l'esiquiver : 
```
	@Action(value = "listAction", results = { @Result(name = "success", location = "/employee/list.jsp") })
	@SkipValidation
	public String listEmployees() throws Exception {
		employees = employeeService.getAll();

		return "success";
	}
```

##Validation Struts :

	###Integration d'un plugin  pour hibernate-validator : https://code.google.com/p/full-hibernate-plugin-for-struts2/wiki/2_Hibernate_Validator_integration
		avec notamment ```<interceptor-ref name="basicStackHibernate" />```

	###Intégration des validations à travers un fichier xml :  https://cwiki.apache.org/confluence/display/WW/Validation
		→ Possibilité de génerer du code javascripte pour la création du formulaire : mais pensé a ajouté la ```< s:head/>```
		→ Attention a l'intégration de la dtd pour validation sans connnection ne fonctionne pas : du à :
			```<!DOCTYPE validators PUBLIC "-//Apache Struts//XWork Validator 1.0.3//EN"
        "http://struts.apache.org/dtds/xwork-validator-1.0.3.dtd">```

#Intercepteur : 

##Creation d'un interceptor : 
```
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
```

Configuration struts.xml :
```
	<package name="secure" extends="struts-default">
 		<interceptors>
 			<interceptor name="LoginInterceptor" class="fr.treeptik.interceptor.LoginInterceptor"/>
 			
 			<interceptor-stack name="secureStack">
 				<interceptor-ref name="LoginInterceptor"/>
 				<interceptor-ref name="defaultStack"/>
 			</interceptor-stack>
 			
 		</interceptors>
 		<default-interceptor-ref name="secureStack"/>
		<global-results>
 			<result name="login">/login.jsp</result>
 		</global-results>

 	</package>
```

		






















