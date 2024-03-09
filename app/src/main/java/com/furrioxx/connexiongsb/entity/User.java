package com.furrioxx.connexiongsb.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    private static int id;
    private static String surname;
    private static String name;
    private static String mail;
    private static String adress;
    private static String cp;
    private static String ville;
    private static String statut;
    private static String ppLink;
    private static String token;

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
        name = in.readString();
        surname = in.readString();
        id = in.readInt();
        mail = in.readString();
        adress = in.readString();
        cp = in.readString();
        ville = in.readString();
        statut = in.readString();
        ppLink = in.readString();
        token = in.readString();
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
    public String getStatut(){
        return this.statut;
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
