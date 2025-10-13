package org.platform_expertise_medicle.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "visite_id", nullable = false, unique = true)
    private SigneVitaux visite;

    @ManyToOne
    @JoinColumn(name = "generaliste_id")
    private MedecinGeneraliste generaliste;

    @ManyToOne
    @JoinColumn(name = "specialiste_id")
    private MedecinSpecialiste medecinSpecialiste;

    @Column(columnDefinition = "TEXT")
    private String symptomes;

    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String prescription;

    private LocalDateTime dateConsultation;

    @Column(nullable = false)
    private String statut;

    public Consultation() {
        this.dateConsultation = LocalDateTime.now();
        this.statut = "ENREGISTREE";
    }

    // Getters & Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public SigneVitaux getVisite() { return visite; }
    public void setVisite(SigneVitaux visite) { this.visite = visite; }

    public MedecinGeneraliste getGeneraliste() { return generaliste; }
    public void setGeneraliste(MedecinGeneraliste generaliste) { this.generaliste = generaliste; }

    public MedecinSpecialiste getMedecinSpecialiste() { return medecinSpecialiste; }
    public void setMedecinSpecialiste(MedecinSpecialiste medecinSpecialiste) { this.medecinSpecialiste = medecinSpecialiste; }

    public String getSymptomes() { return symptomes; }
    public void setSymptomes(String symptomes) { this.symptomes = symptomes; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public LocalDateTime getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(LocalDateTime dateConsultation) { this.dateConsultation = dateConsultation; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
