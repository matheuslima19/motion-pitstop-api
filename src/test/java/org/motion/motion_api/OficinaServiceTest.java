package org.motion.motion_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.motion.motion_api.application.exceptions.DadoUnicoDuplicadoException;
import org.motion.motion_api.application.services.OficinaService;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.oficina.UpdateLogoOficinaDTO;
import org.motion.motion_api.domain.dtos.oficina.UpdateOficinaDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.InformacoesOficina;
import org.motion.motion_api.domain.repositories.IOficinaRepository;
import org.motion.motion_api.domain.repositories.pitstop.IInformacoesOficinaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OficinaServiceTest {

    @Mock
    private IOficinaRepository oficinaRepository;

    @Mock
    private IInformacoesOficinaRepository informacoesOficinaRepository;

    @Mock
    private ServiceHelper serviceHelper;

    @InjectMocks
    private OficinaService oficinaService;

    private Oficina oficina;
    private InformacoesOficina informacoesOficina;

    @BeforeEach
    void setUp() {
        oficina = new Oficina();
        oficina.setId(1);
        oficina.setNome("Oficina Teste");
        oficina.setCnpj("123456789");

        informacoesOficina = new InformacoesOficina();
        oficina.setInformacoesOficina(informacoesOficina);
    }

    @Test
    void testListarTodos() {
        List<Oficina> oficinas = new ArrayList<>();
        oficinas.add(oficina);

        when(oficinaRepository.findAll()).thenReturn(oficinas);

        List<Oficina> result = oficinaService.listarTodos();

        assertEquals(1, result.size());
        assertEquals(oficina, result.get(0));
    }

    @Test
    void testBuscarPorId() {
        when(serviceHelper.pegarOficinaValida(oficina.getId())).thenReturn(oficina);

        Oficina result = oficinaService.buscarPorId(oficina.getId());

        assertEquals(oficina, result);
    }

    @Test
    void testCriar() {
        when(oficinaRepository.findAll()).thenReturn(new ArrayList<>());
        when(informacoesOficinaRepository.save(any(InformacoesOficina.class))).thenReturn(informacoesOficina);
        when(oficinaRepository.save(any(Oficina.class))).thenReturn(oficina);

        Oficina result = oficinaService.criar(oficina);

        assertNotNull(result);
        assertEquals("https://jeyoqssrkcibrvhoobsk.supabase.co/storage/v1/object/public/ofc-photos/base_oficina_image.png", result.getLogoUrl());
        verify(informacoesOficinaRepository, times(1)).save(any(InformacoesOficina.class));
        verify(oficinaRepository, times(1)).save(oficina);
    }

    @Test
    void testCriarCnpjDuplicado() {
        List<Oficina> oficinas = new ArrayList<>();
        oficinas.add(oficina);

        when(oficinaRepository.findAll()).thenReturn(oficinas);

        Oficina oficinaDuplicada = new Oficina();
        oficinaDuplicada.setCnpj("123456789");

        assertThrows(DadoUnicoDuplicadoException.class, () -> {
            oficinaService.criar(oficinaDuplicada);
        });
    }

    @Test
    void testAtualizar() {
        UpdateOficinaDTO updateOficinaDTO = new UpdateOficinaDTO("Nova Oficina", "12345", "123", "Apto 1", true);
        when(serviceHelper.pegarOficinaValida(oficina.getId())).thenReturn(oficina);
        when(oficinaRepository.save(any(Oficina.class))).thenReturn(oficina);

        Oficina result = oficinaService.atualizar(oficina.getId(), updateOficinaDTO);

        assertNotNull(result);
        assertEquals(updateOficinaDTO.nome(), result.getNome());
        assertEquals(updateOficinaDTO.cep(), result.getCep());
        assertEquals(updateOficinaDTO.numero(), result.getNumero());
        assertEquals(updateOficinaDTO.complemento(), result.getComplemento());
        assertEquals(updateOficinaDTO.hasBuscar(), result.isHasBuscar());
        verify(oficinaRepository, times(1)).save(oficina);
    }

    @Test
    void testAtualizarLogoUrl() {
        UpdateLogoOficinaDTO updateLogoOficinaDTO = new UpdateLogoOficinaDTO();
        updateLogoOficinaDTO.setUrl("urllogo");
        when(serviceHelper.pegarOficinaValida(oficina.getId())).thenReturn(oficina);
        when(oficinaRepository.save(any(Oficina.class))).thenReturn(oficina);

        Oficina result = oficinaService.atualizarLogoUrl(oficina.getId(), updateLogoOficinaDTO);

        assertNotNull(result);
        assertEquals(updateLogoOficinaDTO.getUrl(), result.getLogoUrl());
        verify(oficinaRepository, times(1)).save(oficina);
    }

    @Test
    void testDeletar() {
        when(serviceHelper.pegarOficinaValida(oficina.getId())).thenReturn(oficina);

        oficinaService.deletar(oficina.getId());

        verify(oficinaRepository, times(1)).deleteById(oficina.getId());
    }
}
