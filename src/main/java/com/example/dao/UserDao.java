package com.example.dao;

import com.example.model.User;

import java.util.List;


/**
 * Интерфейс UserDao описывает какие операции мы должны и можем делать с таблицей users в базе данных.
 * Определяет CRUD-операции.
 *
 * CRUD — это аббревиатура из английских слов:
 *
 *
 * Буква	Значение	Что значит по-русски	Пример в нашем проекте
 * C	Create	Создать данные	saveUser() — сохранить нового пользователя
 * R	Read	Прочитать данные	getAllUsers() — получить всех пользователей
 * U	Update	Обновить данные	(в нашем проекте нет, потому что задача была без Update)
 * D	Delete	Удалить данные	removeUserById() — удалить пользователя по id
 *
 * ┌──────────────┐
 * │   UserDao    │  →  Делает SQL-запросы (insert, delete, select)
 * │ (UserDaoJDBCImpl) │
 * └──────┬───────┘
 *        │ отправляет запросы
 *        ▼
 */


public interface UserDao {

    void createUsersTable();   //  createUsersTable - Создаёт таблицу пользователей в базе данных, если её ещё нет.

    void dropUsersTable();   //  dropUsersTable -  Удаляет таблицу пользователей (если она есть).

    void saveUser(String name, String lastNAme, byte age);   //  saveUser - Добавляет одного пользователя в таблицу с указанными данными.


    void removeUserById(long id);   //  removeUserById - Удаляет пользователя из таблицы по его id.

    List<User> getAllUsers();  //  getAllUsers - Получает список всех пользователей из таблицы и возвращает его.


    void cleanUsersTable();  //  cleanUsersTable - Очищает таблицу пользователей, но оставляет саму таблицу (не удаляет её структуру).


}
