package org.motion.motion_api.application.services;

import org.motion.motion_api.domain.dtos.pitstop.tarefa.CreateTarefaDTO;
import org.motion.motion_api.domain.dtos.pitstop.tarefa.UpdateTarefaDTO;
import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.Tarefa;
import org.motion.motion_api.domain.repositories.pitstop.ITarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private ITarefaRepository tarefaRepository;
    @Autowired
    ServiceHelper serviceHelper;


    public List<Tarefa> listarTodasTarefasPorIdOficina(int id) {
        serviceHelper.pegarOficinaValida(id);
        return tarefaRepository.findByOficinaId(id);
    }


    public Tarefa buscarPorId(int id) {
        return tarefaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa não encontrada com o id: " + id));
    }


    public Tarefa criar(CreateTarefaDTO createTarefaDTO) {
        Oficina oficina = serviceHelper.pegarOficinaValida(createTarefaDTO.fkOficina());
        Tarefa tarefa = new Tarefa(createTarefaDTO, oficina);
        tarefaRepository.save(tarefa);
        return tarefa;
    }


    public Tarefa atualizar(int id, UpdateTarefaDTO dto) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.setStatus(dto.status());
        return tarefaRepository.save(tarefa);
    }


    public void deletar(int id) {
        Tarefa tarefa = buscarPorId(id);
        tarefaRepository.delete(tarefa);
    }

    public List<Tarefa> listarTarefasDeadlineHoje(Integer idOficina) {
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        return tarefaRepository.findByDtDeadlineAndOficina(LocalDate.now(),oficina);
    }
}
