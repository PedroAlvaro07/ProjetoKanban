package com.kanban.projetoKanban.repository;

import com.kanban.projetoKanban.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repositorio extends JpaRepository<Tarefa, Integer> {
}
