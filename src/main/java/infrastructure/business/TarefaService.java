package infrastructure.business;

import infrastructure.business.dto.TarefaDTO;
import infrastructure.business.mapper.TarefaConverter;
import infrastructure.entity.TarefaEntity;
import infrastructure.enums.StatusNotificacaoEnum;
import infrastructure.repository.TarefaRepository;
import infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefaDTO gravarTarefa(String token, TarefaDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefaEntity entity = tarefaConverter.paraTarefaEntity(dto);
        dto.setEmailUsuario(email);
        return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
    }

    public List<TarefaDTO> buscaTarefaAgendadasPorPeriodo(LocalDateTime dataIncial, LocalDateTime dataFinal) {
        return tarefaConverter.paraListaTarefaDTO(
                tarefaRepository.findByDataEventoBetween(dataIncial, dataFinal));
    }

    public List<TarefaDTO> buscaTarefaPorEmail(String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefaEntity> listaTarefas = tarefaRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListaTarefaDTO(listaTarefas);
    }
}
