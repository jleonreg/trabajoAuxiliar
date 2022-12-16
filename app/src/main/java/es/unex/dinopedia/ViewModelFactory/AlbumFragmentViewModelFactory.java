package es.unex.dinopedia.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.ViewModel.AlbumFragmentViewModel;

public class AlbumFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository mRepository;

    public AlbumFragmentViewModelFactory(Repository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AlbumFragmentViewModel(mRepository);
    }
}
