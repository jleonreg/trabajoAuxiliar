package es.unex.dinopedia.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.ViewModel.IniciarSesionActivityViewModel;

public class IniciarSesionActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final LocalRepository mLocalRepository;
    private final Repository mRepository;

    public IniciarSesionActivityViewModelFactory(LocalRepository localRepository, Repository repository) {
        this.mLocalRepository = localRepository;
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new IniciarSesionActivityViewModel(mLocalRepository, mRepository);
    }
}
