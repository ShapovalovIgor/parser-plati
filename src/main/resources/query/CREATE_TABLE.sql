CREATE TABLE user (id BIGINT NOT NULL,
                    PRIMARY KEY (id));
CREATE TABLE product (id BIGINT NOT NULL,
                      name_goods VARCHAR(100) DEFAULT NULL,
                      lastname DOUBLE (50) DEFAULT NULL,
                      secondname DOUBLE(50) DEFAULT NULL,
                      dob DATE DEFAULT NULL,
                      group_id INTEGER DEFAULT NULL,
                      FOREIGN KEY (group_id) REFERENCES user(id));
SHUTDOWN;