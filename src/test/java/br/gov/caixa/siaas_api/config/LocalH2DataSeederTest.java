package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.GrupoTrabalhoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.TipoUnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.UnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.GrupoTrabalhoRepository;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.TipoUnidadeGestoraRepository;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.UnidadeGestoraRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LocalH2DataSeederTest {

    private static final long COUNT_EXISTENTE = 1L;
    private static final long COUNT_VAZIO = 0L;

    private static final int QTD_BASE_AUT_FIXAS = 6;
    private static final int BASE_AUT_LOOP_INICIO = 5;
    private static final int BASE_AUT_LOOP_FIM = 35;
    private static final int QTD_BASE_AUT_LOOP = (BASE_AUT_LOOP_FIM - BASE_AUT_LOOP_INICIO) + 1;
    private static final int QTD_BASE_AUT_TOTAL = QTD_BASE_AUT_FIXAS + QTD_BASE_AUT_LOOP;

    private static final long ID_TIPO_NEGOCIO = 2L;
    private static final long ID_TIPO_PRODUCAO = 5L;
    private static final long ID_GT_BSB = 1L;
    private static final long ID_GT_RJ = 2L;

    private static final int UG_FIXAS = 3;
    private static final int UG_LOOP_INICIO = 11010;
    private static final int UG_LOOP_FIM = 11035;
    private static final int UG_LOOP_TOTAL = (UG_LOOP_FIM - UG_LOOP_INICIO) + 1;
    private static final int UG_TOTAL = UG_FIXAS + UG_LOOP_TOTAL;

    @Test
    void seedBaseAutenticacao_naoDeveInserir_quandoJaTemDados() throws Exception {
        BaseAutenticacaoRepository repo = mock(BaseAutenticacaoRepository.class);
        when(repo.count()).thenReturn(COUNT_EXISTENTE);

        LocalH2DataSeeder cfg = new LocalH2DataSeeder();
        ApplicationRunner runner = cfg.seedBaseAutenticacao(repo);

        runner.run(null);

        verify(repo, times(1)).count();
        verify(repo, never()).save(any());
        verifyNoMoreInteractions(repo);
    }

    @Test
    void seedBaseAutenticacao_deveInserir_quandoNaoTemDados() throws Exception {
        BaseAutenticacaoRepository repo = mock(BaseAutenticacaoRepository.class);
        when(repo.count()).thenReturn(COUNT_VAZIO);

        LocalH2DataSeeder cfg = new LocalH2DataSeeder();
        ApplicationRunner runner = cfg.seedBaseAutenticacao(repo);

        runner.run(null);

        verify(repo, times(1)).count();
        verify(repo, times(QTD_BASE_AUT_TOTAL)).save(any());
        verifyNoMoreInteractions(repo);
    }

    @Test
    void seedUnidadeGestora_naoDeveInserir_quandoJaTemDados() throws Exception {
        UnidadeGestoraRepository ugRepo = mock(UnidadeGestoraRepository.class);
        TipoUnidadeGestoraRepository tipoRepo = mock(TipoUnidadeGestoraRepository.class);
        GrupoTrabalhoRepository grupoRepo = mock(GrupoTrabalhoRepository.class);

        when(ugRepo.count()).thenReturn(COUNT_EXISTENTE);

        LocalH2DataSeeder cfg = new LocalH2DataSeeder();
        ApplicationRunner runner = cfg.seedUnidadeGestora(ugRepo, tipoRepo, grupoRepo);

        runner.run(null);

        verify(ugRepo, times(1)).count();
        verify(ugRepo, never()).save(any());
        verifyNoInteractions(tipoRepo, grupoRepo);
        verifyNoMoreInteractions(ugRepo);
    }

    @Test
    void seedUnidadeGestora_deveSemearTiposEGrupos_quandoRepositoriosVazios_eInserirUGs() throws Exception {
        UnidadeGestoraRepository ugRepo = mock(UnidadeGestoraRepository.class);
        TipoUnidadeGestoraRepository tipoRepo = mock(TipoUnidadeGestoraRepository.class);
        GrupoTrabalhoRepository grupoRepo = mock(GrupoTrabalhoRepository.class);

        when(ugRepo.count()).thenReturn(COUNT_VAZIO);

        when(tipoRepo.count()).thenReturn(COUNT_VAZIO);
        when(grupoRepo.count()).thenReturn(COUNT_VAZIO);

        TipoUnidadeGestoraEntity tipoNegocio = new TipoUnidadeGestoraEntity();
        tipoNegocio.setNuTipoUnidadeGestora((int) ID_TIPO_NEGOCIO);

        TipoUnidadeGestoraEntity tipoProducao = new TipoUnidadeGestoraEntity();
        tipoProducao.setNuTipoUnidadeGestora((int) ID_TIPO_PRODUCAO);

        GrupoTrabalhoEntity gtBsb = new GrupoTrabalhoEntity();
        gtBsb.setNuGrupoTrabalho((int) ID_GT_BSB);

        GrupoTrabalhoEntity gtRj = new GrupoTrabalhoEntity();
        gtRj.setNuGrupoTrabalho((int) ID_GT_RJ);

        when(tipoRepo.findById(ID_TIPO_NEGOCIO)).thenReturn(Optional.of(tipoNegocio));
        when(tipoRepo.findById(ID_TIPO_PRODUCAO)).thenReturn(Optional.of(tipoProducao));
        when(grupoRepo.findById(ID_GT_BSB)).thenReturn(Optional.of(gtBsb));
        when(grupoRepo.findById(ID_GT_RJ)).thenReturn(Optional.of(gtRj));

        LocalH2DataSeeder cfg = new LocalH2DataSeeder();
        ApplicationRunner runner = cfg.seedUnidadeGestora(ugRepo, tipoRepo, grupoRepo);

        runner.run(null);

        verify(ugRepo, times(1)).count();

        verify(tipoRepo, times(1)).count();
        verify(tipoRepo, times(5)).save(any(TipoUnidadeGestoraEntity.class));
        verify(tipoRepo, times(1)).findById(ID_TIPO_NEGOCIO);
        verify(tipoRepo, times(1)).findById(ID_TIPO_PRODUCAO);

        verify(grupoRepo, times(1)).count();
        verify(grupoRepo, times(4)).save(any(GrupoTrabalhoEntity.class));
        verify(grupoRepo, times(1)).findById(ID_GT_BSB);
        verify(grupoRepo, times(1)).findById(ID_GT_RJ);

        verify(ugRepo, times(UG_TOTAL)).save(any(UnidadeGestoraEntity.class));

        verifyNoMoreInteractions(ugRepo, tipoRepo, grupoRepo);
    }

    @Test
    void seedUnidadeGestora_deveSemearApenasUGs_quandoTiposEGruposJaExistem() throws Exception {
        UnidadeGestoraRepository ugRepo = mock(UnidadeGestoraRepository.class);
        TipoUnidadeGestoraRepository tipoRepo = mock(TipoUnidadeGestoraRepository.class);
        GrupoTrabalhoRepository grupoRepo = mock(GrupoTrabalhoRepository.class);

        when(ugRepo.count()).thenReturn(COUNT_VAZIO);

        when(tipoRepo.count()).thenReturn(COUNT_EXISTENTE);
        when(grupoRepo.count()).thenReturn(COUNT_EXISTENTE);

        TipoUnidadeGestoraEntity tipoNegocio = new TipoUnidadeGestoraEntity();
        tipoNegocio.setNuTipoUnidadeGestora((int) ID_TIPO_NEGOCIO);

        TipoUnidadeGestoraEntity tipoProducao = new TipoUnidadeGestoraEntity();
        tipoProducao.setNuTipoUnidadeGestora((int) ID_TIPO_PRODUCAO);

        GrupoTrabalhoEntity gtBsb = new GrupoTrabalhoEntity();
        gtBsb.setNuGrupoTrabalho((int) ID_GT_BSB);

        GrupoTrabalhoEntity gtRj = new GrupoTrabalhoEntity();
        gtRj.setNuGrupoTrabalho((int) ID_GT_RJ);

        when(tipoRepo.findById(ID_TIPO_NEGOCIO)).thenReturn(Optional.of(tipoNegocio));
        when(tipoRepo.findById(ID_TIPO_PRODUCAO)).thenReturn(Optional.of(tipoProducao));
        when(grupoRepo.findById(ID_GT_BSB)).thenReturn(Optional.of(gtBsb));
        when(grupoRepo.findById(ID_GT_RJ)).thenReturn(Optional.of(gtRj));

        LocalH2DataSeeder cfg = new LocalH2DataSeeder();
        ApplicationRunner runner = cfg.seedUnidadeGestora(ugRepo, tipoRepo, grupoRepo);

        runner.run(null);

        verify(ugRepo, times(1)).count();

        verify(tipoRepo, times(1)).count();
        verify(tipoRepo, never()).save(any(TipoUnidadeGestoraEntity.class));
        verify(tipoRepo, times(1)).findById(ID_TIPO_NEGOCIO);
        verify(tipoRepo, times(1)).findById(ID_TIPO_PRODUCAO);

        verify(grupoRepo, times(1)).count();
        verify(grupoRepo, never()).save(any(GrupoTrabalhoEntity.class));
        verify(grupoRepo, times(1)).findById(ID_GT_BSB);
        verify(grupoRepo, times(1)).findById(ID_GT_RJ);

        verify(ugRepo, times(UG_TOTAL)).save(any(UnidadeGestoraEntity.class));

        verifyNoMoreInteractions(ugRepo, tipoRepo, grupoRepo);
    }
}