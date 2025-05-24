package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.Enums.NivelPrioridad;

public class Solicitud {
    // Atributos
    private String id;
    private Usuario propietario;
    private NivelPrioridad nivelPrioridad;
    private String titulo;
    private String mensaje;
    private String respuesta;

    // Constructor
    public Solicitud(String id, Usuario propietario, NivelPrioridad nivelPrioridad, String titulo, String mensaje) {
        this.id = id;
        this.propietario = propietario;
        this.nivelPrioridad = nivelPrioridad;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.respuesta = respuesta;
    }
    
    // Constructor privado para el Builder
    private Solicitud(Builder builder) {
        this.id = builder.id;
        this.propietario = builder.propietario;
        this.nivelPrioridad = builder.nivelPrioridad;
        this.titulo = builder.titulo;
        this.mensaje = builder.mensaje;
        this.respuesta = builder.respuesta;
    }

    // Clase Builder
    public static class Builder {
        private String id;
        private Usuario propietario;
        private NivelPrioridad nivelPrioridad;
        private String titulo;
        private String mensaje;
        private String respuesta;
        public Builder id(String id) {
            this.id = id;
            return this;
        }
        
        public Builder propietario(Usuario propietario) {
            this.propietario = propietario;
            return this;
        }
        
        public Builder nivelPrioridad(NivelPrioridad nivelPrioridad) {
            this.nivelPrioridad = nivelPrioridad;
            return this;
        }
        
        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }
        
        public Builder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }
        
        public Builder respuesta(String respuesta) {
            this.respuesta = respuesta;
            return this;
        }
        
        public Solicitud build() {
            return new Solicitud(this);
        }
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public NivelPrioridad getNivelPrioridad() {
        return nivelPrioridad;
    }

    public void setNivelPrioridad(NivelPrioridad nivelPrioridad) {
        this.nivelPrioridad = nivelPrioridad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    

    @Override
    public String toString() {
        return "Solicitud{" +
               "id='" + id + '\'' +
               ", propietario=" + propietario.getNombre() +
               ", nivelPrioridad=" + nivelPrioridad +
               ", titulo='" + titulo + '\'' +
               ", mensaje='" + mensaje + '\'' +
               '}';
    }
} 