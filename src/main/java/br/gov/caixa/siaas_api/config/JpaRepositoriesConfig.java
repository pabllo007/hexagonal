package br.gov.caixa.siaas_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EntityScan(basePackages = "br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity")
@EnableJpaRepositories(basePackages = "br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository")
public class JpaRepositoriesConfig {
}