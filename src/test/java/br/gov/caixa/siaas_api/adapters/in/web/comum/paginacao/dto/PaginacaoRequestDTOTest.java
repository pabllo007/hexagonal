package br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto;

import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoRequestDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaginacaoRequestDTOTest {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final String DEFAULT_SORT_BY = "nome";
    private static final String SORT_ASC = "asc";
    private static final String SORT_DESC = "desc";

    @Test
    void deveCriarObjetoCorretamente() {
        PaginacaoRequestDTO dto = new PaginacaoRequestDTO(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, SORT_ASC);

        assertEquals(DEFAULT_PAGE, dto.page());
        assertEquals(DEFAULT_SIZE, dto.size());
        assertEquals(DEFAULT_SORT_BY, dto.sortBy());
        assertEquals(SORT_ASC, dto.direction());
    }

    @Test
    void deveGerarEqualsCorretamente() {
        PaginacaoRequestDTO dto1 = new PaginacaoRequestDTO(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, SORT_ASC);
        PaginacaoRequestDTO dto2 = new PaginacaoRequestDTO(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, SORT_ASC);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void devePermitirValoresNulos() {
        PaginacaoRequestDTO dto = new PaginacaoRequestDTO(null, null, null, null);

        assertNull(dto.page());
        assertNull(dto.size());
        assertNull(dto.sortBy());
        assertNull(dto.direction());
    }
}