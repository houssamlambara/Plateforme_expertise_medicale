package org.platform_expertise_medicle.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("SPECIALISTE")
public class MedecinSpecialiste extends User {

    private String specialite;

    private Double tarif;

    @OneToMany(mappedBy = "medecinSpecialiste")
    private List<Consultation> consultations = new ArrayList<>();

    public MedecinSpecialiste() {}

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
