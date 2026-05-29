-- ── Insertar categorías base ─────────────────────────────────────
-- Estas categorías son el catálogo fijo del sistema
INSERT INTO categories (name, description) VALUES
    ('Conciertos',    'Eventos musicales en vivo de todos los géneros'),
    ('Talleres',      'Actividades de aprendizaje práctico y formación'),
    ('Conferencias',  'Charlas y ponencias de expertos en diversas áreas'),
    ('Deportes',      'Competencias, torneos y eventos deportivos'),
    ('Gastronomía',   'Ferias, festivales y experiencias culinarias'),
    ('Festivales',    'Celebraciones culturales y eventos masivos'),
    ('Teatro',        'Obras teatrales, musicales y artes escénicas');

-- ── Insertar venues base ─────────────────────────────────────────
INSERT INTO venues (name, address, city, capacity) VALUES
    ('Teatro Mayor',          'Calle 24 #5-60',      'Bogotá',    2000),
    ('Plaza Mayor',           'Carrera 50 #36-50',   'Medellín',  5000),
    ('Centro de Convenciones','Calle 100 #8A-55',    'Bogotá',    1500),
    ('Estadio Olímpico',      'Av. Roosevelt s/n',   'Cali',     40000),
    ('Parque Explora',        'Carrera 52 #73-75',   'Medellín',  3000),
    ('Teatro Heredia',        'Plaza de la Trinidad','Cartagena',  800),
    ('Palacio de Eventos',    'Calle 5 #3-20',       'Cali',      2500);