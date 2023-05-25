package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.Categoria;
import es.progcipfpbatoi.modelo.dto.Prioridad;
import es.progcipfpbatoi.modelo.dto.Tipo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileCategoriaDAO implements CategoriaDAO{

    private static final String DATABASE_FILE = "resources/database/categorias.txt";

    private static final String FIELD_SEPARATOR = ";";
    private static final int ID = 0;
    private static final int TIPO = 1;
    private static final int PRIORIDAD = 2;
    private File file;

    public FileCategoriaDAO() {
        this.file = new File(DATABASE_FILE);
    }

    @Override
    public Categoria findById(int id) throws DatabaseErrorException {
        try (FileReader fileReader = new FileReader(this.file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            do {
                String register = bufferedReader.readLine();
                if (register == null) {
                    return null;
                } else if (!register.isBlank()) {
                    Categoria categoria = getCategoryFromRegister(register);
                    if (categoria.getId() == id) {
                        return categoria;
                    }
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ocurrió un error en el acceso a la base de datos de categorías");
        }
    }

    public Categoria getByTypeAndPriority(Tipo tipo, Prioridad prioridad) throws NotFoundException, DatabaseErrorException {
        try (FileReader fileReader = new FileReader(this.file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            do {
                String register = bufferedReader.readLine();
                if (register == null) {
                    throw new NotFoundException("No se ha encontrado ninguna categoría con esa prioridad registrada");
                } else if (!register.isBlank()) {
                    Categoria categoria = getCategoryFromRegister(register);
                    if (categoria.tieneEsteTipoYPrioridad(tipo, prioridad)) {
                        return categoria;
                    }
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ocurrió un error en el acceso a la base de datos de categorías");
        }
    }

    private Categoria getCategoryFromRegister(String register) {
        String[] fields = register.split(FIELD_SEPARATOR);
        int codigo = Integer.parseInt(fields[ID]);
        Tipo tipo = Tipo.parse(fields[TIPO]);
        Prioridad prioridad = Prioridad.parse(fields[PRIORIDAD]);
        return new Categoria(codigo, tipo, prioridad);
    }
}
