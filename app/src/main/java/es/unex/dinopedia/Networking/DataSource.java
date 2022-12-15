package es.unex.dinopedia.Networking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;

public class DataSource {

    private static DataSource sInstance;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<List<Dinosaurio>> mDownloadedDinos;
    private final MutableLiveData<List<Logro>> mDownloadedLogros;

    private DataSource() {
        mDownloadedDinos = new MutableLiveData<>();
        mDownloadedLogros = new MutableLiveData<>();
    }

    public synchronized static DataSource getInstance() {
        if (sInstance == null) {
            sInstance = new DataSource();
        }
        return sInstance;
    }

    public LiveData<List<Dinosaurio>> getCurrentDinos() {
        return mDownloadedDinos;
    }

    public LiveData<List<Logro>> getCurrentLogros() {
        return mDownloadedLogros;
    }

    /**
     * Gets the newest repos
     */
    public void fetchRepos() {
        AppExecutors.getInstance().networkIO().execute(new ApiRunnable((dinos, logros) -> {
            mDownloadedDinos.postValue(dinos);
            mDownloadedLogros.postValue(logros);
        }));
    }

}
