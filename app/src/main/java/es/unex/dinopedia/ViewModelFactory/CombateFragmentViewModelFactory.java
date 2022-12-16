package es.unex.dinopedia.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.ViewModel.CombateFragmentViewModel;

public class CombateFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository mRepository;
    private final LocalRepository mLocalRepository;

    public CombateFragmentViewModelFactory(LocalRepository localRepository, Repository repository) {
        this.mRepository = repository;
        this.mLocalRepository = localRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CombateFragmentViewModel(mLocalRepository, mRepository);
    }
}
