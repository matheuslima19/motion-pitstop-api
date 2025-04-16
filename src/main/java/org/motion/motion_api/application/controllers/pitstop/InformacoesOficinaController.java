package org.motion.motion_api.application.controllers.pitstop;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.oficina.SetInfoOficinaDTO;
import org.motion.motion_api.domain.dtos.oficina.UpdateWhatsappDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.InformacoesOficina;
import org.motion.motion_api.domain.repositories.pitstop.InformacoesOficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/infos-oficina")
@SecurityRequirement(name = "motion_jwt")
public class InformacoesOficinaController {
    @Autowired
    private InformacoesOficinaRepository repository;
    @Autowired
    private ServiceHelper helper;
        /*TODO
        * TALVEZ TENHA ALGO PARA ADICIONAR OU MELHORAR AQUI (NAO USANDO SERVICE)
        * */

    @PutMapping("/{idOficina}")
    @Operation(summary = "Recebe id da oficina, novas informações e atualiza as informações da oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\n" +
                                            "  \"whatsapp\": \"(11) 99999-9999\",\n" +
                                            "  \"horarioIniSem\": \"08:00\",\n" +
                                            "  \"horarioFimSem\": \"18:00\",\n" +
                                            "  \"horarioIniFds\": \"08:00\",\n" +
                                            "  \"horarioFimFds\": \"12:30\",\n" +
                                            "  \"diasSemanaAberto\": \"false;true;true;true;true;true;true\",\n" +
                                            "  \"tipoVeiculosTrabalha\": \"carro;moto\",\n" +
                                            "  \"tipoPropulsaoTrabalha\": \"combustao;eletrico;hibrido\",\n" +
                                            "  \"marcasVeiculosTrabalha\": \"chevrolet;ford;fiat\"\n" +
                                            "}"
                            )))
    })
    public ResponseEntity<InformacoesOficina> atualizarInformacoesOficina(@PathVariable int idOficina, @RequestBody @Valid SetInfoOficinaDTO dto) {
        Oficina oficina = helper.pegarOficinaValida(idOficina);
        InformacoesOficina info = repository.findById(oficina.getInformacoesOficina().getId())
                .orElseThrow(()-> new RecursoNaoEncontradoException("Informações da oficina não encontradas"));
        info.setWhatsapp(dto.getWhatsapp());
        info.setHorarioIniSem(dto.getHorarioIniSem());
        info.setHorarioFimSem(dto.getHorarioFimSem());
        info.setHorarioIniFds(dto.getHorarioIniFds());
        info.setHorarioFimFds(dto.getHorarioFimFds());
        info.setDiasSemanaAberto(dto.getDiasSemanaAberto());
        info.setTipoVeiculosTrabalha(dto.getTipoVeiculosTrabalha());
        info.setTipoPropulsaoTrabalha(dto.getTipoPropulsaoTrabalha());
        info.setMarcasVeiculosTrabalha(dto.getMarcasVeiculosTrabalha());
        repository.save(info);
        return ResponseEntity.ok(info);
    }


    @PutMapping("/atualiza-zap/{idOficina}")
    @Operation(summary = "Recebe id da oficina, e número whatsapp formatado e atualiza o número whatsapp da oficina")
    public ResponseEntity<InformacoesOficina> atualizarWhatsappOficina(@PathVariable int idOficina, @Valid @RequestBody UpdateWhatsappDTO dto) {
        Oficina oficina = helper.pegarOficinaValida(idOficina);
        oficina.getInformacoesOficina().setWhatsapp(dto.getWhatsapp());
        return ResponseEntity.status(200).body(repository.save(oficina.getInformacoesOficina()));
    }
}
