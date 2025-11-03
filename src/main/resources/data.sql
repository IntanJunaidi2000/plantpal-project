-- Create 3 users
INSERT INTO users (username, email, password) VALUES 
('alice', 'alice@email.com', 'password123'),
('bob', 'bob@email.com', 'password123'),
('charlie', 'charlie@email.com', 'password123');

-- Create some plants
-- Alice has 2 plants (user_id = 1)
INSERT INTO plants (nickname, species, location, user_id) VALUES
('Sunny', 'Sunflower', 'outdoor', 1),
('Spike', 'Cactus', 'indoor', 1);

-- Bob has 1 plant (user_id = 2)
INSERT INTO plants (nickname, species, location, user_id) VALUES
('Rosie', 'Rose', 'outdoor', 2);

-- Charlie has 1 plant (user_id = 3)
INSERT INTO plants (nickname, species, location, user_id) VALUES
('Greeny', 'Fern', 'indoor', 3);

-- Create some care logs
-- For Sunny (plant_id = 1)
INSERT INTO care_logs (care_type, care_date, notes, plant_id) VALUES
('watered', '2024-10-20', 'Gave lots of water', 1),
('fertilized', '2024-10-21', 'Added plant food', 1);

-- For Spike (plant_id = 2)
INSERT INTO care_logs (care_type, care_date, notes, plant_id) VALUES
('watered', '2024-10-22', 'Just a little water', 2);

-- For Rosie (plant_id = 3)
INSERT INTO care_logs (care_type, care_date, notes, plant_id) VALUES
('watered', '2024-10-23', 'Watered in morning', 3),
('pruned', '2024-10-24', 'Cut dead flowers', 3);

-- For Greeny (plant_id = 4)
INSERT INTO care_logs (care_type, care_date, notes, plant_id) VALUES
('watered', '2024-10-25', 'Misted the leaves', 4);