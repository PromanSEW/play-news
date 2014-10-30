package controllers;

import play.mvc.*;
import util.Secured;

@Security.Authenticated(Secured.class)
public class Admin extends Controller {
	
}