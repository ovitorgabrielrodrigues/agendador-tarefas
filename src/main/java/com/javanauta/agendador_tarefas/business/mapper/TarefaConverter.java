package com.javanauta.agendador_tarefas.business.mapper;

import com.javanauta.agendador_tarefas.business.dto.TarefaDTO;
import com.javanauta.agendador_tarefas.infrastructure.entity.TarefaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dataEvento", target = "dataEvento")
    @Mapping(source = "dataCriacao", target = "dataCriacao")
    @Mapping(source = "nomeTarefa", target = "nomeTarefa")
    @Mapping(source = "descricao", target = "descricao")
    TarefaEntity paraTarefaEntity(TarefaDTO tarefaDTO);
    TarefaDTO paraTarefaDTO(TarefaEntity tarefaEntity);

    List<TarefaEntity> paraListaTarefaEntity(List<TarefaDTO> tarefaDTO);
    List<TarefaDTO> paraListaTarefaDTO(List<TarefaEntity> tarefaEntity);
}
