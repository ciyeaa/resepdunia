package com.difa.resepdunia;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.difa.resepdunia.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerViewAdapter adapter;
    private List<Meal> mealList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        mealList = new ArrayList<>();
        setupRecyclerView();

        loadData();

        binding.swipeRefreshLayout.setOnRefreshListener(this::loadData);
    }

    private void setupRecyclerView() {
        adapter = new RecyclerViewAdapter(this, mealList);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.swipeRefreshLayout.setRefreshing(true);

        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Dessert";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        mealList.clear();
                        JSONArray jsonArray = response.getJSONArray("meals");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            
                            Meal meal = new Meal(
                                    data.getString("idMeal"),
                                    data.getString("strMeal"),
                                    data.getString("strMealThumb")
                            );
                            mealList.add(meal);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Gagal memproses data", Toast.LENGTH_SHORT).show();
                    } finally {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                },
                error -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(this, "Masalah koneksi internet!", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(request);
    }
}
