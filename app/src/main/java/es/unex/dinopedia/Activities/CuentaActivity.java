package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class CuentaActivity extends AppCompatActivity {

    private String usuario;
    private Button bCambiar;
    private Button bCerrarSesion;
    private Button bAyuda;
    private Button bContacto;
    private EditText eNUsuario;
    private Switch swModo;
    private Switch swInfoDino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
        bCambiar = findViewById(R.id.bCambiar);
        bCerrarSesion = findViewById(R.id.bCerrarSesion);
        bAyuda = findViewById(R.id.bAyuda);
        bContacto = findViewById(R.id.bContactar);
        eNUsuario = findViewById(R.id.eTUsuario);
        swModo = findViewById(R.id.swModo);
        swInfoDino = findViewById(R.id.sInfoDino);

        activarSwitch();

        usuario=getIntent().getStringExtra("USUARIO");
        eNUsuario.setText(usuario);

        botonCambiar();
        botonCerrarSesion();
        botonAyuda();
        botonContacto();
        switchModo();
        switchInfoDino();
    }

    private void setDayNight (int modo){
        if (modo == 0){
            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void cambiarSwitch(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            Switch swModo = findViewById(R.id.swModo);
            Usuario u = DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().getUsuario();
            if(u.isModo()){
                AppExecutors.getInstance().mainThread().execute(()->swModo.setChecked(true));
            }
            else{
                AppExecutors.getInstance().mainThread().execute(()->swModo.setChecked(false));
            }
        });
    }

    private void activarSwitch(){
        AppExecutors.getInstance().diskIO().execute(() -> {
        Usuario u = DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().getUsuario();
        if(u!=null) {
            if (u.isInfoDino()) {
                AppExecutors.getInstance().mainThread().execute(() -> swInfoDino.setChecked(true));
            } else {
                AppExecutors.getInstance().mainThread().execute(() -> swInfoDino.setChecked(false));
            }
        }
    });
    }

    private void botonCambiar(){
        bCambiar.setOnClickListener(view -> AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(CuentaActivity.this);
            Usuario u = new Usuario(database.getUsuarioDao().getUsuario().getId(), eNUsuario.getText().toString(), database.getUsuarioDao().getUsuario().isModo(), database.getUsuarioDao().getUsuario().isInfoDino());
            database.getUsuarioDao().update(u);
        }));
    }

    private void botonCerrarSesion(){
        bCerrarSesion.setOnClickListener(view -> {
            AppExecutors.getInstance().diskIO().execute(() -> {
                DinopediaDatabase database = DinopediaDatabase.getInstance(CuentaActivity.this);
                database.getUsuarioDao().deleteAll();
            });
            finish();
        });
    }

    private void botonAyuda(){
        bAyuda.setOnClickListener(view -> {
            Intent intent = new Intent(CuentaActivity.this, AyudaActivity.class);
            startActivity(intent);
        });
    }

    private void botonContacto(){
        bContacto.setOnClickListener(view -> {
            Intent intent = new Intent(CuentaActivity.this, ContactoActivity.class);
            startActivity(intent);
        });
    }

    private void switchModo() {
        swModo.setOnClickListener(view -> {
            AppExecutors.getInstance().diskIO().execute(() -> {
                Usuario aux = DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().getUsuario();
                if(aux.isModo()==false){
                    DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().updateModoUsuario(aux.getId(), true);
                }
                else{
                    DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().updateModoUsuario(aux.getId(), false);
                }
            });
            if (swModo.isChecked()){
                CuentaActivity.this.setDayNight(0);
            }
            else{
                CuentaActivity.this.setDayNight(1);
            }
        });
    }

    private void switchInfoDino() {
        swInfoDino.setOnClickListener(v -> {
            if(swInfoDino.isChecked()){
                AppExecutors.getInstance().diskIO().execute(() -> {
                    Usuario u = DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().getUsuario();
                    u.setInfoDino(true);
                    DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().update(u);
                });
            }
            else{
                AppExecutors.getInstance().diskIO().execute(() -> {
                    Usuario u = DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().getUsuario();
                    u.setInfoDino(false);
                    DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().update(u);
                });
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        cambiarSwitch();
    }
}