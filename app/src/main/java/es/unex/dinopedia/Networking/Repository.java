package es.unex.dinopedia.Networking;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import java.util.List;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.roomdb.DinosaurioDao;
import es.unex.dinopedia.roomdb.LogroDao;

public class Repository {
    private static Repository sInstance;
    private final DinosaurioDao dinoDAO;
    private final LogroDao logroDAO;
    private final DataSource dataSource;
    private MutableLiveData<String> dinoFilterLiveData = new MutableLiveData<>();


    public Repository(DinosaurioDao dinoDAO, LogroDao logroDAO, DataSource dataSource) {
        this.dinoDAO = dinoDAO;
        this.logroDAO = logroDAO;
        this.dataSource = dataSource;

        dataSource.fetchRepos();
        LiveData<List<Dinosaurio>> dino_data = dataSource.getCurrentDinos();
        LiveData<List<Logro>> logro_data = dataSource.getCurrentLogros();
        dino_data.observeForever(dinoObserver -> AppExecutors.getInstance().diskIO().execute(() -> {
            if (dinoObserver.size() > 0) {
                dinoDAO.deleteAll();
            }
            dinoDAO.insertAll(dinoObserver);
        }));
        logro_data.observeForever(logroObserver -> AppExecutors.getInstance().diskIO().execute(() -> {
            if (logroObserver.size() > 0) {
                logroDAO.deleteAll();
            }
            logroDAO.insertAll(logroObserver);
        }));
    }

    public synchronized static Repository getInstance(DinosaurioDao dinoDAO, LogroDao logroDAO, DataSource dataSource){
        if (sInstance == null) {
            sInstance = new Repository(dinoDAO, logroDAO, dataSource);
        }
        return sInstance;
    }

    public LiveData<List<Dinosaurio>> getCurrentNewsDinos() {
        return dinoDAO.getAll();
    }

    public LiveData<List<Logro>> getCurrentNewsLogros() {
        return logroDAO.getAll();
    }


    public void doFetch(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            dinoDAO.deleteAll();
            logroDAO.deleteAll();
        });
    }

    public LiveData<List<Dinosaurio>> getDinosOpciones() {
        return Transformations.switchMap(dinoFilterLiveData, new Function<String, LiveData<List<Dinosaurio>>>() {
            @Override
            public LiveData<List<Dinosaurio>> apply(String input) {
                return dinoDAO.getOpciones(input);
            }
        });
    }

    public LiveData<List<Dinosaurio>> getDinosFavoritos(){
        return dinoDAO.getFavorito();
    }

    public void setFiltro(String opcion){
        dinoFilterLiveData.postValue(opcion);
    }

    public LiveData<List<Logro>> getLogrosActivos(){
        return logroDAO.getCheck();
    }

    public void comprobarLogros(String logro){
        Logro l = logroDAO.getLogro(logro);
        if(!l.getChecked().equals("1")){
            l.setChecked("1");
            logroDAO.update(l);
        }
    }

    public LiveData<List<String>> getDinosString(){
        return dinoDAO.getNombres();
    }

    public Dinosaurio getDino(String nombre){
        return dinoDAO.getDinosaurioString(nombre);
    }

    public Dinosaurio getDino(long id){
        return dinoDAO.getDinosaurioId(id);
    }

    public void actualizarDinosaurio(Dinosaurio d){
        dinoDAO.update(d);
    }

    public void quitarFavoritos(){
        dinoDAO.quitarFavorite();
    }
}
