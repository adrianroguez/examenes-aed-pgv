package com.docencia.com.examen.procesos.services.impl;

import org.springframework.stereotype.Component;

import com.docencia.com.examen.procesos.domain.Job;
import com.docencia.com.examen.procesos.services.impl.abstracts.CommandServiceAbstract;

/**
 * Implementacion concreta del servicio para el comando 'df' (disk free).
 * Extiende de CommandServiceAbstract herendando la logica de validacion y
 * ejecucion.
 * Anotado con @Component para que Spring lo detecte y gestione como un bean.
 */
@Component
public class DfServiceImpl extends CommandServiceAbstract {

    /**
     * Constructor que configura el tipo de comando y la expresion regular para
     * validarlo.
     */
    public DfServiceImpl() {
        // Establece el tipo de trabajo como DF
        this.setTipo(Job.DF);
        // Define la expresion regular para validar los argumentos permitidos
        // Permite comandos vacios o banderas como -h, -H
        this.setExpresionRegular("^((-(h|H))|\\s*)$");
    }
}
