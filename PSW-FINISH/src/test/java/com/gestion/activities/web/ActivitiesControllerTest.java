package com.gestion.activities.web;

import com.gestion.activities.domain.dto.ActivitiesRequestDto;
import com.gestion.activities.domain.dto.ActivitiesResponseDto;
import com.gestion.activities.service.ActivitiesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ActivitiesControllerTest {

    @Mock
    private ActivitiesService activitiesService;

    @InjectMocks
    private ActivitiesController activitiesController;

    @Test
    void getDataActivitiesById() {
        // Simula el servicio devolviendo un Mono con datos simulados
        when(activitiesService.findById(anyInt())).thenReturn(Mono.just(new ActivitiesResponseDto()));

        // Llama al método del controlador
        Mono<ActivitiesResponseDto> result = activitiesController.getDataActivitiesById(1);

        // Verifica que el servicio fue llamado y que el resultado no es nulo
        verify(activitiesService, times(1)).findById(anyInt());
        assertEquals(result.block(), new ActivitiesResponseDto());
    }

    @Test
    void getDataActivitiesComplete() {
        // Simula el servicio devolviendo un Flux con datos simulados
        when(activitiesService.findAll()).thenReturn(Flux.just(new ActivitiesResponseDto(), new ActivitiesResponseDto()));

        // Llama al método del controlador
        Flux<ActivitiesResponseDto> result = activitiesController.getDataActivitiesComplete();

        // Verifica que el servicio fue llamado y que el resultado no es nulo
        verify(activitiesService, times(1)).findAll();
        assertEquals(result.collectList().block(), Flux.just(new ActivitiesResponseDto(), new ActivitiesResponseDto()).collectList().block());
    }

    @Test
    void updateDataActivities() {
        // Simula el servicio devolviendo un Mono con datos simulados
        when(activitiesService.updateActivities(any(ActivitiesRequestDto.class), anyInt()))
                .thenReturn(Mono.just(new ActivitiesResponseDto()));

        // Llama al método del controlador
        Mono<ActivitiesResponseDto> result = activitiesController.updateDataActivities(new ActivitiesRequestDto(), 1);

        // Verifica que el servicio fue llamado y que el resultado no es nulo
        verify(activitiesService, times(1)).updateActivities(any(ActivitiesRequestDto.class), anyInt());
        assertEquals(result.block(), new ActivitiesResponseDto());
    }

    @Test
    void deleteTotalActivities() {
        // Simula el servicio devolviendo un Mono<Void>
        when(activitiesService.deleteActivities(anyInt())).thenReturn(Mono.empty());

        // Llama al método del controlador
        Mono<Void> result = activitiesController.deleteTotalActivities(558);

        // Verifica que el servicio fue llamado y que el resultado no es nulo
        verify(activitiesService, times(1)).deleteActivities(anyInt());
        assertEquals(result, Mono.empty());
    }
    /*-----SIMULACION DE ESCENARIO INCORRECTO CON UN ID INVALIDO-------*/


    @Test
    void updateDataActivities_ShouldThrowErrorWhenIdIsNull() {
        // Intenta actualizar con un objeto que tiene ID nulo
        ActivitiesRequestDto requestDto = new ActivitiesRequestDto(null, "Nombre", "Descripción", LocalDate.now(), "Duración", "Ubicación", "Activo", "Tipo Pronacej", "Tipo SOA");

        // Asegúrate de que se lance una excepción cuando se intente actualizar
        assertThrows(IllegalArgumentException.class, () -> activitiesController.updateDataActivities(requestDto, 1));

        // Verifica que el servicio no fue llamado
        verify(activitiesService, never()).updateActivities(any(ActivitiesRequestDto.class), anyInt());
    }

    /*-----CORREGIMOS EL DATO NULO QUE LE ESTABAMOS PASANDO EN EL METODO ANTERIOR-------*/

    @Test
    void updateDataActivities_ShouldUpdateWhenIdIsValid() {
        // Objeto con ID válido
        ActivitiesRequestDto requestDto = new ActivitiesRequestDto(1, "Nuevo Nombre", "Nueva Descripción", LocalDate.now(), "Nueva Duración", "Nueva Ubicación", "Activo", "Tipo Pronacej", "Tipo SOA");

        // Simula el servicio devolviendo un Mono con datos simulados
        when(activitiesService.updateActivities(any(ActivitiesRequestDto.class), anyInt()))
                .thenReturn(Mono.just(new ActivitiesResponseDto()));

        // Llama al método del controlador
        activitiesController.updateDataActivities(requestDto, 1);

        // Verifica que el servicio fue llamado con los parámetros correctos
        verify(activitiesService, times(1)).updateActivities(eq(requestDto), eq(1));
    }
}
