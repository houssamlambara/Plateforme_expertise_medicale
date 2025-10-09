package org.platform_expertise_medicle.model;

import jakarta.persistence.*;

@Entity
@Table(name = "consultations")
public class Consultations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "visite_id", unique = true, nullable = false)
    private Visite visite;

    @ManyToOne
    @JoinColumn(name = "specialiste_id", nullable = false)
    private MedecinSpecialiste medecinSpecialiste;

    @ManyToOne
    @JoinColumn(name = "generaliste_id")
    private MedecinGeneraliste medecinGeneraliste;

    private String motif;
    private String observations;
    private String diagnostic;
    private String prescription;

    @Column(nullable = false)
    private Double cout;

    @Column(nullable = false)
    private String statut;

    public Consultations() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visite getVisite() {
        return visite;
    }

    public void setVisite(Visite visite) {
        this.visite = visite;
    }

    public MedecinSpecialiste getMedecinSpecialiste() {
        return medecinSpecialiste;
    }

    public void setMedecinSpecialiste(MedecinSpecialiste medecinSpecialiste) {
        this.medecinSpecialiste = medecinSpecialiste;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public Double getCout() {
        return cout;
    }

    public void setCout(Double cout) {
        this.cout = cout;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
