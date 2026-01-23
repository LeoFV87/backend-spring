import random
from faker import Faker
from datetime import datetime

# Configuración del Dataset (Sección 9.1 y 9.4)
NUM_USERS = 10
NUM_CATEGORIES = 15
NUM_PRODUCTS = 1200
FILENAME = "seed_data.sql"

fake = Faker('es_ES')

CATEGORY_NAMES = ["Electrónica", "Gaming", "Hogar", "Oficina", "Libros", "Deportes", "Ropa", "Juguetes", "Salud", "Belleza", "Automotriz", "Jardín", "Mascotas", "Fotografía", "Música"]
PRODUCT_ADJECTIVES = ["Pro", "Slim", "Ultra", "Gaming", "Ergonómico", "4K", "Inalámbrico", "Inteligente", "Premium", "Básico"]
PRODUCT_NOUNS = ["Laptop", "Mouse", "Teclado", "Monitor", "Escritorio", "Silla", "Auriculares", "Cámara", "Celular", "Tablet"]

def generate_sql():
    print(f"Generando SQL final... verificando sintaxis de relaciones...")
    
    with open(FILENAME, "w", encoding="utf-8") as f:
        f.write("BEGIN;\n")
        f.write("TRUNCATE TABLE product_categories, products, categories, users RESTART IDENTITY CASCADE;\n\n")
        
        # 1. USUARIOS
        f.write("-- Insertando Usuarios\n")
        f.write("INSERT INTO users (id, name, email, password, created_at, updated_at, deleted) VALUES\n")
        user_lines = []
        for i in range(1, NUM_USERS + 1):
            name = fake.name().replace("'", "''")
            email = fake.unique.email()
            date = fake.date_time_between(start_date='-1y', end_date='now')
            user_lines.append(f"({i}, '{name}', '{email}', '123', '{date}', '{date}', false)")
        f.write(",\n".join(user_lines) + ";\n\n")

        # 2. CATEGORÍAS (Con category_id y user_id obligatorios)
        f.write("-- Insertando Categorías\n")
        f.write("INSERT INTO categories (id, category_id, user_id, name, description, created_at, updated_at, deleted) VALUES\n")
        cat_lines = []
        for i, cat_name in enumerate(CATEGORY_NAMES, 1):
            owner = random.randint(1, NUM_USERS)
            cat_lines.append(f"({i}, {i}, {owner}, '{cat_name}', 'Cat de {cat_name}', NOW(), NOW(), false)")
        f.write(",\n".join(cat_lines) + ";\n\n")

        # 3. PRODUCTOS (Sin product_id, usando user_id para owner)
        for chunk in range(0, NUM_PRODUCTS, 100):
            f.write("INSERT INTO products (id, name, description, price, stock, user_id, created_at, updated_at, deleted) VALUES\n")
            prod_lines = []
            for i in range(chunk + 1, chunk + 101):
                name = f"{random.choice(PRODUCT_NOUNS)} {random.choice(PRODUCT_ADJECTIVES)} {fake.word().capitalize()}".replace("'", "''")
                price = round(random.uniform(10.0, 5000.0), 2)
                owner = random.randint(1, NUM_USERS)
                date = fake.date_time_between(start_date='-1y', end_date='now')
                prod_lines.append(f"({i}, '{name}', 'Producto de alta calidad', {price}, 100, {owner}, '{date}', '{date}', false)")
            f.write(",\n".join(prod_lines) + ";\n\n")

        # 4. RELACIÓN PRODUCTO-CATEGORÍA (CORRECCIÓN DE SINTAXIS AQUÍ)
        f.write("-- Insertando Relaciones N:N\n")
        rel_lines = []
        for p_id in range(1, NUM_PRODUCTS + 1):
            chosen_cats = random.sample(range(1, NUM_CATEGORIES + 1), k=2)
            for c_id in chosen_cats:
                rel_lines.append(f"({p_id}, {c_id})")
        
        # Insertamos en bloques asegurando siempre el comando INSERT INTO
        for i in range(0, len(rel_lines), 500):
            f.write("INSERT INTO product_categories (product_id, category_id) VALUES\n")
            f.write(",\n".join(rel_lines[i:i+500]) + ";\n")

        f.write("\nCOMMIT;")
    print(f"¡Hecho! Archivo '{FILENAME}' generado correctamente.")

if __name__ == "__main__":
    generate_sql()