package es.unex.dinopedia.Networking;


import java.util.List;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;

public interface ApiListener {
    public void onDinosauriosLoaded(List<Dinosaurio> repos);
    public void onLogrosLoaded(List<Logro> repos);
}