package ar.edu.unju.fi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unju.fi.app.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	@Query("from Usuario u where u.email =:email")
	public List<Usuario> getUserByEmail(@Param("email") String email);

	@Query("from Usuario u where u.userId =:id")
	public Usuario traerUsuarioPorID(@Param("id") Integer id);
}
