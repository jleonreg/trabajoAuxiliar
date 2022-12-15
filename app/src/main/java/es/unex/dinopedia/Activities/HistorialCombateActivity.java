package es.unex.dinopedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;
import es.unex.dinopedia.Adapters.HistorialCombateAdapter;
import es.unex.dinopedia.AppContainer;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Fragments.CombateFragment;
import es.unex.dinopedia.HistorialCombateViewModel;
import es.unex.dinopedia.LocalDataSource;
import es.unex.dinopedia.LocalRepository;
import es.unex.dinopedia.MainActivityViewModel;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.MyApplication;
import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class HistorialCombateActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HistorialCombateAdapter mAdapter;

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
        HistorialCombateViewModel mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.factory).get(HistorialCombateViewModel.class);
        mViewModel.getHistoriales().observe(this, historiales -> {
            mAdapter.swap(historiales);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}