package com.example.akinai;


public class User {

   public String email;

   public String uid;
   public String name;
   public String address;
   public String yt;

   public String token;

   public User(){

   }

   public User(String address,String email, String name,String uid,String yt)
   {
      this.email = email;
      this.uid = uid;
      this.name = name;
      this.address = address;
      this.yt = yt;
   }
}
