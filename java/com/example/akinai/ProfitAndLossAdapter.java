package com.example.akinai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfitAndLossAdapter extends RecyclerView.Adapter<ProfitAndLossAdapter.EntryViewHolder> {

   private List<ProfitAndLossEntry> entries;

   public ProfitAndLossAdapter(List<ProfitAndLossEntry> entries) {
      this.entries = entries;
   }

   @NonNull
   @Override
   public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profit_and_loss_entry, parent, false);
      return new EntryViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
      ProfitAndLossEntry entry = entries.get(position);
      holder.bind(entry);
   }

   @Override
   public int getItemCount() {
      return entries.size();
   }

   static class EntryViewHolder extends RecyclerView.ViewHolder {

      private TextView accountTextView, amountTextView;

      public EntryViewHolder(@NonNull View itemView) {
         super(itemView);
         accountTextView = itemView.findViewById(R.id.accountTextView);
         amountTextView = itemView.findViewById(R.id.amountTextView);
      }

      public void bind(ProfitAndLossEntry entry) {
         accountTextView.setText(entry.getAccount());
         amountTextView.setText(String.valueOf(entry.getAmount()));
      }
   }
}
