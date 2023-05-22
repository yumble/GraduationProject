package project.graduation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.graduation.entity.Address;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RelationDataDto {
    private String rotation;
    private String translation;
}