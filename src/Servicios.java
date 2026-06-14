import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class Servicios {
    private List<Camion> camiones; //ver el private o protected...
    private List<Paquete> paquetes;
    private Map<String, Paquete> mapaCodigoPaquete;
    private List<Paquete> paquetesConAlimentos;
    private List<Paquete> paquetesSinAlimentos;
    private List<Paquete>[] paquetesPorUrgencia;

    /* Expresar la complejidad temporal del constructor
     * Complejidad temporal:
     * Inicialización de estructuras: O(1).
     * Cargar camiones desde archivo: O(n), donde n es la cantidad de camiones.
     * Cargar paquetes desde archivo: O(m), donde m es la cantidad de paquetes.
     * Complejidad total: O(n + m).
     */
    public Servicios(String pathCamiones, String pathPaquetes) {//recibe las rutas a los archivos .csv{
        this.camiones = new ArrayList<>();
        this.paquetes = new ArrayList<>();

        this.mapaCodigoPaquete = new HashMap<>();

        this.paquetesConAlimentos = new ArrayList<>();
        this.paquetesSinAlimentos = new ArrayList<>();

        this.paquetesPorUrgencia= new ArrayList[101];
        for (int i = 0; i <= 100; i++) {
            paquetesPorUrgencia[i] = new ArrayList<>();
        }

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
                paquetes.add(p); //los agregamos a un ArrayList gral necesario?
                mapaCodigoPaquete.put(p.getCodigo_paquete(), p); //los agregamos a un HashMap para la busqueda eficiente
                if (p.contieneAlimentos()) { // Clasificacamos por alimentos
                    paquetesConAlimentos.add(p);
                } else {
                    paquetesSinAlimentos.add(p);
                }
                int urgencia = p.getNivel_urgencia();
                if (urgencia >= 1 && urgencia <= 100) {
                    paquetesPorUrgencia[urgencia].add(p);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error procesando paquetes: " + e.getMessage());
        }
    }

    /*
    Expresar la complejidad temporal del servicio 1
    *Complejidad temporal:
    *La complejidad temporal del servicio 1 es O(1) en promedio
    *Generalemente una estructura de hashing se supone que tiene tan pocas listas vinculadas
    que el get es màs probable que resuelva accediendo directamente a la estructura primaria
    que a la lista de rebalse.
    */

    public Paquete servicio1(String codigoPaquete) {
        /*if (mapaCodigoPaquete.containsKey(codigoPaquete)) {
            return mapaCodigoPaquete.get(codigoPaquete);
        }else{
            return null;
        }
        existe un metodo propio de la clase Map...*/
        return mapaCodigoPaquete.getOrDefault(codigoPaquete, null);
    }

    /*
     * Expresar la complejidad temporal del servicio 2.
     * Complejidad temporal:
     * La complejidad temporal del servicio  es O(1).
     * Separamos en listas al momento de la cargar para luego tener el acceso directo.
     * Se evalua una condicion y se retorna la referencia de la lista que corresponde.
     */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        if (contieneAlimentos) {
            return paquetesConAlimentos;//List.copyOf(paquetesConAlimentos); para devolver una copia inmutable de las listas
            // return new ArrayList<>(paquetesConAlimentos);
            //tengo que hacer una copia de la lista para
            // que no se modifique la lista original?ENCAPSULAMIENTO??
        } else {
            return paquetesSinAlimentos;
        }
    }

    /*
     Expresar la complejidad temporal del servicio 3.
     *Complejidad temporal:
     *Acceder a las posiciones para cada nivel_urgencia dentro del rango O(1).
     * Utilizamos un arrayList ordenado en la carga por nivel de urgencia, esto hace que
     * a la hora de buscar por un rango se pueda acceder directamente al indice que corresponde del rango que se solicita.
     * Iterar spbre el rango (r) donde r es la cantidad de nivel_urgencia dentro del rango.
     * O(r) + O(p) donde p es el numero de paquetes que se selecciona y se copia a la lista de retorno.
   */

    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        List<Paquete> paquetesRetorno = new ArrayList<>();

        for (int i = urgenciaMinima; i <= urgenciaMaxima; i++) { //se itera sobre el rango solicitado
            paquetesRetorno.addAll(paquetesPorUrgencia[i]);
        }

        return paquetesRetorno;
    }

}


