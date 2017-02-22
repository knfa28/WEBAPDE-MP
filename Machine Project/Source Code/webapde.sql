-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema webapde_mp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema webapde_mp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `webapde_mp` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `webapde_mp` ;

-- -----------------------------------------------------
-- Table `webapde_mp`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapde_mp`.`accounts` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webapde_mp`.`lists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapde_mp`.`lists` (
  `list_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `list_name` VARCHAR(45) NOT NULL,
  `background_color` VARCHAR(45) NOT NULL,
  `foreground_color` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`list_id`),
  UNIQUE INDEX `list_id_UNIQUE` (`list_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webapde_mp`.`tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapde_mp`.`tasks` (
  `task_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `list_id` INT UNSIGNED NOT NULL,
  `task_name` VARCHAR(45) NOT NULL,
  `priority` ENUM('LOW','NORMAL','HIGH') NOT NULL,
  `description` LONGTEXT NULL,
  `due_datetime` DATETIME NULL,
  `is_finished` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`task_id`, `list_id`),
  UNIQUE INDEX `task_id_UNIQUE` (`task_id` ASC),
  CONSTRAINT `fk_tasks_lists1`
    FOREIGN KEY (`list_id`)
    REFERENCES `webapde_mp`.`lists` (`list_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webapde_mp`.`accounts_has_lists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapde_mp`.`accounts_has_lists` (
  `username` VARCHAR(45) NOT NULL,
  `list_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`username`, `list_id`),
  INDEX `fk_accounts_has_lists_lists1_idx` (`list_id` ASC),
  CONSTRAINT `fk_accounts_has_lists_accounts1`
    FOREIGN KEY (`username`)
    REFERENCES `webapde_mp`.`accounts` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_accounts_has_lists_lists1`
    FOREIGN KEY (`list_id`)
    REFERENCES `webapde_mp`.`lists` (`list_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;