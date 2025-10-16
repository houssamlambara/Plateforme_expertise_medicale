package org.platform_expertise_medicle.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.platform_expertise_medicle.model.DemandeExpertise;
import org.platform_expertise_medicle.model.MedecinSpecialiste;
import org.platform_expertise_medicle.util.JpaUtil;

import java.util.List;

public class DemandeExpertiseDAO {

    public MedecinSpecialiste findByEmail(String email) {
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

    public MedecinSpecialiste findById(long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(MedecinSpecialiste.class, id);
        } finally {
            em.close();
        }
    }

    public List<DemandeExpertise> findEnAttenteBySpecialisteId(long specialisteId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<DemandeExpertise> query = em.createQuery(
                    "SELECT d FROM DemandeExpertise d " +
                            "WHERE d.specialiste.id = :specId AND d.statut = :statut " +
                            "ORDER BY d.consultation.dateConsultation DESC",
                    DemandeExpertise.class
            );
            query.setParameter("specId", specialisteId);
            query.setParameter("statut", "EN_ATTENTE_AVIS_SPECIALISTE");
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            em.close();
        }
    }
}
