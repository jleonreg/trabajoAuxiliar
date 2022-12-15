package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import es.unex.dinopedia.Adapters.DinosaurioAdapter;
import es.unex.dinopedia.Adapters.LogroAdapter;
import es.unex.dinopedia.AppContainer;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.LocalDataSource;
import es.unex.dinopedia.LocalRepository;
import es.unex.dinopedia.MainActivityViewModel;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Fragments.AlbumFragment;
import es.unex.dinopedia.Fragments.CombateFragment;
import es.unex.dinopedia.Fragments.EnciclopediaFragment;
import es.unex.dinopedia.Fragments.FavoritoFragment;
import es.unex.dinopedia.Fragments.MainFragment;
import es.unex.dinopedia.Interfaz.MainActivityInterface;
import es.unex.dinopedia.MyApplication;
import es.unex.dinopedia.Networking.ApiListener;
import es.unex.dinopedia.Networking.ApiRunnable;
import es.unex.dinopedia.Networking.DataSource;
import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.R;
import es.unex.dinopedia.databinding.ActivityMainBinding;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    ActivityMainBinding binding;
    FragmentManager fragmentManager = getSupportFragmentManager();
    Repository mRepository;
    LocalRepository mLocalRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepository = Repository.getInstance(DinopediaDatabase.getInstance(MainActivity.this).getDinosaurioDao(), DinopediaDatabase.getInstance(MainActivity.this).getLogroDao(), DataSource.getInstance());
        mLocalRepository = LocalRepository.getInstance(DinopediaDatabase.getInstance(MainActivity.this).getHistorialCombateDao(), DinopediaDatabase.getInstance(MainActivity.this).getUsuarioDao(), LocalDataSource.getInstance());

        AppExecutors.getInstance().diskIO().execute(() -> {
            mRepository.limpiar();
            mLocalRepository.limpiar();
            mRepository.quitarFavoritos();
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cambiarFragment();
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
}