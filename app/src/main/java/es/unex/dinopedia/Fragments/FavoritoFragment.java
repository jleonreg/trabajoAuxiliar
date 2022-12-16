package es.unex.dinopedia.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.unex.dinopedia.Networking.AppContainer;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.ViewModel.FavoritoFragmentViewModel;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Adapters.DinosaurioAdapter;
import es.unex.dinopedia.Interfaz.MainActivityInterface;
import es.unex.dinopedia.Networking.MyApplication;
import es.unex.dinopedia.R;


public class FavoritoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DinosaurioAdapter mAdapter;
    private final Context context;
    private List<Dinosaurio> dinoList;
    private FavoritoFragmentViewModel mViewModel;

    public FavoritoFragment(Context cont) {
        context = cont;
        mAdapter = new DinosaurioAdapter(context, item -> {});
        dinoList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContainer appContainer = ((MyApplication) FavoritoFragment.this.getActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.favoritoFragmentFactory).get(FavoritoFragmentViewModel.class);
        mViewModel.getDinosFavoritos().observe(this, dinosaurios -> {
            mAdapter.swap(dinosaurios);
            dinoList=dinosaurios;
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewMain = inflater.inflate(R.layout.fragment_favorito, container, false);

        mRecyclerView = viewMain.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DinosaurioAdapter(context, item -> {
            MainActivityInterface activity = (MainActivityInterface) FavoritoFragment.this.getActivity();
            activity.classDinosaurio(item);
        });

        mRecyclerView.setAdapter(mAdapter);

        return viewMain;
    }

    @Override
    public void onResume() {
        super.onResume();
        comprobarLogro();
    }


    public void comprobarLogro(){
        if (dinoList.size() > 0) {
            AppExecutors.getInstance().diskIO().execute(() -> mViewModel.comprobarLogros());
        }
    }
}