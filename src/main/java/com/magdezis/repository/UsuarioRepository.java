package com.magdezis.repository;

import com.magdezis.config.DatabaseConfig;
import com.magdezis.model.Rol;
import com.magdezis.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioRepository {

    private static final String SQL_FIND_BY_CORREO = """
            SELECT u.id_usuario, u.nombre, u.apellido, u.correo, u.contrasena,
                   r.id_rol, r.nombre AS rol_nombre
            FROM usuarios u
            JOIN roles r ON u.id_rol = r.id_rol
            WHERE u.correo = ?""";

    private static final String SQL_EXISTS_BY_CORREO =
            "SELECT 1 FROM usuarios WHERE correo = ?";

    private static final String SQL_INSERT =
            "INSERT INTO usuarios (nombre, apellido, correo, contrasena, id_rol) VALUES (?, ?, ?, ?, ?)";

    public Optional<Usuario> findByCorreo(String correo) throws SQLException {
        try (Connection cn = DatabaseConfig.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_BY_CORREO)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("rol_nombre"));
                    return Optional.of(new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("correo"),
                            rs.getString("contrasena"),
                            rol));
                }
            }
        }
        return Optional.empty();
    }

    public boolean existsByCorreo(String correo) throws SQLException {
        try (Connection cn = DatabaseConfig.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_EXISTS_BY_CORREO)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void save(Usuario usuario) throws SQLException {
        try (Connection cn = DatabaseConfig.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasena());
            ps.setInt(5, usuario.getRol().getId());
            ps.executeUpdate();
        }
    }
}
