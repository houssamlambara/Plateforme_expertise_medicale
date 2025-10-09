package org.platform_expertise_medicle.model;

import jakarta.persistence.*;

@Entity
@Table(name = "demandes_ecpertise")

public class DemandesExpertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "consultation_id", unique = true, nullable = false)
    private Consultations consultations;

    @ManyToOne
    @JoinColumn(name = "specialiste_id", nullable = false)
    private User specialiste;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String priorite;

    @Column(name = "avis_medical")
    private String avisMedical;

    private String recommandations;

    @Column(nullable = false)
    private String statut;


    public DemandesExpertise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consultations getConsultation() {
        return consultations;
    }

    public void setConsultation(Consultations consultation) {
        this.consultations = consultation;
    }

    public User getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(User specialiste) {
        this.specialiste = specialiste;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public String getAvisMedical() {
        return avisMedical;
    }

    public void setAvisMedical(String avisMedical) {
        this.avisMedical = avisMedical;
    }

    public String getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(String recommandations) {
        this.recommandations = recommandations;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
