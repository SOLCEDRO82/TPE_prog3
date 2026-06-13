import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class Servicios {
    private List<Camion> camiones; //ver el private o protected...
    private List<Paquete> paquetes;

    /* Expresar la complejidad temporal del constructor*/
    public Servicios(String pathCamiones, String pathPaquetes) {//recibe las rutas a los archivos .csv{
        this.camiones = new ArrayList<>();
        this.paquetes = new ArrayList<>();
        this.cargarCamiones(pathCamiones);
        this.cargarPaquetes(pathPaquetes);
    }

    private void cargarCamiones(String pathCamiones) {
        try (BufferedReader lector = new BufferedReader(new FileReader(pathCamiones))) {
            lector.readLine(); // Saltamos la primera linea y el lector se posiciona a partir de la segunda linea
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datosCamion = linea.split(";");//Toma la línea (ej: "100;AAA;1;100") y la corta en un arreglo de textos usando el ; como divisor.

                Camion c = new Camion(
                        Integer.parseInt(datosCamion[0].trim()),//id_camion
                        datosCamion[1].trim(),//patente
                        datosCamion[2].trim().equals("1"),//esta_refrigerado
                        Integer.parseInt(datosCamion[3].trim())//capacidad_kg

                );
                camiones.add(c);
            }
        } catch (IOException |
                 NumberFormatException e) { //Ocurre si el archivo no existe, está bloqueado, o falla al leerlo
            System.err.println("Error procesando camiones: " + e.getMessage());
        }
    }

    private void cargarPaquetes(String pathPaquetes) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathPaquetes))) {
            br.readLine(); // Saltamos la primera linea
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosPaquete = linea.split(";");
                Paquete p = new Paquete(
                        Integer.parseInt(datosPaquete[0].trim()),
                        datosPaquete[1].trim(),
                        Integer.parseInt(datosPaquete[2].trim()),
                        datosPaquete[3].trim().equals("1"),
                        Integer.parseInt(datosPaquete[4].trim())
                );
                paquetes.add(p);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error procesando paquetes: " + e.getMessage());
        }
    }

    public List<Camion> getCamiones() {
        return camiones;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    /*Dado un còdigo de paquete (String), retornar toda la informaciòn del paquete asociado.
    En caso de no existir, retornar null.
    Expresar la complejidad temporal del servicio 1 */

    public Paquete servicio1(String codigoPaquete) {
        for (Paquete paquete : paquetes) {
            if (paquete.getCodigo_paquete().equals(codigoPaquete)) {
                return paquete;
            }
        }
        return null;
    }

}
