package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private ArrayAdapter<City> cityAdapter;
    private int editPosition = -1;

    @Override
    public void add_edit_City(City city) {
        if (editPosition < 0) {
            dataList.add(city);
        } else {
            dataList.set(editPosition, city);
            editPosition = -1;
        }
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                view.setOnClickListener(view1 -> {
                    editPosition = position;
                    City city = dataList.get(position);
                    AddCityFragment dialog = new AddCityFragment(city);
                    dialog.show(getSupportFragmentManager(), "Edit City");
                });
                return view;
            }
        };
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(view -> {
            AddCityFragment dialog = new AddCityFragment();
            dialog.show(getSupportFragmentManager(), "Add City");
        });
    }
}