package ar.edu.unju.fi.app.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.app.model.Usuario;
import ar.edu.unju.fi.app.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController("/auth/")
public class AuthController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@CrossOrigin
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@RequestBody Usuario user) {
		Usuario log = usuarioService.getUserByCredentials(user);
		
		if(log != null) {
			String token = getJWTToken(log.getUsername());
			return "{\"userId\": "+log.getUserId()+", \"token\": \"" + token +"\"}";
		}
		
		return null;
	}
	
	
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = "Bearer " + Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return token;
	}
	
//	public String getJWTTokenPayload(String token) {
//		String[] chunks = token.split("\\.");
//		Base64.Decoder decoder = Base64.getUrlDecoder();
//
//		//String header = new String(decoder.decode(chunks[0]));
//		String payload = new String(decoder.decode(chunks[1]));
//		return payload;
//	}
}
