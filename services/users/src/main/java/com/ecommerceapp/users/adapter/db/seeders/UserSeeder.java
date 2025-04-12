package com.ecommerceapp.users.adapter.db.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecommerceapp.users.core.domain.entities.User;
import com.ecommerceapp.users.core.port.outbound.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {
    private final UserRepository userRepository;

    private void customerSeeder() {
        if (userRepository.findUserByEmail("thaibao22042k4@gmail.com").isEmpty()) {
            User user = new User(
                    "thaibao22042k4@gmail.com",
                    "22042004bao",
                    "Thai",
                    "Bao",
                    "0828537679");
            userRepository.save(user);
        }
    }

    private void shopOwnerSeeder() {
        if (userRepository.findUserByEmail("banbao22042004@gmail.com").isEmpty()) {
            User user = new User(
                    "banbao22042004@gmail.com",
                    "22042004bao",
                    "Thai",
                    "Bao",
                    "0828537679");
            user.activateShopOwner("test");
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        customerSeeder();
        shopOwnerSeeder();
    }
}
