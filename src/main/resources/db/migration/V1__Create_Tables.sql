CREATE TABLE IF NOT EXISTS categories (
  id INT(15)  AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS movies (
  id INT(15) AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150),
  year SMALLINT(4),
  views INT(15) DEFAULT 0,
  description TEXT(500),
  rating FLOAT(2,1),
  link_imdb VARCHAR(300),
  link_poster VARCHAR(300),
  category INT(15) NOT NULL,
  FOREIGN KEY (category) REFERENCES categories(id)
  );