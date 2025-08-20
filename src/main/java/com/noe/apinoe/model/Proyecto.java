package com.noe.apinoe.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Proyecto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 100, nullable = false)
	private String titulo;

	@Column(length = 500, nullable = false)
	private String descripcion;

	@Column(length = 100, nullable = false)
	private String empresa;

	private LocalDateTime fechaCreacion = LocalDateTime.now();

	private LocalDateTime fechaActualizacion = LocalDateTime.now();

	private boolean activo = true;

}
