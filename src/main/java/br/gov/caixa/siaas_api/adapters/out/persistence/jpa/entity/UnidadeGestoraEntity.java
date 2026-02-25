package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.converter.SimNaoAttributeConverter;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "AASSM001", name = "AASTB003_UNIDADE_GESTORA")
@SequenceGenerator(
        name = "AASSQ003_UNIDADE_GESTORA",
        sequenceName = "AASSM001.AASSQ003_UNIDADE_GESTORA",
        allocationSize = 1
)
public class UnidadeGestoraEntity {
    private static final long serialVersionUID = 8161061894400122393L;

    @Id
    @Column(name = "NU_UNIDADE_GESTORA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AASSQ003_UNIDADE_GESTORA")
    private Long nuUnidadeGestora;

    @Column(name = "NU_UNIDADE", nullable = false)
    private Integer nuUnidade;

    @Column(name = "NU_NATURAL", nullable = false)
    private Integer nuNatural;

    @Convert(converter = SimNaoAttributeConverter.class)
    @Column(name = "IC_ATIVO", length = 1, nullable = false)
    private SimNao icAtivo;

    @Column(name = "NO_CAIXA_POSTAL", length = 100)
    private String noCaixaPostal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NU_TIPO_UNIDADE_GESTORA", nullable = false)
    private TipoUnidadeGestoraEntity tipoUnidadeGestora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NU_GRUPO_TRABALHO")
    private GrupoTrabalhoEntity grupoTrabalho;

}
