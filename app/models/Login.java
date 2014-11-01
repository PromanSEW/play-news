package models;

import javax.persistence.Id;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

public class Login extends Model {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Email
	@Required
	public String email;
	
	@Required
	public String password;
	
	@Required
	public String nick;
	
	// Валидация
	public String validateUser() {
		return User.authenticate(email, password);
	}
}
