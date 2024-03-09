package com.furrioxx.connexiongsb.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    private int id;
    private String surname;
    private String name;
    private String mail;
    private String adress;
    private String cp;
    private String ville;
    private String statut;
    private String ppLink;
    private String token;
    private String TAG = "User";

    public static enum Role{
        VISITEUR,
        COMPTABLE,
        ADMIN
    }

    public User(int id, String surname, String name, String mail, String adress, String cp, String ville, String statut, String ppLink, String token){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.mail = mail;
        this.adress = adress;
        this.cp = cp;
        this.ville = ville;
        this.statut = statut;
        this.ppLink = ppLink;
        this.token = token;
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.surname = in.readString();
        this.id = in.readInt();
        this.mail = in.readString();
        this.adress = in.readString();
        this.cp = in.readString();
        this.ville = in.readString();
        this.statut = in.readString();
        this.ppLink = in.readString();
        this.token = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Integer getId(){
        return this.id;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getName(){
        return this.name;
    }
    public String getMail(){
        return this.mail;
    }
    public String getAdress(){
        return this.adress;
    }
    public String getCp(){
        return this.cp;
    }
    public String getVille(){
        return this.ville;
    }
    public Role getStatut(){
        Role role = null;
        if(this.statut.equals("visiteur")){
            role = role.VISITEUR;
        } else if (this.statut.equals("comptable")) {
            role = role.COMPTABLE;
        }else{
            role = role.ADMIN;
        }
        return role;
    }
    public String getPpLink(){
        return this.ppLink;
    }

    public String getToken(){
        return this.token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeInt(id);
        parcel.writeString(mail);
        parcel.writeString(adress);
        parcel.writeString(cp);
        parcel.writeString(ville);
        parcel.writeString(statut);
        parcel.writeString(ppLink);
        parcel.writeString(token);
    }
}
