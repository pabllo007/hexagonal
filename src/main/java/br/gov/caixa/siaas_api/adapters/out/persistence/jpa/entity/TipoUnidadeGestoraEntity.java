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
@Table(schema = "AASSM001", name = "AASTB002_TIPO_UNIDADE_GESTORA")
public class TipoUnidadeGestoraEntity {
    private static final long serialVersionUID = 5222461121607315765L;

    public static final int DESENVOLVIMENTO = 1;
    public static final int NEGOCIO = 2;
    public static final int TI = 3;
    public static final int SEGURANCA = 4;
    public static final int PRODUCAO = 5;

    @Id
    @Column(name = "NU_TIPO_UNIDADE_GESTORA")
    private Integer nuTipoUnidadeGestora;

    @Column(name = "NO_TIPO_UNIDADE_GESTORA", length = 100, nullable = false, unique = true)
    private String noTipoUnidadeGestora;

    @Convert(converter = SimNaoAttributeConverter.class)
    @Column(name = "IC_GRUPO_TRABALHO", length = 1, nullable = false)
    private SimNao icGrupoTrabalho;

}
