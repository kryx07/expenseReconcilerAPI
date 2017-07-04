package com.kryx07.expensereconcilerapi;

import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.UserGroups;
import com.kryx07.expensereconcilerapi.model.users.Users;
import com.kryx07.expensereconcilerapi.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class HibTest {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            User user = new User();
            user.setUserName("dupa1");
            User user2 = new User();
            user.setUserName("dupa2");
            User user3 = new User();
            user.setUserName("dupa3");

            Users users1 = new Users(new HashSet<>(Arrays.asList(user, user2)));
            Users users2 = new Users(new HashSet<>((Arrays.asList(user, user3))));

            UserGroups groups = new UserGroups();
            groups.add(users1);
            groups.add(users2);


            session.save(users1);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
