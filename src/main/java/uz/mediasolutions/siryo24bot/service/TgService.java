package uz.mediasolutions.siryo24bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TgService extends TelegramLongPollingBot {



    @Override
    public String getBotUsername() {
        return "siryo24bot";
    }

    @Override
    public String getBotToken() {
        return "7174927353:AAFhxxnY8Hkgkx06QFG89UE-JWdhRhEMdDI";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
