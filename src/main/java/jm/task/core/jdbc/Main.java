package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        // 1 - CREATE table
        userService.createUsersTable();

        // 2 - ADD USERS to TABLE
        userService.saveUser("Bob", "Dunkirk", (byte) 25);
        userService.saveUser("Johnny", "Blob", (byte) 34);
        userService.saveUser("Ronny", "Motor", (byte) 45);
        userService.saveUser("Craven", "West", (byte) 88);

//        // OPTION - DELETE USER by ID
//
////        userService.removeUserById(2);

        // 3 - GET ALL USERS and print to console

        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
//
        // 4 - CLEAN table
        userService.cleanUsersTable();
//
        // 5 - DROP table
//        userService.dropUsersTable();

    }
}
