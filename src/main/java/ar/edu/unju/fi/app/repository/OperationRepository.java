package ar.edu.unju.fi.app.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.app.model.*;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer>{
	
	@Query("from Operation o where o.tipo =:egreso")
	public ArrayList<Operation> listarEgresos();
	
	@Query("from Operation o where o.tipo =:ingreso")
	public ArrayList<Operation> listarIngresos();
	
	@Query(value="select * from Operation o where usuario=:id order by o.id desc limit 10", nativeQuery=true)
	public List<Operation> findTop10ByOrderByIdDesc(@Param("id") Integer id);
	
	//@Query(value="select * from Operation o where usuario=:id order by o.id desc limit 1", nativeQuery=true)
	//public List<Operation> findLast(@Param("id") Integer id);

	@Query("from Operation o where o.id =:id")
	public Operation buscarPorId(@Param("id") Integer id);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update Operation o set o.monto=:monto, o.concepto=:concepto where o.id =:id")
	public void updateOp(@Param("monto")Double monto, @Param("id") Integer id, @Param("concepto") String concepto);
}
