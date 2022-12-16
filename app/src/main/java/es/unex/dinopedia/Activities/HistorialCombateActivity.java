package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import es.unex.dinopedia.Adapters.HistorialCombateAdapter;
import es.unex.dinopedia.Networking.AppContainer;
import es.unex.dinopedia.ViewModel.HistorialCombateActivityViewModel;
import es.unex.dinopedia.Networking.MyApplication;
import es.unex.dinopedia.R;

public class HistorialCombateActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HistorialCombateAdapter mAdapter;

    private HistorialCombateActivityViewModel mViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_historial_combate);
        mAdapter = new HistorialCombateAdapter(HistorialCombateActivity.this, item -> {});

        mRecyclerView = findViewById(R.id.rHistorial);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(HistorialCombateActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.historialCombateFactory).get(HistorialCombateActivityViewModel.class);
        mViewModel.getHistoriales().observe(this, historiales -> {
            mAdapter.swap(historiales);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}