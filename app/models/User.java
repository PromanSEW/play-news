package models;

import java.security.*;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Email;
import play.db.ebean.Model;

@Entity
public class User extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@Email
	private String email;
	private String passwordHash;
	public String nick;
	@ManyToOne
	private UserGroup group;
	
	public User(Login l) {
		email = l.email; passwordHash = SHA256(l.password); nick = l.nick; group = UserGroup.getUserGroup(l.sgroup);
	}
	
	private User() { email = ""; passwordHash = ""; nick = ""; group = UserGroup.getUserGroup("user"); }

	private static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

	public static User authenticate(String email, String password) {
		User user = find.byId(email);
		if(user != null) {
			if(SHA256(password).equals(user.passwordHash)) return user;
		} return null;
	}
	
	public static User getUser(String email) {
		if(email == null) return new User();
		else return find.byId(email);
	}
	
	public static Login getLogin(String email) { return new Login(find.byId(email)); }
	
	public UserGroup getUserGroup() { return group; }
	
	public void setUserGroup(UserGroup group) { this.group = group; }
	
	public String getEmail() { return email; }
	
	public static List<User> all() { return find.all(); }
	
	public static void deleteUser(String email) { find.ref(email).delete(); }

	private static String SHA256(String str) {
		try { MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(str.getBytes());
			return javax.xml.bind.DatatypeConverter.printHexBinary(digest.digest());
		} catch (NoSuchAlgorithmException e) { return str; }
	}
}
