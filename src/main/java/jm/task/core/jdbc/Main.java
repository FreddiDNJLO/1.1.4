package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("name1", "lastName1", (byte) 20);
        userService.saveUser("name2", "lastName2", (byte) 30);
        userService.saveUser("name3", "lastName3", (byte) 25);
        userService.saveUser("name4", "lastName4", (byte) 40);
        for (int i = 0; i < userService.getAllUsers().size(); i++) {
            System.out.println(userService.getAllUsers().get(i).toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
