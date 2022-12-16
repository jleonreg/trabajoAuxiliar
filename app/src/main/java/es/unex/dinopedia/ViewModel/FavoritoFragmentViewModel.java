package es.unex.dinopedia.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Networking.Repository;

public class FavoritoFragmentViewModel extends ViewModel {

    private final Repository mRepository;
    private final LiveData<List<Dinosaurio>> mDinos;
    private final LiveData<List<Logro>> mLogros;


    public FavoritoFragmentViewModel(Repository repository) {
        mRepository = repository;
        mDinos = mRepository.getCurrentNewsDinos();
        mLogros = mRepository.getCurrentNewsLogros();
    }

    public void onRefresh() {
        mRepository.doFetch();
    }

    public LiveData<List<Dinosaurio>> getDinosFavoritos(){
        return mRepository.getDinosFavoritos();
    }

    public void comprobarLogros(){
        mRepository.comprobarLogros("Marca tu primer dinosaurio favorito");
    }
}
