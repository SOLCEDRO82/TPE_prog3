//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // rutas a los archivos .csv
        String pathCamiones = "data/Camiones.csv";
        String pathPaquetes = "data/Paquetes.csv";

        // instanciamos servicios para cargar los datos
        Servicios servicios = new Servicios(pathCamiones, pathPaquetes);

        // mostramos los datos cargados
        System.out.println("Camiones cargados:");
        for (Camion camion : servicios.getCamiones()) {
            System.out.println(camion);
        }

        System.out.println("Paquetes cargados:");
        for (Paquete paquete : servicios.getPaquetes()) {
            System.out.println(paquete);
        }
    }
}

