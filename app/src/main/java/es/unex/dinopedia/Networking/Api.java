package es.unex.dinopedia.Networking;

import java.util.List;

import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
//Esta clase es la que se encarga de invocar las llamadas a la API
public interface Api {

    @GET("bfc8b462fce013224e0c918e3fa82229/raw/8005b100ff20d4f74863ca23645c3f8528cacd86/dinosaurios.json")
    Call<List<Dinosaurio>> getListDinosaurios();

    @GET("b27225e896dc6b5313db1ffc66db9575/raw/932bd62d3c857c331a0aec62df6ce1630dc9f040/logros.json")
    Call<List<Logro>> listLogros();
}