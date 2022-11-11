package es.unex.dinopedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DinosaurioAdapter extends RecyclerView.Adapter<DinosaurioAdapter.ViewHolder> {
    private List<Dinosaurio> mItems = new ArrayList<Dinosaurio>();
    Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Dinosaurio item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public DinosaurioAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // - Inflate the View for every element
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dinosaurio_info, parent, false);

        return new ViewHolder(mContext,v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public void add(Dinosaurio item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public void load(List<Dinosaurio> items){

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public Object getItem(int pos) {
        return mItems.get(pos);
    }

     static class ViewHolder extends RecyclerView.ViewHolder {

         private final Context mContext;

        private final TextView nombre;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            mContext = context;

            // - Get the references to every widget of the Item View

            nombre =  itemView.findViewById(R.id.titleView);
        }

        public void bind(final Dinosaurio dinosaurio, final OnItemClickListener listener) {

            // - Display Nombre in TextView
            nombre.setText(dinosaurio.getName());

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(dinosaurio);
                }
            });
        }
    }

}
