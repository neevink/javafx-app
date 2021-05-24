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
                    {"Save", "Сохранить"},
                    {"Back", "Назад"},
                    {"VisualizationArea", "Область визуализации"},
                    {"BrowseCollection", "Обзор коллекции"},
                    {"Owner", "Владелец"},
                    {"Id", "Идентификатор"},
                    {"CreationDate", "Дата создания"},
                    {"Name", "Название"},
                    {"Distance", "Дистанция"},
                    {"CoordinateX", "Коорданата X"},
                    {"CoordinateY", "Координата Y"},
                    {"FromX", "Откуда X"},
                    {"FromY", "Откуда Y"},
                    {"FromName", "Название откуда"},
                    {"ToX", "Куда X"},
                    {"ToY", "Куда Y"},
                    {"ToName", "Название куда"},
                    {"FieldsCantBeEmpty", "Поля не могут быть пустыми"},
                    {"ExecuteScript", "Выполнить скрипт"},
                    {"Information", "Информация"},
                    {"Add", "Добавить"},
                    {"Clear", "Очистить"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
