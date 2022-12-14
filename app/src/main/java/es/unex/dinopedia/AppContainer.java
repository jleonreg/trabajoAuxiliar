package es.unex.dinopedia;

import android.content.Context;

import es.unex.dinopedia.Networking.DataSource;
import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class AppContainer {

    private DinopediaDatabase database;
    private DataSource networkDataSource;
    public Repository repository;
    public MainViewModelFactory factory;

    public AppContainer(Context context){
        database = DinopediaDatabase.getInstance(context);
        networkDataSource = DataSource.getInstance();
        repository = Repository.getInstance(database.getDinosaurioDao(), database.getLogroDao(), networkDataSource);
        factory = new MainViewModelFactory(repository);
    }
}
