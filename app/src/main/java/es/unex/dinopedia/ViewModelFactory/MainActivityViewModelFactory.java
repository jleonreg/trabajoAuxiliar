package es.unex.dinopedia.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.ViewModel.MainActivityViewModel;

public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository mRepository;
    private final LocalRepository mLocalRepository;

    public MainActivityViewModelFactory(LocalRepository localRepository, Repository repository) {
        this.mRepository = repository;
        this.mLocalRepository = localRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(mLocalRepository, mRepository);
    }
}
