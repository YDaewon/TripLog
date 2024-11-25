-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema triplog
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema triplog
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `triplog` DEFAULT CHARACTER SET utf8mb3 ;
USE `triplog` ;

-- -----------------------------------------------------
-- Table `triplog`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`users` (
  `user_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `user_pwd` VARCHAR(20) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `user_image` VARCHAR(100) NULL DEFAULT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `email_id` VARCHAR(45) NOT NULL,
  `email_domain` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` TIMESTAMP NULL,
  `Token` VARCHAR(500) NULL,
  PRIMARY KEY (`user_no`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `triplog`.`plans`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`plans` (
  `plan_no` INT NOT NULL AUTO_INCREMENT,
  `user_no` INT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` TIMESTAMP NULL,
  `description` VARCHAR(100) NULL DEFAULT '',
  `fork_count` INT NOT NULL DEFAULT 0,
  `start_at` DATE NOT NULL,
  `end_at` DATE NOT NULL,
  PRIMARY KEY (`plan_no`),
  INDEX `fk_plan_user1_idx` (`user_no` ASC) VISIBLE,
  CONSTRAINT `fk_plan_user1`
    FOREIGN KEY (`user_no`)
    REFERENCES `triplog`.`users` (`user_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `triplog`.`articles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`articles` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `plan_no` INT NULL,
  `user_no` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `content` VARCHAR(10000) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` TIMESTAMP NULL,
  `hit_count` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`article_no`),
  INDEX `fk_article_plan_plan_idx` (`plan_no` ASC) VISIBLE,
  INDEX `fk_article_plan_user1_idx` (`user_no` ASC) VISIBLE,
  CONSTRAINT `fk_article_plan_plan`
    FOREIGN KEY (`plan_no`)
    REFERENCES `triplog`.`plans` (`plan_no`),
  CONSTRAINT `fk_article_plan_user1`
    FOREIGN KEY (`user_no`)
    REFERENCES `triplog`.`users` (`user_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `triplog`.`sidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`sidos` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sido_code` INT NULL DEFAULT NULL,
  `sido_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `triplog`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`guguns` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `gugun_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `guguns_sido_to_sidos_cdoe_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `triplog`.`sidos` (`sido_code`))
ENGINE = InnoDB
AUTO_INCREMENT = 235
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `triplog`.`contenttypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`contenttypes` (
  `content_type_id` INT NOT NULL,
  `content_type_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `triplog`.`attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`attractions` (
  `attraction_no` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(500) NULL DEFAULT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `area_code` INT NULL DEFAULT NULL,
  `si_gun_gu_code` INT NULL DEFAULT NULL,
  `first_image1` VARCHAR(100) NULL DEFAULT NULL,
  `first_image2` VARCHAR(100) NULL DEFAULT NULL,
  `map_level` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `tel` VARCHAR(20) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(100) NULL DEFAULT NULL,
  `homepage` VARCHAR(1000) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  PRIMARY KEY (`attraction_no`),
  INDEX `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attractions_sido_to_sidos_code_fk_idx` (`area_code` ASC) VISIBLE,
  INDEX `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code` ASC) VISIBLE,
  CONSTRAINT `attractions_area_to_sidos_code_fk`
    FOREIGN KEY (`area_code`)
    REFERENCES `triplog`.`sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `triplog`.`guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `triplog`.`contenttypes` (`content_type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 56644
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `triplog`.`hit_attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`hit_attractions` (
  `hit_no` INT NOT NULL AUTO_INCREMENT,
  `user_no` INT NOT NULL,
  `destinaion_no` INT NOT NULL,
  `attractions_title` VARCHAR(200) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`hit_no`),
  INDEX `fk_views_destinations1_idx` (`destinaion_no` ASC) VISIBLE,
  INDEX `fk_views_user1_idx` (`user_no` ASC) VISIBLE,
  CONSTRAINT `fk_views_destinations1`
    FOREIGN KEY (`destinaion_no`)
    REFERENCES `triplog`.`attractions` (`attraction_no`),
  CONSTRAINT `fk_views_user1`
    FOREIGN KEY (`user_no`)
    REFERENCES `triplog`.`users` (`user_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `triplog`.`hit_articles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`hit_articles` (
  `hit_no` INT NOT NULL AUTO_INCREMENT,
  `user_no` INT NOT NULL,
  `article_no` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`hit_no`),
  INDEX `fk_hit_plan_user1` (`user_no` ASC) VISIBLE,
  INDEX `fk_hit_article_article1_idx` (`article_no` ASC) VISIBLE,
  CONSTRAINT `fk_hit_plan_user1`
    FOREIGN KEY (`user_no`)
    REFERENCES `triplog`.`users` (`user_no`),
  CONSTRAINT `fk_hit_article_article1`
    FOREIGN KEY (`article_no`)
    REFERENCES `triplog`.`articles` (`article_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `triplog`.`plan_destinations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`plan_destinations` (
  `destination_no` INT NOT NULL AUTO_INCREMENT,
  `attraction_no` INT NOT NULL,
  `plan_no` INT NOT NULL,
  `visit_date` DATE NOT NULL,
  `destination_order` INT NOT NULL DEFAULT '99999',
  `memo` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`destination_no`),
  INDEX `fk_destinations_attractions1_idx` (`attraction_no` ASC) VISIBLE,
  INDEX `fk_plan_destinations_plan1_idx` (`plan_no` ASC) VISIBLE,
  CONSTRAINT `fk_destinations_attractions1`
    FOREIGN KEY (`attraction_no`)
    REFERENCES `triplog`.`attractions` (`attraction_no`),
  CONSTRAINT `fk_plan_destinations_plan1`
    FOREIGN KEY (`plan_no`)
    REFERENCES `triplog`.`plans` (`plan_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `triplog`.`article_stars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`article_stars` (
  `article_no` INT NOT NULL,
  `user_no` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `fk_plan_stars_user1_idx` (`user_no` ASC) VISIBLE,
  INDEX `fk_plan_stars_article_plan1_idx` (`article_no` ASC) VISIBLE,
  PRIMARY KEY (`article_no`, `user_no`),
  CONSTRAINT `fk_plan_stars_article_plan1`
    FOREIGN KEY (`article_no`)
    REFERENCES `triplog`.`articles` (`article_no`),
  CONSTRAINT `fk_plan_stars_user1`
    FOREIGN KEY (`user_no`)
    REFERENCES `triplog`.`users` (`user_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `triplog`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`notice` (
  `notice_no` INT NOT NULL AUTO_INCREMENT,
  `user_no` INT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `content` VARCHAR(10000) NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` TIMESTAMP NULL,
  PRIMARY KEY (`notice_no`),
  INDEX `fk_notice_user1_idx` (`user_no` ASC) VISIBLE,
  CONSTRAINT `fk_notice_user1`
    FOREIGN KEY (`user_no`)
    REFERENCES `triplog`.`users` (`user_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triplog`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triplog`.`comment` (
  `comment_no` INT NOT NULL AUTO_INCREMENT,
  `article_no` INT NOT NULL,
  `user_no` INT NOT NULL,
  `content` VARCHAR(200) NOT NULL,
  `parent` INT NULL,
  `create_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_at` TIMESTAMP NULL,
  PRIMARY KEY (`comment_no`),
  INDEX `fk_comment_articles1_idx` (`article_no` ASC) VISIBLE,
  INDEX `fk_comment_users1_idx` (`user_no` ASC) VISIBLE,
  CONSTRAINT `fk_comment_articles1`
    FOREIGN KEY (`article_no`)
    REFERENCES `triplog`.`articles` (`article_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_users1`
    FOREIGN KEY (`user_no`)
    REFERENCES `triplog`.`users` (`user_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
