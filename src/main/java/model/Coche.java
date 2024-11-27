package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coche {
    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
}
