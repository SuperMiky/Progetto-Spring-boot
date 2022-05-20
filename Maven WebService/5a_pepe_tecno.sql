-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 12, 2022 alle 22:31
-- Versione del server: 10.4.21-MariaDB
-- Versione PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `5a_pepe_tecno`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `stringhe`
--

CREATE TABLE `stringhe` (
  `Id` int(11) NOT NULL,
  `Token` char(32) NOT NULL,
  `Identificativo` varchar(228) NOT NULL,
  `Contenuto` varchar(228) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `stringhe`
--

INSERT INTO `stringhe` (`Id`, `Token`, `Identificativo`, `Contenuto`) VALUES
(2, 'uA86W3WNLNkxK9qO5WD9zotu6Lp2LldY', 'Chiave2', 'contenuto2'),
(3, 'uA86W3WNLNkxK9qO5WD9zotu6Lp2LldY', 'chiave3', 'contenuto3');

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `Id` int(11) NOT NULL,
  `Username` varchar(228) NOT NULL,
  `Password` char(32) NOT NULL,
  `Token` char(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`Id`, `Username`, `Password`, `Token`) VALUES
(5, 'pippo', 'd8578edf8458ce06fbc5bb76a58c5ca4', 'uA86W3WNLNkxK9qO5WD9zotu6Lp2LldY');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `stringhe`
--
ALTER TABLE `stringhe`
  ADD PRIMARY KEY (`Id`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `stringhe`
--
ALTER TABLE `stringhe`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `utenti`
--
ALTER TABLE `utenti`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
