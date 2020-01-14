package com.example.lpcouts;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class UserData {
  public static final String ACCOUNT_TYPE = "Account type";
  public static final String BLOCK = "Block";
  public static final String EMAIL = "Email";
  public static final String EXTENSION_TYPE = "Extension type";
  private static final String FILE_NAME = "UserData.txt";
  public static final String FRAGMENT = "Fragment";
  public static final String LINK_SENT = "Verification link sent";
  public static final String NAME = "Name";
  public static final String ON_EXTENSION = "On extension";
  public static final String PIC_PATH = "Pic path";
  public static final String REGULAR_EXTENSION = "Regular extension";
  public static final String REGULAR_EXTENSION_INSTANCE = "Regular extension instance";
  public static final String RETURN_TIME = "Return time";
  public static final String RETURN_TO_FRAGMENT = "Return to fragment";
  public static final String ROOM = "Room";
  public static final String SPECIAL_EXTENSION = "Special extension";
  public static final String SPECIAL_EXTENSION_INSTANCE = "Special extension instance";
  public static final String STATUS = "Status";
  public static final String USERS_APPLIED_FOR_SPECIAL_EXTENSION = "Users applied for special extension";
  public static final String USERS_NOT_SIGNED_BACK_IN = "Users not signed back in";
  public static final String USERS_ON_REGULAR_EXTENSION = "Users on regular extension";
  public static final String USERS_ON_SPECIAL_EXTENSION = "Users on special extension";
  public static final String USERS_SIGNED_OUT = "Users signed out";
  static Context context;
  
  private static SharedPreferences sharedPreferences;
  
  public static void assignSharedPreferences(Context paramContext) {
    sharedPreferences = paramContext.getSharedPreferences("UserData.txt", 0);
    context = paramContext;
  }
  
  public static String getData(String paramString) {
    return sharedPreferences.getString(paramString, null);
  }
  
  public static ArrayList<Outs> getLateUsers() {
    ArrayList<Outs> arrayList = new ArrayList();
    try {
      Iterator<String> iterator = sharedPreferences.getStringSet(USERS_NOT_SIGNED_BACK_IN, null).iterator();
      while (iterator.hasNext())
        arrayList.add(new Outs(iterator.next())); 
      return arrayList;
    } catch (Exception exception) {
      return arrayList;
    } 
  }
  
  public static ArrayList<SpecialExtension> getUsersAppliedForSpecialExtension() {
    ArrayList<SpecialExtension> arrayList = new ArrayList();
    try {
      Iterator<String> iterator = sharedPreferences.getStringSet(USERS_APPLIED_FOR_SPECIAL_EXTENSION, null).iterator();
      while (iterator.hasNext())
        arrayList.add(new SpecialExtension(iterator.next())); 
      return arrayList;
    } catch (Exception exception) {
      return arrayList;
    } 
  }
  
  public static ArrayList<Extension> getUsersOnRegularExtension() {
    ArrayList<Extension> arrayList = new ArrayList();
    try {
      Iterator<String> iterator = sharedPreferences.getStringSet(USERS_ON_REGULAR_EXTENSION, null).iterator();
      while (iterator.hasNext())
        arrayList.add(new Extension(iterator.next())); 
      return arrayList;
    } catch (Exception exception) {
      return arrayList;
    } 
  }
  
  public static ArrayList<SpecialExtension> getUsersOnSpecialExtension() {
    ArrayList<SpecialExtension> arrayList = new ArrayList();
    try {
      Iterator<String> iterator = sharedPreferences.getStringSet(USERS_ON_SPECIAL_EXTENSION, null).iterator();
      while (iterator.hasNext())
        arrayList.add(new SpecialExtension(iterator.next())); 
      return arrayList;
    } catch (Exception exception) {
      return arrayList;
    } 
  }
  
  public static ArrayList<Outs> getUsersSignedOut() {
    ArrayList<Outs> arrayList = new ArrayList();
    try {
      Iterator<String> iterator = sharedPreferences.getStringSet(USERS_SIGNED_OUT, null).iterator();
      while (iterator.hasNext())
        arrayList.add(new Outs(iterator.next())); 
      return arrayList;
    } catch (Exception exception) {
      return arrayList;
    } 
  }
  
  public static boolean isUserOnExtension() {
    return sharedPreferences.getBoolean(ON_EXTENSION, false);
  }
  
  public static void linkSent(boolean paramBoolean) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean("Verification link sent", paramBoolean);
    editor.commit();
  }
  
  public static boolean linkSent() {
    return sharedPreferences.getBoolean("Verification link sent", false);
  }
  
  public static void onRegularExtension(boolean paramBoolean, Extension paramExtension) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(ON_EXTENSION, paramBoolean);
    if (paramBoolean) {
      editor.putString(EXTENSION_TYPE, REGULAR_EXTENSION);
      editor.putString(REGULAR_EXTENSION_INSTANCE, paramExtension.toString());
    } 
    editor.commit();
  }
  
  public static void onSpecialExtension(boolean paramBoolean, SpecialExtension paramSpecialExtension) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(ON_EXTENSION, paramBoolean);
    if (paramBoolean) {
      editor.putString(EXTENSION_TYPE, SPECIAL_EXTENSION);
      editor.putString(SPECIAL_EXTENSION_INSTANCE, paramSpecialExtension.toString());
    } 
    editor.commit();
  }
  
  public static void resetFragments() {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(RETURN_TO_FRAGMENT, false);
    editor.putString(FRAGMENT, null);
    editor.commit();
  }
  
  public static void returnToFragment(String paramString) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(FRAGMENT, paramString);
    editor.putBoolean(RETURN_TO_FRAGMENT, true);
    editor.commit();
  }
  
  public static void saveAccountType(String paramString) { saveData(ACCOUNT_TYPE, paramString); }
  
  public static void saveBlock(String paramString) { saveData(BLOCK, paramString); }
  
  private static void saveData(String paramString1, String paramString2) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(paramString1, paramString2);
    editor.apply();
  }
  
  public static void saveEmail(String paramString) { saveData(EMAIL, paramString); }
  
  public static void saveLateUsers(ArrayList<Outs> paramArrayList) {
    HashSet<String> hashSet = new HashSet();
    Iterator<Outs> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      hashSet.add((iterator.next()).toString());
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putStringSet(USERS_NOT_SIGNED_BACK_IN, hashSet);
    editor.commit();
  }
  
  public static void savePicPath(String paramString) { saveData(PIC_PATH, paramString); }
  
  public static void saveReturnTime(String paramString) { saveData(RETURN_TIME, paramString); }
  
  public static void saveRoom(String paramString) { saveData(ROOM, paramString); }
  
  public static void saveSignedOutUsers(ArrayList<Outs> paramArrayList) {
    HashSet<String> hashSet = new HashSet();
    Iterator<Outs> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      hashSet.add((iterator.next()).toString());
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putStringSet(USERS_SIGNED_OUT, hashSet);
    editor.commit();
  }
  
  public static void saveStatus(String paramString) { saveData(STATUS, paramString); }
  
  public static void saveUserName(String paramString) { saveData(NAME, paramString); }
  
  public static void saveUsersAppliedForSpecialExtension(ArrayList<SpecialExtension> paramArrayList) {
    HashSet<String> hashSet = new HashSet();
    Iterator<SpecialExtension> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      hashSet.add((iterator.next()).toString());
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putStringSet(USERS_APPLIED_FOR_SPECIAL_EXTENSION, hashSet);
    editor.commit();
  }
  
  public static void saveUsersOnRegularExtension(ArrayList<Extension> paramArrayList) {
    HashSet<String> hashSet = new HashSet();
    Iterator<Extension> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      hashSet.add((iterator.next()).toString());
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putStringSet(USERS_ON_REGULAR_EXTENSION, hashSet);
    editor.commit();
  }
  
  public static void saveUsersOnSpecialExtension(ArrayList<SpecialExtension> paramArrayList) {
    HashSet<String> hashSet = new HashSet();
    Iterator<SpecialExtension> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      hashSet.add((iterator.next()).toString());
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putStringSet(USERS_ON_SPECIAL_EXTENSION, hashSet);
    editor.commit();
  }
  
  public static void saveUsersSignedOut(ArrayList<Outs> paramArrayList) {
    HashSet<String> hashSet = new HashSet();
    Iterator<Outs> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      hashSet.add((iterator.next()).toString());
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putStringSet(USERS_SIGNED_OUT, hashSet);
    editor.commit();
  }
  
  public static boolean shouldReturn() { return sharedPreferences.getBoolean(RETURN_TO_FRAGMENT, false); }
}
