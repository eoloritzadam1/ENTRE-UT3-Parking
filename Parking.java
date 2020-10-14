/**
 * La clase representa a un parking de una ciudad europea
 * que dispone de dos tarifas de aparcamiento para los clientes
 * que lo usen: la tarifa regular (que incluye una tarifa plana para
 * entradas "tempranas") y la tarifa comercial para clientes que trabajan
 * cerca del parking, aparcan un nº elevado de horas y se benefician de esta 
 * tarifa más económica
 * (leer enunciado)
 * 
 * @author - Elorri Oloritz
 * @version - 11-10-2020
 */
public class Parking
{
    private final char REGULAR = 'R';
    private final char COMERCIAL = 'C';
    private final double PRECIO_BASE_REGULAR = 2.0;
    private final double PRECIO_MEDIA_REGULAR_HASTA11 = 3.0;
    private final double PRECIO_MEDIA_REGULAR_DESPUES11= 5.0;
    private final int HORA_INICIO_ENTRADA_TEMPRANA = 6 * 60;
    private final int HORA_FIN_ENTRADA_TEMPRANA = 8 * 60 + 30;
    private final int HORA_INICIO_SALIDA_TEMPRANA = 15 * 60;
    private final int HORA_FIN_SALIDA_TEMPRANA = 18 * 60;
    private final double PRECIO_TARIFA_PLANA_REGULAR = 15.0;
    private final double PRECIO_PRIMERAS3_COMERCIAL = 5.00;
    private final double PRECIO_MEDIA_COMERCIAL = 3.00;
    private String nombre;
    private int cliente;
    private double importeTotal;
    private int regular;
    private int comercial;
    private int clientesLunes;
    private int clientesSabado;
    private int clientesDomingo;
    private int clientesMaximoComercial;
    private double importeMaximoComercial;

    /**
     * Inicializa el parking con el nombre indicada por el parámetro.
     * El resto de atributos se inicializan a 0 
     */
    public Parking(String queNombre) {
        nombre = queNombre;
        cliente = 0;
        importeTotal = 0;
        regular = 0;
        comercial = 0;
        clientesLunes = 0;
        clientesSabado = 0;
        clientesDomingo = 0;
        clientesMaximoComercial = 0;
        importeMaximoComercial = 0;
    }

    /**
     * accesor para el nombre del parking
     *  
     */
    public String getNombre() {
        return nombre;

    }

    /**
     * mutador para el nombre del parking
     *  
     */
    public void setNombre(String newNombre) {
        nombre = newNombre;

    }

    /**
     *  Recibe cuatro parámetros que supondremos correctos:
     *    tipoTarifa - un carácter 'R' o 'C'
     *    entrada - hora de entrada al parking
     *    salida – hora de salida del parking
     *    dia – nº de día de la semana (un valor entre 1 y 7)
     *    
     *    A partir de estos parámetros el método debe calcular el importe
     *    a pagar por el cliente y mostrarlo en pantalla 
     *    y  actualizará adecuadamente el resto de atributos
     *    del parking para poder mostrar posteriormente (en otro método) las estadísticas
     *   
     *    Por simplicidad consideraremos que un cliente entra y sale en un mismo día
     *    
     *    (leer enunciado del ejercicio)
     */
    public void facturarCliente(char tipoTarifa, int entrada, int salida, int dia) {
        int horaEntrada = entrada / 100;
        int minutosEntrada = entrada % 100;
        int horaSalida = salida / 100;
        int minutosSalida = salida % 100;
        cliente++;
        // Pasar horas a minutos
        int EntAminutos = horaEntrada * 60 + minutosEntrada;
        int SalAminutos = horaSalida * 60 + minutosSalida;
        // Añade un 0 si el número es menor que 0
        String hE;
        String hS;
        String mE;
        String mS;
        // Tarifa e importe
        String queTarifa = "";
        double queImporte = 0;
        if (horaEntrada < 10){
            hE = "0" + horaEntrada + ":";
        }
        else {
            hE = "" + horaEntrada + ":";
        }

        if (horaSalida < 10){
            hS = "0" + horaSalida + ":";
        }
        else {
            hS = "" + horaSalida + ":";
        }

        if (minutosEntrada < 10){
            mE = "0" + minutosEntrada;
        }
        else {
            mE = "" + minutosEntrada;
        }

        if (minutosSalida < 10){
            mS = "0" + minutosSalida;
        }
        else {
            mS = "" + minutosSalida;
        }

        switch (tipoTarifa){
            case 'R': if (EntAminutos >= HORA_INICIO_ENTRADA_TEMPRANA &&
            EntAminutos <= HORA_FIN_ENTRADA_TEMPRANA &&
            SalAminutos >= HORA_INICIO_SALIDA_TEMPRANA &&
            SalAminutos <= HORA_FIN_SALIDA_TEMPRANA){
                regular++;
                queTarifa = "REGULAR y TEMPRANA";
                queImporte = PRECIO_TARIFA_PLANA_REGULAR;
            }
            else{ 
                if (entrada < 1100){
                    if (salida > 1100){
                        queImporte = PRECIO_BASE_REGULAR + (660 - EntAminutos) /
                        30 * PRECIO_MEDIA_REGULAR_HASTA11 + (SalAminutos - 660) /
                        30 * PRECIO_MEDIA_REGULAR_DESPUES11;
                    }
                    else{
                        queImporte = PRECIO_BASE_REGULAR + (SalAminutos - EntAminutos) /
                        30 * PRECIO_MEDIA_REGULAR_HASTA11;
                    }
                }
                else{
                    queImporte = PRECIO_BASE_REGULAR + (SalAminutos - EntAminutos) /
                    30 * PRECIO_MEDIA_REGULAR_DESPUES11;
                }
                regular++;
                queTarifa = "REGULAR";
            }
            break;

            case 'C': if ((SalAminutos - EntAminutos) > 180) {
                queImporte = PRECIO_PRIMERAS3_COMERCIAL + (SalAminutos - EntAminutos -
                    180) / 30 * PRECIO_MEDIA_COMERCIAL;
            }
            if (queImporte > importeMaximoComercial){
                clientesMaximoComercial = cliente;
                importeMaximoComercial = queImporte;
            }
            comercial++;
            queTarifa = "COMERCIAL";
            break;
        }
        switch (dia) {
            case 1:
            clientesLunes++;
            break;
            case 6:
            clientesSabado++;
            break;
            case 7:
            clientesDomingo++;
            break;
        }
        importeTotal += queImporte;

        System.out.println("**************************");
        System.out.println("Cliente nº: " + cliente);
        System.out.println("Hora entrada: " + hE + mE);
        System.out.println("Hora salida: " + hS + mS);
        System.out.println("Tarifa a aplicar: " + queTarifa);
        System.out.println("Importe a pagar: " + queImporte + "€");
        System.out.println("**************************");
    }

    /**
     * Muestra en pantalla las estadísticcas sobre el parking  
     *   
     * (leer enunciado)
     *  
     */
    public void printEstadísticas() {
        System.out.println("**********************************************************");
        System.out.println("Importe total entre todos los clientes: " + importeTotal + "€");
        System.out.println("Nº clientes tarifa regular: " + regular);
        System.out.println("Nº clientes tarifa comercial: " + comercial);
        System.out.println("Cliente tarifa COMERCIAL con factura máxima fue el nº " +
        clientesMaximoComercial);
        System.out.println("y pagó: " + importeMaximoComercial + "€");
        System.out.println("**********************************************************\n");

    }

    /**
     *  Calcula y devuelve un String que representa el nombre del día
     *  en el que más clientes han utilizado el parking - "SÁBADO"   "DOMINGO" o  "LUNES"
     */
    public String diaMayorNumeroClientes() {
        if (clientesSabado > clientesDomingo && clientesSabado > clientesLunes){
            return "SABADO";
        }
        else if (clientesDomingo > clientesLunes){
            return "DOMINGO";
        }
        else{
            return "LUNES";
        }
    }
}
