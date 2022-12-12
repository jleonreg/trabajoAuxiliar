package es.unex.dinopedia.Networking;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Networking.DataSource;
import es.unex.dinopedia.roomdb.DinosaurioDao;
import es.unex.dinopedia.roomdb.LogroDao;

public class Repository {
    private static Repository sInstance;
    private final DinosaurioDao dinoDAO;
    private final LogroDao logroDAO;
    private final DataSource dataSource;


    public Repository(DinosaurioDao dinoDAO, LogroDao logroDAO, DataSource dataSource) {
        this.dinoDAO = dinoDAO;
        this.logroDAO = logroDAO;
        this.dataSource = dataSource;

        dataSource.fetchRepos();
        LiveData<Dinosaurio[]> dino_data = dataSource.getCurrentDinos();
        LiveData<Logro[]> logro_data = dataSource.getCurrentLogros();
        dino_data.observeForever(dinoObserver -> AppExecutors.getInstance().diskIO().execute(() -> {
            if (dinoObserver.length > 0) {
                dinoDAO.deleteAll();
            }
            dinoDAO.insert(dinoObserver[0]);
        }));
        logro_data.observeForever(logroObserver -> AppExecutors.getInstance().diskIO().execute(() -> {
            if (logroObserver.length > 0) {
                logroDAO.deleteAll();
            }
            logroDAO.insert(logroObserver[0]);
        }));
    }

    public synchronized static Repository getInstance(DinosaurioDao dinoDAO, LogroDao logroDAO, DataSource dataSource){
        if (sInstance == null) {
            sInstance = new Repository(dinoDAO, logroDAO, dataSource);
        }
        return sInstance;
    }

    public LiveData<Dinosaurio[]> getCurrentNewsDinos() {
        return dataSource.getCurrentDinos();
    }

    public LiveData<Logro[]> getCurrentNewsLogros() {
        return dataSource.getCurrentLogros();
    }
}
