-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 26, 2015 at 02:02 AM
-- Server version: 5.5.43-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `housing`
--

-- --------------------------------------------------------

--
-- Table structure for table `property`
--

CREATE TABLE IF NOT EXISTS `property` (
  `propid` int(11) NOT NULL AUTO_INCREMENT,
  `ownerid` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `bhk` int(11) NOT NULL,
  `lat` double(10,7) NOT NULL,
  `lon` double(10,7) NOT NULL,
  `numppl` int(11) NOT NULL,
  `rentable` int(11) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `address` text NOT NULL,
  PRIMARY KEY (`propid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `property`
--

INSERT INTO `property` (`propid`, `ownerid`, `price`, `bhk`, `lat`, `lon`, `numppl`, `rentable`, `description`, `address`) VALUES
(4, 1, 10000, 3, 23.0000000, 77.0000000, 4, 0, 'PH: +9190362610', 'Opp. Shriram apts., S S A Road, Hebbal, Bangalore, Karnataka.'),
(5, 1, 5000, 2, 24.0000000, 76.0000000, 4, 0, 'PG to-let.\r\n\r\nPH: +9181475654', 'Shivananda Circle, Race Course Road, Bangalore, Karnataka.');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
