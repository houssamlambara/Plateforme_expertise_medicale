package org.platform_expertise_medicle.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "signes_vitaux")
public class SigneVitaux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tensionArterielle;
    private int frequenceCardiaque;
    private double temperature;
    private int frequenceRespiratoire;
    private double poids;
    private double taille;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private LocalDateTime dateMesure;

    @Transient
    private String formattedDate;

    public SigneVitaux() {
        this.dateMesure = LocalDateTime.now();
    }

    public void prepareFormattedDate() {
        if (this.dateMesure != null) {
            this.formattedDate = this.dateMesure.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTensionArterielle() { return tensionArterielle; }
    public void setTensionArterielle(String tensionArterielle) { this.tensionArterielle = tensionArterielle; }

    public int getFrequenceCardiaque() { return frequenceCardiaque; }
    public void setFrequenceCardiaque(int frequenceCardiaque) { this.frequenceCardiaque = frequenceCardiaque; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getFrequenceRespiratoire() { return frequenceRespiratoire; }
    public void setFrequenceRespiratoire(int frequenceRespiratoire) { this.frequenceRespiratoire = frequenceRespiratoire; }

    public double getPoids() { return poids; }
    public void setPoids(double poids) { this.poids = poids; }

    public double getTaille() { return taille; }
    public void setTaille(double taille) { this.taille = taille; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateMesure() { return dateMesure; }
    public void setDateMesure(LocalDateTime dateMesure) { this.dateMesure = dateMesure; }

    @Transient
    public String getFormattedDate() {
        if(this.dateMesure != null) {
            return this.dateMesure.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
        return "";
    }
    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate; }
}
