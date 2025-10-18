package org.platform_expertise_medicle.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "creneaux")
public class Disponibilite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialiste_id", nullable = false)
    private MedecinSpecialiste specialiste;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;

    @Column(nullable = false)
    private boolean disponible = true;

    @Column(name = "reservation_id")
    private Long reservationId;

    public Disponibilite() {}

    public Disponibilite(MedecinSpecialiste specialiste, LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        this.specialiste = specialiste;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.disponible = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedecinSpecialiste getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(MedecinSpecialiste specialiste) {
        this.specialiste = specialiste;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getCreneauFormate() {
        return heureDebut + " - " + heureFin;
    }

    public boolean estPasse() {
        LocalDate aujourdhui = LocalDate.now();
        LocalTime maintenant = LocalTime.now();

        if (date.isBefore(aujourdhui)) {
            return true;
        }

        if (date.equals(aujourdhui) && heureFin.isBefore(maintenant)) {
            return true;
        }

        return false;
    }
}

