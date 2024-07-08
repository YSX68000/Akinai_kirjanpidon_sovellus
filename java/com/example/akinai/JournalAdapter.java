package com.example.akinai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

   private List<JournalEntry> journalEntries;

   public JournalAdapter(List<JournalEntry> journalEntries) {
      this.journalEntries = journalEntries;
   }

   @NonNull
   @Override
   public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal_entry, parent, false);
      return new JournalViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
      JournalEntry entry = journalEntries.get(position);
      holder.dateTextView.setText(entry.getDate());
      holder.dateTextView2.setText(entry.getDate2());
      holder.categoryTextView.setText(entry.getCategory());
      holder.itemname.setText(entry.getItemName());
      holder.amountTextView.setText(String.valueOf(entry.getAmount()) + "€");
   }

   @Override
   public int getItemCount() {
      return journalEntries.size();
   }

   static class JournalViewHolder extends RecyclerView.ViewHolder {
      TextView dateTextView, dateTextView2,categoryTextView, amountTextView, itemname;

      JournalViewHolder(View itemView) {
         super(itemView);
         dateTextView = itemView.findViewById(R.id.dateTextView);
         dateTextView2 = itemView.findViewById(R.id.dateTextView2);
         categoryTextView = itemView.findViewById(R.id.categoryTextView);
         amountTextView = itemView.findViewById(R.id.amountTextView);
         itemname = itemView.findViewById(R.id.itemname);
      }
   }
}