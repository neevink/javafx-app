package resources;

import java.util.ListResourceBundle;

public class resource_es extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    {"LanguageName", "Español"},
                    {"Language", "Idioma"},
                    {"Login", "Entrada"},
                    {"Register", "registrarse"},
                    {"Exit", "Producción"},
                    {"AccountName", "Nombre de la cuenta"},
                    {"Password", "Contraseña"},
                    {"PasswordAgain", "La contraseña de nuevo"},
                    {"Cancel", "Cancelación"},
                    {"Save", "Ahorrar"},
                    {"Back", "De regreso"},
                    {"VisualizationArea", "Área de visualización"},
                    {"BrowseCollection", "Resumen de la colección"},
                    {"ExecuteScript", "Ejecutar secuencia de comandos"},
                    {"Information", "Información"},
                    {"ReplaceIf", "Reemplazar si"},
                    {"Owner", "Dueño"},
                    {"Id", "Identificador"},
                    {"CreationDate", "fecha de creación"},
                    {"Name", "Nombre"},
                    {"Distance", "Distancia"},
                    {"CoordinateX", "Coordenada X"},
                    {"CoordinateY", "Coordenada Y"},
                    {"FromX", "Donde X"},
                    {"FromY", "De donde Y"},
                    {"FromName", "Nombre de donde"},
                    {"ToX", "Donde X"},
                    {"ToY", "Donde Y"},
                    {"ToName", "Nombre donde"},
                    {"FieldsCantBeEmpty", "Los campos no pueden estar vacíos"},
                    {"Add", "añadir"},
                    {"Remove", "Borrar"},
                    {"Clear", "Claro"},
                    {"Delete", "Borrar"},
                    {"Create", "Crear un"},
                    {"ChooseFile", "Seleccione un archivo"},
                    {"ReplaceIfGreater", "Reemplazar si es más grande"},
                    {"ReplaceIfLower", "Reemplazar si es menos"},
                    {"Drop", "Reiniciar"},
                    {"StartsWith", "Empezar con"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
