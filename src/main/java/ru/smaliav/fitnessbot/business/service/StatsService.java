package ru.smaliav.fitnessbot.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class StatsService {

    private final UserRepository userRepository;

    public StatsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerLastUsage(FitnessUser fitnessUser) {
        fitnessUser.setLastUsed(LocalDateTime.now());
        userRepository.saveOrUpdateUser(fitnessUser);
    }

}
