package es.unex.dinopedia.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Adapters.DinosaurioAdapter;
import es.unex.dinopedia.Interfaz.MainActivityInterface;
import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;


public class FavoritoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DinosaurioAdapter mAdapter;
    private final Context context;
    private List<Dinosaurio> dinoList;

    public FavoritoFragment(Context cont) {
        context = cont;
        mAdapter = new DinosaurioAdapter(context, item -> {});
        dinoList = new ArrayList<>();
        cargarDinosaurios();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        cargarDinosaurios();
        mAdapter.load(dinoList);
    }


    public void cargarDinosaurios(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(context);
            dinoList = database.getDinosaurioDao().getFavorito();
        });
    }
}