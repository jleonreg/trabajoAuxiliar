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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import es.unex.dinopedia.Networking.AppContainer;
import es.unex.dinopedia.ViewModel.EnciclopediaFragmentViewModel;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Adapters.DinosaurioAdapter;
import es.unex.dinopedia.Interfaz.MainActivityInterface;
import es.unex.dinopedia.Networking.MyApplication;
import es.unex.dinopedia.R;

public class EnciclopediaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DinosaurioAdapter mAdapter;
    private Context context;
    private List<Dinosaurio> dinoList;
    private Spinner opciones;
    private EnciclopediaFragmentViewModel mViewModel;

    public EnciclopediaFragment(){
    }

    public EnciclopediaFragment(Context cont) {
        context = cont;
        dinoList=new ArrayList<>();
        mAdapter = new DinosaurioAdapter(context, item -> {});
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContainer appContainer = ((MyApplication) EnciclopediaFragment.this.getActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.enciclopediaFragmentFactory).get(EnciclopediaFragmentViewModel.class);
        mViewModel.getDinos().observe(this, dinosaurios -> {
            mAdapter.swap(dinosaurios);
            dinoList=dinosaurios;
        });
        mViewModel.getDinosOpciones().observe(this, dinosauriosOpciones -> mAdapter.swap(dinosauriosOpciones));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewMain = inflater.inflate(R.layout.fragment_enciclopedia, container, false);

        mRecyclerView = viewMain.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DinosaurioAdapter(context, item -> {
            MainActivityInterface activity = (MainActivityInterface) EnciclopediaFragment.this.getActivity();
            activity.classDinosaurio(item);
        });

        mRecyclerView.setAdapter(mAdapter);

        opciones = viewMain.findViewById(R.id.sOpciones);
        cargarOpciones(viewMain);

        Button bRestaurar = viewMain.findViewById(R.id.bRestaurar);
        bRestaurar.setOnClickListener(v -> mAdapter.swap(dinoList));

        return viewMain;
    }

    private void cargarOpciones(View viewMain){
        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(context, R.array.opciones, android.R.layout.simple_spinner_item);
        opciones.setAdapter(adp);

        Button bAplicar = viewMain.findViewById(R.id.bAplicar);
        bAplicar.setOnClickListener(v -> {
           mViewModel.setFiltro(opciones.getSelectedItem().toString());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}