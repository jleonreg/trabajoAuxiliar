package es.unex.dinopedia.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.unex.dinopedia.ViewModel.AlbumFragmentViewModel;
import es.unex.dinopedia.Networking.AppContainer;
import es.unex.dinopedia.Adapters.LogroAdapter;
import es.unex.dinopedia.Networking.MyApplication;
import es.unex.dinopedia.R;

public class AlbumFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LogroAdapter mAdapter;
    private Context context;
    private AlbumFragmentViewModel mViewModel;

    public AlbumFragment(Context cont) {
        context = cont;
        mAdapter = new LogroAdapter(context, item -> {});
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContainer appContainer = ((MyApplication) AlbumFragment.this.getActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory)appContainer.albumFragmentFactory).get(AlbumFragmentViewModel.class);
        mViewModel.getLogroActivos().observe(this, logros -> {
            mAdapter.swap(logros);
        });
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

        return viewMain;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}