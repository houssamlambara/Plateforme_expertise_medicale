package org.platform_expertise_medicle.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.platform_expertise_medicle.model.MedecinSpecialiste;
import org.platform_expertise_medicle.util.JpaUtil;

import java.util.List;

public class MedecinSpecialisteDAO {

    public MedecinSpecialiste findById(long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(MedecinSpecialiste.class, id);
        } finally {
            em.close();
        }
    }

    public List<MedecinSpecialiste> findAll() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<MedecinSpecialiste> query = em.createQuery(
                    "SELECT m FROM MedecinSpecialiste m ORDER BY m.nom ASC",
                    MedecinSpecialiste.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Stream API : Filtrer les spécialistes par spécialité
    public List<MedecinSpecialiste> findBySpecialite(String specialite) {
        return findAll().stream()
                .filter(specialiste -> specialiste.getSpecialite() != null
                        && specialiste.getSpecialite().equalsIgnoreCase(specialite))
                .toList();
    }

    // Stream API : Filtrer les spécialistes par spécialité et trier par tarif
    public List<MedecinSpecialiste> findBySpecialiteOrderByTarif(String specialite) {
        return findAll().stream()
                .filter(specialiste -> specialiste.getSpecialite() != null
                        && specialiste.getSpecialite().equalsIgnoreCase(specialite))
                .sorted((s1, s2) -> {
                    Double tarif1 = s1.getTarif() != null ? s1.getTarif() : 0.0;
                    Double tarif2 = s2.getTarif() != null ? s2.getTarif() : 0.0;
                    return Double.compare(tarif1, tarif2);
                })
                .toList();
    }

    // Stream API : Filtrer les spécialistes disponibles (avec tarif défini)
    public List<MedecinSpecialiste> findDisponibles() {
        return findAll().stream()
                .filter(specialiste -> specialiste.getTarif() != null && specialiste.getTarif() > 0)
                .toList();
    }
}
