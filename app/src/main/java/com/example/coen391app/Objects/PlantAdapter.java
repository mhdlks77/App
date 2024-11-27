package com.example.coen391app.Objects;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coen391app.PlantDetailsActivity;
import com.example.coen391app.R;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<Plant> plantList;

    public PlantAdapter(List<Plant> plantList){
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plantList.get(position);
        holder.plantNameTextView.setText(plant.getNickname());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PlantDetailsActivity.class);
            intent.putExtra("plantNickname", plant.getNickname());
            intent.putExtra("plantScientificName", plant.getScientificName());
            intent.putExtra("plantCommonName", plant.getCommonName());
            intent.putExtra("plantDescription", plant.getDescription());
            intent.putExtra("plantAge", plant.getAge());
            intent.putExtra("plantTemperature", plant.getTemperature());
            intent.putExtra("plantSoilMoistureLevel", plant.getSoilMoistureLevel());
            intent.putExtra("plantUvLight", plant.getUvLighting());
            intent.putExtra("plantRecommendedTemperature", plant.getTemplate().getRecommendedTemp());
            intent.putExtra("plantRecommendedSoilMoistureLevel", plant.getRecommendedSoilMoisture());
            intent.putExtra("plantRecommendedUvLight", plant.getRecommendedUvLight());
            v.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return plantList.size();
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder{
        TextView plantNameTextView;
        TextView plantTypeTextView;

        PlantViewHolder(View itemView){
            super(itemView);
            plantNameTextView = itemView.findViewById(R.id.plantNameTextView);
            plantTypeTextView = itemView.findViewById(R.id.plantTypeTextView);
        }
    }
}
