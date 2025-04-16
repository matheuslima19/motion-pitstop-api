package org.motion.motion_api.application.services;

import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.oficina.SetFotoOficinaDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.FotoGaleriaOficina;
import org.motion.motion_api.domain.repositories.pitstop.FotoGaleriaOficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FotoGaleriaOficinaService {
    @Autowired
    private ServiceHelper serviceHelper;
    @Autowired
    private FotoGaleriaOficinaRepository fotoRepository;


    public List<FotoGaleriaOficina> buscarFotosOficina(int idOficina) {
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        return fotoRepository.findAllByOficinaIs(oficina);
    }

    public FotoGaleriaOficina salvarFotoOficina(int idOficina, SetFotoOficinaDTO dto) {
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        FotoGaleriaOficina foto = new FotoGaleriaOficina();
        foto.setUrl(dto.getUrl());
        foto.setOficina(oficina);
        return fotoRepository.save(foto);
    }

    /**@throws RecursoNaoEncontradoException caso a foto não seja encontrada.
     * @param id id da foto a ser buscada.
     * @return a foto encontrada.
     * */
    public FotoGaleriaOficina buscarFotoPorId(int id) {
        return fotoRepository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException("Foto não encontrada com o id: " + id));
    }

    public Void deletarFoto(int id) {
        FotoGaleriaOficina foto = buscarFotoPorId(id);
        fotoRepository.delete(foto);
        return null;
    }




}
