package org.platform_expertise_medicle.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.platform_expertise_medicle.model.ActeTechnique;
import org.platform_expertise_medicle.util.JpaUtil;

public class ActeTechniqueDAO {

    public void save(ActeTechnique acte) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(acte);
            tx.commit();
            System.out.println("Acte technique enregistré avec succès !");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
