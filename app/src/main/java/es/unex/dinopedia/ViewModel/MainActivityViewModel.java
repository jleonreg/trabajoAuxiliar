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

public class MainActivityViewModel extends ViewModel {

    private final Repository mRepository;
    private final LocalRepository mLocalRepository;
    private final LiveData<List<Dinosaurio>> mDinos;
    private final LiveData<List<Logro>> mLogros;
    private final LiveData<List<Usuario>> mUser;


    public MainActivityViewModel(LocalRepository localRepository, Repository repository) {
        mRepository = repository;
        mLocalRepository = localRepository;
        mDinos = mRepository.getCurrentNewsDinos();
        mLogros = mRepository.getCurrentNewsLogros();
        mUser = mLocalRepository.getCurrentNewsUser();
    }

    public void onRefresh() {
        mRepository.doFetch();
    }

    public void quitarFavoritos(){
        mRepository.quitarFavoritos();
    }
}
