package br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto;

import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoResponseDTO;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PaginacaoResponseDTOTest {

    private static final List<String> DEFAULT_CONTENT = List.of("item1", "item2");
    private static final long TOTAL_ELEMENTS = 2L;
    private static final int TOTAL_PAGES = 1;
    private static final int PAGE = 0;
    private static final int SIZE = 10;
    private static final boolean FIRST = true;
    private static final boolean LAST = true;

    @Test
    void deveCriarObjetoCorretamente() {
        PaginacaoResponseDTO<String> dto = new PaginacaoResponseDTO<>(
                DEFAULT_CONTENT, TOTAL_ELEMENTS, TOTAL_PAGES, PAGE, SIZE, FIRST, LAST
        );

        assertEquals(DEFAULT_CONTENT, dto.content());
        assertEquals(TOTAL_ELEMENTS, dto.totalElements());
        assertEquals(TOTAL_PAGES, dto.totalPages());
        assertEquals(PAGE, dto.page());
        assertEquals(SIZE, dto.size());
        assertTrue(dto.first());
        assertTrue(dto.last());
    }

    @Test
    void deveGerarEqualsCorretamente() {
        PaginacaoResponseDTO<String> dto1 = new PaginacaoResponseDTO<>(
                DEFAULT_CONTENT, TOTAL_ELEMENTS, TOTAL_PAGES, PAGE, SIZE, FIRST, LAST
        );
        PaginacaoResponseDTO<String> dto2 = new PaginacaoResponseDTO<>(
                DEFAULT_CONTENT, TOTAL_ELEMENTS, TOTAL_PAGES, PAGE, SIZE, FIRST, LAST
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void devePermitirListaVazia() {
        PaginacaoResponseDTO<String> dto = new PaginacaoResponseDTO<>(
                List.of(), 0L, 0, 0, SIZE, FIRST, LAST
        );

        assertTrue(dto.content().isEmpty());
        assertEquals(0L, dto.totalElements());
        assertEquals(0, dto.totalPages());
    }
}
