import java.util.List;

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

        // Servicio 1: Buscar por códigoPaquete
        System.out.println("Probando el servicio 1:");

        // Caso 1: El paquete existe
        Paquete paqueteEncontrado = servicios.servicio1("P001");
        System.out.println("Buscar 'P001': " + paqueteEncontrado);

        // Caso 2: El paquete no existe (debería mostrar null)
        Paquete paqueteInexistente = servicios.servicio1("P099");
        System.out.println("Buscar 'P099': " + paqueteInexistente);


        System.out.println("Probando el servicio 2:");

        // Caso 1: Lista de paquetes que contienen alimentos
        System.out.println("Paquetes CON alimentos:");
        List<Paquete> paquetesConAlimentos = servicios.servicio2(true);
        for (Paquete paquete : paquetesConAlimentos) {
            System.out.println(paquete);
        }

        // Caso 2: Lista de paquetes sin alimentos
        System.out.println("Paquetes SIN alimentos:");
        List<Paquete> paquetesSinAlimentos = servicios.servicio2(false);
        for (Paquete paquete : paquetesSinAlimentos) {
            System.out.println(paquete);
        }


        System.out.println("Probando el servicio 3:");
        int urgenciaMinima = 2;
        int urgenciaMaxima = 100;
        System.out.println("Paquetes con nivel de urgencia entre " + urgenciaMinima + " y " + urgenciaMaxima + ":");
        List<Paquete> paquetesPorUrgencia = servicios.servicio3(50, 70);
        for (Paquete paquete : paquetesPorUrgencia) {
            System.out.println(paquete);
        }


    }
}

