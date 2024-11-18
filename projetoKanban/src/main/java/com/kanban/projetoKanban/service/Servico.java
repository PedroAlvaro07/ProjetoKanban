package com.kanban.projetoKanban.service;

import com.kanban.projetoKanban.model.Tarefa;
import com.kanban.projetoKanban.repository.Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Servico {

    @Autowired
    private Repositorio repositorio;

    public Tarefa criarTarefa(Tarefa tarefa){
        if(!tarefa.getPrio().equalsIgnoreCase("Baixa") && !tarefa.getPrio().equalsIgnoreCase("Média") && !tarefa.getPrio().equalsIgnoreCase("Alta")){
            throw new IllegalArgumentException("Prioridade deve ser: baixa, média ou alta.");
        }
        tarefa.setStatus("A Fazer");
        return repositorio.save(tarefa);
    }

    public Map<String, List<Tarefa>> listarTarefas() {

        Map<String, Integer> prioridadeOrdem = Map.of("Alta", 3, "Média", 2, "Baixa", 1);

        Map<String, List<Tarefa>> tarefasColuna = new LinkedHashMap<>();
        tarefasColuna.put("A Fazer", new ArrayList<>());
        tarefasColuna.put("Em Progresso", new ArrayList<>());
        tarefasColuna.put("Finalizado", new ArrayList<>());

        for (Tarefa tarefa : repositorio.findAll()) {
            tarefasColuna.get(tarefa.getStatus()).add(tarefa);
        }

        tarefasColuna.forEach((coluna, lista) ->
                lista.sort(Comparator.comparing(t -> prioridadeOrdem.get(t.getPrio()), Comparator.reverseOrder()))
        );
        return tarefasColuna;
    }

    public List<Tarefa> listarTarefasAtrasadas() {
        List<Tarefa> todasTarefas = repositorio.findAll();

        List<Tarefa> tarefasAtrasadas = todasTarefas.stream()
                .filter(tarefa -> tarefa.getDataLimite().isBefore(LocalDate.now()) &&
                        (tarefa.getStatus().equals("A Fazer") || tarefa.getStatus().equals("Em Progresso")))
                .collect(Collectors.toList());

        return tarefasAtrasadas;
    }

    public Tarefa updateTarefa(Tarefa tarefa){
        return repositorio.save(tarefa);
    }

    public void excluirTarefa(int id){
        repositorio.deleteById(id);
    }

    public Tarefa mover(Tarefa tarefa){
        switch(tarefa.getStatus()){
            case "A Fazer":
                tarefa.setStatus("Em Progresso");
                break;
            case "Em Progresso":
                tarefa.setStatus("Finalizado");
                break;
            default:
                System.out.println("Tarefa não pode ser movida");
        }
        return repositorio.save(tarefa);
    }
}