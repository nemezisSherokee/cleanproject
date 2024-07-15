CREATE TABLE IF NOT EXISTS food_menu (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS drink_menu (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS restaurant_table (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(100),
    date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    table_id INTEGER,
    FOREIGN KEY (table_id) REFERENCES restaurant_table(id)
);

CREATE TABLE IF NOT EXISTS client_menu (
    id SERIAL PRIMARY KEY,
    client_id INTEGER,
    description TEXT,
    FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE IF NOT EXISTS client_menu_food_menus (
    client_menu_id INTEGER,
    food_menu_id INTEGER,
    FOREIGN KEY (client_menu_id) REFERENCES client_menu(id),
    FOREIGN KEY (food_menu_id) REFERENCES food_menu(id),
    PRIMARY KEY (client_menu_id, food_menu_id)
);

CREATE TABLE IF NOT EXISTS client_menu_drink_menus (
    client_menu_id INTEGER,
    drink_menu_id INTEGER,
    FOREIGN KEY (client_menu_id) REFERENCES client_menu(id),
    FOREIGN KEY (drink_menu_id) REFERENCES drink_menu(id),
    PRIMARY KEY (client_menu_id, drink_menu_id)
);

CREATE TABLE IF NOT EXISTS bill (
    id SERIAL PRIMARY KEY,
    client_menu_id INTEGER,
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (client_menu_id) REFERENCES client_menu(id)
);

CREATE TABLE IF NOT EXISTS client_order (
    id SERIAL PRIMARY KEY,
    client_id INTEGER,
    order_date TIMESTAMP,
    bill_id INTEGER,
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (bill_id) REFERENCES bill(id)
);

--
-- CREATE TABLE IF NOT EXISTS table_clients (
--     table_id INTEGER,
--     client_id INTEGER,
--     FOREIGN KEY (table_id) REFERENCES restaurant_table(id),
--     FOREIGN KEY (client_id) REFERENCES client(id),
--     PRIMARY KEY (table_id, client_id)
-- );

CREATE TABLE IF NOT EXISTS payment (
    id SERIAL PRIMARY KEY,
    bill_id INTEGER,
    payment_method VARCHAR(50),
    payment_date TIMESTAMP,
    FOREIGN KEY (bill_id) REFERENCES bill(id)
);

CREATE TABLE IF NOT EXISTS day_menu_offers (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS day_menu_offer_food_menus (
    day_menu_offer_id INTEGER,
    food_menu_id INTEGER,
    FOREIGN KEY (day_menu_offer_id) REFERENCES day_menu_offers(id),
    FOREIGN KEY (food_menu_id) REFERENCES food_menu(id),
    PRIMARY KEY (day_menu_offer_id, food_menu_id)
);

CREATE TABLE IF NOT EXISTS day_menu_offer_drink_menus (
    day_menu_offer_id INTEGER,
    drink_menu_id INTEGER,
    FOREIGN KEY (day_menu_offer_id) REFERENCES day_menu_offers(id),
    FOREIGN KEY (drink_menu_id) REFERENCES drink_menu(id),
    PRIMARY KEY (day_menu_offer_id, drink_menu_id)
);
