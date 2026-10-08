package com.magdezis.repository;

import com.magdezis.config.DatabaseConfig;
import com.magdezis.model.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RolRepository {

    private static final String SQL_FIND_BY_NOMBRE =
            "SELECT id_rol, nombre FROM roles WHERE nombre = ?";

    public Optional<Rol> findByNombre(String nombre) throws SQLException {
        try (Connection cn = DatabaseConfig.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_BY_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Rol(rs.getInt("id_rol"), rs.getString("nombre")));
                }
            }
        }
        return Optional.empty();
    }
}
