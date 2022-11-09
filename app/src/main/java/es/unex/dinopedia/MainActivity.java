package es.unex.dinopedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unex.dinopedia.DinosaurioAdapter;

import es.unex.dinopedia.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new MainFragment());

        final List<Dinosaurio> dino = new ArrayList<>();
        EnciclopediaFragment eF = new EnciclopediaFragment(MainActivity.this, dino);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //JsonReader reader = new JsonReader();
                Log.d("DINO", "DINO");
                //Log.d("DINO -> ", reader.toString());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.jurassicpark)));
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while (true) {
                    try {
                        if (!((receiveString = bufferedReader.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stringBuilder.append("\n").append(receiveString);
                }

                String read = stringBuilder.toString();

                /*String read = "[{\n" +
                        "    \"name\": \"aardonyx\",\n" +
                        "    \"diet\": \"herbivorous\",\n" +
                        "    \"lived_in\": \"South Africa\",\n" +
                        "    \"type\": \"sauropod\",\n" +
                        "    \"species\": \"celestae\",\n" +
                        "    \"period_name\": \"Early Jurassic\",\n" +
                        "    \"length_meters;\": \"8.0\"\n" +
                        "  }]";

                 */
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        eF.lista(Arrays.asList(new Gson().fromJson(read, Dinosaurio[].class)));
                    }
                });
            }
        });
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.principal:
                    replaceFragment(new MainFragment());
                    break;
                case R.id.enciclopedia:
                    replaceFragment(eF);
                    break;
                case R.id.batalla:
                    replaceFragment(new CombateFragment());
                    break;
                case R.id.favorito:
                    replaceFragment(new FavoritoFragment());
                    break;
                case R.id.logros:
                    replaceFragment(new AlbumFragment());
                    break;
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}