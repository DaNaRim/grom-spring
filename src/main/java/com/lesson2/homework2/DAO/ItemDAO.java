package com.lesson2.homework2.DAO;

import com.lesson2.homework2.exception.BadRequestException;
import com.lesson2.homework2.exception.InternalServerException;
import com.lesson2.homework2.model.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

public class ItemDAO {

    public Item findById(long id) throws InternalServerException, BadRequestException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            Item object = session.get(Item.class, id);

            if (object == null) {
                throw new BadRequestException("findById failed: missing object with id: " + id);
            }

            return object;
        } catch (HibernateException e) {
            throw new InternalServerException("findById failed: something went wrong");
        }
    }

    public Item save(Item object) throws InternalServerException {

        object.setDateCreated(new Date());
        object.setLastUpdatedDate(new Date());

        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            session.save(object);

            transaction.commit();
            return object;
        } catch (HibernateException e) {
            throw new InternalServerException("save failed: something went wrong");
        }
    }

    public Item update(Item object) throws InternalServerException {

        try {
            object.setDateCreated(findById(object.getId()).getDateCreated());
            object.setLastUpdatedDate(new Date());
        } catch (BadRequestException e) {
            System.err.println("Something went wrong");
        }

        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            session.update(object);

            transaction.commit();
            return object;
        } catch (HibernateException e) {
            throw new InternalServerException("update failed: something went wrong");
        }
    }

    public void delete(Item object) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            session.delete(object);

            transaction.commit();
        } catch (HibernateException e) {
            throw new InternalServerException("delete failed: something went wrong");
        }
    }
}
