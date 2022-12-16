package es.unex.dinopedia.Networking;

import android.content.Context;

import es.unex.dinopedia.Networking.DataSource;
import es.unex.dinopedia.Networking.LocalDataSource;
import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.ViewModelFactory.AlbumFragmentViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.CombateFragmentViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.CuentaActivityViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.DinosaurioInfoActivityViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.EnciclopediaFragmentViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.FavoritoFragmentViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.HistorialCombateActivityViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.IniciarSesionActivityViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.MainActivityViewModelFactory;
import es.unex.dinopedia.ViewModelFactory.MainFragmentViewModelFactory;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class AppContainer {

    private DinopediaDatabase database;

    private DataSource networkDataSource;
    private LocalDataSource localDataSource;

    public Repository repository;
    public LocalRepository localRepository;

    public MainActivityViewModelFactory mainFactory;
    public HistorialCombateActivityViewModelFactory historialCombateFactory;
    public CuentaActivityViewModelFactory cuentaFactory;
    public DinosaurioInfoActivityViewModelFactory dinoInfoFactory;
    public IniciarSesionActivityViewModelFactory iniciarSesionFactory;
    public MainFragmentViewModelFactory mainFragmentFactory;
    public AlbumFragmentViewModelFactory albumFragmentFactory;
    public CombateFragmentViewModelFactory combateFragmentFactory;
    public EnciclopediaFragmentViewModelFactory enciclopediaFragmentFactory;
    public FavoritoFragmentViewModelFactory favoritoFragmentFactory;

    public AppContainer(Context context){
        database = DinopediaDatabase.getInstance(context);
        networkDataSource = DataSource.getInstance();
        localDataSource = LocalDataSource.getInstance();

        repository = Repository.getInstance(database.getDinosaurioDao(), database.getLogroDao(), networkDataSource);
        localRepository = LocalRepository.getInstance(database.getHistorialCombateDao(), database.getUsuarioDao(), localDataSource);

        mainFactory = new MainActivityViewModelFactory(localRepository, repository);
        historialCombateFactory = new HistorialCombateActivityViewModelFactory(localRepository);
        cuentaFactory = new CuentaActivityViewModelFactory(localRepository);
        dinoInfoFactory = new DinosaurioInfoActivityViewModelFactory(localRepository, repository);
        iniciarSesionFactory = new IniciarSesionActivityViewModelFactory(localRepository, repository);
        mainFragmentFactory = new MainFragmentViewModelFactory(localRepository, repository);
        albumFragmentFactory = new AlbumFragmentViewModelFactory(repository);
        combateFragmentFactory = new CombateFragmentViewModelFactory(localRepository, repository);
        enciclopediaFragmentFactory = new EnciclopediaFragmentViewModelFactory(repository);
        favoritoFragmentFactory = new FavoritoFragmentViewModelFactory(repository);
    }
}
