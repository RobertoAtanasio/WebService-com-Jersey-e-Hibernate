package br.com.devmedia.webservice.utils;

import br.com.devmedia.webservice.exceptions.IdInvalidoException;

public class IdUtils {

	public static Long idValido(Long id) {
        if (id <= 0) {
            throw new IdInvalidoException("Id inválido. Deve ser número inteiro maior que zero.");
        }
        return id;
    }
}
