package ru.smaliav.fitnessbot.business.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.smaliav.fitnessbot.BaseTest;
import ru.smaliav.fitnessbot.mapper.UserMapper;
import ru.smaliav.fitnessbot.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.smaliav.fitnessbot.util.EntityConstructorUtil.userEntity;
import static ru.smaliav.fitnessbot.util.ObjectConstructorUtil.fitnessUser;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatsServiceTest extends BaseTest {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private StatsService statsService;

    @BeforeEach
    public void setUp() {
        statsService = new StatsService(
            userRepository,
            userMapper
        );
        userRepository.save(userEntity(1));
    }

    @Test
    public void givenUser_whenRegisterLastUsage_thenUpdateLastUsageTs() {
        var fitnessUser = fitnessUser(1);

        var tsBefore = userRepository.findById(1L).get().getLastUsed();
        statsService.registerLastUsage(fitnessUser);
        var tsAfter = userRepository.findById(1L).get().getLastUsed();

        assertThat(tsBefore).isBefore(tsAfter);
    }
}
