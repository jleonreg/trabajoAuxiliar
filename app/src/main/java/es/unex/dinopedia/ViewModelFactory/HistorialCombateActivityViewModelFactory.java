package es.unex.dinopedia.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.ViewModel.HistorialCombateActivityViewModel;

public class HistorialCombateActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final LocalRepository mLocalRepository;

    public HistorialCombateActivityViewModelFactory(LocalRepository repository) {
        this.mLocalRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new HistorialCombateActivityViewModel(mLocalRepository);
    }
}
