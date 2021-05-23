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
                    {"Save", "Kayıt etmek"},
                    {"Back", "Geri dön"},
                    {"VisualizationArea", "Görselleştirme alanı"},
                    {"Id", "Tanımlayıcı"},
                    {"CreationDate", "yaratma tarihi"},
                    {"Name", "İsim Soyisim"},
                    {"Distance", "Mesafe"},
                    {"CoordinateX", "X koordinatı"},
                    {"CoordinateY", "Y koordinatı"},
                    {"FromX", "Nerede X"},
                    {"FromY", "Nereden Y"},
                    {"FromName", "Nereden isim"},
                    {"ToX", "Nerede X"},
                    {"ToY", "Nerede Y"},
                    {"ToName", "Nerede isim"},
                    {"FieldsCantBeEmpty", "Alanlar boş olamaz"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
