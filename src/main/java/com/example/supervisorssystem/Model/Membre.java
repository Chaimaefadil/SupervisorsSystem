package com.example.supervisorssystem.Model;

import java.util.List;
import java.util.Objects;

public class Membre {
    private static String membreId;
    private static String nom;
    private static String prenom;
    private static String email;
    private static long phone;
    private List<Incident> incidents;
    public Membre(String id,String nom,String prenom,String email,long phone ){
        this.membreId=id;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.phone=phone;

    }

    public Membre() {

    }

    public static String getid(){
        return membreId;
    }
    public static String getnom(){
        return nom;
    }
    public static String getprenom(){
        return prenom;
    }
    public static String getemail(){
        return email;
    }
    public static long getphone(){
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membre membre = (Membre) o;
        return Objects.equals(membreId, membre.membreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(membreId);
    }

    public void addIncident(Incident incident) {
        incidents.add(incident);
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public void setPrenom(String prenom) {
        this.prenom=prenom;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setTelephone(long telephone) {
        this.phone=telephone;
    }
    public void setid(String id){
        this.membreId=id;
    }
}
