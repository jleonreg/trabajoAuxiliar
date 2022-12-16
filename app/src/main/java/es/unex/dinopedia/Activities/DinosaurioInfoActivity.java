package es.unex.dinopedia.Activities;

import static es.unex.dinopedia.R.*;
import static es.unex.dinopedia.R.id.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dinopedia.Networking.AppContainer;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.ViewModel.DinosaurioInfoActivityViewModel;
import es.unex.dinopedia.Networking.MyApplication;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;


public class DinosaurioInfoActivity extends AppCompatActivity {

    private TextView tNombreD;
    private TextView tDietaD;
    private TextView tLivedInD;
    private TextView tTypeD;
    private TextView tSpeciesD;
    private TextView tPeriodNameD;
    private TextView tLengthMetersD;
    private Bundle bundle;
    private String body;
    private boolean infoDino=false;
    private Switch swFavorito;
    private Button bt;

    private DinosaurioInfoActivityViewModel mViewModel;

    DinopediaDatabase database;
    Dinosaurio d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_info_dinosaurio);
        bundle = getIntent().getExtras();
        long id = bundle.getLong("id");
        swFavorito = findViewById(R.id.sFavorito);
        database = DinopediaDatabase.getInstance(DinosaurioInfoActivity.this);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.dinoInfoFactory).get(DinosaurioInfoActivityViewModel.class);

        botonCompartir();
        AppExecutors.getInstance().diskIO().execute(() -> {
            marcarFavorito();
            cargarVariable();
            d = mViewModel.getDino(id);
            AppExecutors.getInstance().mainThread().execute(() -> {
                actualizarDinosaurio(d);
                mostrarImagenes(d);
                if(d.getFavorite().equals("0"))
                    swFavorito.setChecked(false);
                else
                    swFavorito.setChecked(true);
            });
        });

        swFavorito.setOnClickListener(v -> {
            if (d!=null) {
                d.setFavorite(swFavorito.isChecked()?"1":"0");
                AppExecutors.getInstance().diskIO().execute(() -> mViewModel.actualizarDinosaurio(d));
            }
        });
    }

    private void cargarVariable(){
        Usuario u = mViewModel.getUsuario();
        if(u!=null)
            infoDino=u.isInfoDino();
    }

    private void botonCompartir(){
        bt = findViewById(bCompartir);

        bt.setOnClickListener(v1 -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            body = "¡Mira que dinosaurio más interesante! -> https://acortar.link/RzE6K";
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent, "Share Using"));

        });
    }

    private void actualizarDinosaurio(Dinosaurio d){
        tNombreD = findViewById(tNombre);
        tDietaD = findViewById(tDieta);
        tLivedInD = findViewById(tLivedIn);
        tTypeD = findViewById(tType);
        tSpeciesD = findViewById(tSpecies);
        tPeriodNameD = findViewById(tPeriodName);
        tLengthMetersD = findViewById(tLengthMeters);

        tNombreD.setText(d.getName());
        tDietaD.setText(d.getDiet());
        tLivedInD.setText(d.getLivedin());
        tTypeD.setText(d.getType());
        tSpeciesD.setText(d.getSpecies());
        tPeriodNameD.setText(d.getPeriodname());
        tLengthMetersD.setText(d.getLengthmeters());
    }

    private void marcarFavorito(){
        View v = this.findViewById(android.R.id.content);
        if(mViewModel.getUsuario()!=null)
            swFavorito.setVisibility(v.VISIBLE);
    }

    public void mostrarImagenes(Dinosaurio d){
        if(infoDino){
            View v = this.findViewById(android.R.id.content);
            if(d.getDiet()!=null) {
                if (d.getDiet().equals("Carnivoro")) {
                    ImageView iV = findViewById(iVCarnivoro);
                    tDietaD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
                if (d.getDiet().equals("Herbivoro")) {
                    ImageView iV = findViewById(iVHerbivoro);
                    tDietaD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
                if (d.getDiet().equals("Omnivoro")) {
                    ImageView iV = findViewById(iVOmnivoro);
                    tDietaD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
            }
            if(d.getPeriodname()!=null) {
                if (d.getPeriodname().equals("Jurasico")) {
                    ImageView iV = findViewById(iVJurasico);
                    tPeriodNameD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
                if (d.getPeriodname().equals("Cretacico")) {
                    ImageView iV = findViewById(iVCretacico);
                    tPeriodNameD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
                if (d.getPeriodname().equals("Triasico")) {
                    ImageView iV = findViewById(iVTriasico);
                    tPeriodNameD.setVisibility(v.INVISIBLE);
                    iV.setVisibility(v.VISIBLE);
                }
            }
        }
    }
}