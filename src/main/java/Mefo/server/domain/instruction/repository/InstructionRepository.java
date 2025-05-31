package Mefo.server.domain.instruction.repository;

import Mefo.server.domain.instruction.entity.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {
    List<Instruction> findAllByRecipeId(Long id);
}
