package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filme {
    private long codfilme;
    private String titulo;
    private int duracao;

    public String tempo() {
       return getDuracao() / 60 + "h " + getDuracao() % 60 + "min";
    }

    @Override
    public String toString() {
        return codfilme + ". " + ",título: " + titulo + ", duração: " + duracao / 60 + "h" + duracao % 60;
    }
}
