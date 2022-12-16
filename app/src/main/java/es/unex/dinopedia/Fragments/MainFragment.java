package es.unex.dinopedia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import es.unex.dinopedia.Networking.AppContainer;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Activities.CuentaActivity;
import es.unex.dinopedia.ViewModel.MainFragmentViewModel;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Adapters.DinosaurioAdapter;
import es.unex.dinopedia.Activities.IniciarSesionActivity;
import es.unex.dinopedia.Networking.MyApplication;
import es.unex.dinopedia.R;
import es.unex.dinopedia.databinding.ActivityMainBinding;


public class MainFragment extends Fragment implements View.OnClickListener {

    private View vista;
    private Context context;
    ActivityMainBinding binding;
    private DinosaurioAdapter mAdapter;
    private DateFormat formatoFecha;
    private Date fecha;
    private boolean sesionIniciada;
    private int dinosaurioDelDia;
    private List<Dinosaurio> dinoList;
    private List<Dinosaurio> copiaDinosaurio;
    private Button bCuenta;
    private Button bIniciarSesion;
    private MainFragmentViewModel mViewModel;

    public MainFragment(){
    }

    public MainFragment(Context cont, ActivityMainBinding bind) {
        context = cont;
        dinoList=new ArrayList<>();
        mAdapter = new DinosaurioAdapter(context, item -> {});
        formatoFecha = new SimpleDateFormat("dd/MM/yy");
        long momento = System.currentTimeMillis();
        fecha = new Date(momento);
        binding = bind;
        dinosaurioDelDia = 0;
    }

    private void initUsuario(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            if(mViewModel.getUsuario()!=null)
                sesionIniciada=true;
            else
                sesionIniciada=false;
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContainer appContainer = ((MyApplication)  MainFragment.this.getActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.mainFragmentFactory).get(MainFragmentViewModel.class);

        initUsuario();

        mViewModel.getDinos().observe(this, dinosaurios -> {
            dinoList=dinosaurios;
            copiaDinosaurio=dinosaurios;
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        vista = rootView;
        bIniciarSesion = rootView.findViewById(R.id.bIniciarSesion);
        bCuenta = rootView.findViewById(R.id.bCuenta);
        botonIniciarSesion();
        botonCuenta();
        return rootView;
    }

    private void botonIniciarSesion(){
        bIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(context, IniciarSesionActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    private void botonCuenta(){
        bCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(context, CuentaActivity.class);
            AppExecutors.getInstance().diskIO().execute(() -> intent.putExtra("USUARIO", mViewModel.getUsuario().getName()));
            AppExecutors.getInstance().mainThread().execute(()->startActivityForResult(intent, 2));
        });
    }

    @Override
    public void onClick(View v) {}

    public void elegirDinosaurio(){
        String nombre;
        Random random_method = new Random();
        if(copiaDinosaurio!=null) {
            if (copiaDinosaurio.size() != 0) {
                int nuevoDinosaurio;
                long momento = System.currentTimeMillis();
                Date fechaActual = new Date(momento);
                String fechaActualS = formatoFecha.format(fechaActual);
                String fechaS = formatoFecha.format(fecha);
                if (!fechaS.equals(fechaActualS)) {
                    fecha = fechaActual;
                    nuevoDinosaurio = random_method.nextInt(copiaDinosaurio.size());
                    dinosaurioDelDia = nuevoDinosaurio;
                }
                Dinosaurio d = copiaDinosaurio.get(dinosaurioDelDia);
                nombre = d.getName();

                if (copiaDinosaurio.size() != 0) {
                    final TextView dinoDia = vista.findViewById(R.id.nombreDino);
                    dinoDia.setText(nombre);
                }
            }
        }
    }

    public void mostrarBotones(){
        binding.bottomNavigationView.getMenu().getItem(3).setVisible(sesionIniciada);
        binding.bottomNavigationView.getMenu().getItem(4).setVisible(sesionIniciada);
        Button bCuenta = vista.findViewById(R.id.bCuenta);
        Button bIniciarSesion = vista.findViewById(R.id.bIniciarSesion);
        if(sesionIniciada==true) {
            bCuenta.setVisibility(vista.VISIBLE);
            bIniciarSesion.setVisibility(vista.INVISIBLE);
        }
        else{
            bCuenta.setVisibility(vista.INVISIBLE);
            bIniciarSesion.setVisibility(vista.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mostrarBotones();
        if(dinoList.size()!=0){
            elegirDinosaurio();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            AppExecutors.getInstance().diskIO().execute(() -> {
                if(mViewModel.getUsuario()!=null)
                    sesionIniciada=true;
                else
                    sesionIniciada=false;
            });
        }
        if(requestCode == 2){
            AppExecutors.getInstance().diskIO().execute(() -> {
                if(mViewModel.getUsuario()!=null)
                    sesionIniciada=true;
                else
                    sesionIniciada=false;
            });
        }
    }
}