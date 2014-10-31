package controllers;

import models.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Auth extends Controller {
	
	// Страница формы для аутентификации
    public static Result login() {
    	if(session("email") != null) return redirect(routes.Application.index());
    	else return ok(login.render(Form.form(Login.class).fill(new Login()), "", ""));
    }
    
	// Страница формы для аутентификации (админка, установка)
    public static Result loginPage(String page) {
    	Login l = new Login(); l.sgroup = "admin";
    	return ok(login.render(Form.form(Login.class).fill(l), "", page));
    }
    
    // Обработка формы аутентификации
    public static Result auth() {
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    	if(loginForm.hasErrors()) return badRequest(login.render(loginForm, "", ""));
    	else if(loginForm.get().validate() != null) {
    		session("email", loginForm.get().email);
    		return redirect(routes.Application.index());
    	} else return badRequest(login.render(loginForm, "failed", ""));
    }
    
    // Обработка формы аутентификации (админка, установка)
    public static Result authPage(String page) {
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    	if(loginForm.hasErrors()) return badRequest(login.render(loginForm, "", page));
    	else if(loginForm.get().validate() != null) {
    		session("email", loginForm.get().email);
    		if(page.equals("admin")) return redirect(routes.Admin.admin());
    		if(page.equals("install")) return redirect(routes.Admin.install());
    		return redirect(routes.Application.error(page));
    	} else return badRequest(login.render(loginForm, "failed", page));
    }
    
    // Выход и очистка сессии
    public static Result logout() {
    	session().clear();
    	flash("success", "Вы успешно разлогинились");
    	return redirect(routes.Auth.login()); 
    }
    
    // Страница регистрации
    public static Result signup() {
    	return ok(register.render(Form.form(Login.class).fill(new Login())));
    }
    
    // Регистрация
    public static Result register() {
    	Form<Login> regForm = Form.form(Login.class).bindFromRequest();
    	if(regForm.hasErrors()) return badRequest(register.render(regForm));
    	else {
    		(new User(regForm.get())).save();
    		session("email", regForm.get().email);
    		return redirect(routes.Application.index());
    	}
    }
}
