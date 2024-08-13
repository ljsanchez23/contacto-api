package com.SpringAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SpringAPI.model.Contacto;

/**
 * Repositorio JPA para la entidad Contacto.
 */
@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

	/**
	 * Verifica si un contacto con el correo electrónico dado ya existe en la base
	 * de datos.
	 * 
	 * @param email El correo electrónico a verificar.
	 * @return true si existe un contacto con el correo electrónico dado, false en
	 *         caso contrario.
	 */
	boolean existsByEmail(String email);
}
