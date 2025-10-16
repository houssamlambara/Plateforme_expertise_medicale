package org.platform_expertise_medicle.model;

import jakarta.persistence.*;

@Entity
@Table(name = "demandes_expertise")
public class DemandeExpertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "specialiste_id")
    private MedecinSpecialiste specialiste;

    private String statut;
    private String priorite;
    private String question;

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Consultation getConsultation() { return consultation; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }

    public MedecinSpecialiste getSpecialiste() { return specialiste; }
    public void setSpecialiste(MedecinSpecialiste specialiste) { this.specialiste = specialiste; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getPriorite() { return priorite; }
    public void setPriorite(String priorite) { this.priorite = priorite; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
}
