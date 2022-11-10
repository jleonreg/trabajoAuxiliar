package es.unex.dinopedia;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
	@SerializedName("lived_in")
	@Expose
	private String livedIn;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("species")
	@Expose
	private String species;
	@SerializedName("period_name")
	@Expose
	private String periodName;
	@SerializedName("length_meters")
	@Expose
	private String lengthMeters;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	@Ignore
	public Dinosaurio() {
	}

	/**
	 *
	 * @param livedIn
	 * @param species
	 * @param name
	 * @param periodName
	 * @param diet
	 * @param type
	 * @param lengthMeters
	 */
	@Ignore
	public Dinosaurio(String name, String diet, String livedIn, String type, String species, String periodName, String lengthMeters) {
		super();
		this.name = name;
		this.diet = diet;
		this.livedIn = livedIn;
		this.type = type;
		this.species = species;
		this.periodName = periodName;
		this.lengthMeters = lengthMeters;
	}

	public Dinosaurio(long id, String name, String diet, String livedIn, String type, String species, String periodName, String lengthMeters) {
		super();
		this.id = id;
		this.name = name;
		this.diet = diet;
		this.livedIn = livedIn;
		this.type = type;
		this.species = species;
		this.periodName = periodName;
		this.lengthMeters = lengthMeters;
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

	public String getLivedIn() {
		return livedIn;
	}

	public void setLivedIn(String livedIn) {
		this.livedIn = livedIn;
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

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public String getLengthMeters() {
		return lengthMeters;
	}

	public void setLengthMeters(String lengthMeters) {
		this.lengthMeters = lengthMeters;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Dinosaurio.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("name");
		sb.append('=');
		sb.append(((this.name == null)?"<null>":this.name));
		sb.append(',');
		sb.append("diet");
		sb.append('=');
		sb.append(((this.diet == null)?"<null>":this.diet));
		sb.append(',');
		sb.append("livedIn");
		sb.append('=');
		sb.append(((this.livedIn == null)?"<null>":this.livedIn));
		sb.append(',');
		sb.append("type");
		sb.append('=');
		sb.append(((this.type == null)?"<null>":this.type));
		sb.append(',');
		sb.append("species");
		sb.append('=');
		sb.append(((this.species == null)?"<null>":this.species));
		sb.append(',');
		sb.append("periodName");
		sb.append('=');
		sb.append(((this.periodName == null)?"<null>":this.periodName));
		sb.append(',');
		sb.append("lengthMeters");
		sb.append('=');
		sb.append(((this.lengthMeters == null)?"<null>":this.lengthMeters));
		sb.append(',');
		if (sb.charAt((sb.length()- 1)) == ',') {
			sb.setCharAt((sb.length()- 1), ']');
		} else {
			sb.append(']');
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = ((result* 31)+((this.livedIn == null)? 0 :this.livedIn.hashCode()));
		result = ((result* 31)+((this.species == null)? 0 :this.species.hashCode()));
		result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
		result = ((result* 31)+((this.periodName == null)? 0 :this.periodName.hashCode()));
		result = ((result* 31)+((this.diet == null)? 0 :this.diet.hashCode()));
		result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
		result = ((result* 31)+((this.lengthMeters == null)? 0 :this.lengthMeters.hashCode()));
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Dinosaurio) == false) {
			return false;
		}
		Dinosaurio rhs = ((Dinosaurio) other);
		return ((((((((this.livedIn == rhs.livedIn)||((this.livedIn!= null)&&this.livedIn.equals(rhs.livedIn)))&&((this.species == rhs.species)||((this.species!= null)&&this.species.equals(rhs.species))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.periodName == rhs.periodName)||((this.periodName!= null)&&this.periodName.equals(rhs.periodName))))&&((this.diet == rhs.diet)||((this.diet!= null)&&this.diet.equals(rhs.diet))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.lengthMeters == rhs.lengthMeters)||((this.lengthMeters!= null)&&this.lengthMeters.equals(rhs.lengthMeters))));
	}

	public String toLog() {
		return "ID: " + id + ITEM_SEP + "Name:" + name + ITEM_SEP + "Diet:" + diet
				+ ITEM_SEP + "Live in:" + livedIn + ITEM_SEP + "Type:"
				+ type + ITEM_SEP + "Species:" + species + ITEM_SEP + "Period:" + periodName
				+ ITEM_SEP + "LengthMeters:" + lengthMeters;
	}

}