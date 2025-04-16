package org.motion.motion_api.application.services;

import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.domain.dtos.pitstop.financeiro.CreateFinanceiroDTO;
import org.motion.motion_api.domain.dtos.pitstop.financeiro.ResponseDataFinanceiro;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.pitstop.financeiro.ResponseDataUltimoAnoFinanceiroDTO;
import org.motion.motion_api.domain.dtos.pitstop.financeiro.UpdateFinanceiroDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.Financeiro;
import org.motion.motion_api.domain.repositories.pitstop.IFinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FinanceiroService {
    @Autowired
    private IFinanceiroRepository financeiroRepository;
    @Autowired
    private ServiceHelper serviceHelper;

    public ResponseDataFinanceiro listarDadosFinanceirosDoMes(int idOficina) {
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        Integer oficinaId = oficina.getId();
        LocalDate primeiroDiaMesAtual = LocalDate.now().withDayOfMonth(1);
        LocalDate ultimoDiaMesAtual = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        List<Financeiro> financas = financeiroRepository.findAllByOficina_IdAndDataBetween(oficinaId, primeiroDiaMesAtual, ultimoDiaMesAtual);
        List<Financeiro> saidas = financas.stream().filter(f -> f.getTransacao().equalsIgnoreCase("saida")).toList();
        List<Financeiro> entradas = financas.stream().filter(f -> f.getTransacao().equalsIgnoreCase("entrada")).toList();
        Double valorTotalEntradas = entradas.stream().mapToDouble(Financeiro::getValor).sum();
        Double valorTotalSaidas = saidas.stream().mapToDouble(Financeiro::getValor).sum();
        Double saldo = valorTotalEntradas - valorTotalSaidas;

        return new ResponseDataFinanceiro(valorTotalEntradas, valorTotalSaidas, saldo);
    }

    public Financeiro registrarTransacaoFinanceira(CreateFinanceiroDTO dto){
        Oficina oficina = serviceHelper.pegarOficinaValida(dto.getIdOficina());
        Financeiro financeiro = new Financeiro(dto,oficina);
        financeiroRepository.save(financeiro);
        return financeiro;
    }

    public List<Financeiro> listarTodasOperacoesFinanceiras(int idOficina) {
        return financeiroRepository.findAllByOficina_Id(serviceHelper.pegarOficinaValida(idOficina).getId());
    }

    public Financeiro atualizarFinanceiro(int id, UpdateFinanceiroDTO dto){
        Financeiro financeiro = financeiroRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Operação financeira não encontrada com o id: "+id));
        financeiro.setTransacao(dto.getTransacao());
        financeiro.setCategoria(dto.getCategoria());
        financeiro.setFormaPagamento(dto.getFormaPagamento());
        financeiro.setValor(dto.getValor());
        financeiro.setData(dto.getData());
        financeiroRepository.save(financeiro);
        return financeiro;
    }

    public void deletarFinanceiro(int id){
        Financeiro financeiro = financeiroRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Operação financeira não encontrada com o id: "+id));
        financeiroRepository.deleteById(financeiro.getId());
    }

    public List<ResponseDataUltimoAnoFinanceiroDTO> listarDadosFinanceirosDosUltimos12Meses(int idOficina){
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        Integer oficinaId = oficina.getId();

        List<ResponseDataUltimoAnoFinanceiroDTO> response = new ArrayList<>();

        LocalDate primeiroDiaMes = LocalDate.now().withDayOfMonth(1);
        LocalDate ultimoDiaMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        for (int i = 0; i < 12; i++) {
            List<Financeiro> financas = financeiroRepository.findAllByOficina_IdAndDataBetween(oficinaId, primeiroDiaMes, ultimoDiaMes);
            List<Financeiro> saidas = financas.stream().filter(f -> f.getTransacao().equalsIgnoreCase("saida")).toList();
            List<Financeiro> entradas = financas.stream().filter(f -> f.getTransacao().equalsIgnoreCase("entrada")).toList();
            Double valorTotalEntradas = entradas.stream().mapToDouble(Financeiro::getValor).sum();
            Double valorTotalSaidas = saidas.stream().mapToDouble(Financeiro::getValor).sum();
            Double saldo = valorTotalEntradas - valorTotalSaidas;

            String nomeMes = primeiroDiaMes.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
            response.add(new ResponseDataUltimoAnoFinanceiroDTO(nomeMes, valorTotalEntradas, valorTotalSaidas, saldo));

            primeiroDiaMes = primeiroDiaMes.minusMonths(1).withDayOfMonth(1);
            ultimoDiaMes = ultimoDiaMes.minusMonths(1).withDayOfMonth(ultimoDiaMes.minusMonths(1).lengthOfMonth());
        }

        return response;
    }

}
