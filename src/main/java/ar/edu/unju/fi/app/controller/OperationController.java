package ar.edu.unju.fi.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.app.model.Operation;
import ar.edu.unju.fi.app.service.OperationService;
import ar.edu.unju.fi.app.service.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("/operations/")
public class OperationController {
	
	@Autowired
	OperationService operationService;
	
	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/lista_operaciones")
	public ArrayList<Operation> listarOperaciones(){
		return operationService.Listar();
	}
	
	@GetMapping("/lista_ultimas/{id}")
	public List<Operation> listarUltimasOperaciones(@PathVariable Integer id){
		return operationService.ListarUltimosDiez(id);
	}
	
	@GetMapping("/lista_egresos")
	public List<Operation> listarEgresos(){
		return operationService.listarEgresos();
	}
	
	@GetMapping("/lista_ingresos")
	public List<Operation> listarIngresos(){
		return operationService.listarIngresos();
	}
	
	@CrossOrigin
	@PostMapping(value="/nueva_operacion/{userId}", consumes= {"application/json"})
	public void add(@RequestBody Operation op, @PathVariable Integer userId) {
		op.setFecha(new Date());
		op.setUsuario(usuarioService.findById(userId));
		operationService.agregar(op, userId);
	}
	
	@PutMapping("/operation_updated")
	public void update(@RequestBody Operation op) {
		op.setFecha(new Date());
		operationService.updateOp(op);
	}
	
	@DeleteMapping("/deleted_operation/{id}")
	public void delete(@PathVariable Integer id) {
		operationService.deleteOp(id);
	}
}
