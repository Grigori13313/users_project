package com.example.service;

import com.example.dao.UserDao;
import com.example.dao.UserDaoHibernateImpl;
import com.example.dao.UserDaoJDBCImpl;
import com.example.model.User;

import java.util.List;


/**
 * UserServiceImpl  -  служба поддержки пользователей
 * <p>
 * UserServiceImpl сам НЕ работает с базой напрямую!
 * Он поручает всю грязную работу UserDaoJDBCImpl.
 * <p>
 * UserServiceImpl — это	Простой, красивый посредник
 * Он берёт команды от Main	типа "сохрани пользователя"
 * И передаёт их в DAO	где уже пишутся SQL-запросы
 */

public class UserServiceImpl implements UserService {


    //  Мы создаём переменную userDao,
    //  которая сразу ссылается на новый объект UserDaoJDBCImpl,
    //  и больше эту ссылку нельзя изменить (final).

    //  Чтобы код был гибкий и надёжный:
    //Мы работаем через интерфейс UserDao (а не напрямую с классом!).

    //Программирование через интерфейсы (Programming to Interface)

    //Если вдруг ты захочешь использовать другую реализацию (например, UserDaoHibernateImpl),
    // тебе не нужно будет переписывать Main — только создать новый объект.



    // private final UserDao userDao = new UserDaoJDBCImpl();  //    объект (userDao) вызывает реальные методы для базы.
    private final UserDao userDao = new UserDaoHibernateImpl();


    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);  // вызывает у DAO
    }

    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();

    }

    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();

    }
}
