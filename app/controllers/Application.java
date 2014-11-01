package controllers;

import models.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(News.all(), User.getNick(session().get("email"))));
    }
    
    public static Result error(String path) {
    	return ok(err.render(path, User.getNick(session().get("email"))));
    }
}
