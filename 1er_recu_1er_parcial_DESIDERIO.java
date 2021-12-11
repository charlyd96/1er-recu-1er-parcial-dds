class BorradorOrden {
    
    ConvertidorMidi convertidorMidi; //por inyección de dependencias
    EnviadorDeMails enviadorDeMails; //por inyección de dependencias
        
    String melodia;                
    Cliente cliente;
    Estado estado = Estado.PENDIENTE;

    public BorradorOrden (ConvertidorMidi convertidorMidi, EnviadorDeMails enviadorDeMails) {
        this.enviadorDeMails = enviadorDeMails;
        this.convertidorMidi = convertidorMidi;
     }
    
    //Requerimiento 10
    public void subirCancion(byte [] cancion, Cliente cliente) {
        this.cliente = cliente;
        this.melodia = convertidorMidi.convert(cancion);

        //Requerimiento 12
        //Se asume que el convertidor devuelve null si falla
        //En un caso ideal podría devoler una excepción con información
        //más específica y se podría catchearla
        if (this.melodia == null) {
            this.enviadorDeMail.enviar(cliente.getAdress(), "Mala suerte", "Tu canción no puede procesarse")
        }
        else {
            this.enviadorDeMail.enviar(cliente.getAdress(), "Enhorabuena", "Tu canción se procesó correctamente")
        }

    }

    public String escucharMelodia() {
        return melodia; //retorna al navegador el audio
                        //este método no tiene syntax sugar
    }

    public Orden aprobar() {
        return new Orden(this.melodia, Estado.ACEPTADO, cliente.getDireccion());
    }

    public Orden rechazar() {
        this.enviadorDeMail.enviar(cliente.getAdress(), "Mala suerte", "Se rechazó tu orden");
        return new Orden(this.melodia, Estado.RECHAZADO, cliente.getDireccion())
    }

    public Estado getEstado() {
        return this.estado;
    }
}


class Orden {
    Melodia melodia;
    Estado estado;
    String direccion;
    LocalDate fechaCreacion;
    
    public Orden (Melodia melodia, Estado estado, String direccion) {
        this.melodia = melodia;
        this.estado = estado;
        this.fechaCreacion = LocalDate.Now();
    }

    //Requerimiento 3
    public Estado getEstado() {
        return this.estado;
    }

    //Requerimiento 4
    public int getTiempoRestante() {
        LocaDate diaEntrega = this.fechaCreacion + tipo.getTiempoFabricacion(melodia) 
        return diaEntrega - LocalDate.Now()
    }

    //Requerimiento 5
    public String getDireccionOrden() {
        return this.direccion;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    //Requerimiento 11
    public int getPrecio() {
        melodia.getPrecio();
    }
}



enum Estado {
    ACEPTADA, RECHAZADA
}


interface Melodia {


    abstract getPrecio();
}

class MelodiaCatalogo implements Melodia {
    String nombre;
    String contenido;
    int precioDefinido;

    @Override
    public int getPrecio() {
        return this.precio;
    }
}

class MelodiaPersonalizada implements Melodia {
    String nombre;
    String contenido;
    int precioDependiente;

    @Override
    public int getPrecio () {
        return getPrecioSegunContenido(this.contenido);
        //algoritmo desconocido para calcular el precio
    }
}

class Cliente {
    String nombre;
    String apellido;
    String adress; //email
    String direccion;
    //contructor con todos los datos
}

class RepoClientes {
    List<Cliente> clientes;

    //Requerimiento 1
    registrarCliente(string nombre, string apellido, string adress, string direccion) {
        clientes.add(new Cliente(nombre, apellido, adress, direccion))
    }
}

//Esto sería un repositorio
class Catalogo {
    List<MelodiasCatalogo> melodias;

    //Requerimiento 2
    getMelodias() {
        return this.melodias; 
    }
}

class RepoOrdenes {
    List<Orden> ordenes;
    List<BorradorOrden> borradorOrdenes
    //Requerimiento 2
    public Orden crearOrdenDeCatalogo(melodia, cliente) {
        ordenes.add(new Orden(melodia, Estado.ACEPTADA, cliente.getDireccion()));
        //Las de catálogo tienen estado ACEPTADA por defecto
    }

    //Requerimiento 8
    public List<Orden> getOrdenesPendientes() {
        return ordenes.filter(o -> o.getEstado() == Estado.PENDIENTE);
    }
}

class RepoFabricas {
    List <Fabrica> fabricas;

    //Requerimiento 6
    public void darDeAlta(Fabrica fabrica){
        fabricas.add(fabrica);
    }

    //Requerimiento 7
    public List<Fabrica> getFabricasAdecuadas(Orden orden) {
        return fabricas.flter(f -> f.validarConstruccion(orden));
    }


}

class Fabrica {

    List<Orden> ordenes;
    //Requerimiento 7
    public Boolean validarConstruccion(Orden orden) {
        return procesarOrden()
        //En el enunciado no se aclara cómoo se determina
        si una melodía es aceptada o no por una fábrica
        Sólo dice que se las contacta por teléfono para pasarles
        las órdenes y para actualizar datos
    } 

    //Requerimiento 8
    public void asignarFabrica(orden) {
        if (!this.validarConstruccion)
            throw new NoPuedeConstruirCajaMusicaException();

        ordenes.add(orden);
    }

    //Requerimiento 9
    public void getOrdenesPorFecha(LocalDate from, LocalDate to) {
        return ordenes.filter(o -> o.getFechaCreacion > from && o.getFechaCreacion < to);
    }
}

//-----------ADAPTACIÓN DE INTERFACES --------------
interface EnviadorDeMails {
    enviar(String adress, String subject, String body);
}

class Mailer implements EnviadorDeMails {
    MailSender mailSender = new MailSender();

    enviar(String adress, String subject, String body) {
        mailSender.enviar(adress. subject, body);
    }
}




//ver el tema de listar todas las ordenes