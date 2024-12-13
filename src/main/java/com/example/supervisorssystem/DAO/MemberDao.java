package com.example.supervisorssystem.DAO;

import com.example.supervisorssystem.Model.Incident;
import com.example.supervisorssystem.Model.Membre;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface MemberDao {
    void inserer(Membre m) throws SQLException;
    Membre chargerAvecIncidents(String identifiant);

    List<Membre> chargerTous();
}


