package ru.jm.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.jm.crud.model.User;
import ru.jm.crud.model.UserRole;
import ru.jm.crud.service.RoleService;
import ru.jm.crud.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Startup {

    UserService service;
    RoleService roleService;

    @Autowired
    public void setService(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    private static String randomLogin() {
        List<String> logins = new ArrayList<>(Arrays.asList
                ("sam", "cole", "wood", "brick", "car", "rock", "bed", "owl", "rain", "white", "black"));
        int i = ThreadLocalRandom.current().nextInt(1, logins.size());
        int i2 = ThreadLocalRandom.current().nextInt(1, logins.size());
        return logins.get(i) + logins.get(i2) + i2 * i;
    }

    private static String randomPassword() {
        List<String> passwords = new ArrayList<>(Arrays.asList
                ("123", "456", "@", "!", "999", "777", "555", "qwe", "rty", "zxc", "asd", "#", "$"));
        int i = ThreadLocalRandom.current().nextInt(1, passwords.size());
        int i2 = ThreadLocalRandom.current().nextInt(1, passwords.size());
        int i3 = ThreadLocalRandom.current().nextInt(1, passwords.size());
        return passwords.get(i) + passwords.get(i2) + passwords.get(i3) + i2 * i;
    }

    private static String randomName() {
        List<String> names = new ArrayList<>(Arrays.asList
                ("Alexander", "Michael", "Lex", "Sandy", "Roderick", "Bob", "Rick", "Zak", "Robin", "Andy", "Stephen", "Stanley"));
        int i = ThreadLocalRandom.current().nextInt(1, names.size());
        return names.get(i);
    }

    private static String randomLastname() {
        List<String> names = new ArrayList<>(Arrays.asList
                ("Addison", "Black", "White", "Jonson", "Jenkins", "Meyers", "Winslet", "Shades", "Queens", "Abigale", "Dallas", "Rodgers"));
        int i = ThreadLocalRandom.current().nextInt(1, names.size());
        return names.get(i);
    }

    private static String randomDomain() {
        List<String> names = new ArrayList<>(Arrays.asList
                ("bk.ru", "mail.ru", "gmail.com", "msn.com", "hotmail.com", "yandex.ru", "yahoo.com"));
        int i = ThreadLocalRandom.current().nextInt(1, names.size());
        return names.get(i);
    }

    private static int randomAge() {
        return ThreadLocalRandom.current().nextInt(18, 65);
    }

    @Bean
    public void init() {
        System.out.println("Startup initializing");

        try {

            UserRole role1 = new UserRole("ROLE_ADMIN");
            UserRole role2 = new UserRole("ROLE_USER");
            UserRole role3 = new UserRole("ROLE_GUEST");
            roleService.add(role1);
            roleService.add(role2);
            roleService.add(role3);


            service.add(new User("ADMIN", "ADMIN", "Саша", "Moiseev", "36", "sobergrim@gmail.com", role1, role2, role3));
            service.add("USER", "USER", "Патрик", "Douglas", "77", "pat33@yandex.ru", role2, role3);
            service.add("GUEST", "GUEST", "Casper", "Johnson", "22", "casper_chost@yahoo.com", role3);
            service.add("АДМИН", "АДМИН", "Сумерадмин", "Lerok", "32", "moiseeva_val89@bk.ru", role1);
            service.add("ЮЗЕР", "ЮЗЕР", "Vla-зер", "Цепеш", "41", "vlad199332a@yandex.ru", role2);
            service.add("ГОСТЬ", "ГОСТЬ", "Vlad-гость", "Соколов", "64", "влад911@яндекс.рф", role3);
            service.add("mikey777", "mickey&mina", "Mickey", "Mouse", "77", "warnerbrothers@gmail.com", role3);
            service.add("civilV", "imDaBest99", "Sid", "Meyers", "16", "sid@yahoo.com");

            String pass = service.getByUsername("АДМИН").getPassword();
            System.out.println(pass);

            for (int i = 0; i < 10; i++) {
                String name = randomName();
                String lastname = randomLastname();
                int age = randomAge();
                String email;
                if ((age % 2) == 0) {
                    email = name + lastname + (100 - age + i) + "@" + randomDomain();
                } else if ((age % 3) == 0) {
                    email = name + lastname + (2021 - age + i) + "@" + randomDomain();
                } else {
                    email = name + lastname + i + "@" + randomDomain();
                }
                service.add(randomLogin() + i, randomPassword(), name, lastname, String.valueOf(randomAge()), email.toLowerCase(Locale.ROOT), ((randomAge()+i) % 2 == 0) ? role2 : role3);
            }
            System.out.println("Users added");

        } catch (Exception e) {
            System.out.println("Yo bro");
        }
    }
}