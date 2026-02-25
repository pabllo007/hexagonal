package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "AASSM001", name = "AASTB041_GRUPO_TRABALHO")
public class GrupoTrabalhoEntity {
    private static final long serialVersionUID = 3435487546234439661L;

    public static final int CEPTI_SEGURANCA_BSB = 1;
    public static final int CEPTI_SEGURANCA_RJ = 2;
    public static final int CEPTI_SEGURANCA_SP = 3;
    public static final int CETEC_PEDES = 4;

    @Id
    @Column(name = "NU_GRUPO_TRABALHO")
    private Integer nuGrupoTrabalho;

    @Column(name = "NO_GRUPO_TRABALHO", length = 100, nullable = false, unique = true)
    private String noGrupoTrabalho;

}
