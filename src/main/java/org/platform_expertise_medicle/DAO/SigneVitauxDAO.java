package org.platform_expertise_medicle.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.platform_expertise_medicle.model.SigneVitaux;
import org.platform_expertise_medicle.util.JpaUtil;

import java.util.List;

public class SigneVitauxDAO {

    public void save(SigneVitaux signeVitaux) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(signeVitaux);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public SigneVitaux findById(long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(SigneVitaux.class, id);
        } finally {
            em.close();
        }
    }

    public void update(SigneVitaux visite) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(visite);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            em.close();
        }
    }

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

    // 🔹 Méthode pour récupérer la dernière visite d’un patient
    public SigneVitaux findLastByPatientId(long patientId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<SigneVitaux> query = em.createQuery(
                    "SELECT sv FROM SigneVitaux sv WHERE sv.patient.id = :pid ORDER BY sv.dateMesure DESC",
                    SigneVitaux.class
            );
            query.setParameter("pid", patientId);
            query.setMaxResults(1);
            List<SigneVitaux> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
}
