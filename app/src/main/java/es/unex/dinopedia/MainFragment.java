package es.unex.dinopedia;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.DinosaurioDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final Context context;
    private DinosaurioAdapter mAdapter;
    private DateFormat formatoFecha;
    private Date fecha;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Dinosaurio> dinoList = new ArrayList<>();
    private List<Dinosaurio> copiaDinosaurio= new ArrayList<>(dinoList);

    public void lista(List<Dinosaurio> dino){
        //dinoList=new ArrayList<>(dino);
        DinosaurioDatabase database = DinosaurioDatabase.getInstance(context);
        dinoList=database.getDao().getAll();
    }

    public MainFragment(Context cont) {
        context = cont;
        mAdapter = new DinosaurioAdapter(context, new DinosaurioAdapter.OnItemClickListener() {
            @Override public void onItemClick(Dinosaurio item) {
                //Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
            }
        });
        formatoFecha = new SimpleDateFormat("dd/MM/yy");
        long momento = System.currentTimeMillis();
        fecha = new Date(momento);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2, Context cont) {
        MainFragment fragment = new MainFragment(cont);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Button bIniciarSesion = rootView.findViewById(R.id.bIniciarSesion);

        bIniciarSesion.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.frame_container, fragment).addToBackStack(null).commit();

    }

    public void elegirDinosaurio(){
        Random random_method = new Random();
        int valor = copiaDinosaurio.size();
        int nuevoDinosaurio = random_method.nextInt(copiaDinosaurio.size());
        long momento = System.currentTimeMillis();
        Date fechaActual = new Date(momento);
        String salida = formatoFecha.format(fechaActual);
        if(fecha!=fechaActual){
            //mostrar nuevo dino con el int generado aleatorio
            //fecha=fechaActual;
            Log.d("fecha", salida);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.load(dinoList);
        if(dinoList.size()!=0){
            elegirDinosaurio();
        }
    }
}