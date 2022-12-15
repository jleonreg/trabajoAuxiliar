package es.unex.dinopedia.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Model.Usuario;

public class CuentaActivityViewModel extends ViewModel {

    private final LocalRepository mLocalRepository;
    private final LiveData<List<Usuario>> mUser;

    public CuentaActivityViewModel(LocalRepository localRepository) {
        mLocalRepository = localRepository;
        mUser = mLocalRepository.getCurrentNewsUser();
    }

    public Usuario getUsuario(){
        return mLocalRepository.getUsuario();
    }

    public void borrarTodo(){
        mLocalRepository.borrarTodo();
    }

    public void actualizarModoUsuario(long id, Boolean bandera){
        mLocalRepository.actualizarModoUsuario(id, bandera);
    }

    public void actualizar(Usuario u){
        mLocalRepository.actualizar(u);
    }
}
