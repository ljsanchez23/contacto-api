package com.SpringAPI.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SpringAPI.model.Contacto;
import com.SpringAPI.repository.ContactoRepository;

/**
 * Controlador REST para manejar solicitudes relacionadas con Contacto.
 */
@RestController
@Validated
@RequestMapping("/api/contacto")
public class ContactoController {

	@Autowired
	private ContactoRepository contactoRepository;

	/**
	 * Maneja la solicitud POST para enviar un formulario de contacto.
	 * 
	 * @param contacto El objeto Contacto que contiene los datos del formulario.
	 * @return ResponseEntity con el estado HTTP y el objeto Contacto guardado o un
	 *         mensaje de error si el correo ya está registrado.
	 */
	@PostMapping("/submit")
	public ResponseEntity<?> submitContactForm(@Validated @RequestBody Contacto contacto) {
		// Verificar si el correo electrónico ya existe
		if (contactoRepository.existsByEmail(contacto.getEmail())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo electrónico ya está registrado");
		}
		Contacto savedContacto = contactoRepository.save(contacto);
		return ResponseEntity.ok(savedContacto);
	}

	/**
	 * Maneja las excepciones de validación y devuelve una respuesta con los errores
	 * encontrados.
	 * 
	 * @param ex La excepción MethodArgumentNotValidException que contiene los
	 *           detalles de los errores de validación.
	 * @return ResponseEntity con un mapa de errores y el estado HTTP 400 Bad
	 *         Request.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
