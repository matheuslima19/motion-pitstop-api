package org.motion.motion_api.application.services;

import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.buscar.CreateServicoBuscarDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.buscar.ServicoBuscar;
import org.motion.motion_api.domain.repositories.buscar.IServicoBuscarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoBuscarService {

    @Autowired
    private IServicoBuscarRepository servicoBuscarRepository;

    @Autowired
    private ServiceHelper serviceHelper;
    public List<ServicoBuscar> listarServicos() {
        return servicoBuscarRepository.findAll();
    }

    public ServicoBuscar buscarServicoPorId(Integer id) {
        return servicoBuscarRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Serviço não encontrado com o id: " + id));
    }

    public List<ServicoBuscar> buscarPorTipoVeiculo(String tipoVeiculo, String nome) {
        List<ServicoBuscar> servicos = servicoBuscarRepository.findAllByOficina_InformacoesOficinaTipoVeiculosTrabalhaContainingIgnoreCaseAndNomeContainingIgnoreCase(tipoVeiculo,nome);
        return servicos;
    }

    public ServicoBuscar cadastrar(CreateServicoBuscarDTO novoServicoBuscar) {
        Oficina oficina = serviceHelper.pegarOficinaValida(novoServicoBuscar.fkOficina());
        ServicoBuscar servicoBuscar = new ServicoBuscar(novoServicoBuscar);
        servicoBuscar.setOficina(oficina);

        return servicoBuscarRepository.save(servicoBuscar);
    }

    public void deletarServico(Integer id) {
        servicoBuscarRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado com o id: " + id));
        servicoBuscarRepository.deleteById(id);
    }

    public List<ServicoBuscar> buscarPorOficina(Integer idOficina) {
        Oficina o = serviceHelper.pegarOficinaValida(idOficina);
        return servicoBuscarRepository.findByOficina(o);
    }

}
