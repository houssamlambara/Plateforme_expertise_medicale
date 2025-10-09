package org.platform_expertise_medicle.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "crenaux")
public class Crenau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime debut;
    private LocalDateTime fin;
    private boolean disponible = true;

    @ManyToOne
    @JoinColumn(name = "specialiste_id")
    private MedecinSpecialiste specialiste;
}
