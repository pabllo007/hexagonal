package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BaseAutenticacaoRequestTest {

    private static Validator validator;

    private static final String VALID_NAME = "Base Autenticação";
    private static final String SIM = "S";

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void devePassarComValoresValidos() {
        BaseAutenticacaoRequest request = new BaseAutenticacaoRequest(VALID_NAME, SIM, SIM);
        Set<ConstraintViolation<BaseAutenticacaoRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveFalharQuandoNomeVazio() {
        BaseAutenticacaoRequest request = new BaseAutenticacaoRequest("", SIM, SIM);
        Set<ConstraintViolation<BaseAutenticacaoRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void deveFalharQuandoNomeMaiorQue100() {
        String longName = "A".repeat(101);
        BaseAutenticacaoRequest request = new BaseAutenticacaoRequest(longName, SIM, SIM);
        Set<ConstraintViolation<BaseAutenticacaoRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void deveFalharQuandoCamposNulos() {
        BaseAutenticacaoRequest request = new BaseAutenticacaoRequest(VALID_NAME, null, null);
        Set<ConstraintViolation<BaseAutenticacaoRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}