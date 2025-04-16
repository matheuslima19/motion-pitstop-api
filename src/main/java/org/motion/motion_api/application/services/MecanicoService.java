package org.motion.motion_api.application.services;

import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.domain.dtos.pitstop.mecanico.CreateMecanicoDTO;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.pitstop.mecanico.UpdateMecanicoDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.Mecanico;
import org.motion.motion_api.domain.repositories.IOficinaRepository;
import org.motion.motion_api.domain.repositories.pitstop.IMecanicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MecanicoService {

    @Autowired
    IMecanicoRepository mecanicoRepository;
    @Autowired
    IOficinaRepository oficinaRepository;
    @Autowired
    ServiceHelper serviceHelper;

    public List<Mecanico> listarMecanicos(){
        return mecanicoRepository.findAll();
    }
    public Mecanico cadastrar(CreateMecanicoDTO createMecanicoDTO){
        Oficina oficina = serviceHelper.pegarOficinaValida(createMecanicoDTO.fkOficina());
        Mecanico mecanico = new Mecanico(createMecanicoDTO, oficina);
        mecanicoRepository.save(mecanico);
        return mecanico;
    }

    public List<Mecanico> listarMecanicosPorOficina(Integer idOficina){
        Oficina oficina = oficinaRepository.findById(idOficina).orElseThrow(()-> new RecursoNaoEncontradoException("Oficina não encontrada com o id: " + idOficina));
        return mecanicoRepository.findAllByOficina(oficina);
    }

    public Mecanico buscarPorId(Integer id){
        return mecanicoRepository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException("Mecânico não encontrado com o id: " + id));
    }

    public void deletar(Integer id){
        Mecanico mecanico = buscarPorId(id);
        mecanicoRepository.delete(mecanico);
    }

    public Mecanico atualizar(Integer id, UpdateMecanicoDTO mecanico){
        Mecanico mecanicoAtualizado = buscarPorId(id);
        mecanicoAtualizado.setNome(mecanico.getNome());
        mecanicoAtualizado.setTelefone(mecanico.getTelefone());
        mecanicoRepository.save(mecanicoAtualizado);
        return mecanicoAtualizado;
    }

    public Mecanico buscarPorNome(String nome){
        return mecanicoRepository.findByNome(nome).stream().findFirst().orElseThrow(()-> new RecursoNaoEncontradoException("Mecânico não encontrado com o nome: " + nome));
    }

    public Mecanico buscarPorNomeEId(String nome, Integer id){
        return mecanicoRepository.findByNome(nome).stream().filter(mecanico -> mecanico.getId().equals(id)).findFirst().orElseThrow(()-> new RecursoNaoEncontradoException("Mecânico não encontrado com o nome: " + nome + " e id: " + id));
    }
}
