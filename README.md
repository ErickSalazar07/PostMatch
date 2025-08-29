# PostMatch

## Tecnologias
<img width="150px" alt="Static Badge" src="https://img.shields.io/badge/platform-android-ligthgreen?logo=android&logoColor=ligthgreen">
<img width="140px" alt="Static Badge" src="https://img.shields.io/badge/language-kotlin-%23b042ff?logo=kotlin&logoColor=%23b042ff">
<img width="160px" alt="Static Badge" src="https://img.shields.io/badge/framework-flutter-%2304bade?logo=flutter&logoColor=%2304bade">
<img width="157px" alt="Static Badge" src="https://img.shields.io/badge/database-firebase-red?logo=firebase&logoColor=red">
<img width="157px" alt="Static Badge" src="https://img.shields.io/badge/database-fireship-red?logo=fireship&logoColor=red">
<img width="185px" alt="Static Badge" src="https://img.shields.io/badge/project-in%20development-yellow?logo=git">
<img width="165px" alt="Static Badge" src="https://img.shields.io/badge/SDK-android%20studio-ligthgreen?logo=androidstudio&logoColor=%2304bade">


## Indice
- [Integrantes del Proyecto](#integrantes-del-proyecto)
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Diagrama Entidad Relación](#diagrama-entidad-relación)
- [Diagrama de Clases](#diagrama-clases)
- [Requerimientos funcionales](docs/requerimientos_funcionales.pdf)
- [Figma](#mockup-figma)
- [Pantallas principales](#pantallas-principales)

## Integrantes del proyecto
- Juan Eduardo Diaz Rojas
- Felipe Garrido Flores
- Erick Salazar Suarez
- Juan Bernardo Uribe Ramirez

## Descripción del proyecto

### Logo
<p align="center">
  <img src="app/src/main/res/drawable/logo_postmatch.png" alt="Logo de PostMatch" width="200"/>
</p>

**PostMatch** es una aplicación móvil desarrollada en Android Studio que permite a los usuarios realizar reseñas (reviews) de partidos de
fútbol. La aplicación no solo se enfoca en capturar opiniones sobre eventos deportivos, sino que también incorpora funcionalidades propias
de una red social:

- Publicar y comentar reseñas de partidos.
- Puntuar partidos de acuerdo a la experiencia del usuario.
- Seguir a otros usuarios y ver su actividad.
- Notificaciones de nuevas interacciones.
- Historial de partidos reseñados y publicaciones.

Esta app está diseñada para fanáticos del fútbol que desean compartir su perspectiva de los encuentros, generar discusión, y construir
comunidad a través de la experiencia deportiva.

## Paleta de Colores

| Nombre           | HEX      | Muestra |
|------------------|----------|---------|
| Verde O.         | `#121712` | ![#121712](https://singlecolorimage.com/get/121712/20x20) |
| Verde Pigmentado | `#1F241F` | ![#1F241F](https://singlecolorimage.com/get/1F241F/20x20) |
| Verde O2         | `#404F40` | ![#404F40](https://singlecolorimage.com/get/404F40/20x20) |
| Verde            | `#2B362B` | ![#2B362B](https://singlecolorimage.com/get/2B362B/20x20) |
| Verde Claro      | `#38AB3D` | ![#38AB3D](https://singlecolorimage.com/get/38AB3D/20x20) |
| Blanco           | `#FFFFFF` | ![#FFFFFF](https://singlecolorimage.com/get/FFFFFF/20x20) |





## Diagrama Entidad Relación

![Diagrama entidad-relación](docs/diagramas/entidad-relacion.png)

## Diagrama Clases

![Diagrama clases](docs/diagramas/clases.png)  

## Mockup figma  
[Figma - PostMatch](https://www.figma.com/design/AuSTbTEK20Tgqt2AiHOQUT/Untitled?node-id=0-1&p=f)

## Pantallas principales

Dentro de la aplicación se han diseñado varias pantallas que reflejan las funcionalidades principales de PostMatch. 
A continuación se muestran algunas de ellas:

**Crear reseña**
<p align="center"> <img src="app/src/main/res/drawable/postmatch_ui_crear_review_screen_preview.png" alt="Pantalla crear reseña" width="300"/> </p>

- El usuario selecciona un partido y puede escribir su reseña.
- Incluye un sistema de calificación con estrellas.
- Representa el núcleo de la aplicación, ya que permite compartir experiencias sobre los partidos.

**Perfil de usuario**
<p align="center"> <img src="app/src/main/res/drawable/postmatch_ui_follow_screen_preview.png" alt="Pantalla perfil de usuario" width="300"/> </p>

- Muestra la información básica del usuario, sus seguidores y seguidos.
- Incluye el historial de reseñas publicadas y guardadas.
- Permite seguir a otros usuarios, reforzando el carácter de red social de la app.

**Notificaciones**
<p align="center"> <img src="app/src/main/res/drawable/postmatch_ui_notificaciones_screen_preview.png" alt="Pantalla notificaciones" width="300"/> </p>

- Presenta de forma clara las interacciones de otros usuarios con nuestras publicaciones.
- Permite al usuario estar al tanto de “me gusta” y reacciones recibidas en sus reseñas.
- Refuerza la dinámica de comunidad e interacción dentro de PostMatch.

**Detalle del partido**
<p align="center"> <img src="app/src/main/res/drawable/postmatch_ui_partido_detail_screen_review.png" alt="Pantalla detalle del partido" width="300"/> </p>

- Muestra información completa de un partido específico: marcador, posesión, goles y tiros.
- Incluye la sección de reseñas de los usuarios, con calificación y comentarios.
- Facilita la interacción en torno a un evento deportivo puntual.

**Partidos destacados**
<p align="center"> <img src="app/src/main/res/drawable/postmatch_ui_partido_screen_review.png" alt="Pantalla partidos destacados" width="300"/> </p>

- Presenta los partidos más relevantes de la semana o del momento.
- Cada tarjeta muestra el resultado, el torneo (ej. Champions League, La Liga) y detalles del encuentro.
- Sirve como punto de inicio para que los usuarios elijan qué partido reseñar o consultar.

**Perfil de usuario**
<p align="center"> <img src="app/src/main/res/drawable/postmatch_ui_perfil_screen_preview.png" alt="Pantalla perfil de usuario preview" width="300"/> </p>

- El perfil detalla la información básica del usuario, junto con seguidores y seguidos.
- Contiene un listado de reseñas con su calificación y comentarios.
- Refuerza la experiencia social al mostrar cómo otros usuarios interactúan con los partidos.