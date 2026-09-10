-- src/main/resources/data.sql

INSERT INTO polizas (tipo, estado, fecha_inicio_vigencia, fecha_fin_vigencia, numero_meses, valor_canon, valor_prima, arrendatario, arrendador)
VALUES
    ('INDIVIDUAL', 'ACTIVA', '2025-01-01', '2025-12-31', 12, 1500000, 18000000, 'Juan Pérez', 'María Gómez'),
    ('COLECTIVA', 'ACTIVA', '2025-01-01', '2025-12-31', 12, 2000000, 24000000, 'Inmobiliaria XYZ', 'Consorcio ABC');

INSERT INTO riesgos (descripcion, direccion_inmueble, estado, poliza_id)
VALUES
    ('Apartamento arrendado - riesgo único', 'Calle 10 # 5-20, Cúcuta', 'ACTIVO', 1),
    ('Local comercial 1', 'Av. Libertadores # 12-34, Cúcuta', 'ACTIVO', 2),
    ('Local comercial 2', 'Cra 8 # 15-60, Cúcuta', 'ACTIVO', 2);