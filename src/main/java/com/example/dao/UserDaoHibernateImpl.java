package com.example.dao;


import com.example.model.User;
import com.example.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    @Override
    public void createUsersTable() {


        //  Создаём переменную для управления транзакцией в Hibernate.
        //(Транзакция нужна, чтобы несколько операций были атомарными — "либо всё, либо ничего".)
        Transaction transaction = null;

        //  Открываем новую сессию для работы с базой данных.
        // Util.getSessionFactory() —берём SessionFactory, а openSession() открывает подключение.

        //( try (...) означает, что сессия автоматически закроется после работы —это удобно и безопасно.)
        try (Session session = Util.getSessionFactory().openSession()) {

            // Начинаем новую транзакцию — подготавливаем выполнение SQL-запроса.
            //(Всё, что мы сделаем после этого, будет частью этой транзакции.)
            transaction = session.beginTransaction();


            //Пишем SQL-запрос на создание таблицы users, если её ещё нет:
            //
            //id — целое большое число (BIGINT), автоинкремент — номер будет сам увеличиваться.
            //
            //name, lastName — текст до 50 символов.
            //
            //age — маленькое целое число (TINYINT), возраст.
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(50), " +
                    "lastName VARCHAR(50), " +
                    "age TINYINT)";

            // Hibernate создаёт SQL-запрос (createSQLQuery(sql)) и выполняет его (executeUpdate()).
            //(То есть таблица будет создана в Базе Данных.)
            session.createSQLQuery(sql).executeUpdate();

            // Если всё прошло хорошо — фиксируем изменения в базе данных (подтверждаем транзакцию). сохраняем изменения
            transaction.commit();

            // ловим ошибки, если возникнут при выполнении кода выше
        } catch (Exception e) {

            // Если ошибка была и транзакция уже началась, откатываем изменения, чтобы не испортить базу данных.
            if (transaction != null) {
                transaction.rollback();

            }
            //Печатаем стек вызовов ошибки, чтобы увидеть где именно произошёл сбой.
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Ошибка при удалении таблицы. Откат выполнен.");
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Ошибка при сохранении пользователя. Откат выполнен.");
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "DELETE FROM users WHERE id = :id";
            session.createSQLQuery(sql)            //  Создаём объект запроса
                    .setParameter("id", id)     //  Передаём значение переменной id вместо :id в запрос
                    .executeUpdate();              //  Выполняем SQL-команду в базе данных
            transaction.commit();                  //  Подтверждаем изменения в базе данных

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Ошибка при удалении пользователя по id. Откат выполнен.");
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        Transaction transaction = null;
        List<User> users = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            List<Object[]> resultList = session.createSQLQuery("SELECT * FROM users").list();

            for (Object[] objects : resultList) {
                User user = new User();
                user.setId(((Number) objects[0]).longValue());
                user.setName((String) objects[1]);
                user.setLastName((String) objects[2]);
                user.setAge(((Number) objects[0]).byteValue());
                users.add(user);

            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Ошибка при получении пользователей. Откат выполнен.");
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {

    }
}
