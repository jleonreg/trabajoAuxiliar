package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.List;
import es.unex.dinopedia.Adapters.HistorialCombateAdapter;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class HistorialCombateActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HistorialCombateAdapter mAdapter;
    private List<HistorialCombate> listaCombates;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_combate);
        mAdapter = new HistorialCombateAdapter(HistorialCombateActivity.this, item -> {});

        mRecyclerView = findViewById(R.id.rHistorial);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(HistorialCombateActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(HistorialCombateActivity.this);
            listaCombates = database.getHistorialCombateDao().getAll();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter.getItemCount()==0)
            if(listaCombates!=null)
                mAdapter.load(listaCombates);
    }
}