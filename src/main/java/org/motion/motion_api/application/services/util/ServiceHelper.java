package org.motion.motion_api.application.services.util;

import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.Gerente;
import org.motion.motion_api.domain.repositories.IOficinaRepository;
import org.motion.motion_api.domain.repositories.pitstop.IGerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceHelper {

    @Autowired
    private  IOficinaRepository oficinaRepository;

    @Autowired
    private  IGerenteRepository gerenteRepository;


    /**
     * @param id
     * @return Retorna uma oficina caso encontre ou uma exceção caso não.
     * Pode ser usada para pegar uma oficina válida ou verificar se o id é válido.
     * @throws RecursoNaoEncontradoException
     */
    public  Oficina pegarOficinaValida(int id) {
        return oficinaRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Oficina não encontrada com o id: " + id));
    }

    /**
     * @param id
     * @return Retorna um gerente caso encontre ou uma exceção caso não.
     * @throws RecursoNaoEncontradoException
     */
    public Gerente pegarGerenteValido(int id) {
        return gerenteRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Gerente não encontrado com o id: " + id));
    }

}
