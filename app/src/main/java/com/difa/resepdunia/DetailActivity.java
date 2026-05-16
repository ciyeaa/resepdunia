package com.difa.resepdunia;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.difa.resepdunia.databinding.ActivityDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String idMeal = getIntent().getStringExtra("i_idMeal");

        if (idMeal != null) {
            loadDetail(idMeal);
        } else {
            Toast.makeText(this, "ID tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadDetail(String id) {

        binding.progressBar.setVisibility(View.VISIBLE);

        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                response -> {

                    try {

                        JSONArray jsonArray = response.getJSONArray("meals");

                        JSONObject data = jsonArray.getJSONObject(0);

                        String mealName = data.getString("strMeal");
                        String instruction = data.getString("strInstructions");
                        String photoUrl = data.getString("strMealThumb");

                        binding.toolbar.setTitle(mealName);
                        binding.tvName.setText(mealName);
                        binding.tvInstruction.setText(instruction);

                        Glide.with(this)
                                .load(photoUrl)
                                .centerCrop()
                                .into(binding.ivImage);

                    } catch (JSONException e) {

                        e.printStackTrace();

                        Toast.makeText(
                                this,
                                "Gagal mengolah data",
                                Toast.LENGTH_SHORT
                        ).show();

                    } finally {

                        binding.progressBar.setVisibility(View.GONE);
                    }
                },

                error -> {

                    binding.progressBar.setVisibility(View.GONE);

                    Toast.makeText(
                            this,
                            "Masalah koneksi !",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        queue.add(request);
    }
}