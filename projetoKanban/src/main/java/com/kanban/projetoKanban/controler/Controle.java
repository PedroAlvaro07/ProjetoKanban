package com.kanban.projetoKanban.controler;

import com.kanban.projetoKanban.model.Tarefa;
import com.kanban.projetoKanban.repository.Repositorio;
import com.kanban.projetoKanban.service.Servico;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tarefa")
public class Controle {

    @Autowired
    private Servico servico;
    @Autowired
    private Repositorio repositorio;

    @GetMapping
    public ResponseEntity<Map<String, List<Tarefa>>> listarTarefas() {
        return ResponseEntity.ok(servico.listarTarefas());
    }

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return servico.criarTarefa(tarefa);
    }

    @GetMapping("/atrasadas")
    public List<Tarefa> listarTarefasAtrasadas() {
        return servico.listarTarefasAtrasadas();
    }

    @PutMapping("/{id}")
    public Tarefa updateTarefa(@PathVariable int id, @RequestBody Tarefa tarefa) {
        Optional<Tarefa> busca = repositorio.findById(id);
        if (busca.isPresent()) {
            Tarefa tarefaUpdate = busca.get();

            tarefaUpdate.setTitulo(tarefa.getTitulo());
            tarefaUpdate.setDescricao(tarefa.getDescricao());
            tarefaUpdate.setStatus(tarefa.getStatus());
            tarefaUpdate.setDataInicio(tarefa.getDataInicio());
            tarefaUpdate.setDataLimite(tarefa.getDataLimite());

            return servico.updateTarefa(tarefaUpdate);

        }else {
            System.out.println("Tarefa não encontrada com o id: " + id);
            return null;
        }
    }

    @PutMapping("/{id}/move")
    public Tarefa moveTarefa(@PathVariable int id) {
        Optional<Tarefa> busca = repositorio.findById(id);
        Tarefa moveTarefa = busca.get();
        return servico.mover(moveTarefa);
    }

    @DeleteMapping("/{id}")
    public void deleteTarefa(@PathVariable int id) {
        servico.excluirTarefa(id);
    }
}
