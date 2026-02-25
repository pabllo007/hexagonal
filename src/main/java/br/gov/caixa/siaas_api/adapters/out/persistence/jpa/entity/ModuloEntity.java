package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.converter.SimNaoAttributeConverter;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "AASSM001", name = "AASTB005_MODULO")
@SequenceGenerator(
        name = "AASSQ005_MODULO",
        sequenceName = "AASSM001.AASSQ005_MODULO",
        allocationSize = 1
)
public class ModuloEntity {
    private static final long serialVersionUID = 7121937587986320910L;

    @Id
    @Column(name = "NU_MODULO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AASSQ005_MODULO")
    private Long nuModulo;

    @Column(name = "SG_MODULO", length = 10, nullable = false)
    private String sgModulo;

    @Column(name = "NO_MODULO", length = 100, nullable = false)
    private String noModulo;

    @Convert(converter = SimNaoAttributeConverter.class)
    @Column(name = "IC_PERFIL_EVENTUAL", length = 1, nullable = false)
    private SimNao icPerfilEventual;

    @Convert(converter = SimNaoAttributeConverter.class)
    @Column(name = "IC_CENTRALIZADO", length = 1, nullable = false)
    private SimNao icCentralizado;

    @Convert(converter = SimNaoAttributeConverter.class)
    @Column(name = "IC_MULTIPLOS_PERFIS", length = 1, nullable = false)
    private SimNao icMultiplosPerfis;

    @Convert(converter = SimNaoAttributeConverter.class)
    @Column(name = "IC_SIGAL", length = 1, nullable = false)
    private SimNao icSigal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TS_CADASTRO", nullable = false)
    private Date tsCadastro;

    @Column(name = "CO_MATRICULA_CADASTRO", length = 7, nullable = false)
    private String coMatriculaCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TS_EXCLUSAO")
    private Date tsExclusao;

    @Column(name = "CO_MATRICULA_EXCLUSAO", length = 7)
    private String coMatriculaExclusao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NU_BASE_AUTENTICACAO", nullable = false)
    private BaseAutenticacaoEntity baseAutenticacao;

    @Column(name = "NU_PRODUCAO")
    private Long nuProducao;

}
