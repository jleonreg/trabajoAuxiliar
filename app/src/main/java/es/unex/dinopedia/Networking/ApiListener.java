package es.unex.dinopedia.Networking;


import java.util.List;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;

public interface ApiListener {
    public void onLoaded(List<Dinosaurio> dinos, List<Logro> logros);

}