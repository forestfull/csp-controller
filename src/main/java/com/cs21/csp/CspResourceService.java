package com.cs21.csp;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@RequiredArgsConstructor
public class CspResourceService {

    private final SessionFactory sessionFactory;


    public List<CsCspResources> getAll() {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();

            List<CsCspResources> resourcesList = session.createQuery("SELECT csp FROM CsCspResources csp", CsCspResources.class).list();

            transaction.commit();
            session.flush();

            return resourcesList;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(System.err);

        } finally {
            session.close();

        }

        return null;
    }

    public void save(String target, String resource) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();

            session.save(CsCspResources.builder().target(target).resourceUrl(resource).build());

            transaction.commit();
            session.flush();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(System.err);

        } finally {
            session.close();

        }
    }

    public void save(CsCspResources resource) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();

            session.save(resource);

            transaction.commit();
            session.flush();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(System.err);

        } finally {
            session.close();

        }
    }

    public void update(Long id, String target, String resource) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();

            CsCspResources csCspResources = session.get(CsCspResources.class, id);
            csCspResources.setTarget(target);
            csCspResources.setResourceUrl(resource);

            transaction.commit();
            session.flush();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(System.err);

        } finally {
            session.close();

        }
    }

    public void update(CsCspResources resource) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();

            CsCspResources csCspResources = session.get(CsCspResources.class, resource.getId());
            csCspResources.setTarget(resource.getTarget());
            csCspResources.setResourceUrl(resource.getResourceUrl());

            transaction.commit();
            session.flush();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(System.err);

        } finally {
            session.close();

        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();

            session.delete(CsCspResources.builder().id(id).build());

            transaction.commit();
            session.flush();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(System.err);

        } finally {
            session.close();

        }
    }
}