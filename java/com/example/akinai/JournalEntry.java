package com.example.akinai;

public class JournalEntry {
   private String date;

   private String date2;
   private String category;
   private String itemName; // 商品名を追加
   private double amount;

   public JournalEntry(String date, String date2,String category, String itemName, double amount) {
      this.date = date;
      this.date2 = date2;
      this.category = category;
      this.itemName = itemName;
      this.amount = amount;
   }

   public String getDate() {
      return date;
   }

   public String getDate2() {
      return date2;
   }


   public String getCategory() {
      return category;
   }

   public String getItemName() {
      return itemName;
   }

   public double getAmount() {
      return amount;
   }
}
