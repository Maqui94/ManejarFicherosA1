package controller;

import model.Coche;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    private static final String path1 = "coches.dat";
    private static final String path2 = "coches.csv";
    private ArrayList<Coche> coches;

    public Controller() {
        coches = cargarCochesDeFichero();
        if (coches.isEmpty()) {
            cargarCochesIniciales();
        }
    }

    private void cargarCochesIniciales() {
        coches.add(new Coche(1, "ABC123", "Toyota", "Corolla", "Rojo"));
        coches.add(new Coche(2, "DEF456", "Honda", "Civic", "Azul"));
        coches.add(new Coche(3, "GHI789", "Ford", "Focus", "Negro"));
        coches.add(new Coche(4, "JKL012", "Chevrolet", "Spark", "Blanco"));
        coches.add(new Coche(5, "MNO345", "BMW", "Serie 3", "Gris"));
        System.out.println("Coches iniciales cargados en la memoria.");
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("===== MENÚ =====");
            System.out.println("1. Añadir nuevo coche");
            System.out.println("2. Borrar coche por ID");
            System.out.println("3. Consultar coche por ID");
            System.out.println("4. Listado de coches");
            System.out.println("5. Terminar el programa");
            System.out.println("6. Exportar coches a archivo CSV");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1 -> anadirCoche();
                case 2 -> borrarCoche();
                case 3 -> consultarCoche();
                case 4 -> listarCoches();
                case 5 -> terminarPrograma();
                case 6 -> exportarACSV();
                default -> System.out.println("Opción no encontrada, prueba otra");
            }
        } while (opcion != 5);
    }

    private ArrayList<Coche> cargarCochesDeFichero() {
        File file = new File("src/main/java/resources/" + path1);
        if (!file.exists()) {
            System.out.println("El archivo no existe. Iniciando con una lista vacía.");
            return new ArrayList<>();
        }
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            // Leer los objetos del archivo y devolverlos como ArrayList
            return (ArrayList<Coche>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el archivo");
            return new ArrayList<>();
        }
    }

    private void anadirCoche() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (coches.stream().anyMatch(coche -> coche.getId() == id)) {
            System.out.println("Error: Ya existe un coche con este ID.");
            return;
        }

        System.out.print("Introduce la matrícula: ");
        String matricula = scanner.nextLine();
        if (coches.stream().anyMatch(coche -> coche.getMatricula().equalsIgnoreCase(matricula))) {
            System.out.println("Error: Ya existe un coche con esta matrícula.");
            return;
        }

        System.out.print("Introduce la marca: ");
        String marca = scanner.nextLine();
        System.out.print("Introduce el modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Introduce el color: ");
        String color = scanner.nextLine();

        coches.add(new Coche(id, matricula, marca, modelo, color));
        System.out.println("Coche añadido con éxito.");
    }

    private void borrarCoche() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del coche a borrar: ");
        int id = scanner.nextInt();

        if (coches.removeIf(coche -> coche.getId() == id)) {
            System.out.println("Coche borrado con éxito.");
        } else {
            System.out.println("No se encontró un coche con el ID: " + id);
        }
    }

    private void consultarCoche() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del coche a consultar: ");
        int id = scanner.nextInt();

        Coche coche = coches.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        if (coche != null) {
            System.out.println(coche);
        } else {
            System.out.println("No se encontró un coche con el ID: " + id);
        }
    }

    private void listarCoches() {
        if (coches.isEmpty()) {
            System.out.println("No hay coches registrados.");
        } else {
            System.out.println("Listado de coches:");
            for (Coche coche : coches) {
                coche.mostrarDatos();
            }
        }
    }
        private void exportarACSV () {
            try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/java/resources/" + path2))) {
                writer.println("id;matricula;marca;modelo;color");
                for (Coche coche : coches) {
                    writer.printf("%d;%s;%s;%s;%s%n",
                            coche.getId(),
                            coche.getMatricula(),
                            coche.getMarca(),
                            coche.getModelo(),
                            coche.getColor());
                }
                System.out.println("Coches exportados con éxito al archivo CSV.");
            } catch (IOException e) {
                System.out.println("Error al exportar a CSV");
            }
        }

        private void terminarPrograma () {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/main/java/resources/" + path1))) {
                objectOutputStream.writeObject(coches);
                System.out.println("Datos guardados con éxito. Cerrando el programa.");
            } catch (IOException e) {
                System.out.println("Error al guardar los datos");
            }
        }

}
