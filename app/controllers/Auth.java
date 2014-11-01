package controllers;

import models.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Auth extends Controller {
	
	// Страница формы для аутентификации
    public static Result login() {
    	if(session("email") != null) return redirect(routes.Application.index());
    	else return ok(login.render(Form.form(Login.class).fill(new Login()), ""));
    }
    
    // Обработка формы аутентификации
    public static Result auth() {
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    	if(loginForm.hasErrors()) return badRequest(login.render(loginForm, ""));
    	else {
    		if(loginForm.get().validateUser() != null) {
    			session("email", loginForm.get().email);
    			return redirect(routes.Application.index());
    		} else return badRequest(login.render(loginForm, "failed"));
    	}
    }
    
    // Выход и очистка сессии
    public static Result logout() {
    	session().clear();
    	return redirect(routes.Application.index()); 
    }
    
    // Страница регистрации
    public static Result signup() {
    	if(session("email") != null) return redirect(routes.Application.index());
    	else return ok(register.render(Form.form(Login.class).fill(new Login()), ""));
    }
    
    // Регистрация
    public static Result register() {
    	Form<Login> regForm = Form.form(Login.class).bindFromRequest();
    	if(regForm.hasErrors()) return badRequest(register.render(regForm, ""));
    	else {
    		String exists = User.exists(regForm.get());
    		if(exists == null) {
    			(new User(regForm.get())).save();
    			session("email", regForm.get().email);
    			return redirect(routes.Application.index());
    		} else return badRequest(register.render(regForm, exists));
    	}
    }
}
