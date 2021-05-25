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
                    {"BrowseCollection", "Koleksiyona genel bakış"},
                    {"ExecuteScript", "Komut dosyasını yürüt"},
                    {"Information", "Bilgi"},
                    {"ReplaceIf", "Değiştirin"},
                    {"Owner", "Sahip"},
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
                    {"Add", "a ekle"},
                    {"Remove", "Silme"},
                    {"Clear", "Açık"},
                    {"Delete", "Silme"},
                    {"Create", "Oluşturmak"},
                    {"ChooseFile", "Bir dosya seçin"},
                    {"ReplaceIfGreater", "Daha büyükse değiştirin"},
                    {"ReplaceIfLower", "Daha azsa değiştirin"},
                    {"Drop", "Sıfırla"},
                    {"StartsWith", "İle başlar"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
