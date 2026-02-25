package br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.mapper;

import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.mapper.PaginacaoMapper;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaginacaoMapperTest {

    @Test
    void paraRespostaPaginadaDeveMapearConteudoEMetadados() {
        Page<String> pagina =
                new PageImpl<>(List.of("a", "b"),
                        org.springframework.data.domain.PageRequest.of(1, 2),
                        10);

        PaginacaoResponseDTO<String> resposta =
                PaginacaoMapper.paraRespostaPaginada(pagina,
                        String::toUpperCase);

        assertEquals(List.of("A", "B"), resposta.content());
        assertEquals(10, resposta.totalElements());
        assertEquals(5, resposta.totalPages());
        assertEquals(1, resposta.page());
        assertEquals(2, resposta.size());
        assertFalse(resposta.first());
        assertFalse(resposta.last());
    }

    @Test
    void paraRespostaPaginadaDeveTratarListaVazia() {
        Page<String> pagina = new PageImpl<>(List.of());

        PaginacaoResponseDTO<String> resposta =
                PaginacaoMapper.paraRespostaPaginada(pagina, s -> s);

        assertTrue(resposta.content().isEmpty());
        assertEquals(0, resposta.totalElements());
    }
}
