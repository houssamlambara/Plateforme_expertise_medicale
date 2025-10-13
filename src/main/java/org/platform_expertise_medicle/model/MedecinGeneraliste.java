package org.platform_expertise_medicle.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("GENERALISTE")
public class MedecinGeneraliste extends User {

    @OneToMany(mappedBy = "generaliste")
    private List<Consultation> consultations = new ArrayList<>();

    public MedecinGeneraliste() {}

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
