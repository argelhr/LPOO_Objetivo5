package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sala {
    private int cod_sala;
    private int nr_sala;
    private int capacidade;
    private Boolean status;
}
