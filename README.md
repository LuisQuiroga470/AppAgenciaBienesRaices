# App Agencia Bienes Raíces

Aplicación Android desarrollada en Kotlin para administrar propiedades, clientes y agentes inmobiliarios.

## Credenciales de acceso

La aplicación permite iniciar sesión con cualquiera de estas cuentas:

| Usuario | Contraseña |
|---|---|
| `admin` | `1234` |
| `luis` | `1234` |

## Recorrido básico

1. Abrir la aplicación.
2. Ingresar usuario y contraseña.
3. Presionar **Iniciar sesión**.
4. En el panel principal, elegir uno de los mantenedores:
   - Propiedades.
   - Clientes / compradores.
   - Agentes inmobiliarios.
5. Presionar el botón **+** para agregar un registro.
6. Completar el formulario y presionar **Guardar**.
7. Usar **Editar** para modificar un registro o **Eliminar** para quitarlo.
8. Presionar **Volver** para regresar al panel principal.

## Evento de Firebase Analytics

Firebase Analytics está configurado en la pantalla de inicio de sesión, dentro de `MainActivity.kt`.

Cada vez que se presiona el botón **Iniciar sesión**, la aplicación registra el evento personalizado `clic_iniciar_sesion`. El evento sirve para saber cuántas veces se intenta usar el inicio de sesión y se puede revisar en Firebase Console, en **Analytics → DebugView** durante las pruebas.

El evento no envía el nombre de usuario ni la contraseña. Solo registra que el botón fue presionado. En los reportes normales de Analytics, los datos pueden tardar algunas horas en aparecer.

## Mantenedores

Cada mantenedor muestra sus registros en tarjetas desplazables, con todos sus atributos e ID automático.

- Propiedades: usa una interfaz verde.
- Clientes: usa una interfaz azul.
- Agentes inmobiliarios: usa una interfaz negra.

Los datos se mantienen en memoria mientras la aplicación está abierta. No se utiliza una base de datos, por lo que los registros se pierden si Android termina el proceso de la aplicación.

## APK

El APK de prueba se encuentra en [APK/app-debug.apk](APK/app-debug.apk).
