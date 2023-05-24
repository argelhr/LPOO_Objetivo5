package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {
    private int cod_sessao;
    private LocalDate dt_sessao;
    private LocalTime hora_sessao;
    private double valor_inteira;
    private double valor_meia;
    private boolean encerrada;
    private Filme filme;
    private Sala sala;

}
