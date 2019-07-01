package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<ContactData> mDataset;
    private Context mContext;

//    public interface  OnItemClickListener {
//        void onItemClick(View v, int position);
//    }
//
//    private OnItemClickListener mListener = null;
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mListener = listener;
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number;

        //ViewHolder
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
//            number = (TextView) view.findViewById(R.id.number);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
//                        ContactData data = mDataset.get(pos);

//                        mDataset.set(pos, new ContactData(data.getNumber(), data.getName(), data.getEmail()));
//                        notifyItemChanged(pos);
                        Intent intent = new Intent(mContext, PersonInfoActivity.class);
                        intent.putExtra("name", mDataset.get(pos).getName());
                        intent.putExtra("number", mDataset.get(pos).getNumber());
                        if(mDataset.get(pos).hasEmail()) {
                            intent.putExtra("email", mDataset.get(pos).getEmail());
                        }
                        if(mDataset.get(pos).hasPhoto()) {
                            intent.putExtra("photo", mDataset.get(pos).getPhoto());
                        }
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    public RecyclerAdapter(Context context, ArrayList<ContactData> myData){
        this.mDataset = myData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {

        holder.name.setText(mDataset.get(position).getName());
//        holder.number.setText(mDataset.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}