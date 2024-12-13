package com.example.supervisorssystem.DAO;

import com.example.supervisorssystem.Model.Incident;

import java.util.List;
import java.util.Set;

public interface IncidentDao {
    void inserer(Incident i);
    List<Incident> chargerParMembre(String membreId);


    List<Incident> chargerTous();
}
