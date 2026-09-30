# App Agencia Bienes Raíces

Aplicación Android desarrollada en Kotlin para la gestión y administración de propiedades, clientes y agentes inmobiliarios, integrada con Firebase Authentication, Firebase Firestore y Firebase Analytics.

---

## Métodos de Autenticación Integrados

La aplicación cuenta con inicio de sesión seguro gestionado a través de Firebase Authentication:

1. **Correo y Contraseña:** 
   - Permite iniciar sesión con credenciales registradas.
   - Formulario de Registro de Nuevos Usuarios con almacenamiento automático del perfil en Cloud Firestore.
2. **Google Sign-In (Gmail):** 
   - Inicio de sesión con un solo clic mediante la API de Google Sign-In (OAuth2).
   - Selector forzado de cuentas para elegir o cambiar de cuenta Gmail fácilmente.
3. **Microsoft Sign-In (Outlook / Hotmail):** 
   - Inicio de sesión federado mediante Microsoft Azure / Entra ID (OAuthProvider).

---

## Funcionalidades y Pantallas

- **Visualización de Usuario Activo:** Todas las pantallas (PanelPrincipal, GestionPropiedades, GestionClientes, GestionAgentes) disponen del método actualizarUI() que identifica y muestra el correo del usuario actualmente autenticado en la barra superior.
- **Navegación Intuitiva:** Botón de regreso integrado en el formulario de registro y en los tres mantenedores.
- **Mantenedores CRUD en Memoria:**
  - **Propiedades:** Interfaz temática en tonos verdes.
  - **Clientes / Compradores:** Interfaz temática en tonos azules.
  - **Agentes Inmobiliarios:** Interfaz temática en tonos oscuros.

---

## Firebase Analytics

Firebase Analytics está integrado en la aplicación para registrar métricas de uso:
- Evento personalizado Formulario_registro al ingresar a la pantalla de registro.
- Seguimiento de eventos disponible en Firebase Console -> Analytics -> DebugView.
