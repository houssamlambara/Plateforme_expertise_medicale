package org.platform_expertise_medicle.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("GENERALISTE")
public class MedecinGeneraliste extends User {

    @OneToMany(mappedBy = "medecinGeneraliste")
    private List<Consultations> consultations = new ArrayList<>();

    public MedecinGeneraliste() {}

    public List<Consultations> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultations> consultations) {
        this.consultations = consultations;
    }
}
