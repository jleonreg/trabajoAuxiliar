package es.unex.dinopedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.R;

public class DinosaurioAdapter extends RecyclerView.Adapter<DinosaurioAdapter.ViewHolder> {
    private List<Dinosaurio> mItems = new ArrayList<Dinosaurio>();
    Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Dinosaurio item);
    }

    private final OnItemClickListener listener;

    public DinosaurioAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dinosaurio_info, parent, false);
        return new ViewHolder(mContext,v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);
    }

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
            nombre =  itemView.findViewById(R.id.tLogro);
        }

        public void bind(final Dinosaurio dinosaurio, final OnItemClickListener listener) {
            nombre.setText(dinosaurio.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(dinosaurio));
        }
    }

}
