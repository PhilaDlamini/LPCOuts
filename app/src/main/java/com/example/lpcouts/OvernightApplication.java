package com.example.lpcouts;

public class OvernightApplication {
  String address, companion, hostMobile, hostName, name, tutor, year;
  
  public OvernightApplication() {}
  
  public OvernightApplication(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) {
    this.name = paramString1;
    this.tutor = paramString2;
    this.year = paramString3;
    this.address = paramString4;
    this.companion = paramString5;
    this.hostName = paramString6;
    this.hostMobile = paramString7;
  }
  
  public String getAddress() { return this.address; }
  
  public String getCompanion() { return this.companion; }
  
  public String getHostMobile() { return this.hostMobile; }
  
  public String getHostName() { return this.hostName; }
  
  public String getTutor() { return this.tutor; }
  
  public String getUserName() { return this.name; }
  
  public String getYear() { return this.year; }
}
