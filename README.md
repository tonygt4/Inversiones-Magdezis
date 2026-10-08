# Inversiones Magdezis - Login y Registro

Proyecto Java con Ant, JavaFX (Scene Builder), BCrypt y JDBC, en arquitectura en capas.

## Requisitos
- JDK 17 o superior
- JavaFX SDK 17+ (ajustar `javafx.lib.dir` en `build.properties`)
- MySQL 8+
- Jars en `lib/`: `jbcrypt-0.4.jar` y `mysql-connector-j-8.x.jar`

## Pasos
1. Ejecutar `database/schema.sql` en MySQL.
2. Ajustar `src/main/resources/db.properties` con sus credenciales.
3. Ejecutar `ant run`.
