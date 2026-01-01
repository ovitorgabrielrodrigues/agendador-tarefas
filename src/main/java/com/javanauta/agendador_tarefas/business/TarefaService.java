package com.javanauta.agendador_tarefas.business;

import com.javanauta.agendador_tarefas.business.dto.TarefaDTO;
import com.javanauta.agendador_tarefas.business.mapper.TarefaConverter;
import com.javanauta.agendador_tarefas.business.mapper.TarefaUpdateConverter;
import com.javanauta.agendador_tarefas.infrastructure.entity.TarefaEntity;
import com.javanauta.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.javanauta.agendador_tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.agendador_tarefas.infrastructure.repository.TarefaRepository;
import com.javanauta.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

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

    public List<TarefaDTO> buscaTarefaPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefaEntity> listaTarefas = tarefaRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListaTarefaDTO(listaTarefas);
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefaRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id não existente " + id, e.getCause());
        }
    }

    public TarefaDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefaEntity entity = tarefaRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada! " + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar stataus da tarefa " + e.getCause());
        }
    }

    public TarefaDTO updateTarefas(TarefaDTO dto, String id) {
        try {
            TarefaEntity entity = tarefaRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }
}