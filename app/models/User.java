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
	private String email; // Email
	
	private String passwordHash; // Хэш пароля (HEX String)
	
	private String nick; // Ник
	
	// Конструктор юзера по форме логина
	public User(Login l) { email = l.email; passwordHash = SHA256(l.password); nick = l.nick; }

	private static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

	// Аутентификация (при успехе return null)
	public static String authenticate(String email, String password) {
		User user = find.byId(email);
		if(user != null && SHA256(password).equals(user.passwordHash)) return null;
		return "failed";
	}
	
	// Существует ли пользователь (если нет return null)
	public static String exists(Login l) {
		if(find.byId(l.email) != null) return "email";
		else {
			if(find.where().like("nick", "%" + l.nick + "%").findList().isEmpty()) return null;
			else return "nick"; 
		}
	}
	
	// Получить ник по email в cookies
	public static String getNick(String email) {
		if(email == null) return "";
		else {
			User user = find.byId(email);
			if(user != null) return user.nick;
			else return "";
		}
	}

	// Шифрование пароля через SHA-256 (return HEX string)
	private static String SHA256(String str) {
		try { MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(str.getBytes());
			return javax.xml.bind.DatatypeConverter.printHexBinary(digest.digest());
		} catch (NoSuchAlgorithmException e) { return str; }
	}
}
