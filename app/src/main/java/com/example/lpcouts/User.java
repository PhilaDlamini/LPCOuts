package com.example.lpcouts;

public class User {
  private String block;
  private String email;
  private String fullName;
  private String room;
  
  public User() {

  }
  
  public User(String paramString1, String paramString2, String paramString3, String paramString4) {
    this.fullName = paramString1;
    this.email = paramString2;
    this.block = paramString3;
    this.room = paramString4;
  }
  
  public String getBlock() { return this.block; }
  
  public String getEmail() { return this.email; }
  
  public String getFullName() { return this.fullName; }
  
  public String getRoom() { return this.room; }
}
