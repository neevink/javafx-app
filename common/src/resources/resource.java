package resources;

import java.util.ListResourceBundle;

public class resource extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    {"LanguageName", "Русский"},
                    {"Language", "Язык"},
                    {"Login", "Вход"},
                    {"Register", "Регистрация"},
                    {"Exit", "Выход"},
                    {"AccountName", "Имя аккаунта"},
                    {"Password", "Пароль"},
                    {"PasswordAgain", "Пароль ещё раз"},
                    {"Cancel", "Отмена"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
