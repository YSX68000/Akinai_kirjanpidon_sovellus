package com.example.akinai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaxAdapter extends RecyclerView.Adapter<TaxAdapter.TaxViewHolder> {

   private List<TaxEntry> taxEntries;

   public TaxAdapter(List<TaxEntry> taxEntries) {
      this.taxEntries = taxEntries;
   }

   @NonNull
   @Override
   public TaxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tax_entry, parent, false);
      return new TaxViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull TaxViewHolder holder, int position) {
      TaxEntry taxEntry = taxEntries.get(position);
      holder.amountTextView.setText(String.valueOf(taxEntry.getAmount()));
   }

   @Override
   public int getItemCount() {
      return taxEntries.size();
   }

   static class TaxViewHolder extends RecyclerView.ViewHolder {

      private TextView amountTextView;

      public TaxViewHolder(@NonNull View itemView) {
         super(itemView);
         amountTextView = itemView.findViewById(R.id.amountTextView);
      }
   }
}
