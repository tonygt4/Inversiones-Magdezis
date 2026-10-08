package com.magdezis.service;

import com.magdezis.config.SecurityConfig;
import com.magdezis.dto.LoginDTO;
import com.magdezis.dto.RegistroDTO;
import com.magdezis.model.Rol;
import com.magdezis.model.Usuario;
import com.magdezis.repository.RolRepository;
import com.magdezis.repository.UsuarioRepository;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AuthService {

    private static final Pattern CORREO_VALIDO = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$");
    private static final int MIN_PASSWORD = 6;
    private static final String ROL_USER = "USER";

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public AuthService() {
        this(new UsuarioRepository(), new RolRepository());
    }

    public AuthService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public Usuario login(LoginDTO dto) throws AuthException {
        if (vacio(dto.correo()) || vacio(dto.contrasena())) {
            throw new AuthException("Debe ingresar correo y contraseña.");
        }
        try {
            Usuario usuario = usuarioRepository.findByCorreo(normalizarCorreo(dto.correo()))
                    .orElseThrow(() -> new AuthException("Correo o contraseña incorrectos."));
            if (!SecurityConfig.verificar(dto.contrasena(), usuario.getContrasena())) {
                throw new AuthException("Correo o contraseña incorrectos.");
            }
            return usuario;
        } catch (SQLException e) {
            throw new AuthException("Error al consultar la base de datos.", e);
        }
    }

    public void registrar(RegistroDTO dto) throws AuthException {
        validarRegistro(dto);
        String correo = normalizarCorreo(dto.correo());
        try {
            if (usuarioRepository.existsByCorreo(correo)) {
                throw new AuthException("El correo ya está registrado.");
            }
            Rol rolUser = rolRepository.findByNombre(ROL_USER)
                    .orElseThrow(() -> new AuthException("El rol USER no existe en la base de datos."));
            Usuario nuevo = new Usuario(
                    0,
                    dto.nombre().trim(),
                    dto.apellido().trim(),
                    correo,
                    SecurityConfig.encriptar(dto.contrasena()),
                    rolUser);
            usuarioRepository.save(nuevo);
        } catch (SQLException e) {
            throw new AuthException("Error al registrar el usuario.", e);
        }
    }

    private void validarRegistro(RegistroDTO dto) throws AuthException {
        if (vacio(dto.nombre()) || vacio(dto.apellido()) || vacio(dto.correo()) || vacio(dto.contrasena())) {
            throw new AuthException("Todos los campos son obligatorios.");
        }
        if (!CORREO_VALIDO.matcher(normalizarCorreo(dto.correo())).matches()) {
            throw new AuthException("El correo electrónico no es válido.");
        }
        if (dto.contrasena().length() < MIN_PASSWORD) {
            throw new AuthException("La contraseña debe tener al menos " + MIN_PASSWORD + " caracteres.");
        }
    }

    private static boolean vacio(String valor) {
        return valor == null || valor.isBlank();
    }

    private static String normalizarCorreo(String correo) {
        return correo.trim().toLowerCase();
    }
}
