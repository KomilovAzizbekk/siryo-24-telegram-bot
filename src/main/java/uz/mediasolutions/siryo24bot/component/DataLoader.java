package uz.mediasolutions.siryo24bot.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.mediasolutions.siryo24bot.entity.*;
import uz.mediasolutions.siryo24bot.enums.*;
import uz.mediasolutions.siryo24bot.repository.*;
import uz.mediasolutions.siryo24bot.service.TgService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ApplicationContext applicationContext;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final LanguageSourceRepositoryPs languageSourceRepositoryPs;
    private final LanguageRepositoryPs languageRepositoryPs;
    private final StepRepository stepRepository;
    private final LanguageRepository languageRepository;

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        TgService tgService = applicationContext.getBean(TgService.class);
        telegramBotsApi.registerBot(tgService);

        if (mode.equals("always")) {
            addRole();
            addAdmin();
            addSteps();
            addUzLangValues();
            addRuLangValues();
            addLanguage();
        }
    }

    public void addLanguage() {
        for (LanguageName value : LanguageName.values()) {
            Language language = Language.builder().name(value).build();
            languageRepository.save(language);
        }
    }

    private void addRole() {
        for (RoleName value : RoleName.values()) {
            Role role = Role.builder().name(value).build();
            roleRepository.save(role);
        }
    }


    public void addAdmin() {
        User admin = User.builder()
                .role(roleRepository.findByName(RoleName.ROLE_ADMIN))
                .username("admin")
                .password(passwordEncoder.encode("Qwerty123@"))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .build();
        userRepository.save(admin);
    }

    public void addSteps() {
        for (StepName value : StepName.values()) {
            Step step = Step.builder().name(value).build();
            stepRepository.save(step);
        }
    }

    public void addUzLangValues() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = DataLoader.class.getClassLoader()
                .getResourceAsStream("messages_uz.properties")) {
            properties.load(input);
        }
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            LanguagePs ps = LanguagePs.builder().primaryLang("UZ").key(key).build();
            LanguagePs save = languageRepositoryPs.save(ps);
            LanguageSourcePs sourcePs = LanguageSourcePs.builder()
                    .languagePs(save).language("UZ").translation(value).build();
            languageSourceRepositoryPs.save(sourcePs);
        }
    }

    public void addRuLangValues() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = DataLoader.class.getClassLoader()
                .getResourceAsStream("messages_ru.properties")) {
            properties.load(input);
        }
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            LanguagePs languagePs = languageRepositoryPs.findByKey(key);
            LanguageSourcePs sourcePs = LanguageSourcePs.builder()
                    .languagePs(languagePs).language("RU").translation(value).build();
            languageSourceRepositoryPs.save(sourcePs);
        }
    }
}
