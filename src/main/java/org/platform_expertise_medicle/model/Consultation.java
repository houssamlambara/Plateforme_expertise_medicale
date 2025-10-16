package org.platform_expertise_medicle.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultations")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

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

    @Column(columnDefinition = "TEXT")
    private String motif;

    @Column(columnDefinition = "TEXT")
    private String observations;

    private LocalDateTime dateConsultation;

    @Column(nullable = false)
    private String statut;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ActeTechnique> actesTechniques = new ArrayList<>();

    public Consultation() {
        this.dateConsultation = LocalDateTime.now();
        this.statut = "ENREGISTREE";
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

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

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public LocalDateTime getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(LocalDateTime dateConsultation) { this.dateConsultation = dateConsultation; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public List<ActeTechnique> getActesTechniques() { return actesTechniques; }

    public void setActesTechniques(List<ActeTechnique> actesTechniques) {
        this.actesTechniques.clear();
        if (actesTechniques != null) {
            actesTechniques.forEach(this::addActeTechnique);
        }
    }

    public void addActeTechnique(ActeTechnique acte) {
        actesTechniques.add(acte);
        acte.setConsultation(this);
    }

    public void removeActeTechnique(ActeTechnique acte) {
        actesTechniques.remove(acte);
        acte.setConsultation(null);
    }
}
