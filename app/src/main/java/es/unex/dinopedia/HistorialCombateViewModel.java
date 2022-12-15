package es.unex.dinopedia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.Networking.Repository;

public class HistorialCombateViewModel extends ViewModel {

    private final LocalRepository mLocalRepository;
    private final LiveData<List<HistorialCombate>> mHC;
    private final LiveData<List<Usuario>> mUser;

    public HistorialCombateViewModel(LocalRepository localRepository) {
        mLocalRepository = localRepository;
        mHC = mLocalRepository.getCurrentNewsHc();
        mUser = mLocalRepository.getCurrentNewsUser();
    }

    public LiveData<List<HistorialCombate>> getHistoriales(){
        return mHC;
    }
}
