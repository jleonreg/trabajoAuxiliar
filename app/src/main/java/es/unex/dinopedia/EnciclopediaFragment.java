package es.unex.dinopedia;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.unex.dinopedia.database.DinosaurioCRUD;
import es.unex.dinopedia.roomdb.DinosaurioDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnciclopediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnciclopediaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DinosaurioAdapter mAdapter;
    private final Context context;
    private List<Dinosaurio> dinoList = new ArrayList<>();

    public void lista(List<Dinosaurio> dino){
        dinoList=new ArrayList<>(dino);
        //Log.d("DINO", dino.get(0).getName());
        //
        //mAdapter.add(new Dinosaurio());
    }

    public EnciclopediaFragment(Context cont, List<Dinosaurio> dino) {
        // Required empty public constructor
        context = cont;
        //dinoList = dino;
        mAdapter = new DinosaurioAdapter(context, new DinosaurioAdapter.OnItemClickListener() {
            @Override public void onItemClick(Dinosaurio item) {
                //Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnciclopediaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnciclopediaFragment newInstance(String param1, String param2, Context context, List<Dinosaurio> dino) {
        EnciclopediaFragment fragment = new EnciclopediaFragment(context, dino);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewMain = inflater.inflate(R.layout.fragment_enciclopedia, container, false);

        mRecyclerView = viewMain.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // - Set a Linear Layout Manager to the RecyclerView
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // - Create a new Adapter for the RecyclerView
        // specify an adapter (see also next example)
        View view = mRecyclerView.findViewById(android.R.id.content);

        //mAdapter.add(new Dinosaurio());
        //mAdapter.load(dinoList);

        // - Attach the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        //DinosaurioCRUD crud = DinosaurioCRUD.getInstance(context);
        //DinosaurioDatabase.getInstance(context);

        return viewMain;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary
        //lista(dinoList);
        //if(dinoList.size()!=0)
        //    mAdapter.add(dinoList.get(0));
        mAdapter.load(dinoList);
        //if (mAdapter.getItemCount() == 0)
            //loadItems();
    }

    private void loadItems() {
        DinosaurioCRUD crud = DinosaurioCRUD.getInstance(context);
        List<Dinosaurio> items = crud.getAll();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Dinosaurio> items = DinosaurioDatabase.getInstance(context).getDao().getAll();
                requireActivity().runOnUiThread( () -> mAdapter.load(items));
            }
        });

    }
}