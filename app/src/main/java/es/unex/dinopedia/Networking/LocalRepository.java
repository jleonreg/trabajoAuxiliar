package es.unex.dinopedia.Networking;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.roomdb.HistorialCombateDao;
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
        user_data.observeForever(userObserver -> AppExecutors.getInstance().diskIO().execute(() -> {
            if (userObserver.size() > 0) {
                userDAO.deleteAll();
            }
            userDAO.insertAll(userObserver);
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

    public void insertarHistorial(Dinosaurio d1, Dinosaurio d2, String resultado){
        HistorialCombate hc = new HistorialCombate(d1.getName(), d2.getName(), resultado);
        hcDAO.insert(hc);
    }

    public List<HistorialCombate> obtenerLista(){
        return hcDAO.getAllList();
    }

    public Usuario getUsuario(){
        return userDAO.getUsuario();
    }

    public void insertarUsuario(Usuario u){
        userDAO.insert(u);
    }

    public void borrarTodo(){
        userDAO.deleteAll();
    }

    public void actualizarModoUsuario(long id, Boolean bool){
        userDAO.updateModoUsuario(id, bool);
    }

    public void actualizar(Usuario u){
        userDAO.update(u);
    }

    public void limpiar(){
        hcDAO.deleteAll();
    }
}
