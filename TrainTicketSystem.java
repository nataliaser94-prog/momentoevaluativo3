package ejemploproyectofinal;

import java.util.Scanner;

public class TrainTicketSystem {

    // Crear las variables para las entidades
    /* Ferrocarriles de Antioquia requiere un sistema que permita asignar
     * un ticket en un tren a un usuario a través de una venta.
     * Del Usuario requiere: id, nombre , apellido, origen , destino.
     * Del cajero requiere conocer los mismos datos (id, nombre, apellido) y adicional
     * el rol y la contraseña.
     * Del tren requiere conocer: # de máquina , módelo, capacidad.
     * Del Ticket requiere conocer: Id del ticket , usuario, tren , asiento , origen , destino, precio.
     * De la venta requiere conocer: id de la venta , id del ticket, precio, cantidad , total
     * El sistema debe permitir registrar, actualizar , consultar por id , consultar todos y eliminar, para cada
     * entidad del sistema (Usuarios, trenes, tickets , ventas)
     * El sistema debe permitir navegar las opciones a través de menús
     * El sistema debe permitir registrar un cajero e iniciar sesión
     * El sistema debe permitir al cajero contar con un dashboard con los menus a las diferentes opciones.
     * después de iniciar sesión.
     * La compañia cuenta con tres trenes cada uno con capacidad de 4 pasajeros.
     * Cada tren hace un viaje de ida y vuelta, en un día , a tres destinos: Santa Fe , Cisneros y Puerto Berrio */

    // Scanner para capturar los datos
    static Scanner sc = new Scanner(System.in);

    // Usuario tren
    static int id;
    static String nombre;
    static String apellido;
    static String origen;
    static String destino;

    // Usuario cajero
    static String rol;
    static String password;

    // Tren
    static int trenId;
    static String modelo;
    static int capacidad;

    // Ticket
    static int ticketId;
    static int usuario;
    static String origenRuta;
    static String destinoRuta;
    static String silla;
    static int tren;
    static String fecha;
    static String hora;
    static double precio;

    // Venta
    static int ventaId;
    static int ticketIdVenta;
    static int cantidad;
    static double precioVentaUnitario;
    static double totalVenta;

    // Arreglos y matrices

    static String[][] usuarios = new String[4][5];
    static String[][] cajeros = new String[10][5];
    static String[][] trenes = new String[3][3];
    static String[][] tickets = new String[20][9];
    static String[][] ventas = new String[20][5];

    // Encabezados de cada matriz. La primera columna siempre guarda el ID.
    static String[] encabezados = {"Id", "Nombre", "Apellido", "Origen", "Destino"};
    static String[] encabezadosCajeros = {"Id", "Nombre", "Apellido", "Rol", "Contraseña"};
    static String[] encabezadosTrenes = {"Id máquina", "Modelo", "Capacidad"};
    static String[] encabezadosTickets = {"Id ticket", "Id usuario", "Id tren", "Asiento", "Origen", "Destino", "Fecha", "Hora", "Precio"};
    static String[] encabezadosVentas = {"Id venta", "Id ticket", "Precio unitario", "Cantidad", "Total"};

    public static void main(String[] args) {
        menuInicio();
    }

    // ------------------------- MENÚ PRINCIPAL Y CAJEROS -------------------------

    public static void menuInicio() {
        int opcion;
        do {
            System.out.println("\n===== FERROCARRILES DE ANTIOQUIA =====");
            System.out.println("1. Registrar cajero");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarCajero();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    System.out.println("Gracias por utilizar el sistema.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }

    public static void registrarCajero() {
        int fila = filaDisponible(cajeros);
        if (fila == -1) {
            System.out.println("No hay espacio para registrar más cajeros.");
            return;
        }

        id = leerEntero("Ingrese el ID del cajero: ");
        if (buscarPorId(cajeros, String.valueOf(id)) != -1) {
            System.out.println("Ya existe un cajero con ese ID.");
            return;
        }

        nombre = leerTexto("Ingrese el nombre: ");
        apellido = leerTexto("Ingrese el apellido: ");
        rol = leerTexto("Ingrese el rol: ");
        password = leerTexto("Cree una contraseña: ");

        cajeros[fila][0] = String.valueOf(id);
        cajeros[fila][1] = nombre;
        cajeros[fila][2] = apellido;
        cajeros[fila][3] = rol;
        cajeros[fila][4] = password;

        System.out.println("Cajero registrado correctamente.");
    }

    public static void iniciarSesion() {
        String idIngresado = leerTexto("Ingrese su ID: ");
        password = leerTexto("Ingrese su contraseña: ");
        int fila = buscarPorId(cajeros, idIngresado);

        if (fila != -1 && cajeros[fila][4].equals(password)) {
            System.out.println("\nBienvenido/a, " + cajeros[fila][1] + " " + cajeros[fila][2]);
            menuApp();
        } else {
            System.out.println("ID o contraseña incorrectos.");
        }
    }

    // Dashboard que aparece después de iniciar sesión.
    public static void menuApp() {
        int opcion;
        do {
            System.out.println("\n========== DASHBOARD ==========");
            System.out.println("1. Gestión de usuarios");
            System.out.println("2. Gestión de trenes");
            System.out.println("3. Gestión de tickets");
            System.out.println("4. Gestión de ventas");
            System.out.println("5. Cerrar sesión");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    menuUsuarios();
                    break;
                case 2:
                    menuTrenes();
                    break;
                case 3:
                    menuTickets();
                    break;
                case 4:
                    menuVentas();
                    break;
                case 5:
                    System.out.println("Sesión cerrada.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    // ------------------------- MENÚ Y MÉTODOS DE USUARIO -------------------------

    public static void menuUsuarios() {
        int opcion;
        do {
            System.out.println("\n----- MENÚ USUARIOS -----");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Actualizar usuario");
            System.out.println("3. Consultar usuario por ID");
            System.out.println("4. Consultar todos los usuarios");
            System.out.println("5. Eliminar usuario");
            System.out.println("6. Volver al dashboard");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: crearUsuario(); break;
                case 2: actualizarDatos(); break;
                case 3: consultarUsuarioPorId(); break;
                case 4: consultarUsuarios(); break;
                case 5: eliminarUsuario(); break;
                case 6: break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }

    public static void crearUsuario() {
        int fila = filaDisponible(usuarios);
        if (fila == -1) {
            System.out.println("La matriz de usuarios está llena.");
            return;
        }

        id = leerEntero("Ingrese el ID del usuario: ");
        if (buscarPorId(usuarios, String.valueOf(id)) != -1) {
            System.out.println("Ya existe un usuario con ese ID.");
            return;
        }

        nombre = leerTexto("Ingrese el nombre: ");
        apellido = leerTexto("Ingrese el apellido: ");
        origen = leerTexto("Ingrese el origen: ");
        destino = leerTexto("Ingrese el destino: ");

        usuarios[fila][0] = String.valueOf(id);
        usuarios[fila][1] = nombre;
        usuarios[fila][2] = apellido;
        usuarios[fila][3] = origen;
        usuarios[fila][4] = destino;
        System.out.println("Usuario registrado correctamente.");
    }

    public static void consultarUsuarios() {
        consultarTodos(usuarios, encabezados, "usuarios");
    }

    public static void consultarUsuarioPorId() {
        consultarPorId(usuarios, encabezados, "usuario");
    }

    public static void actualizarDatos() {
        id = leerEntero("Ingrese el ID del usuario que desea actualizar: ");
        int fila = buscarPorId(usuarios, String.valueOf(id));
        if (fila == -1) {
            System.out.println("No se encontró un usuario con ese ID.");
            return;
        }

        System.out.println("1. Nombre  2. Apellido  3. Origen  4. Destino");
        int opcion = leerEntero("Seleccione el dato que desea actualizar: ");
        switch (opcion) {
            case 1: usuarios[fila][1] = leerTexto("Nuevo nombre: "); break;
            case 2: usuarios[fila][2] = leerTexto("Nuevo apellido: "); break;
            case 3: usuarios[fila][3] = leerTexto("Nuevo origen: "); break;
            case 4: usuarios[fila][4] = leerTexto("Nuevo destino: "); break;
            default:
                System.out.println("Opción no válida.");
                return;
        }
        System.out.println("Usuario actualizado correctamente.");
    }

    public static void eliminarUsuario() {
        eliminarPorId(usuarios, "usuario");
    }

    // ------------------------- MENÚ Y MÉTODOS DE TREN -------------------------

    public static void menuTrenes() {
        int opcion;
        do {
            System.out.println("\n----- MENÚ TRENES -----");
            System.out.println("1. Registrar tren");
            System.out.println("2. Actualizar tren");
            System.out.println("3. Consultar tren por ID");
            System.out.println("4. Consultar todos los trenes");
            System.out.println("5. Eliminar tren");
            System.out.println("6. Volver al dashboard");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: crearTren(); break;
                case 2: actualizarTren(); break;
                case 3: consultarPorId(trenes, encabezadosTrenes, "tren"); break;
                case 4: consultarTodos(trenes, encabezadosTrenes, "trenes"); break;
                case 5: eliminarPorId(trenes, "tren"); break;
                case 6: break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }

    public static void crearTren() {
        int fila = filaDisponible(trenes);
        if (fila == -1) {
            System.out.println("La compañía ya tiene registrados sus tres trenes.");
            return;
        }

        trenId = leerEntero("Ingrese el número de máquina (ID): ");
        if (buscarPorId(trenes, String.valueOf(trenId)) != -1) {
            System.out.println("Ya existe un tren con ese ID.");
            return;
        }
        modelo = leerTexto("Ingrese el modelo del tren: ");
        capacidad = 4; // Cada tren tiene capacidad fija para cuatro pasajeros.

        trenes[fila][0] = String.valueOf(trenId);
        trenes[fila][1] = modelo;
        trenes[fila][2] = String.valueOf(capacidad);
        System.out.println("Tren registrado. Capacidad asignada: 4 pasajeros.");
    }

    public static void actualizarTren() {
        trenId = leerEntero("Ingrese el ID del tren que desea actualizar: ");
        int fila = buscarPorId(trenes, String.valueOf(trenId));
        if (fila == -1) {
            System.out.println("No se encontró ese tren.");
            return;
        }
        trenes[fila][1] = leerTexto("Ingrese el nuevo modelo: ");
        // La capacidad no se modifica porque el enunciado establece cuatro pasajeros por tren.
        System.out.println("Tren actualizado correctamente.");
    }

    // ------------------------- MENÚ Y MÉTODOS DE TICKET -------------------------

    public static void menuTickets() {
        int opcion;
        do {
            System.out.println("\n----- MENÚ TICKETS -----");
            System.out.println("1. Registrar ticket");
            System.out.println("2. Actualizar ticket");
            System.out.println("3. Consultar ticket por ID");
            System.out.println("4. Consultar todos los tickets");
            System.out.println("5. Eliminar ticket");
            System.out.println("6. Volver al dashboard");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: crearTicket(); break;
                case 2: actualizarTicket(); break;
                case 3: consultarPorId(tickets, encabezadosTickets, "ticket"); break;
                case 4: consultarTodos(tickets, encabezadosTickets, "tickets"); break;
                case 5: eliminarPorId(tickets, "ticket"); break;
                case 6: break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }

    public static void crearTicket() {
        int fila = filaDisponible(tickets);
        if (fila == -1) {
            System.out.println("No hay espacio para más tickets.");
            return;
        }

        ticketId = leerEntero("Ingrese el ID del ticket: ");
        if (buscarPorId(tickets, String.valueOf(ticketId)) != -1) {
            System.out.println("Ya existe un ticket con ese ID.");
            return;
        }

        usuario = leerEntero("Ingrese el ID del usuario: ");
        if (buscarPorId(usuarios, String.valueOf(usuario)) == -1) {
            System.out.println("Primero debe registrar ese usuario.");
            return;
        }

        tren = leerEntero("Ingrese el ID del tren: ");
        if (buscarPorId(trenes, String.valueOf(tren)) == -1) {
            System.out.println("Primero debe registrar ese tren.");
            return;
        }

        System.out.println("Asientos disponibles por tren: del 1 al 4.");
        int numeroAsiento = leerEntero("Ingrese el número de asiento: ");
        if (numeroAsiento < 1 || numeroAsiento > 4) {
            System.out.println("El asiento debe estar entre 1 y 4.");
            return;
        }
        silla = String.valueOf(numeroAsiento);
        fecha = leerTexto("Ingrese la fecha del viaje (dd/mm/aaaa): ");
        hora = leerTexto("Ingrese la hora del viaje: ");

        if (asientoOcupado(String.valueOf(tren), fecha, hora, silla, -1)) {
            System.out.println("Ese asiento ya está ocupado para ese tren, fecha y hora.");
            return;
        }

        mostrarDestinos();
        origenRuta = leerTexto("Ingrese el origen: ");
        destinoRuta = leerTexto("Ingrese el destino: ");
        precio = leerDouble("Ingrese el precio del ticket: ");

        tickets[fila][0] = String.valueOf(ticketId);
        tickets[fila][1] = String.valueOf(usuario);
        tickets[fila][2] = String.valueOf(tren);
        tickets[fila][3] = silla;
        tickets[fila][4] = origenRuta;
        tickets[fila][5] = destinoRuta;
        tickets[fila][6] = fecha;
        tickets[fila][7] = hora;
        tickets[fila][8] = String.valueOf(precio);
        System.out.println("Ticket registrado correctamente.");
    }

    public static void actualizarTicket() {
        ticketId = leerEntero("Ingrese el ID del ticket que desea actualizar: ");
        int fila = buscarPorId(tickets, String.valueOf(ticketId));
        if (fila == -1) {
            System.out.println("No se encontró ese ticket.");
            return;
        }

        System.out.println("1. Asiento  2. Origen  3. Destino  4. Fecha  5. Hora  6. Precio");
        int opcion = leerEntero("Seleccione el dato que desea actualizar: ");
        switch (opcion) {
            case 1:
                int nuevoAsiento = leerEntero("Ingrese el nuevo asiento (1 a 4): ");
                if (nuevoAsiento < 1 || nuevoAsiento > 4) {
                    System.out.println("El asiento debe estar entre 1 y 4.");
                    return;
                }
                if (asientoOcupado(tickets[fila][2], tickets[fila][6], tickets[fila][7], String.valueOf(nuevoAsiento), fila)) {
                    System.out.println("Ese asiento ya está ocupado para ese viaje.");
                    return;
                }
                tickets[fila][3] = String.valueOf(nuevoAsiento);
                break;
            case 2: tickets[fila][4] = leerTexto("Nuevo origen: "); break;
            case 3: tickets[fila][5] = leerTexto("Nuevo destino: "); break;
            case 4: tickets[fila][6] = leerTexto("Nueva fecha: "); break;
            case 5: tickets[fila][7] = leerTexto("Nueva hora: "); break;
            case 6: tickets[fila][8] = String.valueOf(leerDouble("Nuevo precio: ")); break;
            default:
                System.out.println("Opción no válida.");
                return;
        }
        System.out.println("Ticket actualizado correctamente.");
    }

    public static void mostrarDestinos() {
        System.out.println("Destinos de la compañía: Santa Fe, Cisneros y Puerto Berrío.");
        System.out.println("Para registrar el regreso, escriba el origen y el destino en sentido contrario.");
    }

    // Comprueba que no se asigne dos veces el mismo asiento al mismo tren, fecha y hora.
    public static boolean asientoOcupado(String idTren, String fechaViaje, String horaViaje, String asiento, int filaExcluir) {
        for (int i = 0; i < tickets.length; i++) {
            if (i != filaExcluir && tickets[i][0] != null
                    && tickets[i][2].equals(idTren)
                    && tickets[i][3].equals(asiento)
                    && tickets[i][6].equalsIgnoreCase(fechaViaje)
                    && tickets[i][7].equalsIgnoreCase(horaViaje)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------- MENÚ Y MÉTODOS DE VENTA -------------------------

    public static void menuVentas() {
        int opcion;
        do {
            System.out.println("\n----- MENÚ VENTAS -----");
            System.out.println("1. Registrar venta");
            System.out.println("2. Actualizar venta");
            System.out.println("3. Consultar venta por ID");
            System.out.println("4. Consultar todas las ventas");
            System.out.println("5. Eliminar venta");
            System.out.println("6. Volver al dashboard");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: registrarVenta(); break;
                case 2: actualizarVenta(); break;
                case 3: consultarPorId(ventas, encabezadosVentas, "venta"); break;
                case 4: consultarTodos(ventas, encabezadosVentas, "ventas"); break;
                case 5: eliminarPorId(ventas, "venta"); break;
                case 6: break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }

    public static void registrarVenta() {
        int fila = filaDisponible(ventas);
        if (fila == -1) {
            System.out.println("No hay espacio para más ventas.");
            return;
        }

        ventaId = leerEntero("Ingrese el ID de la venta: ");
        if (buscarPorId(ventas, String.valueOf(ventaId)) != -1) {
            System.out.println("Ya existe una venta con ese ID.");
            return;
        }

        ticketIdVenta = leerEntero("Ingrese el ID del ticket que se va a vender: ");
        int filaTicket = buscarPorId(tickets, String.valueOf(ticketIdVenta));
        if (filaTicket == -1) {
            System.out.println("No existe ese ticket. Registre primero el ticket.");
            return;
        }
        if (ticketVendido(String.valueOf(ticketIdVenta))) {
            System.out.println("Ese ticket ya tiene una venta registrada.");
            return;
        }

        precioVentaUnitario = Double.parseDouble(tickets[filaTicket][8]);
        cantidad = leerEntero("Ingrese la cantidad: ");
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }
        totalVenta = precioVentaUnitario * cantidad;

        ventas[fila][0] = String.valueOf(ventaId);
        ventas[fila][1] = String.valueOf(ticketIdVenta);
        ventas[fila][2] = String.valueOf(precioVentaUnitario);
        ventas[fila][3] = String.valueOf(cantidad);
        ventas[fila][4] = String.valueOf(totalVenta);

        System.out.printf("Venta registrada. Total: $%.2f%n", totalVenta);
    }

    public static void actualizarVenta() {
        ventaId = leerEntero("Ingrese el ID de la venta que desea actualizar: ");
        int fila = buscarPorId(ventas, String.valueOf(ventaId));
        if (fila == -1) {
            System.out.println("No se encontró esa venta.");
            return;
        }

        System.out.println("1. Cantidad  2. Precio unitario");
        int opcion = leerEntero("Seleccione el dato que desea actualizar: ");
        if (opcion == 1) {
            cantidad = leerEntero("Ingrese la nueva cantidad: ");
            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor que cero.");
                return;
            }
            ventas[fila][3] = String.valueOf(cantidad);
        } else if (opcion == 2) {
            precioVentaUnitario = leerDouble("Ingrese el nuevo precio unitario: ");
            ventas[fila][2] = String.valueOf(precioVentaUnitario);
        } else {
            System.out.println("Opción no válida.");
            return;
        }

        precioVentaUnitario = Double.parseDouble(ventas[fila][2]);
        cantidad = Integer.parseInt(ventas[fila][3]);
        totalVenta = precioVentaUnitario * cantidad;
        ventas[fila][4] = String.valueOf(totalVenta);
        System.out.printf("Venta actualizada. Nuevo total: $%.2f%n", totalVenta);
    }

    public static boolean ticketVendido(String idTicket) {
        for (int i = 0; i < ventas.length; i++) {
            if (ventas[i][0] != null && ventas[i][1].equals(idTicket)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------- MÉTODOS GENERALES PARA LAS MATRICES -------------------------

    // Busca un registro comparando el ID de la primera columna. Retorna su fila o -1 si no existe.
    public static int buscarPorId(String[][] datos, String idBuscado) {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i][0] != null && datos[i][0].equals(idBuscado)) {
                return i;
            }
        }
        return -1;
    }

    // Encuentra la primera fila vacía de una matriz.
    public static int filaDisponible(String[][] datos) {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i][0] == null) {
                return i;
            }
        }
        return -1;
    }

    // Muestra todos los registros de una matriz con sus encabezados.
    public static void consultarTodos(String[][] datos, String[] titulos, String entidad) {
        boolean hayRegistros = false;
        System.out.println("\n===== LISTADO DE " + entidad.toUpperCase() + " =====");
        for (int i = 0; i < datos.length; i++) {
            if (datos[i][0] != null) {
                hayRegistros = true;
                mostrarRegistro(datos[i], titulos);
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay registros para mostrar.");
        }
    }

    // Muestra un solo registro.
    public static void mostrarRegistro(String[] registro, String[] titulos) {
        for (int j = 0; j < titulos.length; j++) {
            System.out.println(titulos[j] + ": " + registro[j]);
        }
        System.out.println("------------------------------");
    }

    // Consulta un registro por su ID.
    public static void consultarPorId(String[][] datos, String[] titulos, String entidad) {
        String idBuscado = leerTexto("Ingrese el ID del " + entidad + ": ");
        int fila = buscarPorId(datos, idBuscado);
        if (fila == -1) {
            System.out.println("No se encontró " + entidad + " con ese ID.");
        } else {
            mostrarRegistro(datos[fila], titulos);
        }
    }

    // Elimina un registro y mueve las filas siguientes para no dejar espacios intermedios.
    public static void eliminarPorId(String[][] datos, String entidad) {
        String idBuscado = leerTexto("Ingrese el ID del " + entidad + " que desea eliminar: ");
        int fila = buscarPorId(datos, idBuscado);
        if (fila == -1) {
            System.out.println("No se encontró usuario " + entidad + " con ese ID.");
            return;
        }
        for (int i = fila; i < datos.length - 1; i++) {
            datos[i] = datos[i + 1];
        }
        datos[datos.length - 1] = new String[datos[0].length];
        System.out.println(entidad + " eliminado correctamente.");
    }

    // Lee texto usando nextLine para evitar problemas al mezclar números y palabras.
    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    // Lee un número entero y vuelve a pedirlo si el dato no es válido.
    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero.");
            }
        }
    }

    // Lee un número decimal. También acepta coma o punto como separador decimal.
    public static double leerDouble(String mensaje) {
        while (true) {
            try {
                String dato = leerTexto(mensaje).replace(',', '.');
                return Double.parseDouble(dato);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor numérico, por ejemplo 25000 o 25000.50.");
            }
        }
    }
}
