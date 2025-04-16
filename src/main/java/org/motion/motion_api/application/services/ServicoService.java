package org.motion.motion_api.application.services;

import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.pitstop.servico.CreateServicoDTO;
import org.motion.motion_api.domain.dtos.pitstop.servico.ServicoResponseDTO;
import org.motion.motion_api.domain.dtos.pitstop.servico.UpdateServicoDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.Servico;
import org.motion.motion_api.domain.repositories.IOficinaRepository;
import org.motion.motion_api.domain.repositories.pitstop.IOrdemDeServicoRepository;
import org.motion.motion_api.domain.repositories.pitstop.IServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    IServicoRepository servicoRepository;
    @Autowired
    IOficinaRepository oficinaRepository;
    @Autowired
    IOrdemDeServicoRepository ordemDeServicoRepository;
    @Autowired
    ServiceHelper serviceHelper;

    public ServicoResponseDTO buscarPorId(Integer id){
        Servico servico = servicoRepository.findById(id).orElseThrow(()-> new RuntimeException("Serviço não encontrado com o id: " + id));
        return new ServicoResponseDTO(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getValorServico(),
                servico.getGarantia()
        );
    }
    public List<ServicoResponseDTO> listarServicos(){
        List<Servico> servicos = servicoRepository.findAll();
        return servicos.stream().map(servico -> new ServicoResponseDTO(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getValorServico(),
                servico.getGarantia()
        )).collect(java.util.stream.Collectors.toList());
    }
    public Servico cadastrar(CreateServicoDTO servico){
        Servico servicoCadastrado = new Servico(servico, oficinaRepository.findById(servico.fkOficina()).orElseThrow(()-> new RecursoNaoEncontradoException("Oficina não encontrada com o id: " + servico.fkOficina())));
        servicoRepository.save(servicoCadastrado);
        return servicoCadastrado;
    }

    public void deletar(Integer id){
        Servico servico = servicoRepository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException("Serviço não encontrado com o id: " + id));
        servicoRepository.delete(servico);
    }

    public Servico atualizar(Integer id, UpdateServicoDTO servico){
        Servico servicoAtualizado = servicoRepository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException("Serviço não encontrado com o id: " + id));
        servicoAtualizado.setNome(servico.getNome());
        servicoAtualizado.setDescricao(servico.getDescricao());
        servicoAtualizado.setValorServico(servico.getValorServico());
        servicoAtualizado.setGarantia(servico.getGarantia());
        servicoRepository.save(servicoAtualizado);
        return servicoAtualizado;
    }


    public List<ServicoResponseDTO> buscarPorOficina(Integer idOficina){
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        List<Servico> servicos = servicoRepository.findByOficina(oficina);
        return servicos.stream().map(servico -> new ServicoResponseDTO(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getValorServico(),
                servico.getGarantia()
        )).collect(java.util.stream.Collectors.toList());
    }
}
