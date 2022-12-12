package es.unex.dinopedia.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

@Entity(tableName = "Dinosaurio")
public class Dinosaurio {

	@Ignore
	public static final String ITEM_SEP = System.getProperty("line.separator");

	@SerializedName("id")
	@Expose
	@PrimaryKey(autoGenerate = true)
	private long id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("diet")
	@Expose
	private String diet;
	@SerializedName("livedin")
	@Expose
	private String livedin;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("species")
	@Expose
	private String species;
	@SerializedName("periodname")
	@Expose
	private String periodname;
	@SerializedName("lengthmeters")
	@Expose
	private String lengthmeters;
	@SerializedName("favorite")
	@Expose
	private String favorite;

	@Ignore
	public Dinosaurio() {
	}

	@Ignore
	public Dinosaurio(String name, String diet, String livedin, String type, String species, String periodname, String lengthmeters, String favorite) {
		super();
		this.name = name;
		this.diet = diet;
		this.livedin = livedin;
		this.type = type;
		this.species = species;
		this.periodname = periodname;
		this.lengthmeters = lengthmeters;
		this.favorite = favorite;
	}

	public Dinosaurio(long id, String name, String diet, String livedin, String type, String species, String periodname, String lengthmeters, String favorite) {
		super();
		this.id = id;
		this.name = name;
		this.diet = diet;
		this.livedin = livedin;
		this.type = type;
		this.species = species;
		this.periodname = periodname;
		this.lengthmeters = lengthmeters;
		this.favorite = favorite;
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

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public String getLivedin() {
		return livedin;
	}

	public void setLivedin(String livedin) {
		this.livedin = livedin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getPeriodname() {
		return periodname;
	}

	public void setPeriodname(String periodname) {
		this.periodname = periodname;
	}

	public String getLengthmeters() {
		return lengthmeters;
	}

	public void setLengthmeters(String lengthmeters) {
		this.lengthmeters = lengthmeters;
	}

	public String getFavorite() { return favorite;	}

	public void setFavorite(String favorite) { this.favorite = favorite; }

	@Override
	public String toString() {
		return "Dinosaurio{" +
				"id=" + id +
				", name='" + name + '\'' +
				", diet='" + diet + '\'' +
				", livedin='" + livedin + '\'' +
				", type='" + type + '\'' +
				", species='" + species + '\'' +
				", periodname='" + periodname + '\'' +
				", lengthmeters='" + lengthmeters + '\'' +
				", favorite='" + favorite + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Dinosaurio that = (Dinosaurio) o;
		return id == that.id && Objects.equals(name, that.name) && Objects.equals(diet, that.diet) && Objects.equals(livedin, that.livedin) && Objects.equals(type, that.type) && Objects.equals(species, that.species) && Objects.equals(periodname, that.periodname) && Objects.equals(lengthmeters, that.lengthmeters) && Objects.equals(favorite, that.favorite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, diet, livedin, type, species, periodname, lengthmeters, favorite);
	}

	public String toLog() {
		return "ID: " + id + ITEM_SEP + "Name:" + name + ITEM_SEP + "Diet:" + diet
				+ ITEM_SEP + "Live in:" + livedin + ITEM_SEP + "Type:"
				+ type + ITEM_SEP + "Species:" + species + ITEM_SEP + "Period:" + periodname
				+ ITEM_SEP + "lengthmeters:" + lengthmeters + ITEM_SEP + "Favorite:" + favorite;
	}

}