package project.graduation.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GPSDto {
    @NotNull @NotBlank
    private String latitude;
    @NotNull @NotBlank
    private String longitude;
    private String altitude;
    @NotNull
    private Integer floor;

}
