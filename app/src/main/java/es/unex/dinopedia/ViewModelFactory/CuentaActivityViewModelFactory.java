package es.unex.dinopedia.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.ViewModel.CuentaActivityViewModel;

public class CuentaActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final LocalRepository mLocalRepository;

    public CuentaActivityViewModelFactory(LocalRepository repository) {
        this.mLocalRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CuentaActivityViewModel(mLocalRepository);
    }
}
