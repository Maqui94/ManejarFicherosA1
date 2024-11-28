package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coche implements Serializable {
    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;


    public void mostrarDatos(){
        System.out.println("id= " +id);
        System.out.println("marca= " +marca);
        System.out.println("modelo= " +modelo);
        System.out.println("matricula= " +matricula);
        System.out.println("color= " +color);
    }

}
