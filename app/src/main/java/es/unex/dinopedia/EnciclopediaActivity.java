package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EnciclopediaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enciclopedia);

        TextView tHolaEnciclopedia = findViewById(R.id.tHolaEnciclopedia);
    }


}
