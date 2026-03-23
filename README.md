# Sistema de Inventario de Herramientas (Proyecto Integrado Completo)

**Desarrollador (Miembro Clave - Core y BD):** Vega Negrete Fernando  
**Institución:** Universidad Tecnológica del Norte de Guanajuato (UTNG)  
**Asignatura:** Programación Orientada a Objetos / Proyecto Integrador 
**Período:** 2025  

## 📋 Resumen General

El **Sistema de Inventario de Herramientas** es una aplicación de escritorio desarrollada en Java encargada de administrar los consumibles, herramientas y préstamos en un entorno educativo. Este repositorio representa la **versión integrada del equipo de desarrollo (`7EE2_Completo` o `V7 INTEGRADORA`)**.

Este sistema centraliza el trabajo de múltiples partes bajo mi arquitectura MVC inicial y mi diseño de Base de Datos relacional, logrando una plataforma 100% funcional y extendida que añade interfaces, vistas para distintos módulos y un flujo completo.

## 🚀 Características y Funcionalidades Principales

*   **Módulo de Administradores:** Login con validación de credenciales, operaciones CRUD completas e implementación de seguridad para cuentas autorizadas.
*   **Módulo de Materiales Consumibles:** Registro preciso de consumibles, asignación de ubicaciones, y control continuo de disponibilidad e inventario mediante estados controlados (Bueno, Regular, Desgastado, Dañado).
*   **Módulo de Materiales No Consumibles:** Seguimiento del estado, marca, modelo y ubicación física de herramientas no fungibles y serializadas (ej. registradas bajo la UTNG).
*   **Módulo de Préstamos:** Administración segura y eficiente en la asignación de herramientas hacia docentes y alumnos, manteniendo un control exhaustivo de fechas de salida, bitácora de retorno y estados funcionales del préstamo (Activo, Completado, Cancelado).

## 💻 Entorno Técnico y Dependencias
- **Tecnología Base:** Java JDK 8+
- **Interfaz de Usuario Gráfica:** Java Swing
- **Base de Datos:** MySQL 8.0 (Estructura relacional 3FN diseñada íntegramente de mi autoría)
- **Persistencia de Datos:** JDBC acoplado con MySQL Connector/J

## ⚙️ Despliegue e Instalación
1. Configura una instancia local de MySQL y ejecuta el script unificado de creación de BD (`ProyectoV9` o equivalente).
2. Abre la carpeta principal como un Proyecto con el IDE NetBeans.
3. Asegúrate de vincular `mysql-connector-j` en las bibliotecas de tu proyecto, limpia y compila la clase inicial principal para poder ejecutarlo exitosamente.

---
*Para revisar detalladamente el diferencial estructural, la aplicación estricta de patrones como DAO e interfaces y la arquitectura MVC 100% limpia sin duplicación, favor de consultar mi otro repositorio subido, correspondiente exclusivamente al Módulo Core.*

## 📄 Licencia

**© 2026 Vega Negrete Fernando. Todos los derechos reservados.**

Este proyecto fue desarrollado como parte del curso de Programación Orientada a Objetos y Proyecto Integrador en la UTNG. El código fuente es privado y no se permite su uso, distribución o modificación sin autorización del autor.
