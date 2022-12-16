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

public class MainFragmentViewModel extends ViewModel {

    private final Repository mRepository;
    private final LocalRepository mLocalRepository;
    private final LiveData<List<Dinosaurio>> mDinos;
    private final LiveData<List<Usuario>> mUser;


    public MainFragmentViewModel(LocalRepository localRepository, Repository repository) {
        mRepository = repository;
        mLocalRepository = localRepository;
        mDinos = mRepository.getCurrentNewsDinos();
        mUser = mLocalRepository.getCurrentNewsUser();
    }

    public void onRefresh() {
        mRepository.doFetch();
    }

    public LiveData<List<Dinosaurio>> getDinos(){
        return mRepository.getCurrentNewsDinos();
    }

    public Usuario getUsuario(){
        return mLocalRepository.getUsuario();
    }
}
