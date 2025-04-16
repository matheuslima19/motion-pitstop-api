package org.motion.motion_api.application.services;

import jakarta.transaction.Transactional;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.oficina.UpdateLogoOficinaDTO;
import org.motion.motion_api.domain.dtos.oficina.UpdateOficinaDTO;
import org.motion.motion_api.application.exceptions.DadoUnicoDuplicadoException;
import org.motion.motion_api.application.services.strategies.OficinaServiceStrategy;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.InformacoesOficina;
import org.motion.motion_api.domain.repositories.IOficinaRepository;
import org.motion.motion_api.domain.repositories.pitstop.IInformacoesOficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OficinaService implements OficinaServiceStrategy{

    @Autowired
    private IOficinaRepository oficinaRepository;

    @Autowired
    private IInformacoesOficinaRepository informacoesOficinaRepository;
    @Autowired
    private ServiceHelper serviceHelper;

    public List<Oficina> listarTodos() {
        return oficinaRepository.findAll();
    }

    public Oficina buscarPorId(int id) {
        return serviceHelper.pegarOficinaValida(id);
    }

    public List<Oficina> buscarPorTipoVeiculo(String tipo){
        return oficinaRepository.findByInformacoesOficina_TipoVeiculosTrabalhaContainingIgnoreCase(tipo);
    }

    public List<Oficina> buscarPorTipoVeiculoPropulsaoMarcaNome(String tipoVeiculo, String tipoPropulsao, String marca, String nome){
        return oficinaRepository.findByInformacoesOficina_TipoVeiculosTrabalhaContainingIgnoreCaseAndInformacoesOficina_TipoPropulsaoTrabalhaContainingIgnoreCaseAndInformacoesOficina_MarcasVeiculosTrabalhaContainingIgnoreCaseAndNomeContainingIgnoreCase(tipoVeiculo, tipoPropulsao, marca,nome);
    }


    public Oficina criar(Oficina oficina) {
        checarConflitoCnpj(oficina);

        InformacoesOficina informacoesOficina = setDefaultInfo();

        informacoesOficinaRepository.save(informacoesOficina);
        oficina.setLogoUrl("https://jeyoqssrkcibrvhoobsk.supabase.co/storage/v1/object/public/ofc-photos/base_oficina_image.png");
        oficina.setInformacoesOficina(informacoesOficina);
        return oficinaRepository.save(oficina);
    }

    @Transactional
    public Oficina atualizar(int id, UpdateOficinaDTO oficinaAtualizada) {
        Oficina oficina = serviceHelper.pegarOficinaValida(id);

        oficina.setNome(oficinaAtualizada.nome());
        oficina.setCep(oficinaAtualizada.cep());
        oficina.setNumero(oficinaAtualizada.numero());
        oficina.setComplemento(oficinaAtualizada.complemento());
        oficina.setHasBuscar(oficinaAtualizada.hasBuscar());

        return oficinaRepository.save(oficina);
    }

    @Transactional
    public Oficina atualizarLogoUrl(int id, UpdateLogoOficinaDTO dto) {
        Oficina oficina = serviceHelper.pegarOficinaValida(id);
        oficina.setLogoUrl(dto.getUrl());
        return oficinaRepository.save(oficina);
    }

    public void deletar(int id) {
        serviceHelper.pegarOficinaValida(id);
        oficinaRepository.deleteById(id);
        //throw new NotImplementedException("");
    }



    //MÉTODOS AUXILIARES

    /**
     * @param oficina Oficina a ser checada.
     * @return CnpjDuplicadoException caso o cnpj já esteja cadastrado.
     */
    private void checarConflitoCnpj(Oficina oficina){
        //TODO tirar listagem e implementar um select específico para o cnpj
        if(listarTodos().stream().anyMatch(o->o.getCnpj().equals(oficina.getCnpj())))
            throw new DadoUnicoDuplicadoException("CNPJ já cadastrado");
    }

    private InformacoesOficina setDefaultInfo(){
        return new InformacoesOficina(
                "",
                "08:00",
                "18:00",
                "10:00",
                "18:00",
                "false;true;true;true;true;true;true",
                "carro;moto",
                "combustão",
                ""
        );
    }
}
