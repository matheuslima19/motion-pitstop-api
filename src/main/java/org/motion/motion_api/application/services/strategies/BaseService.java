package org.motion.motion_api.application.services.strategies;

import jakarta.mail.MessagingException;

import java.util.List;

public interface BaseService <E,DTOUpdate,DTOCreate> {
    List<E> listarTodos();
    E buscarPorId(int id);
    E criar(DTOCreate object) throws MessagingException;
    E atualizar(int id, DTOUpdate dto);
    void deletar(int id);
}
