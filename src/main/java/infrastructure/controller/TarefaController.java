package infrastructure.controller;

import infrastructure.business.TarefaService;
import infrastructure.business.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaDTO> gravarTarefas(@RequestBody TarefaDTO tarefaDTO,
                                                   @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefaService.gravarTarefa(token, tarefaDTO));
    }
}
