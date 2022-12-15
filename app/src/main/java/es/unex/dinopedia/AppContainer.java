package es.unex.dinopedia;

import android.content.Context;

import es.unex.dinopedia.Networking.DataSource;
import es.unex.dinopedia.Networking.LocalDataSource;
import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.ViewModelFactory.CuentaActivityViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.DinosaurioInfoActivityViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.HistorialCombateActivityViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.IniciarSesionActivityViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.MainViewModelFactory;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class AppContainer {

    private DinopediaDatabase database;

    private DataSource networkDataSource;
    private LocalDataSource localDataSource;

    public Repository repository;
    public LocalRepository localRepository;

    public MainViewModelFactory mainFactory;
    public HistorialCombateActivityViewModelFactory historialCombateFactory;
    public CuentaActivityViewModelFactory cuentaFactory;
    public DinosaurioInfoActivityViewModelFactory dinoInfoFactory;
    public IniciarSesionActivityViewModelFactory iniciarSesionFactory;

    public AppContainer(Context context){
        database = DinopediaDatabase.getInstance(context);
        networkDataSource = DataSource.getInstance();

        repository = Repository.getInstance(database.getDinosaurioDao(), database.getLogroDao(), networkDataSource);
        localRepository = LocalRepository.getInstance(database.getHistorialCombateDao(), database.getUsuarioDao(), localDataSource);

        mainFactory = new MainViewModelFactory(repository);
        historialCombateFactory = new HistorialCombateActivityViewModelFactory(localRepository);
        cuentaFactory = new CuentaActivityViewModelFactory(localRepository);
        dinoInfoFactory = new DinosaurioInfoActivityViewModelFactory(localRepository, repository);
        iniciarSesionFactory = new IniciarSesionActivityViewModelFactory(localRepository, repository);
    }
}
