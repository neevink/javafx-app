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
                    {"ExecuteScript", "Выполнить скрипт"},
                    {"Information", "Информация"},
                    {"ReplaceIf", "Заменить если"},
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
                    {"Add", "Добавить"},
                    {"Remove", "Удалить"},
                    {"Clear", "Очистить"},
                    {"Delete", "Удалить"},
                    {"Create", "Создать"},
                    {"ChooseFile", "Выбрать файл"},
                    {"ReplaceIfGreater", "Заменить, если больше"},
                    {"ReplaceIfLower", "Заменить, если меньше"},
                    {"Drop", "Сбросить"},
                    {"StartsWith", "Начинается с"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
