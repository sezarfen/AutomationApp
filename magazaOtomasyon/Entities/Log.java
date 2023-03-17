package magazaOtomasyon.Entities;

public class Log {

	private int id;
	private String kind;
	private String content;
	private String date;

	public Log(int id, String kind, String content, String date) {
		this.id = id;
		this.kind = kind;
		this.content = content;
		this.date = date;
	}

	public Log() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", content=" + content + ", date=" + date + "]";
	}

}
