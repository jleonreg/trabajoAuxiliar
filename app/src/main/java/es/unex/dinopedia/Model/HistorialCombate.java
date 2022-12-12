package es.unex.dinopedia.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

@Entity(tableName = "HistorialCombate")
public class HistorialCombate {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");
    @SerializedName("name")
    @Expose
    @PrimaryKey (autoGenerate=true)
    private long id;

    @NonNull
    private String dinosaurio1;

    @NonNull
    private String dinosaurio2;

    @NonNull
    private String estado;

    @Ignore
    public HistorialCombate() {
    }

    public HistorialCombate(long id, String dinosaurio1, String dinosaurio2, String estado) {
        super();
        this.id = id;
        this.dinosaurio1 = dinosaurio1;
        this.dinosaurio2 = dinosaurio2;
        this.estado = estado;
    }

    @Ignore
    public HistorialCombate(String dinosaurio1, String dinosaurio2, String estado) {
        super();
        this.dinosaurio1 = dinosaurio1;
        this.dinosaurio2 = dinosaurio2;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getDinosaurio1() {
        return dinosaurio1;
    }

    public void setDinosaurio1(@NonNull String dinosaurio1) {
        this.dinosaurio1 = dinosaurio1;
    }

    @NonNull
    public String getDinosaurio2() {
        return dinosaurio2;
    }

    public void setDinosaurio2(@NonNull String dinosaurio2) {
        this.dinosaurio2 = dinosaurio2;
    }

    @NonNull
    public String getEstado() {
        return estado;
    }

    public void setEstado(@NonNull String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistorialCombate that = (HistorialCombate) o;
        return id == that.id && dinosaurio1.equals(that.dinosaurio1) && dinosaurio2.equals(that.dinosaurio2) && estado.equals(that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dinosaurio1, dinosaurio2, estado);
    }

    @Override
    public String toString() {
        return "HistorialCombate{" +
                "id=" + id +
                ", dinosaurio1='" + dinosaurio1 + '\'' +
                ", dinosaurio2='" + dinosaurio2 + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
