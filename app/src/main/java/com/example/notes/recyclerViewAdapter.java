package com.example.notes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHoler> {
    ArrayList<DataModel> data;
    Context context;
    onItemClick onItemClick;

    public recyclerViewAdapter(ArrayList<DataModel> data, Context context, com.example.notes.onItemClick onItemClick) {
        this.data = data;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public recyclerViewAdapter(ArrayList<DataModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public recyclerViewAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_card, parent, false);
        ViewHoler viewHoler = new ViewHoler(view);
        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.tvTitle.setText(data.get(position).getNoteTitle());
        holder.tvDate.setText(data.get(position).getNoteDateTime().toString());
        holder.tvMessage.setText(data.get(position).getNoteMessage());
        //float a = 0.0f;
        int r = new Random().nextInt(255);
        int g = new Random().nextInt(255);
        int b = new Random().nextInt(255);
        String hex = String.format("#73%02x%02x%02x", r, g, b);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.cvNote.setCardBackgroundColor(Color.parseColor(hex));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialCardView cvNote;
        private TextView tvTitle, tvDate;
        private TextView tvMessage;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            cvNote = itemView.findViewById(R.id.cvNote);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null) onItemClick.onClick(view, getAdapterPosition());
        }
    }
}
