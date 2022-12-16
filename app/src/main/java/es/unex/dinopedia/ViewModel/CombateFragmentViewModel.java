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

public class CombateFragmentViewModel extends ViewModel {

    private final Repository mRepository;
    private final LocalRepository mLocalRepository;
    private final LiveData<List<Dinosaurio>> mDinos;
    private final LiveData<List<Logro>> mLogros;
    private final LiveData<List<HistorialCombate>> mHC;


    public CombateFragmentViewModel(LocalRepository localRepository, Repository repository) {
        mRepository = repository;
        mLocalRepository = localRepository;
        mDinos = mRepository.getCurrentNewsDinos();
        mLogros = mRepository.getCurrentNewsLogros();
        mHC = mLocalRepository.getCurrentNewsHc();
    }

    public void onRefresh() {
        mRepository.doFetch();
    }

    public LiveData<List<String>> getDinosNombres(){
        return mRepository.getDinosString();
    }

    public Dinosaurio getDino(String d){
        return mRepository.getDino(d);
    }

    public void insertarHistorial(Dinosaurio d1, Dinosaurio d2, String resultado){
        mLocalRepository.insertarHistorial(d1, d2, resultado);
    }

    public List<HistorialCombate> obtenerLista(){
        return mLocalRepository.obtenerLista();
    }

    public void comprobarLogros(String logro){
        mRepository.comprobarLogros(logro);
    }
}
