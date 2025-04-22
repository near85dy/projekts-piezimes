package org.example.services;

import org.example.interfaces.IService;
import org.example.models.UserModel;
import org.example.utils.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserService implements IService {
    private Database database;
    private List<UserModel> users;
    private UserModel currentUser;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public UserService() {
        this.users = new ArrayList<>();
        String dbPath = System.getProperty("user.dir") + "/src/main/resources/users.csv";
        this.database = new Database(dbPath);
    }

    public void initialize() throws IOException {
        List<String[]> dbUsers = database.loadDatabase();

        for(int i = 1; i < dbUsers.size(); i++) {
            String[] user = dbUsers.get(i);
            UserModel userModel = new UserModel();
            userModel.id = Integer.parseInt(user[0]);
            userModel.email = user[1];
            userModel.name = user[2];
            userModel.surname = user[3];
            userModel.password = user[4];
            userModel.age = Integer.parseInt(user[5]);

            users.add(userModel);
        }
    }

    public UserModel login(String email, String password) {
        Optional<UserModel> user = users.stream()
                .filter(u -> u.email.equals(email) && u.password.equals(password))
                .findFirst();
        
        if (user.isPresent()) {
            this.currentUser = user.get();
            return this.currentUser;
        }
        return null;
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }

    public String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email cannot be empty";
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "Invalid email format";
        }
        if (users.stream().anyMatch(u -> u.email.equals(email))) {
            return "Email already exists";
        }
        return null;
    }

    public UserModel register(String email, String name, String surname, String password, int age) throws IOException {
        String emailError = validateEmail(email);
        if (emailError != null) {
            throw new IllegalArgumentException(emailError);
        }

        UserModel newUser = new UserModel();
        newUser.id = users.size() + 1;
        newUser.email = email;
        newUser.name = name;
        newUser.surname = surname;
        newUser.password = password;
        newUser.age = age;

        users.add(newUser);

        String[] userData = {
            String.valueOf(newUser.id),
            newUser.email,
            newUser.name,
            newUser.surname,
            newUser.password,
            String.valueOf(newUser.age)
        };
        database.insertRow(userData);
        database.saveDatabase();
        return newUser;
    }

    public void destroy() {
        try {
            database.shutdown();
        } catch (Exception e) {
            System.err.println("Error during UserService shutdown: " + e.getMessage());
        }
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
