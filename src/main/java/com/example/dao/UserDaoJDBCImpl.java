package com.example.dao;

import com.example.model.User;
import com.example.util.Util;

import java.security.PrivilegedAction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс, который
 * реализует интерфейс UserDao
 * и напрямую работает с базой данных через SQL-запросы.
 *
 * Связывается с базой данных (MySQL)	Через Connection, PreparedStatement, Statement
 * Выполняет реальные SQL-запросы	Создание таблицы, добавление, удаление, получение данных
 * Принимает команды от UserServiceImpl	Например, сохранить нового пользователя
 * Скрывает детали работы с базой от Main	(в Main нет ни одного SQL-запроса!)
 *
 * Он отделяет работу с базой от логики программы.
 * Он делает код удобным для поддержки.
 * Он позволяет легко заменить способ хранения данных в будущем (например, вместо MySQL использовать другую БД).
 *
 * UserDaoJDBCImpl — это строительный рабочий, который выполняет всю тяжёлую работу с базой данных по команде сервиса.
 */

public class UserDaoJDBCImpl implements UserDao {


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +   //   СОЗДАТЬ ТАБЛИЦУ, ЕСЛИ НЕ СУЩЕСТВУЕТ с именем users
                "id BIGINT NOT NULL AUTO_INCREMENT, " +  //  большое целое число, не может быть пустым, автоматически увеличивается.
                "name VARCHAR (50), " +  //  Фамилия до 50 символов
                "lastName VARCHAR(50), " +  //  Фамилия до 50 символов
                "age TINYINT, " +  //  маленькое целое число для хранения возраста
                "PRIMARY KEY (id))";   //    поле id назначается первичным ключом (оно будет уникальным для каждой записи).


        try (Connection connection = Util.getConnection();  //   Ресурсы (Connection, Statement) автоматически закрываются.
             Statement statement = connection.createStatement()) {  //  createStatement - создать заявление

            //  statement - заявление
            statement.executeUpdate(sql);  //   передаем нашу строку sql в базу      executeUpdate - выполнить обновление
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS users";  //   удалить таблицу, если существует.


        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();  //  printStackTrace - распечатать трассировку стека
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {  //  Добавляет нового пользователя в таблицу

        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";


        try (Connection connection = Util.getConnection();

             //  preparedStatement - подготовленное заявление
             //  не соединяет строки вручную,
             //
             //автоматически экранирует кавычки и специальные символы,
             //
             //проверяет тип данных,
             //
             //защищает от атак типа "SQL-инъекция".
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();  //  executeUpdate - выполнить обновление (отправить в базу)

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);  //   Говорим, что на место первого ? нужно поставить наш id
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery(); //  Эта строка выполняет запрос к базе данных и получает
            // результаты выборки в виде таблицы, которую потом можно обрабатывать в Java-коде.

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {

        String sql = "TRUNCATE TABLE users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
