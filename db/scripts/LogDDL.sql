DROP TABLE IF EXISTS t_log_report_detail;
DROP TABLE IF EXISTS t_report;
DROP TABLE IF EXISTS t_log;

CREATE TABLE t_log (
	log_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	ip_address VARCHAR(15) NOT NULL,
	request_time DATETIME NOT NULL,
	request VARCHAR(512) NOT NULL,
	server_status SMALLINT UNSIGNED NOT NULL,
	user_agent VARCHAR(512) NOT NULL,
	log_number INT UNSIGNED NOT NULL,

	PRIMARY KEY (log_id),
	INDEX (ip_address)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE t_report (
	report_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	start_date DATETIME NOT NULL,
	duration ENUM('hourly', 'daily') NOT NULL DEFAULT 'hourly',
	threshold INT(11) UNSIGNED NOT NULL,
	report_comment VARCHAR(64) NOT NULL,
	report_number INT UNSIGNED NOT NULL,

	PRIMARY KEY (report_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE t_log_report_detail (
	log_id INT(11) UNSIGNED NOT NULL,
	report_id INT(11) UNSIGNED NOT NULL,

	PRIMARY KEY (log_id, report_id),
	CONSTRAINT fk_log FOREIGN KEY (log_id)
	REFERENCES t_log(log_id)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT,
	CONSTRAINT fk_report FOREIGN KEY (report_id)
	REFERENCES t_report(report_id)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;