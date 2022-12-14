package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Model.Usuario;
import es.unex.dinopedia.Networking.DataSource;
import es.unex.dinopedia.Networking.Repository;
import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class IniciarSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        botonConfirmar();
    }

    private void botonConfirmar(){
        Button bConfirmar = findViewById(R.id.bConfirmar);
        final EditText eDName = findViewById(R.id.eTIniciarSesion);
        bConfirmar.setOnClickListener(view -> {
            AppExecutors.getInstance().diskIO().execute(() -> {
                DinopediaDatabase database = DinopediaDatabase.getInstance(IniciarSesionActivity.this);
                Usuario u = new Usuario();
                u.setName(eDName.getText().toString());
                u.setInfoDino(false);
                database.getUsuarioDao().insert(u);

                Repository mRepository = Repository.getInstance(DinopediaDatabase.getInstance(IniciarSesionActivity.this).getDinosaurioDao(), DinopediaDatabase.getInstance(IniciarSesionActivity.this).getLogroDao(), DataSource.getInstance());
                mRepository.comprobarLogros("Inicia Sesión en la aplicación");
            });
            finish();
        });
    }
}