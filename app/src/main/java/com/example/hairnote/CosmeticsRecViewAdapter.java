package com.example.hairnote;

import static com.example.hairnote.CosmeticDetails.COSMETIC_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CosmeticsRecViewAdapter extends RecyclerView.Adapter<CosmeticsRecViewAdapter.ViewHolder> {

    private static final String TAG = "CosmeticRecViewAdapter";

    private ArrayList<Cosmetic> cosmetics = new ArrayList<>();
    private ArrayList<Cosmetic> cosmeticsAll = new ArrayList<>();
    private Context mContext;

    public CosmeticsRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cosmetics_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: Called");
        holder.txtName.setText(cosmetics.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(cosmetics.get(position).getImgPath())
                .into(holder.imgCosmetic);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, cosmetics.get(position).getName() + "Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, CosmeticDetails.class);
                intent.putExtra(COSMETIC_ID_KEY,cosmetics.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cosmetics.size();
    }

    public void setCosmetics(ArrayList<Cosmetic> cosmetics) {
        this.cosmetics = cosmetics;
        this.cosmeticsAll = new ArrayList<>(cosmetics);
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Cosmetic> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(cosmeticsAll);
            }else {
                for (Cosmetic cosmetic: cosmeticsAll) {
                    if (cosmetic.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(cosmetic);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            cosmetics.clear();
            cosmetics.addAll((Collection<? extends Cosmetic>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private ImageView imgCosmetic;
        private TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgCosmetic = itemView.findViewById(R.id.imgCosmetic);
            txtName = itemView.findViewById(R.id.txtCosmeticName);
        }
    }

}
