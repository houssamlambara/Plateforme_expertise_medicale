package org.platform_expertise_medicle.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.MedecinGeneraliste;
import org.platform_expertise_medicle.model.MedecinSpecialiste;
import org.platform_expertise_medicle.util.JpaUtil;

import java.util.List;

public class ConsultationDAO {

    // ✅ Enregistrer une nouvelle consultation
    public void save(Consultation consultation) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(consultation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Consultation findById(long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Consultation.class, id);
        } finally {
            em.close();
        }
    }

    public void update(Consultation consultation) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(consultation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    // ✅ Lister toutes les consultations
    public List<Consultation> findAll() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c", Consultation.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Consultation> findByGeneralisteId(long generalisteId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Consultation c WHERE c.generaliste.id = :genId ORDER BY c.dateConsultation DESC",
                            Consultation.class)
                    .setParameter("genId", generalisteId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ✅ Récupérer les consultations assignées à un spécialiste
    public List<Consultation> findBySpecialiste(MedecinSpecialiste specialiste) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Consultation> query = em.createQuery(
                    "SELECT c FROM Consultation c WHERE c.medecinSpecialiste = :specialiste ORDER BY c.dateConsultation DESC",
                    Consultation.class
            );
            query.setParameter("specialiste", specialiste);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ✅ Mettre à jour uniquement le statut (utile pour “Clôturer” ou “Demander avis spécialiste”)
    public void updateStatut(long id, String nouveauStatut) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Consultation consultation = em.find(Consultation.class, id);
            if (consultation != null) {
                consultation.setStatut(nouveauStatut);
                em.merge(consultation);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
