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
	public String author;
	
	@Required
	public String topic;
	
	@Required
	@Column(columnDefinition = "TEXT")
	public String content;
	
	private static Finder<Long, News> find = new Finder<Long, News>(Long.class, News.class);
	
	public static List<News> all() { return find.order("id DESC").findList(); }
	
	// CRUD
	
	public static void create(News n) {
		n.date = new Date();
		n.save();
	}
	
	public static News byId(Long Id) { return find.byId(Id); }
	
	public static void update(News n) {
		n.date = find.byId(n.id).date;
		n.update();
	}
	
	public static void delete(Long Id) { find.ref(Id).delete(); }
}
