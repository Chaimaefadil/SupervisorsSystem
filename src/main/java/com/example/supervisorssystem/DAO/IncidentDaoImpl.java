package com.example.supervisorssystem.DAO;

import com.example.supervisorssystem.Model.Incident;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;
import com.example.supervisorssystem.Database.DatabaseConnection;

import java.sql.SQLException;
import java.sql.ResultSet;

public class IncidentDaoImpl implements IncidentDao {
    @Override
        public void inserer(Incident incident) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO incident (reference, time, status, membre_id) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, incident.getreference());
                stmt.setTime(2, incident.gettime());
                stmt.setString(3, incident.getStatus());
                stmt.setString(4, incident.getMembreId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<Incident> chargerParMembre(String membreId) {
            List<Incident> incidents = new ArrayList<>();
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM incident WHERE membre_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, membreId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Incident incident = new Incident(
                            rs.getString("reference"),
                            rs.getTime("time"),
                            rs.getString("status"),
                            rs.getString("membre_id")
                    );
                    incidents.add(incident);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return incidents;
        }

        @Override
        public List<Incident> chargerTous() {
            List<Incident> incidents = new ArrayList<>();
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM incident";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Incident incident = new Incident(
                            rs.getString("reference"),
                            rs.getTime("time"),
                            rs.getString("status"),
                            rs.getString("membre_id")
                    );
                    incidents.add(incident);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return incidents;
        }
    }

