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
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.R;

public class HistorialCombateAdapter extends RecyclerView.Adapter<HistorialCombateAdapter.ViewHolder> {
    private List<HistorialCombate> mItems = new ArrayList<HistorialCombate>();
    Context mContext;

    public interface OnItemClickListener {
        void onItemClick(HistorialCombate item);
    }

    private final OnItemClickListener listener;

    public HistorialCombateAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historial_combate_info, parent, false);
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

    public void add(HistorialCombate item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
    }

    public void load(List<HistorialCombate> items){
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

        public void bind(final HistorialCombate hC, final OnItemClickListener listener) {
            nombre.setText(hC.getDinosaurio1()+ " - " + hC.getDinosaurio2() + " - " + hC.getEstado());
            itemView.setOnClickListener(v -> listener.onItemClick(hC));
        }
    }

    public void swap(List<HistorialCombate> dataset){
        mItems=dataset;
        notifyDataSetChanged();
    }
}
