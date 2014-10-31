package controllers;

import models.*;
import play.data.Form;
import play.mvc.*;
import util.Secured;
import views.html.*;

@Security.Authenticated(Secured.class)
public class Admin extends Controller {
    
    public static Result admin() {
    	User user = User.getUser(session().get("email"));
    	if(user.getUserGroup().name.equals("admin")) return ok(admin.render(user));
    	else return redirect(routes.Application.index());
    }
    
    public static Result install() {
    	return ok(install.render(Form.form(Login.class)));
    }
    
    public static Result users() {
    	return ok(users.render(User.all(), Form.form(Login.class), "user"));
    }
    
    public static Result addUser(String group) {
    	Form<Login> userForm = Form.form(Login.class).bindFromRequest();
    	if(userForm.hasErrors()) return badRequest(users.render(User.all(), userForm, group));
    	else {
    		User user = new User(userForm.get());
    		user.setUserGroup(UserGroup.getUserGroup(group));
    		user.save();
    		return redirect(routes.Admin.users());
    	}
    }
    
    public static Result deleteUser(String email) {
    	User.deleteUser(email);
    	return redirect(routes.Admin.users());
    }
}