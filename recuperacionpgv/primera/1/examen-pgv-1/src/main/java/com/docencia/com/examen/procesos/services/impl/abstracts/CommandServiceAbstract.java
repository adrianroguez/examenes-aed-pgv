package com.docencia.com.examen.procesos.services.impl.abstracts;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.docencia.com.examen.procesos.domain.Job;
import com.docencia.com.examen.procesos.repositories.file.FileJobRepository;
import com.docencia.com.examen.procesos.services.interfaces.CommandService;

/**
 * Clase abstracta que implementa la logica comun del servicio de comandos.
 * Maneja la validacion del comando, la ejecucion del proceso y el
 * redireccionamiento de la salida.
 */
public abstract class CommandServiceAbstract implements CommandService {
    // Repositorio para manejar el archivo de salida (logger)
    FileJobRepository fileRepository;

    /**
     * Inyeccion de dependencias del repositorio mediante setter.
     * 
     * @param fileRepository repositorio a inyectar
     */
    @Autowired
    public void setFileRepository(FileJobRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    private String comando;
    private Job tipo;
    private String expresionRegular;

    /**
     * Metodo principal para procesar la linea de comandos.
     * 
     * @param command   Comando completo introducido por el usuario.
     * @param changeCmd flag booleano (no usado en la logica actual).
     * @return true si se ejecuto correctamente, false si fallo.
     */
    @Override
    public boolean processLine(String command, boolean changeCmd) {
        // Inicializa el repositorio. Nota: Esto sobreescribe la inyeccion, lo cual es
        // inusual,
        // pero asegura una nueva instancia del repositorio.
        fileRepository = new FileJobRepository();

        // Separa el comando de sus argumentos usando espacios como separador
        String[] arrayComando = command.split("\s+");
        setComando(arrayComando[0]);
        System.out.println("Comando: " + getComando());

        // Valida el comando y sus argumentos
        if (!validar(arrayComando)) {
            System.out.println("El comando no es valido");
            return false;
        }

        Process proceso;
        try {
            // Crea y ejecuta el proceso usando ProcessBuilder.
            // "sh -c" permite ejecutar una linea de comandos completa en la shell.
            // " > " redirige la salida estandar al archivo especificado por el repositorio.
            proceso = new ProcessBuilder("sh", "-c", command + " > " + fileRepository.getPath()).start();
            ejecutarProceso(proceso);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Espera a que el proceso termine su ejecucion.
     * 
     * @param proceso El proceso que se esta ejecutando.
     * @return true
     */
    public boolean ejecutarProceso(Process proceso) {
        try {
            // Bloquea el hilo actual hasta que el subproceso termine
            proceso.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Valida que el comando corresponda al tipo esperado y que sus argumentos
     * cumplan con la regex.
     * 
     * @param arrayComando Array con el comando y sus argumentos.
     * @return true si es valido.
     */
    public boolean validar(String[] arrayComando) {
        // Valida que el nombre del comando coincida con el tipo configurado (ej: DF)
        if (!validarComando()) {
            return false;
        }
        // Si no hay argumentos, es valido (depende de la logica, aqui parece aceptar
        // comando sin args)
        if (arrayComando.length - 1 == 0) {
            return true;
        }
        // Reconstruye los argumentos en un string unico
        String parametro = String.join(" ", Arrays.copyOfRange(arrayComando, 1, arrayComando.length));

        // Compila y verifica la expresion regular contra los argumentos
        Pattern pattern = Pattern.compile(expresionRegular);
        Matcher matcher = pattern.matcher(parametro);
        if (!matcher.find()) {
            System.out.println("No cumple el formato esperado");
            return false;
        }
        return true;
    }

    /**
     * Verifica que el comando ejecutado sea del tipo esperado.
     */
    public boolean validarComando() {
        if (!getComando().toUpperCase().equals(getTipoString())) {
            System.out.println("El comando no es valido");
            return false;
        }
        return true;
    }

    public FileJobRepository getFileRepository() {
        return fileRepository;
    }

    public void setTipo(Job tipo) {
        this.tipo = tipo;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public Job getTipo() {
        if (this.tipo == null) {
            return null;
        }
        return this.tipo;
    }

    public String getTipoString() {
        if (this.tipo == null) {
            return null;
        }
        return this.tipo.toString();
    }

    public String getComando() {
        return this.comando;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }
}
