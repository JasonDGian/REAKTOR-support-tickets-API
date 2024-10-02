package ies_jandula.incidencia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ies_jandula.incidencia.persistence.model.Incidencia;
import ies_jandula.incidencia.service.ServiceIncidencia;
import ies_jandula.incidencia.utils.EstadosIncidencia;
// import ies_jandula.incidencia.utils.EstadoIncidencia; DESACTIVADO A FAVOR DE TIPO STRING

@RestController
public class IncidenciaController
{

	@Autowired
	ServiceIncidencia serviceIncidencia; // Servicio que consume los metodos del repositorio.

	// LISTAR INCIDENCIAS.
	@RequestMapping(value = "/incidencias")
	public List<Incidencia> listarIncidencias()
	{
		return serviceIncidencia.listaIncidencias();
	}

	// CREAR INCIDENCIA
	@PostMapping(value = "/nueva")
	public String crearIncidencia(@RequestBody Incidencia incidencia)
	{
		// Por defecto pone el estado de la incidencia en EN_PROCESO.

		incidencia.setEstadoIncidencia(EstadosIncidencia.EN_PROGRESO);
		serviceIncidencia.crearIncidencia(incidencia);
		return "Incidencia creada " + incidencia.toString();
	}

	// MODIFICAR A RESUELTA.
	@PostMapping(value = "/resuelta")
	public String incidenciaResuelta(@RequestParam Long id)
	{
		return serviceIncidencia.modificarEstadoResuelta(id);
	}

	// CANCELAR INCIDENCIA.
	@PostMapping(value = "/cancelar")
	public String cancelarIncidencia(@RequestParam Long id)
	{
		return serviceIncidencia.modificarEstadoCancela(id);
	}

	// LISTAR INCIDENCIAS RESUELTAS.
	@GetMapping(value = "/incidencias_resueltas")
	public List<Incidencia> mostrarResueltas()
	{
		return serviceIncidencia.listaResueltas();
	}

	// LISTAR INCIDENCIAS CANCELADASS.
	@GetMapping(value = "/incidencias_canceladas")
	public List<Incidencia> mostrarCanceladas()
	{
		return serviceIncidencia.listaCanceladas();
	}

	// LISTAR INCIDENCIAS EN PROGRESO
	@GetMapping(value = "/incidencias_en_progreso")
	public List<Incidencia> mostrarEnProgreso()
	{
		return serviceIncidencia.listaEnProgreso();
	}

}
