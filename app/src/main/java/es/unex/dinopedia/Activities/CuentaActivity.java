package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.LocalDataSource;
import es.unex.dinopedia.LocalRepository;
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
    private LocalRepository mLocalRepository;

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
        mLocalRepository = LocalRepository.getInstance(DinopediaDatabase.getInstance(CuentaActivity.this).getHistorialCombateDao(), DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao(), LocalDataSource.getInstance());

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
        Usuario u = mLocalRepository.getUsuario();
        if(u.isModo()){
            swModo.setChecked(true);
        }
        else{
            swModo.setChecked(false);
        }
    }

    private void activarSwitch(){
        Usuario u = mLocalRepository.getUsuario();
        if(u!=null) {
            if (u.isInfoDino()) {
                swInfoDino.setChecked(true);
            } else {
                swInfoDino.setChecked(false);
            }
        }
    }

    private void botonCambiar(){
        bCambiar.setOnClickListener(view -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(CuentaActivity.this);
            Usuario u = new Usuario(mLocalRepository.getUsuario().getId(), eNUsuario.getText().toString(), mLocalRepository.getUsuario().isModo(), mLocalRepository.getUsuario().isInfoDino());
            database.getUsuarioDao().update(u);
        });
    }

    private void botonCerrarSesion(){
        bCerrarSesion.setOnClickListener(view -> {
            mLocalRepository.borrarTodo();
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
            Usuario aux = mLocalRepository.getUsuario();
            if (aux.isModo() == false) {
                mLocalRepository.actualizarModoUsuario(aux.getId(), true);
            } else {
                mLocalRepository.actualizarModoUsuario(aux.getId(), false);
            }
            if (swModo.isChecked()) {
                CuentaActivity.this.setDayNight(0);
            } else {
                CuentaActivity.this.setDayNight(1);
            }
        });
    }

    private void switchInfoDino() {
        swInfoDino.setOnClickListener(v -> {
            if(swInfoDino.isChecked()){
                Usuario u = mLocalRepository.getUsuario();
                u.setInfoDino(true);
                DinopediaDatabase.getInstance(CuentaActivity.this).getUsuarioDao().update(u);
            }
            else{
                Usuario u = mLocalRepository.getUsuario();
                u.setInfoDino(false);
                mLocalRepository.actualizar(u);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        cambiarSwitch();
    }
}