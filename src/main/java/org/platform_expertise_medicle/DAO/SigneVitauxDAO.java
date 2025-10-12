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

    public List<SigneVitaux> findByStatut(String statut) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Cette requête est la "file d'attente"
            TypedQuery<SigneVitaux> query = em.createQuery(
                    "SELECT sv FROM SigneVitaux sv WHERE sv.statut = :statut ORDER BY sv.dateMesure ASC",
                    SigneVitaux.class
            );
            query.setParameter("statut", statut);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}