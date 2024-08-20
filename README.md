# Proyecto Backend - Simulación Bancaria

Este proyecto es una aplicación backend desarrollada con Spring Boot que gestiona operaciones bancarias básicas, como la
administración de clientes, cuentas y transacciones.
Para el almacenamiento de los datos se utiliza la base de datos de H2 en memoria, esta ya se encuentra configurada en el archivo de application.properties, y se instala junto con las demas dependencias. 

## Requisitos

Antes de correr la aplicación, asegúrate de tener instalados los siguientes programas:

- **JDK 17 o superior**: Puedes descargarlo
  desde [Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
  o [AdoptOpenJDK](https://adoptopenjdk.net/).
- **Gradle 7.0 o superior**: [Instalación de Gradle](https://gradle.org/install/).

## Configuración

1. **Clonar el repositorio**:

```bash
git clone https://github.com/Darkper/mibanco.git
cd mibanco
```

## Instalar dependencias

1. **Si no tienes las dependencias ya instaladas, ejecuta el siguiente comando en la raíz del proyecto:**:

```bash
gradle build
```

## Ejecución de la Aplicación

Una vez configurada la base de datos y las dependencias, puedes ejecutar la aplicación con:

```bash
gradle bootRun
```

O puedes compilar un archivo JAR y ejecutarlo directamente:

```bash
gradle assemble
java -jar build/libs/nombre-del-jar-generado.jar
```
## Ejecución de pruebas

Para ejecutar las pruebas unitarias y de integración, utiliza el siguiente comando:
```bash
gradle test
```

## Ejecución del Proyecto Backend en IntelliJ IDEA
El proyecto tambien puedes ejecutarlo directamente desde IntelliJ IDEA:

1. Abre el proyecto backend en IntelliJ IDEA.
2. Asegúrate de que las configuraciones de Gradle estén correctas y que todas las dependencias estén instaladas.
3. En el panel de la parte superior derecha, selecciona la configuración de ejecución correspondiente (o crea una nueva configuración si es necesario).
4. Haz clic en el botón de ejecución (el triángulo verde) para iniciar la aplicación.
4. Para más detalles sobre cómo configurar y ejecutar proyectos en IntelliJ IDEA, consulta la documentación de IntelliJ IDEA.
