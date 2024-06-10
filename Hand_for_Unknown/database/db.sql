/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.9 : Database - handforunknown
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`handforunknown` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `handforunknown`;

/*Table structure for table `assign_games` */

DROP TABLE IF EXISTS `assign_games`;

CREATE TABLE `assign_games` (
  `ass_game_id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL,
  `child_id` int(11) DEFAULT NULL,
  `game_id` int(11) DEFAULT NULL,
  `ass_date` date DEFAULT NULL,
  PRIMARY KEY (`ass_game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `assign_games` */

insert  into `assign_games`(`ass_game_id`,`doctor_id`,`child_id`,`game_id`,`ass_date`) values (1,1,1,1,'2020-02-10'),(2,1,2,1,'2020-02-10'),(3,1,1,2,'2020-02-10');

/*Table structure for table `child` */

DROP TABLE IF EXISTS `child`;

CREATE TABLE `child` (
  `child_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `dob` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`child_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `child` */

insert  into `child`(`child_id`,`parent_id`,`name`,`gender`,`dob`) values (1,1,'Ammu','female','20/08/1996'),(2,2,'Appu','male','12/03/2001');

/*Table structure for table `doctors` */

DROP TABLE IF EXISTS `doctors`;

CREATE TABLE `doctors` (
  `doctor_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_id` int(11) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `dob` varbinary(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `doctors` */

insert  into `doctors`(`doctor_id`,`log_id`,`fname`,`lname`,`gender`,`dob`,`phone`,`email`,`qualification`) values (1,2,'Bency','Baby','female','1996-08-20','9656323070','as@dfs.sd','MD'),(2,3,'Blesson','Baby','female','2001-12-03','9656323070','as@dfs.sd','MD');

/*Table structure for table `enquiry` */

DROP TABLE IF EXISTS `enquiry`;

CREATE TABLE `enquiry` (
  `enquiry_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`enquiry_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `enquiry` */

insert  into `enquiry`(`enquiry_id`,`parent_id`,`description`,`reply`,`date`) values (1,1,'kindly inform doctor details under the category of pediatrics.','yes.','2020-02-10'),(2,2,'kindly inform the activities based on maentally disabled childs.','pending','2020-02-11');

/*Table structure for table `game_master` */

DROP TABLE IF EXISTS `game_master`;

CREATE TABLE `game_master` (
  `gm_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`gm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `game_master` */

insert  into `game_master`(`gm_id`,`game_id`,`image`,`description`) values (1,1,'static/uploads/b3b3aea9-595f-4d95-b3e2-90946ca6d40eIMG-20180809-WA0032.jpg','Identify Color.		'),(2,2,'static/uploads/d9b7a0d1-415e-4cce-bcb5-385af4d11eeaIMG-20180809-WA0032.jpg','Identify the birds.');

/*Table structure for table `game_options` */

DROP TABLE IF EXISTS `game_options`;

CREATE TABLE `game_options` (
  `goption_id` int(11) NOT NULL AUTO_INCREMENT,
  `gm_id` int(11) DEFAULT NULL,
  `option` varchar(50) DEFAULT NULL,
  `option_status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`goption_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `game_options` */

/*Table structure for table `game_result` */

DROP TABLE IF EXISTS `game_result`;

CREATE TABLE `game_result` (
  `result_id` int(11) NOT NULL AUTO_INCREMENT,
  `ass_game_id` int(11) DEFAULT NULL,
  `goption_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`result_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `game_result` */

/*Table structure for table `gameimage` */

DROP TABLE IF EXISTS `gameimage`;

CREATE TABLE `gameimage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) DEFAULT NULL,
  `image_name` varchar(500) DEFAULT NULL,
  `imagepath` varchar(500) DEFAULT NULL,
  `options` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `gameimage` */

insert  into `gameimage`(`id`,`game_id`,`image_name`,`imagepath`,`options`) values (1,3,'apple','static/uploads/acd1c532-836e-4199-a0cd-d1fc99d53d26apple.jpg','grapes,apple,orange,pappaya'),(2,3,'orange','static/uploads/577294f7-da2f-48a8-8336-48d39776b736orange.jpg','apple,grapes,orange,pappaya'),(3,3,'grapes','static/uploads/59b31acd-0ee3-4d2f-84db-224812edffadgrapes.jpg','grapes,apple,pappaya,orange'),(4,3,'pappaya','static/uploads/89854252-c85b-48fd-8647-0f5b95de23a2pappaya.jpg','pappaya,grapes,apple,orange');

/*Table structure for table `games` */

DROP TABLE IF EXISTS `games`;

CREATE TABLE `games` (
  `game_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `games` */

insert  into `games`(`game_id`,`title`,`description`) values (1,'Color','Identify Different types of colors			'),(2,'Find Birds','Identify the name of bird.');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`log_id`,`username`,`password`,`usertype`) values (1,'admin','admin','admin'),(2,'bency','bency','doctor'),(3,'bless','bless','doctor'),(4,'baby','baby','parent'),(5,'susan','susan','parent');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `mess_des` varchar(50) DEFAULT NULL,
  `mess_reply` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `message` */

insert  into `message`(`message_id`,`parent_id`,`mess_des`,`mess_reply`,`date`) values (1,1,'Hai','Hai..','2020-02-10');

/*Table structure for table `parents` */

DROP TABLE IF EXISTS `parents`;

CREATE TABLE `parents` (
  `parent_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_id` int(11) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `hname` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `pincode` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `parents` */

insert  into `parents`(`parent_id`,`log_id`,`fname`,`lname`,`hname`,`place`,`pincode`,`phone`,`email`) values (1,4,'Baby','Daniel','Modiyil','Kollakadavu','690509','9656323078','as@asd.as'),(2,5,'Susan','Baby','Modiyil','Kollakadavu','690509','9656323078','as@asd.as');

/*Table structure for table `task_assign` */

DROP TABLE IF EXISTS `task_assign`;

CREATE TABLE `task_assign` (
  `assign_id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL,
  `child_id` int(11) DEFAULT NULL,
  `assign_date` date DEFAULT NULL,
  `doctors_note` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`assign_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `task_assign` */

insert  into `task_assign`(`assign_id`,`doctor_id`,`child_id`,`assign_date`,`doctors_note`) values (1,1,1,'2020-02-11','1.Testing Memory\r\n2.IQ Testing\r\n		\r\n				\r\n				');

/*Table structure for table `task_performance` */

DROP TABLE IF EXISTS `task_performance`;

CREATE TABLE `task_performance` (
  `per_id` int(11) NOT NULL AUTO_INCREMENT,
  `assign_id` int(11) DEFAULT NULL,
  `per_des` varchar(100) DEFAULT NULL,
  `per_date` date DEFAULT NULL,
  PRIMARY KEY (`per_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `task_performance` */

insert  into `task_performance`(`per_id`,`assign_id`,`per_des`,`per_date`) values (1,1,'1.Based on their activity.		','2020-02-10'),(2,1,'1.Based on their activity.			','2020-02-10');

/*Table structure for table `tasks` */

DROP TABLE IF EXISTS `tasks`;

CREATE TABLE `tasks` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `tasks` */

insert  into `tasks`(`task_id`,`title`,`description`) values (1,'Memory Assessment','Assessment of memory for each child based on certain criteria.'),(2,'IQ','Based on their activity.		');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
