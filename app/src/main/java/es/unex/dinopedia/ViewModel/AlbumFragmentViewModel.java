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

public class AlbumFragmentViewModel extends ViewModel {

    private final Repository mRepository;
    private final LiveData<List<Logro>> mLogros;


    public AlbumFragmentViewModel(Repository repository) {
        mRepository = repository;
        mLogros = mRepository.getCurrentNewsLogros();
    }

    public void onRefresh() {
        mRepository.doFetch();
    }

    public LiveData<List<Logro>> getLogroActivos(){
        return mRepository.getLogrosActivos();
    }
}
