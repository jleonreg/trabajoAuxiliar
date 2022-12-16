package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import es.unex.dinopedia.Networking.AppContainer;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.ViewModel.CuentaActivityViewModel;
import es.unex.dinopedia.Networking.MyApplication;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.R;

public class CuentaActivity extends AppCompatActivity {

    private String usuario;
    private Button bCambiar;
    private Button bCerrarSesion;
    private Button bAyuda;
    private Button bContacto;
    private EditText eNUsuario;
    private Switch swModo;
    private Switch swInfoDino;
    private CuentaActivityViewModel mViewModel;

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

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.cuentaFactory).get(CuentaActivityViewModel.class);

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
        Switch swModo = findViewById(R.id.swModo);
        AppExecutors.getInstance().diskIO().execute(() -> {
            Usuario u = mViewModel.getUsuario();
            if(u.isModo()){
                AppExecutors.getInstance().mainThread().execute(() -> swModo.setChecked(true));
            }
            else{
                AppExecutors.getInstance().mainThread().execute(() -> swModo.setChecked(false));
            }
        });
    }

    private void activarSwitch(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            Usuario u = mViewModel.getUsuario();
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
        bCambiar.setOnClickListener(view -> {
            AppExecutors.getInstance().diskIO().execute(() -> {
                Usuario u = new Usuario(mViewModel.getUsuario().getId(), eNUsuario.getText().toString(), mViewModel.getUsuario().isModo(), mViewModel.getUsuario().isInfoDino());
                mViewModel.actualizar(u);
            });
        });
    }

    private void botonCerrarSesion(){
        bCerrarSesion.setOnClickListener(view -> {
            AppExecutors.getInstance().diskIO().execute(() -> mViewModel.borrarTodo());
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
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Usuario aux = mViewModel.getUsuario();
                    if (aux.isModo() == false) {
                        mViewModel.actualizarModoUsuario(aux.getId(), true);
                    } else {
                        mViewModel.actualizarModoUsuario(aux.getId(), false);
                    }
                    if (swModo.isChecked()) {
                        AppExecutors.getInstance().mainThread().execute(() -> CuentaActivity.this.setDayNight(0));
                    } else {
                        AppExecutors.getInstance().mainThread().execute(() -> CuentaActivity.this.setDayNight(1));
                    }
                }
            });
        });
    }

    private void switchInfoDino() {
        swInfoDino.setOnClickListener(v -> {
            if(swInfoDino.isChecked()){
                AppExecutors.getInstance().diskIO().execute(() -> {
                    Usuario u = mViewModel.getUsuario();
                    u.setInfoDino(true);
                    mViewModel.actualizar(u);
                });
            }
            else{
                AppExecutors.getInstance().diskIO().execute(() -> {
                    Usuario u = mViewModel.getUsuario();
                    u.setInfoDino(false);
                    mViewModel.actualizar(u);
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