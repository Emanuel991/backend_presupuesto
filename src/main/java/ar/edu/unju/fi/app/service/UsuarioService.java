package ar.edu.unju.fi.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.app.model.Usuario;
import ar.edu.unju.fi.app.repository.UsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Usuario findById(Integer id) {
		return usuarioRepository.traerUsuarioPorID(id);
	}
	
	public Boolean guardar(Usuario user) {
		//System.out.println("email: " + user.getEmail());
		List<Usuario> usuarios = usuarioRepository.getUserByEmail(user.getEmail());
		if(usuarios.size()==0) {
		usuarioRepository.save(user);
		return true;
		}
		else { 
			return false;
		}	
	}
	
	public Usuario getUserByCredentials(Usuario user) {
		List<Usuario> users = usuarioRepository.getUserByEmail(user.getEmail());
		
		if(users.isEmpty()) {
			return null;
		}
		String passwordHashed = users.get(0).getPassword();
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
		if(argon2.verify(passwordHashed, user.getPassword())) {
			return users.get(0);
		}
		return null;
	}

	public void delete(Integer id) {
		Usuario user = usuarioRepository.traerUsuarioPorID(id);
		usuarioRepository.delete(user);
	}

}
