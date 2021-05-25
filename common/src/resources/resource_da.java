package resources;

import java.util.ListResourceBundle;

public class resource_da extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    {"LanguageName", "Dansk"},
                    {"Language", "Sprog"},
                    {"Login", "indgang"},
                    {"Register", "tjek ind"},
                    {"Exit", "Produktion"},
                    {"AccountName", "Kontonavn"},
                    {"Password", "Adgangskode"},
                    {"PasswordAgain", "Adgangskoden igen"},
                    {"Cancel", "Aflysning"},
                    {"Save", "Gemme"},
                    {"Back", "Tilbage til"},
                    {"VisualizationArea", "Visualiseringsområde"},
                    {"BrowseCollection", "Samlingsoversigt"},
                    {"ExecuteScript", "Udfør script"},
                    {"Information", "Information"},
                    {"ReplaceIf", "Udskift hvis"},
                    {"Owner", "Ejer"},
                    {"Id", "Identifikator"},
                    {"CreationDate", "dato for oprettelse"},
                    {"Name", "Navn"},
                    {"Distance", "Afstand"},
                    {"CoordinateX", "X-koordinat"},
                    {"CoordinateY", "Y koordinat"},
                    {"FromX", "Hvor X"},
                    {"FromY", "Hvorfra Y"},
                    {"FromName", "Navn hvorfra"},
                    {"ToX", "Hvor X"},
                    {"ToY", "Hvor Y"},
                    {"ToName", "Navn hvor"},
                    {"FieldsCantBeEmpty", "Felter kan ikke være tomme"},
                    {"Add", "Tilføj til"},
                    {"Remove", "Slet"},
                    {"Clear", "Klar"},
                    {"Delete", "Slet"},
                    {"Create", "Lave en"},
                    {"ChooseFile", "Vælg en fil"},
                    {"ReplaceIfGreater", "Udskift hvis større"},
                    {"ReplaceIfLower", "Udskift hvis mindre"},
                    {"Drop", "Nulstil"},
                    {"StartsWith", "Begynd med"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
