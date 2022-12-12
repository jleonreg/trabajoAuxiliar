package es.unex.dinopedia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Activities.CombateResultActivity;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Adapters.DinosaurioAdapter;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Activities.HistorialCombateActivity;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class CombateFragment extends Fragment {

    private Spinner mSpinnerdino1;
    private Spinner mSpinnerdino2;
    private List<String> dinoListNombres = new ArrayList<>();
    private static Context context;
    private ArrayAdapter adp;
    private Dinosaurio dinosaurio1;
    private Dinosaurio dinosaurio2;
    private Button bCombate;
    private Button bHistorial;

    public CombateFragment(Context cont) {
        context = cont;
    }

    public static CombateFragment newInstance(Context cont) {
        CombateFragment fragment = new CombateFragment(cont);
        Bundle args = new Bundle();
        context = cont;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewMain = inflater.inflate(R.layout.fragment_combate, container, false);

        mSpinnerdino1 = viewMain.findViewById(R.id.mSpinnerdino1);
        mSpinnerdino2 = viewMain.findViewById(R.id.mSpinnerdino2);
        bCombate = viewMain.findViewById(R.id.bCombate);
        bHistorial = viewMain.findViewById(R.id.bHistorial);

        AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(context);
            dinoListNombres=database.getDinosaurioDao().getNombres();
        });

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
                DinopediaDatabase database = DinopediaDatabase.getInstance(context);
                dinosaurio1=database.getDinosaurioDao().getDinosaurioString(dino1);
                dinosaurio2=database.getDinosaurioDao().getDinosaurioString(dino2);
            });
            combatir();
        });
    }

    public void cargarSpinner(){
        adp = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dinoListNombres);
        if (adp!= null) {
            mSpinnerdino1.setAdapter(adp);
            mSpinnerdino2.setAdapter(adp);
        }
    }

    public void combatir(){
        if(dinosaurio1!=null && dinosaurio2!=null) {
            Intent intent = new Intent(context, CombateResultActivity.class);
            if (Float.parseFloat(dinosaurio1.getLengthmeters()) < Float.parseFloat(dinosaurio2.getLengthmeters())) {
                intent.putExtra("GANADOR", dinosaurio2.getName());
                AppExecutors.getInstance().diskIO().execute(() -> {
                    DinopediaDatabase database = DinopediaDatabase.getInstance(context);
                    HistorialCombate hC = new HistorialCombate(dinosaurio1.getName(), dinosaurio2.getName(), "Gana dino2");
                    database.getHistorialCombateDao().insert(hC);

                    modificarLogroPrimerCombate();
                    cambiarLogro(dinosaurio2);
                });
            }
            if (Float.parseFloat(dinosaurio1.getLengthmeters()) > Float.parseFloat(dinosaurio2.getLengthmeters())) {
                intent.putExtra("GANADOR", dinosaurio1.getName());
                AppExecutors.getInstance().diskIO().execute(() -> {
                    DinopediaDatabase database = DinopediaDatabase.getInstance(context);
                    HistorialCombate hC = new HistorialCombate(dinosaurio1.getName(), dinosaurio2.getName(), "Gana dino1");
                    database.getHistorialCombateDao().insert(hC);

                    modificarLogroPrimerCombate();
                    cambiarLogro(dinosaurio1);
                });
            }
            if (Float.parseFloat(dinosaurio1.getLengthmeters()) == Float.parseFloat(dinosaurio2.getLengthmeters())) {
                intent.putExtra("EMPATE1", dinosaurio1.getName());
                intent.putExtra("EMPATE2", dinosaurio2.getName());
                AppExecutors.getInstance().diskIO().execute(() -> {
                    DinopediaDatabase database = DinopediaDatabase.getInstance(context);
                    HistorialCombate hC = new HistorialCombate(dinosaurio1.getName(), dinosaurio2.getName(), "Empate");
                    database.getHistorialCombateDao().insert(hC);

                    modificarLogroPrimerCombate();
                    cambiarLogro(dinosaurio1);
                    cambiarLogro(dinosaurio2);
                });
            }
            startActivity(intent);
        }
    }

    public void modificarLogroPrimerCombate(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(context);
            if(database.getHistorialCombateDao().getAll().size()>=1){
                Logro l = database.getLogroDao().getLogro("Realiza tu primer combate con la aplicación");
                l.setChecked("1");
                database.getLogroDao().update(l);
            }
        });
    }

    public void cambiarLogro(Dinosaurio dino){
        if(dino.getDiet().equals("Carnivoro")) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                DinopediaDatabase database2 = DinopediaDatabase.getInstance(context);
                    Logro l = database2.getLogroDao().getLogro("Primera victoria de un dinosaurio carnívoro en tu aplicación");
                    l.setChecked("1");
                    database2.getLogroDao().update(l);

            });
        }
        if(dino.getDiet().equals("Herbivoro")) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                DinopediaDatabase database2 = DinopediaDatabase.getInstance(context);
                    Logro l = database2.getLogroDao().getLogro("Primera victoria de un dinosaurio herbívoro en tu aplicación");
                    l.setChecked("1");
                    database2.getLogroDao().update(l);

            });
        }
        if(dino.getDiet().equals("Omnivoro")) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                DinopediaDatabase database2 = DinopediaDatabase.getInstance(context);
                    Logro l = database2.getLogroDao().getLogro("Primera victoria de un dinosaurio omnivoro en tu aplicación");
                    l.setChecked("1");
                    database2.getLogroDao().update(l);

            });
        }
        if(dino.getPeriodname().equals("Jurasico")) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                DinopediaDatabase database2 = DinopediaDatabase.getInstance(context);
                    Logro l = database2.getLogroDao().getLogro("Primera victoria de un dinosaurio del jurásico en tu aplicación");
                    l.setChecked("1");
                    database2.getLogroDao().update(l);

            });
        }
        if(dino.getPeriodname().equals("Cretacico")) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                DinopediaDatabase database2 = DinopediaDatabase.getInstance(context);
                    Logro l = database2.getLogroDao().getLogro("Primera victoria de un dinosaurio del cretácico en tu aplicación");
                    l.setChecked("1");
                    database2.getLogroDao().update(l);

            });
        }
        if(dino.getPeriodname().equals("Triasico")) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                DinopediaDatabase database2 = DinopediaDatabase.getInstance(context);
                    Logro l = database2.getLogroDao().getLogro("Primera victoria de un dinosaurio del triásico en tu aplicación");
                    l.setChecked("1");
                    database2.getLogroDao().update(l);

            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarSpinner();
    }
}
