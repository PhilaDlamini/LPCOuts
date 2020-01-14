package com.example.lpcouts;

public class SpecialExtension implements Comparable<SpecialExtension> {
  String address, companion, name, reason, returnTime;
  
  public SpecialExtension() {

  }
  
  public SpecialExtension(String extension) {
    String[] extensionData = extension.split(";");
    this.name = extensionData[0];
    this.reason = extensionData[1];
    this.companion = extensionData[2];
    this.address = extensionData[3];
    this.returnTime = extensionData[4];
  }
  
  public SpecialExtension(String name, String reason, String companion, String address, String returnTime) {
    this.reason = reason;
    this.companion = companion;
    this.address = address;
    this.returnTime = returnTime;
    this.name = name;
  }
  
  public int compareTo(SpecialExtension specialExtension) {
    return this.name.compareTo(specialExtension.getName());
  }
  
  public String getAddress() {
    return this.address;
  }
  
  public String getCompanion() {
    return this.companion;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getReason() {
    return this.reason;
  }
  
  public String getReturnTime() {
    return this.returnTime;
  }
  
  public String toString() {
    return name + ";" + reason + ";" + companion + ";" + address + ";" + returnTime + ";null";
  }
}
