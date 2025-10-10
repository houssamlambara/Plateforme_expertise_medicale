package org.platform_expertise_medicle.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.platform_expertise_medicle.model.Patient;
import org.platform_expertise_medicle.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class PatientDAO {

    public void save(Patient patient) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(patient);
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

    public List<Patient> findAll() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT p FROM Patient p ORDER BY p.nom, p.prenom";
            TypedQuery<Patient> query = em.createQuery(jpql, Patient.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}