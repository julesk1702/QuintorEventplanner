USE `quintor-main`;

CREATE TABLE users (
    user_id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user', 'graduate'),
    PRIMARY KEY (user_id)
);

CREATE TABLE events (
    event_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT(1000) NOT NULL,
    briefDescription VARCHAR(100) NOT NULL,
    startDateTime DATETIME NOT NULL,
    location VARCHAR(255) NOT NULL,
    isApproved BIT,
    isGuestEnabled TINYINT NOT NULL,
    isGraduateChecked TINYINT NOT NULL,
    PRIMARY KEY (event_id)
);

CREATE TABLE registrations (
	registration_id INT NOT NULL AUTO_INCREMENT,
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    note VARCHAR(255),
    PRIMARY KEY (registration_id),
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE guests (
    guest_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    note VARCHAR(255),
    PRIMARY KEY (guest_id),
	FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE diets (
	diet_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (diet_id)
);

CREATE TABLE guest_diets (
	guest_id INT NOT NULL,
    diet_id INT NOT NULL,
    FOREIGN KEY (guest_id) REFERENCES guests(guest_id) ON DELETE CASCADE,
    FOREIGN KEY (diet_id) REFERENCES diets(diet_id) ON DELETE CASCADE
);

CREATE TABLE user_diets (
	user_id INT NOT NULL,
    diet_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (diet_id) REFERENCES diets(diet_id)
);

CREATE TABLE user_custom_diets (
    user_id INT NOT NULL,
    customDiets VARCHAR(100) NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT UC_user_id UNIQUE (user_id)
);

CREATE TABLE ideas (
    ideas_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    idea VARCHAR(255) NOT NULL,
    likes INT NULL DEFAULT 0,
    PRIMARY KEY (ideas_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE user_likes_ideas (
	id INT NOT NULL AUTO_INCREMENT,
	ideas_id INT NOT NULL,
    user_id INT NOT NULL,
    liked BOOLEAN NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ideas_id) REFERENCES ideas(ideas_id) ON DELETE CASCADE
);

CREATE TABLE event_feedback (
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    feedback VARCHAR(500) NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);