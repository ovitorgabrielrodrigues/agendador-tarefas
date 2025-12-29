package infrastructure.business;

import infrastructure.business.dto.TarefaDTO;
import infrastructure.business.mapper.TarefaConverter;
import infrastructure.entity.TarefaEntity;
import infrastructure.enums.StatusNotificacaoEnum;
import infrastructure.repository.TarefaRepository;
import infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefaDTO gravarTarefa(String token, TarefaDTO tarefaDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefaEntity entity = tarefaConverter.paraTarefaEntity(tarefaDTO);
        tarefaDTO.setEmailUsuario(email);
        return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
    }
}
