package org.motion.motion_api.application.services;

import jakarta.transaction.Transactional;
import org.motion.motion_api.domain.dtos.pitstop.ordemDeServico.*;
import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.ProdutoOrdemDTO;
import org.motion.motion_api.domain.dtos.pitstop.servico.ServicoOrdemDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.*;
import org.motion.motion_api.domain.repositories.pitstop.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrdemDeServicoService {

    @Autowired
    IOrdemDeServicoRepository ordemDeServicoRepository;
    @Autowired
    IClienteRepository clienteRepository;
    @Autowired
    IVeiculoRepository veiculoRepository;
    @Autowired
    IMecanicoRepository mecanicoRepository;
    @Autowired
    IProdutoEstoqueRepository produtoEstoqueRepository;
    @Autowired
    IServicoRepository servicoRepository;
    @Autowired
    ServiceHelper serviceHelper;


    public List<OrdemCriadaDTO> listarOrdensDeServico() {
        List<OrdemDeServico> ordens = ordemDeServicoRepository.findAll();

        return ordens.stream()
                .map(this::converterParaOrdemCriadaDTO)
                .toList();
    }



    public List<OrdemCriadaDTO> listarOrdensDeServicoPorOficina(Integer idOficina) {
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);

        List<OrdemDeServico> ordens = ordemDeServicoRepository.findAllByOficina(oficina);

        return ordens.stream()
                .map(this::converterParaOrdemCriadaDTO)
                .toList();
    }



    public List<OrdemCriadaDTO> listarOrdensDeServicoPorCliente(String email) {
        List<OrdemDeServico> ordens = ordemDeServicoRepository.findAllByVeiculo_Cliente_Email(email);

        return ordens.stream()
                .map(this::converterParaOrdemCriadaDTO)
                .toList();
    }


    public OrdemCriadaDTO cadastrar(CreateOrdemDeServicoDTO createOrdemDeServicoDTO) {
        OrdemDeServico ordemDeServico = new OrdemDeServico();
        ordemDeServico.setStatus(createOrdemDeServicoDTO.status());
        ordemDeServico.setGarantia(createOrdemDeServicoDTO.garantia());
        ordemDeServico.setToken(UUID.randomUUID().toString().substring(29, 36).toUpperCase());
        ordemDeServico.setDataInicio(createOrdemDeServicoDTO.dataInicio());
        ordemDeServico.setDataFim(createOrdemDeServicoDTO.dataFim());
        ordemDeServico.setTipoOs(createOrdemDeServicoDTO.tipoOs());
        ordemDeServico.setObservacoes(createOrdemDeServicoDTO.observacoes());
        ordemDeServico.setValorTotal(createOrdemDeServicoDTO.valorTotal());

        ordemDeServico.setCliente(clienteRepository.findById(createOrdemDeServicoDTO.fkCliente())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com o id: " + createOrdemDeServicoDTO.fkCliente())));
        Oficina oficina = serviceHelper.pegarOficinaValida(createOrdemDeServicoDTO.fkOficina());
        ordemDeServico.setOficina(oficina);
        Veiculo veiculo = veiculoRepository.findById(createOrdemDeServicoDTO.fkVeiculo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado com o id: " + createOrdemDeServicoDTO.fkVeiculo()));
        ordemDeServico.setVeiculo(veiculo);

        if (createOrdemDeServicoDTO.fkMecanico() != null) {
            Mecanico mecanico = mecanicoRepository.findById(createOrdemDeServicoDTO.fkMecanico()).orElse(null);
            ordemDeServico.setMecanico(mecanico);
        }

        ordemDeServico = ordemDeServicoRepository.save(ordemDeServico);

        for (ProdutoOrdemDTO produtoOrdemDTO : createOrdemDeServicoDTO.produtos()) {
            Optional<ProdutoEstoque> opProduto = produtoEstoqueRepository.findByNomeAndOficina(produtoOrdemDTO.nome(), oficina);

            if (opProduto.isEmpty()) {
                throw new RecursoNaoEncontradoException("Produto não encontrado: " + produtoOrdemDTO.nome());
            }
            ProdutoEstoque produto = opProduto.get();

            if (produto.getQuantidade() < produtoOrdemDTO.quantidade()) {
                throw new RuntimeException("Quantidade de produto em estoque insuficiente para a ordem de serviço");
            }


            ProdutoOrdemServico produtoOrdemServico = new ProdutoOrdemServico();
            produtoOrdemServico.setProdutoEstoque(produto);
            produtoOrdemServico.setOrdemDeServico(ordemDeServico);
            produtoOrdemServico.setQuantidade(produtoOrdemDTO.quantidade());
            produtoOrdemServico.setValor(produto.getValorVenda());
            ordemDeServico.getProdutos().add(produtoOrdemServico);

            produto.setQuantidade(produto.getQuantidade() - produtoOrdemDTO.quantidade());
            produtoEstoqueRepository.save(produto);
        }

        for (ServicoOrdemDTO servicoOrdemDTO : createOrdemDeServicoDTO.servicos()) {
            Optional<Servico> opServico = servicoRepository.findByNomeAndOficina(servicoOrdemDTO.nome(), oficina);
            if (opServico.isEmpty()) {
                throw new RecursoNaoEncontradoException("Serviço não encontrado: " + servicoOrdemDTO.nome());
            }
            Servico servico = opServico.get();
            ServicoOrdemServico servicoOrdemServico = new ServicoOrdemServico();
            servicoOrdemServico.setServico(servico);
            servicoOrdemServico.setOrdemDeServico(ordemDeServico);
            servicoOrdemServico.setNome(servico.getNome());
            servicoOrdemServico.setGarantia(servico.getGarantia());
            servicoOrdemServico.setValor(servico.getValorServico());
            ordemDeServico.getServicos().add(servicoOrdemServico);
            servicoRepository.save(servico);
        }

        ordemDeServico = ordemDeServicoRepository.save(ordemDeServico);

        return converterParaOrdemCriadaDTO(ordemDeServico);
    }



    public OrdemCriadaDTO buscarPorId(Integer id) {
        OrdemDeServico ordem = ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de serviço não encontrada com o id: " + id));

        return converterParaOrdemCriadaDTO(ordem);
    }

    public OrdemCriadaDTO atualizarStatus(Integer id, String status) {
        OrdemDeServico ordemDeServico = ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de serviço não encontrada com o id: " + id));

        ordemDeServico.setStatus(status);
        ordemDeServico = ordemDeServicoRepository.save(ordemDeServico);

        return converterParaOrdemCriadaDTO(ordemDeServico);
    }


    public void deletar(Integer id) {
        OrdemDeServico ordemDeServico = ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de serviço não encontrada com o id: " + id));

        ordemDeServico.getProdutos().clear();
        ordemDeServico.getServicos().clear();
        ordemDeServicoRepository.save(ordemDeServico);
        ordemDeServicoRepository.delete(ordemDeServico);
    }

    public OrdemCriadaDTO buscarPorToken(String token) {
        // Busca a ordem de serviço pelo token ou lança uma exceção caso não seja encontrada
        OrdemDeServico ordem = ordemDeServicoRepository.findByToken(token);
        if (ordem == null) {
            throw new RecursoNaoEncontradoException("Ordem de serviço não encontrada com o token: " + token);
        }

        return converterParaOrdemCriadaDTO(ordem);
    }

    public List<OrdensPendentesUltimaSemanaDTO> quantidadeOrdensPendentes(Integer idOficina) {
        List<OrdensPendentesUltimaSemanaDTO> ordensPendentesUltimaSemana = new ArrayList<>();
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        for (int i = 0; i < 7; i++) {
            LocalDate data = LocalDate.now().minusDays(i);
            Integer qtd = ordemDeServicoRepository.countByDataInicioAndStatusEqualsIgnoreCaseAndOficina(data, "PENDENTE",oficina);
            String diaSemana = data.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
            ordensPendentesUltimaSemana.add(new OrdensPendentesUltimaSemanaDTO(qtd, diaSemana));
        }
        return ordensPendentesUltimaSemana;
    }



    public List<OrdensUltimoAnoDTO> quantidadeOrdensMes(Integer idOficina) {
        List<OrdensUltimoAnoDTO> ordensUltimoAno = new ArrayList<>();
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        for (int i = 0; i < 12; i++) {
            LocalDate dataInicio = LocalDate.now().minusMonths(i).withDayOfMonth(1);
            LocalDate dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

            Integer qtd = ordemDeServicoRepository.countByDataInicioBetweenAndOficina(dataInicio, dataFim, oficina);

            String mes = dataInicio.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
            ordensUltimoAno.add(new OrdensUltimoAnoDTO(qtd, mes));
        }
        return ordensUltimoAno;
    }




    public OrdemCriadaDTO converterParaOrdemCriadaDTO(OrdemDeServico ordem) {
        List<ProdutoOrdemDTO> produtosDto = ordem.getProdutos().stream()
                .map(produtoOrdemServico -> new ProdutoOrdemDTO(
                        produtoOrdemServico.getProdutoEstoque().getNome(),
                        produtoOrdemServico.getValor(),
                        produtoOrdemServico.getQuantidade()
                ))
                .collect(Collectors.toList());

        List<ServicoOrdemDTO> servicosDto = ordem.getServicos().stream()
                .map(servico -> new ServicoOrdemDTO(
                        servico.getNome(),
                        servico.getGarantia(),
                        servico.getValor()
                ))
                .collect(Collectors.toList());

        return new OrdemCriadaDTO(
                ordem.getId(),
                ordem.getOficina(),
                ordem.getStatus(),
                ordem.getDataInicio(),
                ordem.getDataFim(),
                ordem.getToken(),
                ordem.getTipoOs(),
                ordem.getGarantia(),
                ordem.getCliente().getNome(),
                ordem.getCliente().getTelefone(),
                ordem.getCliente().getEmail(),
                ordem.getVeiculo().getPlaca(),
                ordem.getVeiculo().getMarca(),
                ordem.getVeiculo().getModelo(),
                ordem.getVeiculo().getAnoFabricacao(),
                ordem.getVeiculo().getCor(),
                ordem.getMecanico() != null ? ordem.getMecanico().getNome() : null,
                ordem.getMecanico() != null ? ordem.getMecanico().getTelefone() : null,
                ordem.getValorTotal(),
                ordem.getObservacoes(),
                produtosDto,
                servicosDto
        );
    }


}