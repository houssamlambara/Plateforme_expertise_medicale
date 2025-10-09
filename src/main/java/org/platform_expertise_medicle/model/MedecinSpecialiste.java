package org.platform_expertise_medicle.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("SPECIALISTE")
public class MedecinSpecialiste extends User {

    @OneToMany(mappedBy = "medecinSpecialiste")
    private List<Consultations> consultations = new ArrayList<>();

    public MedecinSpecialiste() {}

    public List<Consultations> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultations> consultations) {
        this.consultations = consultations;
    }
}
