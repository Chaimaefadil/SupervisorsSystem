package com.example.supervisorssystem.DAO;

import com.example.supervisorssystem.Database.DatabaseConnection;
import com.example.supervisorssystem.Model.Incident;
import com.example.supervisorssystem.Model.Membre;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;


public class MembreDaoImpl implements MemberDao {
    @Override
    public void inserer(Membre m) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO membre (identifiant, nom, prenom, email, phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Membre.getid());
            stmt.setString(2, Membre.getnom());
            stmt.setString(3, Membre.getprenom());
            stmt.setString(4, Membre.getemail());
            stmt.setLong(5, Membre.getphone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Membre chargerAvecIncidents(String identifiant) {
        Membre membre = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String membreSql = "SELECT * FROM membre WHERE identifiant = ?";
            PreparedStatement membreStmt = conn.prepareStatement(membreSql);
            membreStmt.setString(1, identifiant);
            ResultSet membreRs = membreStmt.executeQuery();

            if (membreRs.next()) {
                membre = new Membre(
                        membreRs.getString("identifiant"),
                        membreRs.getString("nom"),
                        membreRs.getString("prenom"),
                        membreRs.getString("email"),
                        membreRs.getLong("phone")
                );

                String incidentSql = "SELECT * FROM incident WHERE membre_id = ?";
                PreparedStatement incidentStmt = conn.prepareStatement(incidentSql);
                incidentStmt.setString(1, identifiant);
                ResultSet incidentRs = incidentStmt.executeQuery();

                while (incidentRs.next()) {
                    Incident incident = new Incident(
                            incidentRs.getString("reference"),
                            incidentRs.getTime("time"),
                            incidentRs.getString("status"),
                            identifiant
                    );
                    membre.addIncident(incident);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membre;
    }

    @Override
    public List<Membre> chargerTous() {
        List<Membre> membres = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM membre";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Membre membre = new Membre(
                        rs.getString("identifiant"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getLong("phone")
                );
                membres.add(membre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membres;
    }

}
