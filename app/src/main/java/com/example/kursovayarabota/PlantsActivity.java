package com.example.kursovayarabota;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlantsActivity extends AppCompatActivity implements PlantAdapter.OnItemClickListener {
    private RecyclerView plantList;
    private PlantAdapter plantAdapter;
    private List<Plant> allPlants;
    private List<Plant> filteredPlants;
    FloatingActionButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

        plantList = findViewById(R.id.plant_list);
        allPlants = loadPlantsFromJson();
        filteredPlants = new ArrayList<>(allPlants);

        plantAdapter = new PlantAdapter(filteredPlants, this);
        plantList.setLayoutManager(new LinearLayoutManager(this));
        plantList.setAdapter(plantAdapter);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        btn = findViewById(R.id.homehome);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<Plant> loadPlantsFromJson() {
        List<Plant> plants = new ArrayList<>();
        try {
            InputStream is = getAssets().open("plants.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("plants");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject plantObject = jsonArray.getJSONObject(i);
                String name = plantObject.getString("name");
                String species = plantObject.getString("species");
                String description = plantObject.getString("description");
                Plant plant = new Plant(name, species,description);
                plants.add(plant);
            }
    } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return plants;
    }

    @Override
    public void onItemClick(Plant plant) {
        // Обработчик клика по элементу списка
    }
}
