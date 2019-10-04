package com.example.webbrowser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<String> mHistoryList;
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView1;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
        }
    }

    // Takes in url List
    public HistoryAdapter(ArrayList<String> historyList) {
        mHistoryList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        HistoryViewHolder hvh = new HistoryViewHolder(v);
        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        String currentItem = mHistoryList.get(position);

        holder.mTextView1.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }
}
