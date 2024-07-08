package com.example.akinai;
public class ProfitAndLossEntry {
   private String account;
   private double amount;

   public ProfitAndLossEntry(String account, double amount) {
      this.account = account;
      this.amount = amount;
   }

   public String getAccount() {
      return account;
   }

   public double getAmount() {
      return amount;
   }
}
