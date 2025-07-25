package com.example.springsecurity.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/* cuando una clase se declara como un registro, el compilador de Java genera automaticamente
 * ciertos métodos como el constructor, los metodos equals (), hashcode() y toString(),
 * basados en los componenetes de datos declarados en la clase
 *  */
@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponseDTO(String username, String message, String jwt, boolean status) {
}
