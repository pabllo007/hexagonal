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
@Table(schema = "AASSM001", name = "AASTB001_BASE_AUTENTICACAO")
@SequenceGenerator(
        name = "AASSQ001_BASE_AUTENTICACAO",
        sequenceName = "AASSM001.AASSQ001_BASE_AUTENTICACAO",
        allocationSize = 1
)
public class BaseAutenticacaoEntity {
    private static final long serialVersionUID = 8161061894400122393L;
    public static final Long SISGR = 7l;
    public static final Long SINAV = 6l;
    @Id
    @Column(name = "NU_BASE_AUTENTICACAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AASSQ001_BASE_AUTENTICACAO")
    private Long nuBaseAutenticacao;

    @Column(name = "NO_BASE_AUTENTICACAO", length = 100, nullable = false, unique = true)
    private String noBaseAutenticacao;

    @Convert(converter = SimNaoAttributeConverter.class)
    @Column(name = "IC_RECURSO", length = 1, nullable = false)
    private SimNao icRecurso;

    @Convert(converter = SimNaoAttributeConverter.class)
    @Column(name = "IC_SINAV", length = 1, nullable = false)
    private SimNao icSinav;

    @Convert(converter = SimNaoAttributeConverter.class)
    @Column(name = "IC_ATIVO", length = 1, nullable = false)
    private SimNao icAtivo;

}
