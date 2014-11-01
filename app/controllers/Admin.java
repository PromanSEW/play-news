package controllers;

import models.*;
import play.mvc.*;
import util.Secured;
import views.html.*;

@Security.Authenticated(Secured.class)
public class Admin extends Controller {
    
    public static Result admin() {
    	return ok(admin.render(User.getNick(session().get("email"))));
    }
}