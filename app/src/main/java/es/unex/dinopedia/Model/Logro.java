package es.unex.dinopedia.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

@Entity(tableName = "Logro")
public class Logro {

	@Ignore
	public static final String ITEM_SEP = System.getProperty("line.separator");

	@SerializedName("id")
	@Expose
	@PrimaryKey(autoGenerate = true)
	private long id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("checked")
	@Expose
	private String checked;

	@Ignore
	public Logro() {
	}

	@Ignore
	public Logro(String name, String checked) {
		super();
		this.name = name;
		this.checked = checked;
	}

	public Logro(long id, String name, String checked) {
		super();
		this.id = id;
		this.name = name;
		this.checked = checked;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChecked() { return checked; }

	public void setChecked(String checked) { this.checked = checked; }

	@Override
	public String toString() {
		return "Logro{" +
				"id=" + id +
				", name='" + name + '\'' +
				", checked='" + checked + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Logro logro = (Logro) o;
		return id == logro.id && Objects.equals(name, logro.name) && Objects.equals(checked, logro.checked);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, checked);
	}

	public String toLog() {
		return "ID: " + id + ITEM_SEP + "Name:" + name + ITEM_SEP + "checked:" + checked;
	}

}