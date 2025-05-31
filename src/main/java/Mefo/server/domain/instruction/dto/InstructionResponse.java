package Mefo.server.domain.instruction.dto;

import Mefo.server.domain.instruction.entity.Instruction;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class InstructionResponse {
    private int stepNumber;
    @Column(length = 1000)
    private String description;
    public static List<InstructionResponse> from(List<Instruction> instructions){
        List<InstructionResponse> instructionResponses = new ArrayList<>();
        for(Instruction instruction : instructions){
            InstructionResponse instructionResponse = InstructionResponse.builder()
                    .stepNumber(instruction.getStepNumber())
                    .description(instruction.getDescription())
                    .build();
            instructionResponses.add(instructionResponse);
        }
        return instructionResponses;
    }
}
