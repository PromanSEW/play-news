package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class News extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public Date date;
	
	@Required
	public String author; // Автор
	
	@Required
	public String topic; // Заголовок
	
	@Required
	@Column(columnDefinition = "TEXT")
	public String content; // Текст записи, в том числе HTML, до 65536 символов
	
	private static Finder<Long, News> find = new Finder<Long, News>(Long.class, News.class);
	
	// Получить все новости
	public static List<News> all() { return find.order("id DESC").findList(); }
	
	// CRUD
	
	// Создать новость
	public static void create(News n) {
		n.date = new Date();
		n.save();
	}
	
	// Получить новость по ID
	public static News byId(Long Id) { return find.byId(Id); }
	
	// Отредактировать новость
	public static void update(News n) {
		n.date = find.byId(n.id).date;
		n.update();
	}
	
	// Удалить новость по ID
	public static void delete(Long Id) { find.ref(Id).delete(); }
}
