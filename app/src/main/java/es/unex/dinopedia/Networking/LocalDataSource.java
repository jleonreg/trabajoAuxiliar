package es.unex.dinopedia.Networking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Model.Usuario;

public class LocalDataSource {

    private static LocalDataSource sInstance;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<List<HistorialCombate>> mDownloadedHC;
    private final MutableLiveData<List<Usuario>> mDownloadeUser;

    private LocalDataSource() {
        mDownloadedHC = new MutableLiveData<>();
        mDownloadeUser = new MutableLiveData<>();
    }

    public synchronized static LocalDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new LocalDataSource();
        }
        return sInstance;
    }

    public LiveData<List<Usuario>> getCurrentUser() {
        return mDownloadeUser;
    }

    public LiveData<List<HistorialCombate>> getCurrentHC() {
        return mDownloadedHC;
    }

}
