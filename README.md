<h1>🌌 Proyecto API de Gestión de Viajes y Terrenos Espaciales</h1>

<p>
    Este proyecto es una API RESTful desarrollada en <strong>Java</strong> con <strong>Spring Boot</strong>. Su propósito es gestionar un sistema de compra de terrenos en planetas y reservas de viajes espaciales. Los clientes pueden adquirir terrenos en diferentes planetas y reservar viajes, añadiendo pasajeros a cada viaje. Además, se ha utilizado una base de datos <strong>MySQL en la nube</strong>, conectando el proyecto a esta base de datos para gestionar la información de manera centralizada.
</p>

<h2>🚀 Funcionalidades</h2>
<ul>
    <li><strong>Gestión de Clientes:</strong>
        <ul>
            <li>Crear, obtener, actualizar y eliminar clientes.</li>
            <li>Visualizar los terrenos adquiridos y las reservas de viajes.</li>
        </ul>
    </li>
    <li><strong>Gestión de Planetas y Terrenos:</strong>
        <ul>
            <li>Visualizar y gestionar los planetas disponibles.</li>
            <li>Permitir a los clientes comprar terrenos en los planetas.</li>
        </ul>
    </li>
    <li><strong>Sistema de Reservas y Pasajeros:</strong>
        <ul>
            <li>Reservar viajes espaciales.</li>
            <li>Añadir pasajeros a cada viaje, independientemente de si son clientes o no.</li>
        </ul>
    </li>
    <li><strong>Funciones Administrativas:</strong>
        <ul>
            <li>Funcionalidades exclusivas para el administrador, como la gestión completa de clientes, planetas y reservas.</li>
        </ul>
    </li>
</ul>

<h2>📦 Estructura del Proyecto</h2>
<p>
    El backend está desarrollado en <strong>Java con Spring Boot</strong>. Expone una API REST para realizar las operaciones de gestión de clientes, planetas, terrenos, reservas y pasajeros. Las entidades principales son:
</p>
<ul>
    <li><strong>Cliente:</strong> Información del cliente (id, nombre, apellido, email, etc.).</li>
    <li><strong>Planeta:</strong> Detalles de cada planeta (id, nombre, tipo, kmCuadrados disponibles, etc.).</li>
    <li><strong>ClientePlanetaPropiedad:</strong> Relación entre cliente y terreno en un planeta.</li>
    <li><strong>Viaje:</strong> Información sobre el viaje (destino, fecha, precio, etc.) que se puede reservar.</li>
    <li><strong>Pasajero:</strong> Detalles de cada pasajero (nombre, apellido, email) y su relación con un viaje.</li>
    <li><strong>Reserva:</strong> Información sobre las reservas realizadas por los clientes.</li>
    <li><strong>Administrador:</strong> Acceso a funcionalidades avanzadas de gestión.</li>
</ul>

<h2>📚 Tecnologías Utilizadas</h2>
<ul>
    <li><strong>Java 11 o superior</strong></li>
    <li><strong>Spring Boot</strong></li>
    <li><strong>Spring Data JPA</strong></li>
    <li><strong>MySQL</strong> (en la nube, para la gestión de datos)</li>
</ul>

<h2>🛠 Instalación y Configuración</h2>
<h3>Prerrequisitos</h3>
<ul>
    <li><strong>Java 11 o superior</strong></li>
    <li><strong>MySQL</strong> (o cualquier otra base de datos que prefieras usar)</li>
    <li><strong>IDE</strong> (Eclipse, IntelliJ, etc.)</li>
</ul>

<h2>📖 Documentación de la API</h2>

<h3>Endpoints Administrativos</h3>
<ul>
    <li><strong>Obtener todos los clientes:</strong> <code>GET /api/v1/administradores/clientes/traer</code></li>
    <li><strong>Obtener cliente por ID:</strong> <code>GET /api/v1/administradores/clientes/traer/{id}</code></li>
    <li><strong>Actualizar cliente:</strong> <code>PUT /api/v1/administradores/clientes/actualizar</code></li>
    <li><strong>Eliminar cliente por ID:</strong> <code>DELETE /api/v1/administradores/clientes/eliminar/{id}</code></li>
    <li><strong>Obtener todos los planetas:</strong> <code>GET /api/v1/administradores/planetas/traer</code></li>
    <li><strong>Crear planeta:</strong> <code>POST /api/v1/administradores/planetas/crear</code></li>
    <li><strong>Actualizar planeta:</strong> <code>PUT /api/v1/administradores/planetas/actualizar</code></li>
    <li><strong>Eliminar planeta por ID:</strong> <code>DELETE /api/v1/administradores/planetas/eliminar/{id}</code></li>
    <li><strong>Obtener todos los viajes:</strong> <code>GET /api/v1/administradores/viajes/traer</code></li>
    <li><strong>Crear viaje:</strong> <code>POST /api/v1/administradores/viajes/crear</code></li>
    <li><strong>Actualizar viaje:</strong> <code>PUT /api/v1/administradores/viajes/actualizar</code></li>
    <li><strong>Eliminar viaje por ID:</strong> <code>DELETE /api/v1/administradores/viajes/eliminar/{id}</code></li>
    <li><strong>Obtener todas las reservas:</strong> <code>GET /api/v1/administradores/reservas/traer</code></li>
    <li><strong>Eliminar reserva por ID:</strong> <code>DELETE /api/v1/administradores/reservas/eliminar/{id}</code></li>
</ul>

<h3>Endpoints de Clientes</h3>
<ul>
    <li><strong>Obtener todos los clientes:</strong> <code>GET /api/v1/clientes/traer</code></li>
    <li><strong>Crear cliente:</strong> <code>POST /api/v1/clientes/crear</code></li>
    <li><strong>Actualizar cliente:</strong> <code>PUT /api/v1/clientes/actualizar</code></li>
    <li><strong>Eliminar cliente por ID:</strong> <code>DELETE /api/v1/clientes/eliminar/{id}</code></li>
</ul>

<h3>Endpoints de Clientes-Planetas</h3>
<ul>
    <li><strong>Obtener todas las propiedades de clientes en planetas:</strong> <code>GET /api/v1/clientes-planetas/traer</code></li>
    <li><strong>Crear propiedad de cliente en un planeta:</strong> <code>POST /api/v1/clientes-planetas/crear</code></li>
    <li><strong>Actualizar propiedad de cliente en un planeta:</strong> <code>PUT /api/v1/clientes-planetas/actualizar</code></li>
    <li><strong>Eliminar propiedad de cliente por ID:</strong> <code>DELETE /api/v1/clientes-planetas/eliminar/{id}</code></li>
</ul>

<h3>Endpoints de Pasajeros</h3>
<ul>
    <li><strong>Obtener todos los pasajeros:</strong> <code>GET /api/v1/pasajeros/traer</code></li>
    <li><strong>Crear pasajero:</strong> <code>POST /api/v1/pasajeros/crear</code></li>
    <li><strong>Actualizar pasajero:</strong> <code>PUT /api/v1/pasajeros/actualizar</code></li>
    <li><strong>Eliminar pasajero por ID:</strong> <code>DELETE /api/v1/pasajeros/eliminar/{id}</code></li>
</ul>

<h3>Endpoints de Reservas</h3>
<ul>
    <li><strong>Obtener todas las reservas:</strong> <code>GET /api/v1/reservas/traer</code></li>
    <li><strong>Crear reserva:</strong> <code>POST /api/v1/reservas/crear</code></li>
    <li><strong>Actualizar reserva:</strong> <code>PUT /api/v1/reservas/actualizar</code></li>
    <li><strong>Eliminar reserva por ID:</strong> <code>DELETE /api/v1/reservas/eliminar/{id}</code></li>
</ul>

<h3>Endpoints de Viajes</h3>
<ul>
    <li><strong>Obtener todos los viajes:</strong> <code>GET /api/v1/viajes/traer</code></li>
    <li><strong>Reservar viaje:</strong> <code>POST /api/v1/viajes/reservar</code></li>
    <li><strong>Actualizar viaje:</strong> <code>PUT /api/v1/viajes/actualizar</code></li>
    <li><strong>Eliminar viaje por ID:</strong> <code>DELETE /api/v1/viajes/eliminar/{id}</code></li>
</ul>


