package com.example.akinai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

   // ジャーナルエントリのリストを保持するフィールド
   private List<JournalEntry> journalEntries;

   // コンストラクタでジャーナルエントリのリストを受け取る
   public JournalAdapter(List<JournalEntry> journalEntries) {
      this.journalEntries = journalEntries;
   }

   // ViewHolderの生成時に呼び出されるメソッド
   @NonNull
   @Override
   public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      // レイアウトインフレータを使用してアイテムビューを生成
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal_entry, parent, false);
      return new JournalViewHolder(view);
   }

   // ViewHolderにデータをバインドするメソッド
   @Override
   public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
      // 現在のポジションのジャーナルエントリを取得
      JournalEntry entry = journalEntries.get(position);
      // ViewHolderのテキストビューにデータを設定
      holder.dateTextView.setText(entry.getDate());
      holder.categoryTextView.setText(entry.getCategory());
      holder.amountTextView.setText(String.valueOf(entry.getAmount()) + "€");
   }

   // アイテムの総数を返すメソッド
   @Override
   public int getItemCount() {
      return journalEntries.size();
   }

   // RecyclerViewの各アイテムを保持するViewHolderクラス
   static class JournalViewHolder extends RecyclerView.ViewHolder {
      TextView dateTextView, categoryTextView, amountTextView;

      // ViewHolderのコンストラクタ
      JournalViewHolder(View itemView) {
         super(itemView);
         // アイテムビュー内のテキストビューを初期化
         dateTextView = itemView.findViewById(R.id.dateTextView);
         categoryTextView = itemView.findViewById(R.id.categoryTextView);
         amountTextView = itemView.findViewById(R.id.amountTextView);
      }
   }
}
