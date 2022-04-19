package com.example.hairnote;


import static com.example.hairnote.WashDetails.WASH_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class WashesRecViewAdapter extends RecyclerView.Adapter<WashesRecViewAdapter.ViewHolder> {

    private ArrayList<Wash> washes = new ArrayList<>();
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
        holder.txtWashTitle.setText(washes.get(position).getDate()); //!!!!!!! tutaj trzeba pamietac

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
        notifyDataSetChanged();
        Collections.reverse(washes);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtWashTitle;
        private RelativeLayout parentWash;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWashTitle = itemView.findViewById(R.id.txtWashTitle);
            parentWash = itemView.findViewById(R.id.parentWash);
        }
    }

}
