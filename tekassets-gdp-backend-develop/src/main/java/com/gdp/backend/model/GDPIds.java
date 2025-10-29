package com.gdp.backend.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Project")
@Data
public class GDPIds {
    @Id
    @Column(name = "Id")
    private int id;

    @Column(name = "GDPId")
    private String GDPId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGDPId() {
        return GDPId;
    }

    public void setGDPId(String GDPId) {
        this.GDPId = GDPId;
    }

    @Override
    public String toString() {
        return "GDPIds{" +
                "id=" + id +
                ", GDPId='" + GDPId + '\'' +
                '}';
    }
}
