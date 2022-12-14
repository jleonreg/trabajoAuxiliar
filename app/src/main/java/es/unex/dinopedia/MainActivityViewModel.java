package es.unex.dinopedia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Networking.Repository;

public class MainActivityViewModel extends ViewModel {

    private final Repository mRepository;
    private final LiveData<List<Dinosaurio>> mDinos;
    private final LiveData<List<Logro>> mLogros;

    public MainActivityViewModel(Repository repository) {
        mRepository = repository;
        mDinos = mRepository.getCurrentNewsDinos();
        mLogros = mRepository.getCurrentNewsLogros();
    }

    public void onRefresh() {
        mRepository.doFetch();
    }

    public LiveData<List<Dinosaurio>> getDinos() {
        return mDinos;
    }

    public LiveData<List<Dinosaurio>> getDinosFavoritos(){
        return mRepository.getDinosFavoritos();
    }

    public LiveData<List<String>> getDinosNombres(){
        return mRepository.getDinosString();
    }

    public LiveData<List<Logro>> getLogros() {
        return mLogros;
    }

    public LiveData<List<Logro>> getLogroActivos(){
        return mRepository.getLogrosActivos();
    }
}
