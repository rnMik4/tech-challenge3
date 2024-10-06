package com.restaurantes.repository;

import com.restaurantes.entity.MesasRestaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface MesasRepository extends JpaRepository<MesasRestaurante, Long> {
//    @Query(value = "select " +
//            "mesa.* " +
//            "from mesas_restaurante mesa " +
//            "left join reservas rese " +
//            "on rese.restaurante_id = mesa.id_restaurante " +
//            "where mesa.id_restaurante =  ?1 " +
//            "and rese.datahora_reserva between ?2 and (timestamp ?2 + interval '2 hour')", nativeQuery = true)
//    List<Map<String, Object>> findLugaresDisponiveisRestaurante(long id, String data);

    @Query(value = "select mesa.* from mesas_restaurante mesa " +
            "left join reservas rese " +
            "on rese.restaurante_id = mesa.id_restaurante " +
            "and mesa.id = rese.mesa_id " +
            "and rese.datahora_reserva >= :data " +
            "and rese.status_reserva = 'Reservado' " +
            "where mesa.id_restaurante = :id " +
            "and rese.id is null", nativeQuery = true)
    List<Map<String, Object>> findLugaresDisponiveisRestaurante(@Param("id") Long id, @Param("data") String data);

    List<MesasRestaurante> findByIdRestaurante(Long idRestaurante);

}
