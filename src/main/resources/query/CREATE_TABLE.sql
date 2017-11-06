CREATE TABLE student_group (id BIGINT NOT NULL,
                    number INTEGER DEFAULT NULL,
                    faculty VARCHAR(200) DEFAULT NULL,
                    PRIMARY KEY (id));
CREATE TABLE student (id BIGINT NOT NULL,
                      firstname VARCHAR(50) DEFAULT NULL,
                      lastname VARCHAR(50) DEFAULT NULL,
                      secondname VARCHAR(50) DEFAULT NULL,
                      dob DATE DEFAULT NULL,
                      group_id INTEGER DEFAULT NULL,
                      FOREIGN KEY (group_id) REFERENCES student_group(id));