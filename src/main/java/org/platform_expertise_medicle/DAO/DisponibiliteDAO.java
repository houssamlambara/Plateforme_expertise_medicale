package org.platform_expertise_medicle.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.platform_expertise_medicle.model.Disponibilite;
import org.platform_expertise_medicle.model.MedecinSpecialiste;
import org.platform_expertise_medicle.util.JpaUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DisponibiliteDAO {

    public void save(Disponibilite disponibilite) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(disponibilite);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void genererCreneauxJournee(MedecinSpecialiste specialiste, LocalDate date) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(d) FROM Disponibilite d WHERE d.specialiste.id = :specId AND d.date = :date",
                Long.class
            );
            countQuery.setParameter("specId", specialiste.getId());
            countQuery.setParameter("date", date);
            Long count = countQuery.getSingleResult();

            if (count > 0) {
                System.out.println("Des créneaux existent déjà pour cette date");
                tx.rollback();
                return;
            }

            LocalTime heureDebut = LocalTime.of(9, 0);
            LocalTime heureFin = LocalTime.of(12, 0);

            while (heureDebut.isBefore(heureFin)) {
                LocalTime finCreneau = heureDebut.plusMinutes(30);

                Disponibilite dispo = new Disponibilite();
                dispo.setSpecialiste(specialiste);
                dispo.setDate(date);
                dispo.setHeureDebut(heureDebut);
                dispo.setHeureFin(finCreneau);
                dispo.setDisponible(true);

                em.persist(dispo);

                heureDebut = finCreneau;
            }

            tx.commit();
            System.out.println("Créneaux générés pour le " + date);

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Disponibilite> findBySpecialisteAndDate(Long specialisteId, LocalDate date) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Disponibilite> query = em.createQuery(
                "SELECT d FROM Disponibilite d WHERE d.specialiste.id = :specId AND d.date = :date ORDER BY d.heureDebut",
                Disponibilite.class
            );
            query.setParameter("specId", specialisteId);
            query.setParameter("date", date);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Disponibilite> findCreneauxDisponibles(Long specialisteId, LocalDate date) {
        return findBySpecialisteAndDate(specialisteId, date).stream()
                .filter(Disponibilite::isDisponible)
                .filter(dispo -> !dispo.estPasse())
                .toList();
    }

    public boolean reserverCreneau(Long disponibiliteId, Long consultationId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Disponibilite dispo = em.find(Disponibilite.class, disponibiliteId);
            if (dispo != null && dispo.isDisponible()) {
                dispo.setDisponible(false);
                dispo.setReservationId(consultationId);
                em.merge(dispo);
                tx.commit();
                return true;
            }

            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public void libererCreneau(Long disponibiliteId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Disponibilite dispo = em.find(Disponibilite.class, disponibiliteId);
            if (dispo != null) {
                dispo.setDisponible(true);
                dispo.setReservationId(null);
                em.merge(dispo);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Disponibilite> findBySpecialiste(Long specialisteId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Disponibilite> query = em.createQuery(
                "SELECT d FROM Disponibilite d WHERE d.specialiste.id = :specId ORDER BY d.date, d.heureDebut",
                Disponibilite.class
            );
            query.setParameter("specId", specialisteId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Disponibilite findById(Long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Disponibilite.class, id);
        } finally {
            em.close();
        }
    }

    public boolean delete(Long disponibiliteId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Disponibilite dispo = em.find(Disponibilite.class, disponibiliteId);
            if (dispo != null && dispo.isDisponible()) {
                em.remove(dispo);
                tx.commit();
                return true;
            }

            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public void creerCreneauManuel(MedecinSpecialiste specialiste, LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Vérifier qu'il n'y a pas de chevauchement
            TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(d) FROM Disponibilite d WHERE d.specialiste.id = :specId AND d.date = :date " +
                "AND ((d.heureDebut < :heureFin AND d.heureFin > :heureDebut))",
                Long.class
            );
            countQuery.setParameter("specId", specialiste.getId());
            countQuery.setParameter("date", date);
            countQuery.setParameter("heureDebut", heureDebut);
            countQuery.setParameter("heureFin", heureFin);
            Long count = countQuery.getSingleResult();

            if (count > 0) {
                throw new RuntimeException("Ce créneau chevauche un créneau existant");
            }

            Disponibilite dispo = new Disponibilite();
            dispo.setSpecialiste(specialiste);
            dispo.setDate(date);
            dispo.setHeureDebut(heureDebut);
            dispo.setHeureFin(heureFin);
            dispo.setDisponible(true);

            em.persist(dispo);
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Erreur lors de la création du créneau : " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
