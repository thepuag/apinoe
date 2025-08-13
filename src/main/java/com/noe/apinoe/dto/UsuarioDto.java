package com.noe.apinoe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioDto {
    private Integer id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String email;
    
    @Size(max = 500, message = "La URL de imagen no puede exceder 500 caracteres")
    private String imagenUrl;
    
    private Boolean activo;

    // Constructores
    public UsuarioDto() {}

    public UsuarioDto(String nombre, String email, String imagenUrl) {
        this.nombre = nombre;
        this.email = email;
        this.imagenUrl = imagenUrl;
    }

    // Getters y setters generados automáticamente
}
