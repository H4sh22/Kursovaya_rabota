package com.example.kursovayarabota;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {
    private List<Plant> plants;
    private OnItemClickListener listener;

    public PlantAdapter(List<Plant> plants, OnItemClickListener listener) {
        this.plants = plants;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plants.get(position);
        holder.nameTextView.setText(plant.getName());
        holder.speciesTextView.setText(plant.getSpecies());
        holder.descriptionTextView.setText(plant.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(plant));
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView speciesTextView;
        TextView descriptionTextView;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            speciesTextView = itemView.findViewById(R.id.species_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Plant plant);
    }
}
