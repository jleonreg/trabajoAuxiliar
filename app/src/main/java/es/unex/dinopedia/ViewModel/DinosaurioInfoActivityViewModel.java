package es.unex.dinopedia.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.Networking.Repository;

public class DinosaurioInfoActivityViewModel extends ViewModel {

    private final LocalRepository mLocalRepository;
    private final Repository mRepository;
    private final LiveData<List<Usuario>> mUser;
    private final LiveData<List<Dinosaurio>> mDinos;
    private final LiveData<List<Logro>> mLogros;

    public DinosaurioInfoActivityViewModel(LocalRepository localRepository, Repository repository) {
        mLocalRepository = localRepository;
        mRepository = repository;
        mUser = mLocalRepository.getCurrentNewsUser();
        mDinos = mRepository.getCurrentNewsDinos();
        mLogros = mRepository.getCurrentNewsLogros();
    }

    public Dinosaurio getDino(long id){
        return mRepository.getDino(id);
    }

    public void actualizarDinosaurio(Dinosaurio d){
        mRepository.actualizarDinosaurio(d);
    }

    public Usuario getUsuario(){
        return mLocalRepository.getUsuario();
    }
}
