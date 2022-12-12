package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.R;

public class CombateResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combate_result);
        marcarResultado();
    }

    private void marcarResultado(){
        String ganador = getIntent().getStringExtra("GANADOR");
        String empate1 = getIntent().getStringExtra("EMPATE1");
        String empate2 = getIntent().getStringExtra("EMPATE2");
        AppExecutors.getInstance().diskIO().execute(() -> {
            TextView tResultado = findViewById(R.id.tResultado);
            if(ganador!=null){
                AppExecutors.getInstance().mainThread().execute(()->tResultado.setText("El ganador del combate es: "+ ganador));
            }
            else{
                AppExecutors.getInstance().mainThread().execute(()->tResultado.setText("El combate ha resultado en empate: " + empate1 + " y " + empate2 + " tienen la misma fuerza"));
            }
        });
    }
}