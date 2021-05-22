package resources;

import java.util.ListResourceBundle;

public class resource_tr extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    {"LanguageName", "Türk"},
                    {"Language", "Dil"},
                    {"Login", "Giriş"},
                    {"Register", "giriş"},
                    {"Exit", "Çıktı"},
                    {"AccountName", "Hesap adı"},
                    {"Password", "Parola"},
                    {"PasswordAgain", "Tekrar şifre"},
                    {"Cancel", "İptal"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
