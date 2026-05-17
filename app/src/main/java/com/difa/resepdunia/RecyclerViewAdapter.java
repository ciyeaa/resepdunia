package com.difa.resepdunia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.difa.resepdunia.databinding.ItemLayoutBinding;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.GridViewHolder> {

    private final List<Meal> meals;
    private final Context context;

    public RecyclerViewAdapter(Context context, List<Meal> meals) {
        this.meals = meals;
        this.context = context;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemLayoutBinding binding = ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new GridViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {

        Meal currentMeal = meals.get(position);

        holder.binding.tvMeal.setText(currentMeal.getStrMeal());
        holder.binding.tvCategory.setText(context.getString(R.string.food_category));

        Glide.with(context)
                .load(currentMeal.getStrMealThumb())
                .centerCrop()
                .placeholder(R.drawable.broken_image_24)
                .into(holder.binding.imgMeal);

        holder.itemView.setOnClickListener(view -> {

            Intent i = new Intent(context, DetailActivity.class);

            i.putExtra("i_idMeal", currentMeal.getIdMeal());

            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {

        private final ItemLayoutBinding binding;

        public GridViewHolder(ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}