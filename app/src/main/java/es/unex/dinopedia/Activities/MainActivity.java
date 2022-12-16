package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import es.unex.dinopedia.Networking.AppContainer;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Networking.MyApplication;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Fragments.AlbumFragment;
import es.unex.dinopedia.Fragments.CombateFragment;
import es.unex.dinopedia.Fragments.EnciclopediaFragment;
import es.unex.dinopedia.Fragments.FavoritoFragment;
import es.unex.dinopedia.Fragments.MainFragment;
import es.unex.dinopedia.Interfaz.MainActivityInterface;
import es.unex.dinopedia.R;
import es.unex.dinopedia.ViewModel.MainActivityViewModel;
import es.unex.dinopedia.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    private ActivityMainBinding binding;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.mainFactory).get(MainActivityViewModel.class);

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