package models;

import javax.persistence.*;

import play.db.ebean.Model;

@Entity
public class UserGroup extends Model {
	private static final long serialVersionUID = 1L;
	
	@Id
	public String name;
	public String title;
	
	private UserGroup(String n, String t) {
		name = n; title = t;
	}
	
	public static UserGroup ADMIN() {
		return new UserGroup("admin", "Администратор");
	}
	
	public static UserGroup USER() {
		return new UserGroup("user", "Пользователь");
	}
}
