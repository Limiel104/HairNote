package com.example.hairnote;

import static com.example.hairnote.IngredientDetails.INGREDIENT_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class IngredientsRecViewAdapter extends RecyclerView.Adapter<IngredientsRecViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Ingredient> ingredientsAll = new ArrayList<>();
    private Context context;



    public IngredientsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(ingredients.get(position).getName());

        holder.parentIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, IngredientDetails.class);
                intent.putExtra(INGREDIENT_ID_KEY,ingredients.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.ingredientsAll = new ArrayList<>(ingredients);
        Collections.sort(ingredients, Ingredient::compareTo);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Ingredient> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(ingredientsAll);
            }else {
                for (Ingredient ingredient: ingredientsAll) {
                    if (ingredient.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(ingredient);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ingredients.clear();
            ingredients.addAll((Collection<? extends Ingredient>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName;
        private RelativeLayout parentIngredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.ingredientNameTitle);
            parentIngredient = itemView.findViewById(R.id.parentIngredient);
        }
    }


}
