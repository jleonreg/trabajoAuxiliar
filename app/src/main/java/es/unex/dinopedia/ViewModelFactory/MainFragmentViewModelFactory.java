package es.unex.dinopedia.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dinopedia.ViewModel.MainFragmentViewModel;
import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Networking.Repository;

public class MainFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository mRepository;
    private final LocalRepository mLocalRepository;

    public MainFragmentViewModelFactory(LocalRepository localRepository, Repository repository) {
        this.mRepository = repository;
        this.mLocalRepository = localRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainFragmentViewModel(mLocalRepository, mRepository);
    }
}
