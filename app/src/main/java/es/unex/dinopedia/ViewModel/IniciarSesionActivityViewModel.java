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

public class IniciarSesionActivityViewModel extends ViewModel {

    private final LocalRepository mLocalRepository;
    private final Repository mRepository;
    private final LiveData<List<Usuario>> mUser;
    private final LiveData<List<Logro>> mLogros;

    public IniciarSesionActivityViewModel(LocalRepository localRepository, Repository repository) {
        mLocalRepository = localRepository;
        mRepository = repository;
        mUser = mLocalRepository.getCurrentNewsUser();
        mLogros = mRepository.getCurrentNewsLogros();
    }

    public Usuario getUsuario(){
        return mLocalRepository.getUsuario();
    }

    public void insertar(Usuario u){
        mLocalRepository.insertarUsuario(u);
    }

    public void actualizar(Usuario u){
        mLocalRepository.actualizar(u);
    }

    public void marcarLogro(){
        mRepository.comprobarLogros("Inicia Sesión en la aplicación");
    }

}
