package infrastructure.business.mapper;

import infrastructure.business.dto.TarefaDTO;
import infrastructure.entity.TarefaEntity;
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
