package controllers;

import models.*;
import play.data.Form;
import play.mvc.*;
import util.Secured;
import views.html.*;

@Security.Authenticated(Secured.class)
public class Admin extends Controller {
	
	static Form<News> newsForm = Form.form(News.class);
    
	// Страница добавления новости
    public static Result addNews() {
    	return ok(add.render(newsForm, User.getNick(session().get("email"))));
    }
    
    // Добавить новость
    public static Result add() {
		Form<News> filledForm = newsForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(add.render(filledForm, User.getNick(session().get("email"))));
		} else {
			News.create(filledForm.get());
			return redirect(routes.Application.index());
		}
    }
    
    // Страница редактирования новости
    public static Result openNews(Long id) {
    	return ok(edit.render(newsForm.fill(News.byId(id)), User.getNick(session().get("email"))));
    }
    
    // Редактировать новость
    public static Result edit(String id) {
		Form<News> filledForm = newsForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(edit.render(filledForm, User.getNick(session().get("email"))));
		} else {
			News.update(filledForm.get());
			return redirect(routes.Application.index());
		}
    }
}