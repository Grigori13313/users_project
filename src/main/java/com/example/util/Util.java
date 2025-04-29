package com.example.util;


import com.mysql.cj.jdbc.exceptions.ConnectionFeatureNotAvailableException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.example.model.User;

import java.sql.Connection;     //   Чтобы описать соединение с базой данных
import java.sql.DriverManager;  //  Чтобы подключаться к базе через URL
import java.sql.SQLException;   //  Чтобы ловить ошибки при подключении

/**
 * открывает подключение к базе
 */

public class Util {

//    /**
//     * jdbc	Используем JDBC (Java Database Connectivity)
//     * mysql	Через какой драйвер — MySQL драйвер (mysql-connector)
//     * localhost	Адрес сервера базы данных — localhost = твой компьютер
//     * 3306	Порт базы данных (у MySQL по умолчанию порт 3306)
//     * users_db	Название базы данных, которую мы сами создали ранее
//     * <p>
//     * <p>
//     * <p>
//     * jdbc:mysql://	Работает через JDBC драйвер для MySQL
//     * localhost	Подключаемся к своему компьютеру
//     * 3306	Стандартный порт MySQL
//     * users_db	Имя базы, которую мы создали
//     */
//
//
//    private static final String URL = "jdbc:mysql://localhost:3306/users_db";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "root";
//
//
//    public static Connection getConnection() {//   Connection(соединение) — это стандартный тип
//        // в JDBC, который нужен для работы с базой.
//
//        Connection connection = null;
//        try {                      //  обработка ошибок, чтобы приложение не падало.
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }






    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {  // если sessionFactory еще не создана
            try {
                Configuration configuration = new Configuration().configure();  // загрузка hibernate.cfg.xml
                configuration.addAnnotatedClass(User.class);  // подключаем класс User как сущность
                sessionFactory = configuration.buildSessionFactory();  // строим фабрику сессий
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;  // возвращаем синглтон фабрику
    }

    public static Connection getConnection() {
    }
}
