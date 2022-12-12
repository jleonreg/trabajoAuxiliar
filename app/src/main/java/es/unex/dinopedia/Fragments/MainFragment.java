package es.unex.dinopedia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Activities.CuentaActivity;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Adapters.DinosaurioAdapter;
import es.unex.dinopedia.Activities.IniciarSesionActivity;
import es.unex.dinopedia.R;
import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.DinopediaDatabase;


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
        initUsuario();
        dinosaurioDelDia = 0;
    }

    private void initUsuario(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(context);
            if(database.getUsuarioDao().getUsuario()!=null)
                sesionIniciada=true;
            else
                sesionIniciada=false;
        });
    }

    private void initDinosaurio(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(context);
            dinoList = database.getDinosaurioDao().getAll();
            AppExecutors.getInstance().mainThread().execute(()->copiaDinosaurio=dinoList);
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initDinosaurio();
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
        bCuenta.setOnClickListener(v -> AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(context);
            Intent intent = new Intent(context, CuentaActivity.class);
            intent.putExtra("USUARIO", database.getUsuarioDao().getUsuario().getName());
            AppExecutors.getInstance().mainThread().execute(()->startActivityForResult(intent, 2));
        }));
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
                DinopediaDatabase database = DinopediaDatabase.getInstance(context);
                if(database.getUsuarioDao().getUsuario()!=null)
                    sesionIniciada=true;
                else
                    sesionIniciada=false;
            });
        }
        if(requestCode == 2){
            AppExecutors.getInstance().diskIO().execute(() -> {
                DinopediaDatabase database = DinopediaDatabase.getInstance(context);
                if(database.getUsuarioDao().getUsuario()!=null)
                    sesionIniciada=true;
                else
                    sesionIniciada=false;
            });
        }
    }
}