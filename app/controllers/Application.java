package controllers;

import models.User;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(User.getNick(session().get("email"))));
    }
    
    public static Result news() {
    	return ok(news.render(User.getNick(session().get("email"))));
    }
    
    public static Result error(String path) {
    	return ok(err.render(path));
    }
}
