package controllers;

import models.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Auth extends Controller {
	
	// Страница формы для аутентификации
    public static Result login() {
    	if(session("email") != null) return redirect(routes.Application.index());
    	else return ok(login.render(Form.form(Login.class), ""));
    }
    // Обработка формы аутентификации
    public static Result authenticate() {
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    	if(loginForm.hasErrors()) return badRequest(login.render(loginForm, ""));
    	else if(loginForm.get().validate() != null) {
    		session("email", loginForm.get().email);
    		return redirect(routes.Application.index());
    	} else return badRequest(login.render(loginForm, "failed"));
    }
    
    // Выход и очистка сессии
    public static Result logout() {
    	session().clear();
    	flash("success", "Вы успешно разлогинились");
    	return redirect(routes.Auth.login()); 
    }
    
    public static Result signup() {
    	return ok(register.render(Form.form(Login.class)));
    }
    
    public static Result register() {
    	Form<Login> regForm = Form.form(Login.class).bindFromRequest();
    	if(regForm.hasErrors()) return badRequest(register.render(regForm));
    	else {
    		User.create(new User(regForm.get()));
    		session("email", regForm.get().email);
    		return redirect(routes.Application.index());
    	}
    }
}
