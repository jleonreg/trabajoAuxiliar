package es.unex.dinopedia;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Arrays;
import java.util.List;

import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.Networking.DataSource;
import es.unex.dinopedia.roomdb.DinosaurioDao;
import es.unex.dinopedia.roomdb.HistorialCombateDao;
import es.unex.dinopedia.roomdb.LogroDao;
import es.unex.dinopedia.roomdb.UsuarioDao;

public class LocalRepository {
    private static LocalRepository sInstance;
    private final UsuarioDao userDAO;
    private final HistorialCombateDao hcDAO;
    private final LocalDataSource localDataSource;
    private MutableLiveData<String> dinoFilterLiveData = new MutableLiveData<>();


    public LocalRepository(UsuarioDao userDAO, HistorialCombateDao hcDAO, LocalDataSource localDataSource) {
        this.userDAO = userDAO;
        this.hcDAO = hcDAO;
        this.localDataSource = localDataSource;

        LiveData<List<HistorialCombate>> hc_data = localDataSource.getCurrentHC();
        LiveData<List<Usuario>> user_data = localDataSource.getCurrentUser();
        hc_data.observeForever(historialCombateObserver -> AppExecutors.getInstance().diskIO().execute(() -> {
            if (historialCombateObserver.size() > 0) {
                hcDAO.deleteAll();
            }
            hcDAO.insertAll(historialCombateObserver);
        }));
        user_data.observeForever(logroObserver -> AppExecutors.getInstance().diskIO().execute(() -> {
            if (logroObserver.size() > 0) {
                userDAO.deleteAll();
            }
            userDAO.insertAll(logroObserver);
        }));
    }

    public synchronized static LocalRepository getInstance(HistorialCombateDao hcDAO, UsuarioDao userDAO, LocalDataSource localDataSource){
        if (sInstance == null) {
            sInstance = new LocalRepository(userDAO, hcDAO, localDataSource);
        }
        return sInstance;
    }

    public LiveData<List<HistorialCombate>> getCurrentNewsHc() {
        return hcDAO.getAll();
    }

    public LiveData<List<Usuario>> getCurrentNewsUser() {
        return userDAO.getAll();
    }
}
