package models;

import java.security.*;

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
	
	public User(Login l) { email = l.email; passwordHash = SHA256(l.password); nick = l.nick; }
	
	private User() { email = ""; passwordHash = ""; nick = ""; }

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

	private static String SHA256(String str) {
		try { MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(str.getBytes());
			return javax.xml.bind.DatatypeConverter.printHexBinary(digest.digest());
		} catch (NoSuchAlgorithmException e) { return str; }
	}
}
