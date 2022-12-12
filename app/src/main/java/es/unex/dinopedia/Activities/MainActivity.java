package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import es.unex.dinopedia.Adapters.DinosaurioAdapter;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Fragments.AlbumFragment;
import es.unex.dinopedia.Fragments.CombateFragment;
import es.unex.dinopedia.Fragments.EnciclopediaFragment;
import es.unex.dinopedia.Fragments.FavoritoFragment;
import es.unex.dinopedia.Fragments.MainFragment;
import es.unex.dinopedia.Interfaz.MainActivityInterface;
import es.unex.dinopedia.Networking.ApiListener;
import es.unex.dinopedia.Networking.ApiRunnable;
import es.unex.dinopedia.R;
import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    ActivityMainBinding binding;
    FragmentManager fragmentManager = getSupportFragmentManager();
    DinosaurioAdapter mAdapter = new DinosaurioAdapter(MainActivity.this, item -> {});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase.getInstance(MainActivity.this).getDinosaurioDao().deleteAll();
            DinopediaDatabase.getInstance(MainActivity.this).getLogroDao().deleteAll();
            DinopediaDatabase.getInstance(MainActivity.this).getHistorialCombateDao().deleteAll();
        });
        quitarFavoritos();
        cargaDatos();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cambiarFragment();
    }

    private void cargaDatos(){
        AppExecutors.getInstance().networkIO().execute(new ApiRunnable((new ApiListener() {
            @Override
            public void onDinosauriosLoaded(List<Dinosaurio> dinoList) {
                añadirDinoRoom(dinoList);
            }

            @Override
            public void onLogrosLoaded(List<Logro> logroList) {
                añadirLogroRoom(logroList);
            }
        })));
    }


    private void añadirDinoRoom(List<Dinosaurio> dinoList){
        AppExecutors.getInstance().diskIO().execute(() -> {
            for (int i = 0; i < dinoList.size(); i++) {
                Dinosaurio d = dinoList.get(i);
                DinopediaDatabase.getInstance(MainActivity.this).getDinosaurioDao().insert(d);
            }
            AppExecutors.getInstance().mainThread().execute(()->mAdapter.load(dinoList));
        });
    }

    private void añadirLogroRoom(List<Logro> logroList){
        AppExecutors.getInstance().diskIO().execute(() -> {
            for (int i = 0; i < logroList.size(); i++) {
                Logro l = logroList.get(i);
                DinopediaDatabase.getInstance(MainActivity.this).getLogroDao().insert(l);
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void cambiarFragment(){

        MainFragment mF = new MainFragment(MainActivity.this, binding);
        replaceFragment(mF);

        EnciclopediaFragment eF = new EnciclopediaFragment(MainActivity.this);
        FavoritoFragment fF = new FavoritoFragment(MainActivity.this);
        CombateFragment cF = new CombateFragment(MainActivity.this);
        AlbumFragment aF = new AlbumFragment(MainActivity.this);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.principal:
                    replaceFragment(mF);
                    break;
                case R.id.enciclopedia:
                    replaceFragment(eF);
                    break;
                case R.id.batalla:
                    replaceFragment(cF);
                    break;
                case R.id.favorito:
                    replaceFragment(fF);
                    break;
                case R.id.logros:
                    replaceFragment(aF);
                    break;
            }
            return true;
        });
    }

    @Override
    public void classDinosaurio(Dinosaurio d) {
        Intent intent = new Intent(MainActivity.this, DinosaurioInfoActivity.class);
        intent.putExtra("id", d.getId());
        startActivity(intent);
    }

    public void quitarFavoritos(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(MainActivity.this);
            database.getDinosaurioDao().quitarFavorite();
        });
    }
}