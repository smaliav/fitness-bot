package ru.smaliav.fitnessbot.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.smaliav.fitnessbot.business.object.FitnessUser;
import ru.smaliav.fitnessbot.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerIfNotExist(User tUser, Chat chat) {
        FitnessUser fUser = userRepository.getUserByTelegramId(tUser.getId());

        if (fUser != null) {
            updateUserInfo(tUser, chat, fUser);
        } else {
            fUser = registerUser(tUser, chat);
        }

        userRepository.saveUser(fUser);
    }

    private void updateUserInfo(User tUser, Chat chat, FitnessUser fUser) {
        boolean wasModified = false;

        // TODO smaliy-av Refactor
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

        fUser.setTelegramId(tUser.getId());
        fUser.setNickname(tUser.getUserName());
        fUser.setFirstName(tUser.getFirstName());
        fUser.setSecondName(tUser.getLastName());
        fUser.setChatId(chat.getId());
        fUser.setCreated(LocalDateTime.now());

        return fUser;
    }

}
