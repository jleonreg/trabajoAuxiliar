package es.unex.dinopedia.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import es.unex.dinopedia.AppExecutors.AppExecutors;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Adapters.LogroAdapter;
import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

public class AlbumFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LogroAdapter mAdapter;
    private Context context;
    private List<Logro> listaLogros;
    public AlbumFragment(Context cont) {
        context = cont;
        mAdapter = new LogroAdapter(context, item -> {});
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewMain = inflater.inflate(R.layout.fragment_album, container, false);

        mRecyclerView = viewMain.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LogroAdapter(context, item -> {});

        mRecyclerView.setAdapter(mAdapter);

        AppExecutors.getInstance().diskIO().execute(() -> {
            DinopediaDatabase database = DinopediaDatabase.getInstance(context);
            listaLogros = database.getLogroDao().getCheck();
        });

        return viewMain;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter.getItemCount()==0)
            if(listaLogros!=null)
                mAdapter.load(listaLogros);
    }
}