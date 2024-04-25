package uz.mediasolutions.siryo24bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import uz.mediasolutions.siryo24bot.entity.LanguagePs;
import uz.mediasolutions.siryo24bot.entity.LanguageSourcePs;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.enums.LanguageName;
import uz.mediasolutions.siryo24bot.enums.RoleName;
import uz.mediasolutions.siryo24bot.enums.StepName;
import uz.mediasolutions.siryo24bot.repository.*;
import uz.mediasolutions.siryo24bot.utills.constants.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MakeService {

    private final LanguageRepositoryPs languageRepositoryPs;
    private final TgUserRepository tgUserRepository;
    private final StepRepository stepRepository;
    private final LanguageRepository languageRepository;
    private final RoleRepository roleRepository;

    private final String WEB_URL = "https://siryo24-bot-web-app.netlify.app";

    public String getMessage(String key, String language) {
        List<LanguagePs> allByLanguage = languageRepositoryPs.findAll();
        if (!allByLanguage.isEmpty()) {
            for (LanguagePs languagePs : allByLanguage) {
                for (LanguageSourcePs languageSourceP : languagePs.getLanguageSourcePs()) {
                    if (languageSourceP.getTranslation() != null &&
                            languageSourceP.getLanguage().equals(language) &&
                            languagePs.getKey().equals(key)) {
                        return languageSourceP.getTranslation();
                    }
                }
            }
        }
        return null;
    }

    public void setUserStep(String chatId, StepName stepName) {
        TgUser tgUser = tgUserRepository.findByChatId(chatId);
        tgUser.setStep(stepRepository.findByName(stepName));
        tgUserRepository.save(tgUser);
    }

    public StepName getUserStep(String chatId) {
        TgUser tgUser = tgUserRepository.findByChatId(chatId);
        return tgUser.getStep().getName();
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "\\+998[1-9]\\d{8}";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    public String getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId().toString();
        }
        return "";
    }

    public String getUsername(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getUserName();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getUserName();
        }
        return "";
    }

    public String getUserLanguage(String chatId) {
        if (tgUserRepository.existsByChatId(chatId)) {
            TgUser tgUser = tgUserRepository.findByChatId(chatId);
            return tgUser.getLanguage().getName().name();
        } else
            return "UZ";
    }

    public SendMessage whenStart(Update update) {
        String chatId = getChatId(update);
        SendMessage sendMessage = new SendMessage(chatId, getMessage(Message.LANG_SAME_FOR_2_LANG,
                "UZ"));
        sendMessage.setReplyMarkup(forStart());
        if (!tgUserRepository.existsByChatId(chatId)) {
            TgUser tgUser = TgUser.builder().chatId(chatId)
                    .banned(false)
                    .username(getUsername(update))
                    .chatId(chatId)
                    .build();
            tgUserRepository.save(tgUser);
        }
        setUserStep(chatId, StepName.CHOOSE_LANG);
        return sendMessage;
    }

    private ReplyKeyboardMarkup forStart() {
        String chatId = getChatId(new Update());

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();

        KeyboardButton button1 = new KeyboardButton();
        KeyboardButton button2 = new KeyboardButton();

        button1.setText(getMessage(Message.UZBEK, getUserLanguage(chatId)));
        button2.setText(getMessage(Message.RUSSIAN, getUserLanguage(chatId)));

        row1.add(button1);
        row1.add(button2);

        rowList.add(row1);
        markup.setKeyboard(rowList);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setIsPersistent(true);
        return markup;
    }

    public SendMessage whenChosenLang(Update update) {
        String chatId = getChatId(update);
        String text = update.getMessage().getText();
        TgUser user = tgUserRepository.findByChatId(chatId);

        if (text.equals(getMessage(Message.UZBEK, "UZ"))) {
            user.setLanguage(languageRepository.findByName(LanguageName.UZ));
        } else {
            user.setLanguage(languageRepository.findByName(LanguageName.RU));
        }
        tgUserRepository.save(user);
        setUserStep(chatId, StepName.SHARE_PHONE_NUMBER);
        return whenPhoneNumber(update);
    }

    public SendMessage whenPhoneNumber(Update update) {
        String chatId = getChatId(update);
        String language = getUserLanguage(chatId);
        SendMessage sendMessage = new SendMessage(chatId, getMessage(Message.ENTER_PHONE_NUMBER, language));
        sendMessage.setReplyMarkup(forPhoneNumber(language));
        return sendMessage;
    }

    private ReplyKeyboardMarkup forPhoneNumber(String lang) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();

        KeyboardButton button1 = new KeyboardButton();

        button1.setText(getMessage(Message.SHARE_PHONE_NUMBER, lang));
        button1.setRequestContact(true);

        row1.add(button1);

        rowList.add(row1);
        markup.setKeyboard(rowList);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setIsPersistent(true);
        return markup;
    }

    public SendMessage whenSharePhoneNumber(Update update) {
        String chatId = getChatId(update);
        TgUser tgUser = tgUserRepository.findByChatId(chatId);

        if (update.getMessage().hasText()) {
            if (isValidPhoneNumber(update.getMessage().getText())) {
                String phoneNumber = update.getMessage().getText();
                tgUser.setPhoneNumber(phoneNumber);
                tgUserRepository.save(tgUser);
                return whenEnterName(update);
            } else {
                SendMessage sendMessage = new SendMessage(chatId,
                        getMessage(Message.INCORRECT_PHONE_FORMAT, getUserLanguage(chatId)));
                sendMessage.setReplyMarkup(forPhoneNumber(getUserLanguage(chatId)));
                setUserStep(chatId, StepName.INCORRECT_PHONE_FORMAT);
                return sendMessage;
            }
        } else {
            String phoneNumber = update.getMessage().getContact().getPhoneNumber();
            phoneNumber = phoneNumber.startsWith("+") ? phoneNumber : "+" + phoneNumber;
            tgUser.setPhoneNumber(phoneNumber);
            tgUserRepository.save(tgUser);
            return whenEnterName(update);
        }
    }

    public SendMessage whenIncorrectPhoneFormat(Update update) {
        return whenSharePhoneNumber(update);
    }

    public SendMessage whenEnterName(Update update) {
        String chatId = getChatId(update);
        String language = getUserLanguage(chatId);

        setUserStep(chatId, StepName.ENTER_NAME);
        SendMessage sendMessage = new SendMessage(chatId, getMessage(Message.ENTER_NAME, language));
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sendMessage;
    }


    public SendMessage check(Update update) {
        String chatId = getChatId(update);
        TgUser user = tgUserRepository.findByChatId(chatId);

        if (user.getLanguage() == null) {
            return whenStart(update);
        } else if (user.getPhoneNumber() == null) {
            return whenPhoneNumber(update);
        } else if (user.getName() == null) {
            return whenEnterName(update);
        } else {
            return whenMenu(update);
        }
    }

    public SendMessage whenChooseRole(Update update) {
        String chatId = getChatId(update);
        String language = getUserLanguage(chatId);
        TgUser user = tgUserRepository.findByChatId(chatId);
        if (user.getName() == null) {
            String name = update.getMessage().getText();
            user.setName(name);
            tgUserRepository.save(user);
        }
        SendMessage sendMessage = new SendMessage(chatId,
                String.format(getMessage(Message.CHOOSE_ROLE, language), user.getName()));
        sendMessage.setReplyMarkup(forChooseRole(chatId));
        setUserStep(chatId, StepName.CHOOSE_ROLE);
        return sendMessage;
    }


    private ReplyKeyboardMarkup forChooseRole(String chatId) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();

        String language = getUserLanguage(chatId);

        KeyboardButton button1 = new KeyboardButton();
        KeyboardButton button2 = new KeyboardButton();

        button1.setText(getMessage(Message.CUSTOMER, language));
        button2.setText(getMessage(Message.SELLER, language));

        row1.add(button1);
        row1.add(button2);

        rowList.add(row1);

        markup.setKeyboard(rowList);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setIsPersistent(true);
        return markup;
    }

    public SendMessage whenMenu(Update update) {
        String chatId = getChatId(update);
        String language = getUserLanguage(chatId);
        TgUser user = tgUserRepository.findByChatId(chatId);
        if (user.getRole() == null) {
            String role = update.getMessage().getText();
            if (role.equals(getMessage(Message.CUSTOMER, language)))
                user.setRole(roleRepository.findByName(RoleName.ROLE_CUSTOMER));
            else
                user.setRole(roleRepository.findByName(RoleName.ROLE_SELLER));
            tgUserRepository.save(user);
        }

        SendMessage sendMessage = new SendMessage(chatId, getMessage(Message.MENU_MSG, language));
        sendMessage.setReplyMarkup(forMenu(chatId));
        setUserStep(chatId, StepName.CHOOSE_FROM_MENU);
        return sendMessage;
    }

    private ReplyKeyboardMarkup forMenu(String chatId) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        String language = getUserLanguage(chatId);

        KeyboardButton button1 = new KeyboardButton();
        KeyboardButton button2 = new KeyboardButton();
        KeyboardButton button3 = new KeyboardButton();
        KeyboardButton button4 = new KeyboardButton();
        KeyboardButton button5 = new KeyboardButton();

        button1.setText(getMessage(Message.MENU_CATALOG, language));
        button2.setText(getMessage(Message.MENU_SETTINGS, language));
        button3.setText(getMessage(Message.MENU_MY_APPLICATIONS, language));
        button4.setText(getMessage(Message.MENU_INSTRUCTION, language));
        button5.setText(getMessage(Message.MENU_SUG_COMP, language));

        button3.setWebApp(new WebAppInfo(WEB_URL + "/request" + chatId + "/" + language));

        row1.add(button1);
        row1.add(button2);
        row2.add(button3);
        row3.add(button4);
        row3.add(button5);

        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        markup.setKeyboard(rowList);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setIsPersistent(true);
        return markup;
    }

    public SendMessage whenCatalog(Update update) {
        String chatId = getChatId(update);
        String language = getUserLanguage(chatId);

        SendMessage sendMessage = new SendMessage(chatId, getMessage(Message.PRESS_CATALOG, language));
        sendMessage.setReplyMarkup(forCatalog(chatId));
        return sendMessage;
    }

    private InlineKeyboardMarkup forCatalog(String chatId) {
        String language = getUserLanguage(chatId);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();

        button1.setText(getMessage(Message.MENU_CATALOG, language));

        button1.setWebApp(new WebAppInfo(WEB_URL + "/catalog" + "/" + chatId + "/" + language));

        List<InlineKeyboardButton> row1 = new ArrayList<>();

        row1.add(button1);

        rowsInline.add(row1);

        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

}
