
CREATE TABLE tb_food_item (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    validade DATE NOT NULL
);