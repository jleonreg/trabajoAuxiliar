package es.unex.dinopedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bEnciclopedia = findViewById(R.id.bEnciclopedia);
        Button bCombate = findViewById(R.id.bCombate);

        bEnciclopedia.setOnClickListener(view -> {
            //Snackbar.make(view, "Remplaza con una acción", Snackbar.LENGTH_LONG)
            //       .setAction("Action", null).show();
            Intent intent = new Intent(MainActivity.this, EnciclopediaActivity.class);
            startActivity(intent);
        });


        bCombate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Remplaza con una acción", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, CombateActivity.class);
                startActivity(intent);
            }
        });
    }
}