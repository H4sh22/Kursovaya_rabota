package com.example.kursovayarabota;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParksActivity extends AppCompatActivity implements ParkAdapter.OnItemClickListener {
    private RecyclerView parkList;
    private ParkAdapter parkAdapter;
    private List<Park> allParks;
    private DatabaseReference databaseReference;

    FloatingActionButton btn,btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parks);

        // Инициализация RecyclerView
        parkList = findViewById(R.id.park_list);
        allParks = new ArrayList<>();
        parkAdapter = new ParkAdapter(allParks,this);
        parkList.setLayoutManager(new LinearLayoutManager(this));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        parkList.setAdapter(parkAdapter);
        btn = findViewById(R.id.homehome3);
        btn1 = findViewById(R.id.addpark);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParksActivity.this,CategoryAddActivity.class));
            }
        });

        // Получение ссылки на раздел "Parks" в базе данных Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Parks");

        // Добавление слушателя событий для получения данных из Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Очистка списка перед добавлением новых данных
                allParks.clear();
                // Перебор всех дочерних элементов в разделе "Parks"
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Получение значения каждого дочернего элемента
                    String name = snapshot.child("name").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String comment = snapshot.child("comment").getValue(String.class);
                    // Создание объекта Park и добавление его в список
                    Park park = new Park(name, address, comment);
                    allParks.add(park);
                }
                // Уведомление адаптера об изменениях в данных
                parkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибки при загрузке данных
                Toast.makeText(ParksActivity.this, "Failed to load parks: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(Park park) {

    }
}
