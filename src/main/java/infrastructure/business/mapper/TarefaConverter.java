package infrastructure.business.mapper;

import infrastructure.business.dto.TarefaDTO;
import infrastructure.entity.TarefaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    TarefaEntity paraTarefaEntity(TarefaDTO tarefaDTO);
    TarefaDTO paraTarefaDTO(TarefaEntity tarefaEntity);
}
