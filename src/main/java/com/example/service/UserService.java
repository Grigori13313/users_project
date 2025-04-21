package com.example.service;

import com.example.model.User;

import java.util.List;


/**
 * В UserDao мы говорим: "выполни запрос к Базе Данных".
 *
 * В UserService мы говорим: "предоставь метод бизнес-логики пользователю".
 *
 *
 * Service вызывает Dao, а тот работает с Базой.
 * (методы почти одинаковые — но уровни разные.
 *
 * метод saveUser в сервисе вызывает saveUser из DAO внутри себя.
 *
 * То есть Сервис = посредник между Main и UserDao.
 *
 * ┌──────────────┐
 * │ UserService  │  →  Управляет логикой (например: saveUser(name, lastName, age))
 * │ (UserServiceImpl) │
 * └──────┬───────┘
 *        │ вызывает методы
 *        ▼
 *
 */



public interface UserService {

    void createUsersTable();   //  createUsersTable - Создаёт таблицу пользователей в базе данных, если её ещё нет.

    void dropUsersTable();   //  dropUsersTable -  Удаляет таблицу пользователей (если она есть).

    void saveUser(String name, String lastName, byte age);   //  saveUser - Добавляет одного пользователя в таблицу с указанными данными.


    void removeUserById(long id);   //  removeUserById - Удаляет пользователя из таблицы по его id.

    List<User> getAllUsers();  //  getAllUsers - Получает список всех пользователей из таблицы и возвращает его.


    void cleanUsersTable();  //  cleanUsersTable - Очищает таблицу пользователей, но оставляет саму таблицу (не удаляет её структуру).


}

