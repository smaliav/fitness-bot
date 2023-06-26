package ru.smaliav.fitnessbot.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.mapper.IdCycleAvoidingContext;
import ru.smaliav.fitnessbot.mapper.UserMapper;
import ru.smaliav.fitnessbot.repository.UserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class StatsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void registerLastUsage(FitnessUser fitnessUser) {
        fitnessUser.setLastUsed(LocalDateTime.now());
        userRepository.save(userMapper.b2e(fitnessUser, new IdCycleAvoidingContext()));
    }
}
