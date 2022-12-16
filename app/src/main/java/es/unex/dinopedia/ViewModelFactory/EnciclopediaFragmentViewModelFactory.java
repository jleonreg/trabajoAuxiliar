package es.unex.dinopedia.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.ViewModel.EnciclopediaFragmentViewModel;

public class EnciclopediaFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository mRepository;

    public EnciclopediaFragmentViewModelFactory(Repository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new EnciclopediaFragmentViewModel(mRepository);
    }
}
