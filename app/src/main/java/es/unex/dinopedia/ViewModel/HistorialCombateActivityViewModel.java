package es.unex.dinopedia.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Model.Usuario;

public class HistorialCombateActivityViewModel extends ViewModel {

    private final LocalRepository mLocalRepository;
    private final LiveData<List<HistorialCombate>> mHC;
    private final LiveData<List<Usuario>> mUser;

    public HistorialCombateActivityViewModel(LocalRepository localRepository) {
        mLocalRepository = localRepository;
        mHC = mLocalRepository.getCurrentNewsHc();
        mUser = mLocalRepository.getCurrentNewsUser();
    }

    public LiveData<List<HistorialCombate>> getHistoriales(){
        return mHC;
    }
}
