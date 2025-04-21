package com.example;

import com.example.service.UserServiceImpl;


/**
 *  Не нужно в Main писать SQL-запросы.
 *  Если вдруг поменяется способ хранения (например, перейти с MySQL на PostgreSQL) — нужно будет изменить только UserDaoJDBCImpl,
 *  а не переписывать всё приложение.
 *  Такой стиль называется трёхуровневая архитектура (Presentation → Service → DAO → Database).
 *
 * ┌──────────────┐
 * │    Main      │  →  Пишем команды: создать таблицу, добавить пользователя
 * └──────┬───────┘
 *        │ вызывает методы
 *        ▼
 */

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userServiceImpl = new UserServiceImpl();  //  userServiceImpl - реализация службы пользователя
        userServiceImpl.createUsersTable();
        userServiceImpl.cleanUsersTable();

        String name1 = "Grigori";
        userServiceImpl.saveUser(name1, "Kuznetsov", (byte) 34);
        System.out.println("User с именем: " + name1 + " добавлен в БАЗУ ДАННЫХ.");
        System.out.println("----------------------------------------");
        String name2 = "Ivan";
        userServiceImpl.saveUser(name2, "Petrov", (byte) 23);
        System.out.println("User с именем: " + name2 + " добавлен в БАЗУ ДАННЫХ.");
        System.out.println("-------------------------------------------");
        String name3 = "Oleg";
        userServiceImpl.saveUser(name3, "Sidorov", (byte) 38);
        System.out.println("User с именем: " + name3 + " добавлен в БАЗУ ДАННЫХ.");
        System.out.println("-------------------------------------------");
        String name4 = "Olga";
        userServiceImpl.saveUser(name4, "Frolova", (byte) 27);
        System.out.println("User с именем: " + name4 + " добавлен в БАЗУ ДАННЫХ.");

        System.out.println("------------------------------------------");
        userServiceImpl.getAllUsers().forEach(System.out::println);
    }
}
