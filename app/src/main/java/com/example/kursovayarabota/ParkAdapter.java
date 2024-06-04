package com.example.kursovayarabota;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.ParkViewHolder> {
    private List<Park> parks;
    private OnItemClickListener listener;

    public ParkAdapter(List<Park> parks, OnItemClickListener listener) {
        this.parks = parks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ParkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_park, parent, false);
        return new ParkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkViewHolder holder, int position) {
        Park park = parks.get(position);
        holder.nameTextView.setText(park.getComment());
        holder.addressTextView.setText(park.getAddress());
        holder.commentTextView.setText(park.getName());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(park));
    }

    @Override
    public int getItemCount() {
        return parks.size();
    }

    static class ParkViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView commentTextView;

        public ParkViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            addressTextView = itemView.findViewById(R.id.address_text_view);
            commentTextView = itemView.findViewById(R.id.comment_text_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Park park);
    }
}
