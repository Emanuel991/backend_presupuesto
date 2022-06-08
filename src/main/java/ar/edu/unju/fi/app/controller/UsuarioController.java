package ar.edu.unju.fi.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.app.model.Usuario;
import ar.edu.unju.fi.app.service.UsuarioService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@CrossOrigin
@RestController
@RequestMapping("/users/")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/usuario/{id}")
	public Usuario listarOperaciones(@PathVariable Integer id){
		return usuarioService.findById(id);
	}
	
	@PostMapping("/new_user")
	public Boolean addUser(@RequestBody Usuario user) {
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
		String hash = argon2.hash(1, 1024, 1, user.getPassword());
		user.setPassword(hash);
		
		return usuarioService.guardar(user);
	}
	
	@DeleteMapping("/user_deleted/{id}")
	public void deleteUser(@PathVariable Integer id) {
		usuarioService.delete(id);
	}
}
