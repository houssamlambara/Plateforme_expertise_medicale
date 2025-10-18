package org.platform_expertise_medicle.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.platform_expertise_medicle.enums.StatutConsultation;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.DemandeExpertise;
import org.platform_expertise_medicle.model.MedecinSpecialiste;
import org.platform_expertise_medicle.util.JpaUtil;

import java.util.List;

public class DemandeExpertiseDAO {

    // Trouver un spécialiste par email
    public MedecinSpecialiste findSpecialisteByEmail(String email) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<MedecinSpecialiste> query = em.createQuery(
                    "SELECT m FROM MedecinSpecialiste m WHERE m.email = :email",
                    MedecinSpecialiste.class
            );
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // Trouver un spécialiste par ID
    public MedecinSpecialiste findSpecialisteById(long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(MedecinSpecialiste.class, id);
        } finally {
            em.close();
        }
    }

    public List<Consultation> findEnAttenteBySpecialisteId(long specialisteId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Consultation> query = em.createQuery(
                    "SELECT DISTINCT c FROM Consultation c JOIN c.demandesExpertise d WHERE d.specialiste.id = :specId AND c.statut = :statut",
                    Consultation.class
            );
            query.setParameter("specId", specialisteId);
            query.setParameter("statut", StatutConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Sauvegarder une demande
    public void save(DemandeExpertise demande) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(demande);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}