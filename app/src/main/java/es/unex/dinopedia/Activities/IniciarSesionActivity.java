package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import es.unex.dinopedia.Networking.AppContainer;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.ViewModel.IniciarSesionActivityViewModel;
import es.unex.dinopedia.Networking.MyApplication;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.R;

public class IniciarSesionActivity extends AppCompatActivity {

    private IniciarSesionActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        botonConfirmar();

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.iniciarSesionFactory).get(IniciarSesionActivityViewModel.class);
    }

    private void botonConfirmar(){
        Button bConfirmar = findViewById(R.id.bConfirmar);
        final EditText eDName = findViewById(R.id.eTIniciarSesion);
        bConfirmar.setOnClickListener(view -> {
            Usuario u = new Usuario();
            u.setName(eDName.getText().toString());
            u.setInfoDino(false);
            AppExecutors.getInstance().diskIO().execute(() -> {
                if(mViewModel.getUsuario()!=null)
                    mViewModel.actualizar(u);
                else
                    mViewModel.insertar(u);
                mViewModel.marcarLogro();
            });
            finish();
        });
    }
}