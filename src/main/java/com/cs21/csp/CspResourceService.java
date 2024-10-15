package com.cs21.csp;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.List;

@RequiredArgsConstructor
public class CspResourceService {

    private final EntityManagerFactory entityManagerFactory;

    public List<CsCspResources> getAll() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            List<CsCspResources> resourcesList = entityManager.createQuery("SELECT csp FROM CsCspResources csp", CsCspResources.class).getResultList();

            transaction.commit();
            entityManager.flush();
            return resourcesList;

        } catch (IllegalArgumentException e) {
            if (transaction.isActive()) transaction.rollback();

        } finally {
            entityManager.close();

        }
        return null;
    }

    public void save(String target, String resource) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.persist(CsCspResources.builder().target(target).resourceUrl(resource).build());

            transaction.commit();
            entityManager.flush();
        } catch (IllegalArgumentException e) {
            if (transaction.isActive()) transaction.rollback();

        } finally {
            entityManager.close();

        }
    }

    public void save(CsCspResources resource) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.persist(resource);

            transaction.commit();
            entityManager.flush();
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            if (transaction.isActive()) transaction.rollback();

        } finally {
            entityManager.close();

        }
    }

    public void update(Long id, String target, String resource) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            CsCspResources csCspResources = entityManager.find(CsCspResources.class, id);
            csCspResources.setTarget(target);
            csCspResources.setResourceUrl(resource);

            transaction.commit();
            entityManager.flush();
        } catch (IllegalArgumentException e) {
            if (transaction.isActive()) transaction.rollback();

        } finally {
            entityManager.close();

        }
    }

    public void update(CsCspResources resource) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            CsCspResources csCspResources = entityManager.find(CsCspResources.class, resource.getId());
            csCspResources.setTarget(resource.getTarget());
            csCspResources.setResourceUrl(resource.getResourceUrl());

            transaction.commit();
            entityManager.flush();
        } catch (IllegalArgumentException e) {
            if (transaction.isActive()) transaction.rollback();

        } finally {
            entityManager.close();

        }
    }

    public void delete(Long id) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            CsCspResources csCspResources = entityManager.find(CsCspResources.class, id);
            entityManager.remove(csCspResources);

            transaction.commit();
            entityManager.flush();
        } catch (IllegalArgumentException e) {
            if (transaction.isActive()) transaction.rollback();

        } finally {
            entityManager.close();

        }
    }
}