package br.gov.caixa.siaas_api.utils;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.utils.PaginacaoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

class PaginacaoUtilsTest {

    @Test
    void criarPageable_deveUsarPadroes_quandoNulos() {
        Pageable p = PaginacaoUtils.criarPageable(null, null, null, null, "nuBaseAutenticacao");

        assertEquals(0, p.getPageNumber());
        assertEquals(20, p.getPageSize());

        Sort.Order order = p.getSort().getOrderFor("nuBaseAutenticacao");
        assertNotNull(order);
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }

    @Test
    void criarPageable_deveUsarDefaultSort_quandoSortByEmBranco() {
        Pageable p = PaginacaoUtils.criarPageable(0, 20, "   ", "DESC", "nuBaseAutenticacao");

        Sort.Order order = p.getSort().getOrderFor("nuBaseAutenticacao");
        assertNotNull(order);
        assertEquals(Sort.Direction.DESC, order.getDirection());
    }

    @Test
    void criarPageable_deveUsarSortBy_quandoInformado() {
        Pageable p = PaginacaoUtils.criarPageable(1, 10, "noBaseAutenticacao", "ASC", "nuBaseAutenticacao");

        assertEquals(1, p.getPageNumber());
        assertEquals(10, p.getPageSize());

        Sort.Order order = p.getSort().getOrderFor("noBaseAutenticacao");
        assertNotNull(order);
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }

    @Test
    void criarPageable_deveNormalizarPageESize_invalidos() {
        Pageable p = PaginacaoUtils.criarPageable(-1, 0, null, null, "nuBaseAutenticacao");
        assertEquals(0, p.getPageNumber());
        assertEquals(20, p.getPageSize());
    }

    @Test
    void criarPageable_sobrecargaSemDefaultSort_deveUsarId() {
        Pageable p = PaginacaoUtils.criarPageable(null, null, null, null);

        Sort.Order order = p.getSort().getOrderFor("id");
        assertNotNull(order);
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }

    @Test
    void criarPageable_obterOrdenacaoPadrao_deveCairNoId_quandoDefaultSortNuloOuEmBranco() {
        Pageable p1 = PaginacaoUtils.criarPageable(0, 20, null, null, null);
        assertNotNull(p1.getSort().getOrderFor("id"));

        Pageable p2 = PaginacaoUtils.criarPageable(0, 20, null, null, "  ");
        assertNotNull(p2.getSort().getOrderFor("id"));
    }

    @Test
    void criarPageable_deveAssumirAsc_quandoDirectionEmBranco() {
        Pageable p = PaginacaoUtils.criarPageable(
                0, 20,
                "nuBaseAutenticacao",
                "   ",
                "nuBaseAutenticacao"
        );

        Sort.Order order = p.getSort().getOrderFor("nuBaseAutenticacao");
        assertNotNull(order);
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }
}