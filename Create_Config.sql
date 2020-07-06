CREATE TABLE config (id_config INT NOT NULL auto_increment, name_database VARCHAR(255) NOT NULL, username_database VARCHAR(255) NOT NULL, password_database VARCHAR(255) NOT NULL, query VARCHAR(255),
username_data VARCHAR(255),  password_data VARCHAR(255), file_format VARCHAR(255), remote_dir VARCHAR(255), local_dir VARCHAR(255) , PRIMARY KEY (id_config));

INSERT INTO config (name_database,username_database, password_database, query, username_data, password_data, file_format, remote_dir, local_dir) 
VALUES ("controll", "root", "", "", "guest_access", "123456", "sinhvien_sang_*.xlsx, sinhvien_chieu_*.xlsx", "/volume1/ECEP/song.nguyen/DW_2020/data", "data");

INSERT INTO config (name_database,username_database, password_database, query) 
VALUES ("control_db", "root", "", "insert into log (urlLocal, status_download, status_upload_staging) values (?, ?, ?, ?)");

INSERT INTO config (name_database,username_database, password_database, query) 
VALUES ("control_db", "root", "", "SELECT * FROM log");

INSERT INTO config (name_database,username_database, password_database, query) 
VALUES ("staging", "root", "", "INSERT INTO student (MSSV, ho, ten, dob, lop, tenlop, sdt, email, quequan, ghichu, id_log) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

INSERT INTO config (name_database,username_database, password_database, query) 
VALUES ("control_db", "root", "", "UPDATE log SET status_upload_staging = ?, WHERE id_log = ?");

INSERT INTO config (name_database, username_database, password_database, query) 
VALUES ("staging", "root", "", "SELECT * from student WHERE id_log = ?");

INSERT INTO config (name_database,username_database, password_database, query) 
VALUES ("warehouse", "root", "", "INSERT INTO student (MSSV, ho, ten, dob, lop, tenlop, sdt, email, quequan, ghichu) VALUES (?,?,?,?,?,?,?,?,?,?)");
