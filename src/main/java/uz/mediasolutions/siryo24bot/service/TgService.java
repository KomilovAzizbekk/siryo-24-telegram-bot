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
                } else if (makeService.getUserStep(chatId).equals(StepName.CHOOSE_FROM_MENU) &&
                        text.equals(makeService.getMessage(Message.MENU_INSTRUCTION, makeService.getUserLanguage(chatId)))) {
                    execute(makeService.whenInstruction(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHOOSE_FROM_MENU) &&
                        text.equals(makeService.getMessage(Message.MENU_SUG_COMP, makeService.getUserLanguage(chatId)))) {
                    execute(makeService.whenSuggestComplaint(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.SEND_SUGG_COMP)) {
                    execute(makeService.whenSendSugCompUser(update));
                    execute(makeService.whenSendSugCompChannel(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHOOSE_FROM_MENU) &&
                        text.equals(makeService.getMessage(Message.MENU_SETTINGS, makeService.getUserLanguage(chatId)))) {
                    execute(makeService.whenSettings1(update));
                    execute(makeService.whenSettings2(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHANGE_NAME)) {
                    execute(makeService.whenChangeName2(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHANGE_PHONE_NUMBER)) {
                    execute(makeService.whenChangePhoneNumber2(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.INCORRECT_PHONE_FORMAT_1)) {
                    execute(makeService.whenIncorrectPhoneFormat1(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHANGE_LANGUAGE) &&
                        (text.equals(makeService.getMessage(Message.UZBEK, makeService.getUserLanguage(chatId))) ||
                        text.equals(makeService.getMessage(Message.RUSSIAN, makeService.getUserLanguage(chatId))))) {
                    execute(makeService.whenChangeLanguage2(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHANGE_ROLE) &&
                        (text.equals(makeService.getMessage(Message.SELLER, makeService.getUserLanguage(chatId))) ||
                                text.equals(makeService.getMessage(Message.CUSTOMER, makeService.getUserLanguage(chatId))))) {
                    execute(makeService.whenChangeRole2(update));
                }
            } else if (update.hasMessage() && update.getMessage().hasContact()) {
                if (makeService.getUserStep(chatId).equals(StepName.SHARE_PHONE_NUMBER)) {
                    execute(makeService.whenSharePhoneNumber(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.CHANGE_PHONE_NUMBER)) {
                    execute(makeService.whenChangePhoneNumber2(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.INCORRECT_PHONE_FORMAT)) {
                    execute(makeService.whenIncorrectPhoneFormat(update));
                } else if (makeService.getUserStep(chatId).equals(StepName.INCORRECT_PHONE_FORMAT_1)) {
                    execute(makeService.whenIncorrectPhoneFormat1(update));
                }
            } else if (update.hasCallbackQuery()) {
                String data = update.getCallbackQuery().getData();
                if (data.equals("changeName")) {
                    execute(makeService.whenChangeName1(update));
                } else if (data.equals("changePhone")) {
                    execute(makeService.deleteMessageForCallback(update));
                    execute(makeService.whenChangePhoneNumber1(update));
                } else if (data.equals("changeRole")) {
                    execute(makeService.deleteMessageForCallback(update));
                    execute(makeService.whenChangeRole1(update));
                } else if (data.equals("changeLanguage")) {
                    execute(makeService.deleteMessageForCallback(update));
                    execute(makeService.whenChangeLanguage1(update));
                }
            }
        }
    }
}
