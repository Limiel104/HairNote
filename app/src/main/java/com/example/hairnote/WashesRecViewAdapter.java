package com.example.hairnote;


import static com.example.hairnote.WashDetails.WASH_ID_KEY;

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

public class WashesRecViewAdapter extends RecyclerView.Adapter<WashesRecViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<Wash> washes = new ArrayList<>();
    private ArrayList<Wash> washesAll = new ArrayList<>();
    private Context context;

    public WashesRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washes_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtWashTitle.setText(washes.get(position).getDate());

        holder.parentWash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WashDetails.class);
                intent.putExtra(WASH_ID_KEY,washes.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return washes.size();
    }

    public void setWashes(ArrayList<Wash> washes) {
        this.washes = washes;
        this.washesAll = new ArrayList<>(washes);
        Collections.sort(washes,Wash::compareTo);
        Collections.reverse(washes);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Wash> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(washesAll);
            }else {
                for (Wash wash: washesAll) {
                    if (wash.getDate().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(wash);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            washes.clear();
            washes.addAll((Collection<? extends Wash>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtWashTitle;
        private RelativeLayout parentWash;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWashTitle = itemView.findViewById(R.id.washDateTitle);
            parentWash = itemView.findViewById(R.id.parentWash);
        }
    }

}
