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
	
	private static Finder<String, UserGroup> find = new Finder<String, UserGroup>(String.class, UserGroup.class);
	
	private static UserGroup ADMIN() {
		return new UserGroup("admin", "Администратор");
	}
	
	private static UserGroup USER() {
		return new UserGroup("user", "Пользователь");
	}
	
	public static UserGroup getUserGroup(String group) {
		if(find.all().isEmpty()) addUserGroups();
		return find.byId(group);
	}
	
	private static void addUserGroups() {
		//ADMIN().save(); USER().save();
	}
}
