package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Usuario {
    // Listas-Consenso
    private List<String> historiasConsenso;
    private List<String> consensios;
    private List<String> grupos;
    private List<String> mensajes;
    private List<String> practicas;

    // Colabrencias-Gelicituativas
    private String estichayuda;

    // Constructor
    public Estudiante(String id, String nombre, String correo, String telefono, String contraseña, TipoUsuario tipoUsuario,
                      List<String> historiasConsenso, List<String> consensios, List<String> grupos,
                      List<String> mensajes, List<String> practicas, String estichayuda) {
        super(id, nombre, correo, telefono, contraseña, tipoUsuario);
        this.historiasConsenso = historiasConsenso;
        this.consensios = consensios;
        this.grupos = grupos;
        this.mensajes = mensajes;
        this.practicas = practicas;
        this.estichayuda = estichayuda;
    }

    // Métodos para valorar diferentes aspectos
    public void valorarConsensato(String valoracion) {
        System.out.println("Valorando consensato: " + valoracion);
    }

    public void valorarAyudo(String valoracion) {
        System.out.println("Valorando ayudo: " + valoracion);
    }

    public void valorSuperficial(String valor) {
        System.out.println("Valor superficial: " + valor);
    }

    public void enviarMensaje(String mensaje) {
        this.mensajes.add(mensaje);
        System.out.println("Mensaje enviado: " + mensaje);
    }

    // Getters y setters
    public List<String> getHistoriasConsenso() {
        return historiasConsenso;
    }

    public void setHistoriasConsenso(List<String> historiasConsenso) {
        this.historiasConsenso = historiasConsenso;
    }

    public List<String> getConsensios() {
        return consensios;
    }

    public void setConsensios(List<String> consensios) {
        this.consensios = consensios;
    }

    public List<String> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<String> grupos) {
        this.grupos = grupos;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }

    public List<String> getPracticas() {
        return practicas;
    }

    public void setPracticas(List<String> practicas) {
        this.practicas = practicas;
    }

    public String getEstichayuda() {
        return estichayuda;
    }

    public void setEstichayuda(String estichayuda) {
        this.estichayuda = estichayuda;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "historiasConsenso=" + historiasConsenso +
                ", consensios=" + consensios +
                ", grupos=" + grupos +
                ", mensajes=" + mensajes +
                ", practicas=" + practicas +
                ", estichayuda='" + estichayuda + '\'' +
                '}';
    }

    // ----- BUILDER -----
    public static class Builder {
        private String id;
        private String nombre;
        private String correo;
        private String telefono;
        private String contraseña;
        private TipoUsuario tipoUsuario;
        private List<String> historiasConsenso = new ArrayList<>();
        private List<String> consensios = new ArrayList<>();
        private List<String> grupos = new ArrayList<>();
        private List<String> mensajes = new ArrayList<>();
        private List<String> practicas = new ArrayList<>();
        private String estichayuda;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder setContraseña(String contraseña) {
            this.contraseña = contraseña;
            return this;
        }

        public Builder setTipoUsuario(TipoUsuario tipoUsuario) {
            this.tipoUsuario = tipoUsuario;
            return this;
        }

        public Builder setHistoriasConsenso(List<String> historiasConsenso) {
            this.historiasConsenso = historiasConsenso;
            return this;
        }

        public Builder setConsensios(List<String> consensios) {
            this.consensios = consensios;
            return this;
        }

        public Builder setGrupos(List<String> grupos) {
            this.grupos = grupos;
            return this;
        }

        public Builder setMensajes(List<String> mensajes) {
            this.mensajes = mensajes;
            return this;
        }

        public Builder setPracticas(List<String> practicas) {
            this.practicas = practicas;
            return this;
        }

        public Builder setEstichayuda(String estichayuda) {
            this.estichayuda = estichayuda;
            return this;
        }

        public Estudiante build() {
            return new Estudiante(
                id,
                nombre,
                correo,
                telefono,
                contraseña,
                tipoUsuario,
                historiasConsenso,
                consensios,
                grupos,
                mensajes,
                practicas,
                estichayuda
            );
        }
    }
}