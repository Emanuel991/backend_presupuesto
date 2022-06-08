package ar.edu.unju.fi.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.unju.fi.app.model.*;
import ar.edu.unju.fi.app.repository.OperationRepository;

@Service
public class OperationService {
	
	@Autowired
	OperationRepository operationRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	public ArrayList<Operation> Listar(){
		return (ArrayList<Operation>) operationRepository.findAll();
	}
	
	public Operation agregar(Operation op, Integer id) {
		Double saldo = usuarioService.findById(id).getSaldo();
		Double monto = op.getMonto();
			if(op.getTipo().equals("egreso") && saldo > op.getMonto()) {
				usuarioService.findById(id).setSaldo(saldo - monto);
				operationRepository.save(op);
			}else if(op.getTipo().equals("ingreso")) {
				usuarioService.findById(id).setSaldo(saldo + monto);
				operationRepository.save(op);
			}
			
			return null;
	}
	
	
	public List<Operation> ListarUltimosDiez(Integer id){
		return operationRepository.findTop10ByOrderByIdDesc(id);
	}
	
	public List<Operation> listarEgresos(){
		return operationRepository.listarEgresos();
	}
	
	public List<Operation> listarIngresos(){
		return operationRepository.listarIngresos();
	}

	public void deleteOp(Integer id) {
		Operation op = operationRepository.buscarPorId(id);
		operationRepository.delete(op);
	}

	public void updateOp(Operation op) {
		operationRepository.updateOp(op.getMonto(), op.getId(), op.getConcepto());
	}
}
