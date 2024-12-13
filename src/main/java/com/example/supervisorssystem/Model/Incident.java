package com.example.supervisorssystem.Model;

import java.sql.Time;

public class Incident {
    private String reference;
    public Time time;
    private String status;
    private String membreId;

    public Incident(String ref, Time time, String status, String member){
        this.reference=ref;
        this.time=time;
        this.status=status;
        this.membreId=member;

    }

    public String getreference(){
        return reference;
    }
    public String getStatus(){
        return status;
    }
    public Time gettime(){
        return time;
    }

    public String getMembreId() {
        return membreId;
    }
}
