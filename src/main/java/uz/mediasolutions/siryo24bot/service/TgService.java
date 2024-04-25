package uz.mediasolutions.siryo24bot.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.enums.StepName;
import uz.mediasolutions.siryo24bot.repository.TgUserRepository;
import uz.mediasolutions.siryo24bot.utills.constants.Message;

@Service
@RequiredArgsConstructor
public class TgService extends TelegramLongPollingBot {

    private final MakeService makeService;
    private final TgUserRepository tgUserRepository;

    @Override
    public String getBotUsername() {
        return "siryo24bot";
//        return "sakaka_bot";
    }

    @Override
    public String getBotToken() {
        return "7174927353:AAFhxxnY8Hkgkx06QFG89UE-JWdhRhEMdDI";
//        return "6052104473:AAEscLILevwPMcG_00PYqAf-Kpb7eIUCIGg";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = makeService.getChatId(update);
        TgUser tgUser = tgUserRepository.findByChatId(chatId);
        boolean existsByChatId = tgUserRepository.existsByChatId(chatId);

        System.out.println(update);

        if (existsByChatId && tgUser.isBanned()) {
            execute(new SendMessage(chatId, makeService.getMessage(Message.YOU_ARE_BANNED,
                    makeService.getUserLanguage(chatId))));
        } else {
            if (update.hasMessage() && update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                if (text.equals("/start") && !existsByChatId) {
                    execute(makeService.whenStart(update));
                } else if (text.equals("/start")) {
                    execute(makeService.check(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHOOSE_LANG) &&
                        (text.equals(makeService.getMessage(Message.UZBEK, "UZ")) ||
                                text.equals(makeService.getMessage(Message.RUSSIAN, "UZ")))) {
                    execute(makeService.whenChosenLang(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.SHARE_PHONE_NUMBER)) {
                    execute(makeService.whenSharePhoneNumber(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.INCORRECT_PHONE_FORMAT)) {
                    execute(makeService.whenIncorrectPhoneFormat(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.ENTER_NAME)) {
                    execute(makeService.whenChooseRole(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHOOSE_ROLE) &&
                        (text.equals(makeService.getMessage(Message.SELLER, makeService.getUserLanguage(chatId))) ||
                                text.equals(makeService.getMessage(Message.CUSTOMER, makeService.getUserLanguage(chatId))))) {
                    execute(makeService.whenMenu(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHOOSE_FROM_MENU) &&
                        text.equals(makeService.getMessage(Message.MENU_CATALOG, makeService.getUserLanguage(chatId)))) {
                    execute(makeService.whenCatalog(update));
                }
            } else if (update.hasMessage() && update.getMessage().hasContact()) {
                if (makeService.getUserStep(chatId).equals(StepName.SHARE_PHONE_NUMBER)) {
                    execute(makeService.whenSharePhoneNumber(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.INCORRECT_PHONE_FORMAT)) {
                    execute(makeService.whenIncorrectPhoneFormat(update));
                }
            }
        }
    }
}
