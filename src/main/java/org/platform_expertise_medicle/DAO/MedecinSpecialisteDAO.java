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
}
