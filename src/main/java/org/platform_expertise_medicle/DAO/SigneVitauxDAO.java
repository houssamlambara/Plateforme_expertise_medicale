package org.platform_expertise_medicle.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.platform_expertise_medicle.model.SigneVitaux;
import org.platform_expertise_medicle.model.Patient;
import org.platform_expertise_medicle.util.JpaUtil;

import java.util.List;

public class SigneVitauxDAO {

    // Enregistrer un SigneVitaux
    public void save(SigneVitaux signeVitaux) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(signeVitaux);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    // Mettre à jour un SigneVitaux
    public void update(SigneVitaux signeVitaux) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(signeVitaux);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    // Trouver par ID
    public SigneVitaux findById(long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(SigneVitaux.class, id);
        } finally {
            em.close();
        }
    }

    // Récupérer tous les SigneVitaux avec un statut donné, du plus récent au plus ancien
    public List<SigneVitaux> findByStatut(String statut) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<SigneVitaux> query = em.createQuery(
                    "SELECT sv FROM SigneVitaux sv WHERE sv.statut = :statut ORDER BY sv.dateMesure DESC",
                    SigneVitaux.class
            );
            query.setParameter("statut", statut);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Récupérer le premier SigneVitaux d'un statut donné (FIFO)
    public SigneVitaux findFirstByStatut(String statut) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<SigneVitaux> query = em.createQuery(
                    "SELECT sv FROM SigneVitaux sv WHERE sv.statut = :statut ORDER BY sv.dateMesure ASC",
                    SigneVitaux.class
            );
            query.setParameter("statut", statut);
            query.setMaxResults(1);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    // Récupérer le dernier SigneVitaux d’un patient donné (par ID), le plus récent
    public SigneVitaux findLastByPatientId(long patientId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<SigneVitaux> query = em.createQuery(
                    "SELECT sv FROM SigneVitaux sv WHERE sv.patient.id = :pid ORDER BY sv.dateMesure DESC",
                    SigneVitaux.class
            );
            query.setParameter("pid", patientId);
            query.setMaxResults(1);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    // **Nouvelle méthode recommandée** : récupérer le premier SigneVitaux d’un patient spécifique avec un statut donné (FIFO)
    public SigneVitaux findFirstByPatientAndStatut(Patient patient, String statut) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<SigneVitaux> query = em.createQuery(
                    "SELECT sv FROM SigneVitaux sv WHERE sv.patient = :patient AND sv.statut = :statut ORDER BY sv.dateMesure ASC",
                    SigneVitaux.class
            );
            query.setParameter("patient", patient);
            query.setParameter("statut", statut);
            query.setMaxResults(1);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }
}
