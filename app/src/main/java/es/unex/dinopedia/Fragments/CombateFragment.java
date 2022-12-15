package es.unex.dinopedia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import es.unex.dinopedia.AppContainer;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Activities.CombateResultActivity;
import es.unex.dinopedia.Networking.LocalDataSource;
import es.unex.dinopedia.Networking.LocalRepository;
import es.unex.dinopedia.ViewModel.MainActivityViewModel;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Activities.HistorialCombateActivity;
import es.unex.dinopedia.MyApplication;
import es.unex.dinopedia.Networking.DataSource;
import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class CombateFragment extends Fragment {

    private Spinner mSpinnerdino1;
    private Spinner mSpinnerdino2;
    private List<String> dinoListNombres;
    private static Context context;
    private ArrayAdapter adp;
    private Dinosaurio dinosaurio1;
    private Dinosaurio dinosaurio2;
    private Button bCombate;
    private Button bHistorial;
    private Repository mRepository;
    private LocalRepository mLocalRepository;

    public CombateFragment(Context cont) {
        context = cont;
        mRepository = Repository.getInstance(DinopediaDatabase.getInstance(context).getDinosaurioDao(), DinopediaDatabase.getInstance(context).getLogroDao(), DataSource.getInstance());
        mLocalRepository = LocalRepository.getInstance(DinopediaDatabase.getInstance(context).getHistorialCombateDao(), DinopediaDatabase.getInstance(context).getUsuarioDao(), LocalDataSource.getInstance());
    }

    public static CombateFragment newInstance(Context cont) {
        CombateFragment fragment = new CombateFragment(cont);
        context = cont;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContainer appContainer = ((MyApplication) CombateFragment.this.getActivity().getApplication()).appContainer;
        MainActivityViewModel mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.factory).get(MainActivityViewModel.class);
        mViewModel.getDinosNombres().observe(this, dinosaurios -> {
            dinoListNombres=dinosaurios;
            adp = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dinoListNombres);
            if (adp!= null) {
                mSpinnerdino1.setAdapter(adp);
                mSpinnerdino2.setAdapter(adp);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewMain = inflater.inflate(R.layout.fragment_combate, container, false);

        mSpinnerdino1 = viewMain.findViewById(R.id.mSpinnerdino1);
        mSpinnerdino2 = viewMain.findViewById(R.id.mSpinnerdino2);
        bCombate = viewMain.findViewById(R.id.bCombate);
        bHistorial = viewMain.findViewById(R.id.bHistorial);
        botonCombate();
        botonHistorial();

        return viewMain;
    }

    private void botonHistorial(){
        bHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(context, HistorialCombateActivity.class);
            startActivity(intent);
        });
    }
    private void botonCombate(){
        bCombate.setOnClickListener(v -> {
            String dino1 = mSpinnerdino1.getSelectedItem().toString();
            String dino2 = mSpinnerdino2.getSelectedItem().toString();
            AppExecutors.getInstance().diskIO().execute(() -> {
                dinosaurio1 = mRepository.getDino(dino1);
                dinosaurio2 = mRepository.getDino(dino2);
            });
            combatir();
        });
    }

    public void combatir(){
        if(dinosaurio1!=null && dinosaurio2!=null) {
            Intent intent = new Intent(context, CombateResultActivity.class);
            if (Float.parseFloat(dinosaurio1.getLengthmeters()) < Float.parseFloat(dinosaurio2.getLengthmeters())) {
                intent.putExtra("GANADOR", dinosaurio2.getName());
                AppExecutors.getInstance().diskIO().execute(() -> {
                    mLocalRepository.insertarHistorial(dinosaurio1, dinosaurio2, "Gana dino2");
                    modificarLogroPrimerCombate();
                    cambiarLogro(dinosaurio2);
                });
            }
            if (Float.parseFloat(dinosaurio1.getLengthmeters()) > Float.parseFloat(dinosaurio2.getLengthmeters())) {
                intent.putExtra("GANADOR", dinosaurio1.getName());
                AppExecutors.getInstance().diskIO().execute(() -> {
                    mLocalRepository.insertarHistorial(dinosaurio1, dinosaurio2, "Gana dino1");
                    modificarLogroPrimerCombate();
                    cambiarLogro(dinosaurio1);
                });
            }
            if (Float.parseFloat(dinosaurio1.getLengthmeters()) == Float.parseFloat(dinosaurio2.getLengthmeters())) {
                intent.putExtra("EMPATE1", dinosaurio1.getName());
                intent.putExtra("EMPATE2", dinosaurio2.getName());
                AppExecutors.getInstance().diskIO().execute(() -> {
                    mLocalRepository.insertarHistorial(dinosaurio1, dinosaurio2, "Empate");
                    modificarLogroPrimerCombate();
                    cambiarLogro(dinosaurio1);
                    cambiarLogro(dinosaurio2);
                });
            }
            startActivity(intent);
        }
    }

    public void modificarLogroPrimerCombate(){
        if(mLocalRepository.obtenerLista().size()>=1){
            mRepository.comprobarLogros("Realiza tu primer combate con la aplicación");
        }
    }

    public void cambiarLogro(Dinosaurio dino){
        if(dino.getDiet().equals("Carnivoro")) {
            mRepository.comprobarLogros("Primera victoria de un dinosaurio carnívoro en tu aplicación");
        }
        if(dino.getDiet().equals("Herbivoro")) {
            mRepository.comprobarLogros("Primera victoria de un dinosaurio herbívoro en tu aplicación");
        }
        if(dino.getDiet().equals("Omnivoro")) {
            mRepository.comprobarLogros("Primera victoria de un dinosaurio omnivoro en tu aplicación");
        }
        if(dino.getPeriodname().equals("Jurasico")) {
            mRepository.comprobarLogros("Primera victoria de un dinosaurio del jurásico en tu aplicación");
        }
        if(dino.getPeriodname().equals("Cretacico")) {
            mRepository.comprobarLogros("Primera victoria de un dinosaurio del cretácico en tu aplicación");
        }
        if(dino.getPeriodname().equals("Triasico")) {
            mRepository.comprobarLogros("Primera victoria de un dinosaurio del triásico en tu aplicación");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
