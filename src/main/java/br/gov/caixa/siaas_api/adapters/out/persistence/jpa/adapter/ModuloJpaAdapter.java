package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.application.modulo.port.out.ModuloConsultaPort;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.ModuloRepository;

public class ModuloJpaAdapter implements ModuloConsultaPort {

    private final ModuloRepository moduloRepository;

    public ModuloJpaAdapter(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    @Override
    public boolean existeModuloUsandoBaseAutenticacao(Long idBaseAutenticacao) {
        return moduloRepository.existeModuloUsandoBaseAutenticacao(idBaseAutenticacao);
    }
}