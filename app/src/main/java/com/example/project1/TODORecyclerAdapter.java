package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TODORecyclerAdapter extends RecyclerView.Adapter<TODORecyclerAdapter.TODOViewHolder> {
    private ArrayList<TODOData> mDataset;
    private OnItemClickListener mListner = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListner = listener;
    }

    public TODORecyclerAdapter(ArrayList<TODOData> myData) {
        this.mDataset = myData;
    }

    public class TODOViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public TODOViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListner != null) {
                            mListner.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public TODOViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_recycler_item, parent, false);
        return new TODOViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TODORecyclerAdapter.TODOViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.title.setTextSize(14);
        if(mDataset.get(position).isDone()) {
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.title.setTextColor(Color.LTGRAY);
        }
        else {
            holder.title.setPaintFlags(0);
            holder.title.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
