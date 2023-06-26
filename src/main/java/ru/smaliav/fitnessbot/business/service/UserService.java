package ru.smaliav.fitnessbot.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.mapper.IdCycleAvoidingContext;
import ru.smaliav.fitnessbot.mapper.UserMapper;
import ru.smaliav.fitnessbot.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public FitnessUser registerOrUpdate(User tUser, Chat chat) {
        var userEntity = userRepository.findUserEntityByTelegramId(tUser.getId());

        FitnessUser fUser;
        if (userEntity != null) {
            fUser = userMapper.e2b(userEntity, new IdCycleAvoidingContext());
            updateUserInfo(tUser, chat, fUser);
        } else {
            fUser = registerUser(tUser, chat);
        }

        userEntity = userRepository.save(userMapper.b2e(fUser, new IdCycleAvoidingContext()));
        return userMapper.e2b(userEntity, new IdCycleAvoidingContext());
    }

    private void updateUserInfo(User tUser, Chat chat, FitnessUser fUser) {
        boolean wasModified = false;

        if (!Objects.equals(fUser.getNickname(), tUser.getUserName())) {
            fUser.setNickname(tUser.getUserName());
            wasModified = true;
        }
        if (!Objects.equals(fUser.getFirstName(), tUser.getFirstName())) {
            fUser.setFirstName(tUser.getFirstName());
            wasModified = true;
        }
        if (!Objects.equals(fUser.getSecondName(), tUser.getLastName())) {
            fUser.setSecondName(tUser.getLastName());
            wasModified = true;
        }
        if (!Objects.equals(fUser.getChatId(), chat.getId())) {
            fUser.setChatId(chat.getId());
            wasModified = true;
        }

        if (wasModified) fUser.setModified(LocalDateTime.now());
    }

    private FitnessUser registerUser(User tUser, Chat chat) {
        FitnessUser fUser = new FitnessUser();
        LocalDateTime now = LocalDateTime.now();

        fUser.setTelegramId(tUser.getId());
        fUser.setNickname(tUser.getUserName());
        fUser.setFirstName(tUser.getFirstName());
        fUser.setSecondName(tUser.getLastName());
        fUser.setChatId(chat.getId());
        fUser.setCreated(now);
        fUser.setLastUsed(now);

        return fUser;
    }
}
