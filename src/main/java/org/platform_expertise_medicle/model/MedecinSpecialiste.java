package org.platform_expertise_medicle.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("SPECIALISTE")
public class MedecinSpecialiste extends User {

    @OneToMany(mappedBy = "medecinSpecialiste")
    private List<Consultation> consultations = new ArrayList<>();

    public MedecinSpecialiste() {}

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
