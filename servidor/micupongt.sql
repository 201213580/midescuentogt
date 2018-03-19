-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-03-2018 a las 12:09:24
-- Versión del servidor: 10.1.21-MariaDB
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `micupongt`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `cargar_codigo` (IN `Codi` VARCHAR(255), IN `Esta` INT, IN `Fech` DATE, IN `Promo` INT, IN `Usu` INT)  NO SQL
INSERT INTO `usuario_promocion`(`Codigo`, `Estado`, `Fecha`, `Promocion_id_promocion`, `Usuario_Id`) VALUES (Codi,Esta,Fech,Promo,Usu)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Crear_Usuarios` (IN `Nom` VARCHAR(255), IN `Usu` VARCHAR(255), IN `Corre` VARCHAR(255), IN `Contr` VARCHAR(255))  NO SQL
insert into usuario(Nombre,Usuario,Correo,Contra)values(Nom,Usu,Corre,Contr)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `login` (IN `usu` VARCHAR(255), IN `contra` VARCHAR(255))  NO SQL
select 1 from usuario where usuario=usu and Contra=contra$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `obtener_id` (IN `usu` VARCHAR(255))  NO SQL
SELECT Id_Usuario FROM `usuario` WHERE Usuario=usu$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `promocion` ()  NO SQL
select * from promocion$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `promocion_contenido` (IN `t` INT)  NO SQL
select * from promocion where tipo=t$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `recuperar` (IN `corr` VARCHAR(255))  NO SQL
select * from usuario where correo=corr$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `verificacion_correo` (IN `corr` VARCHAR(255))  NO SQL
Select 1 from usuario where Correo=corr$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `verificar_usuario` (IN `usu` VARCHAR(255))  NO SQL
Select 1 from usuario where Usuario=usu$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promocion`
--

CREATE TABLE `promocion` (
  `id_promocion` int(11) NOT NULL,
  `fecha` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `contenido` varchar(500) DEFAULT NULL,
  `ruta` varchar(255) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `nombre_empresa` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `region` int(11) DEFAULT NULL,
  `imagen2` varchar(255) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `promocion`
--

INSERT INTO `promocion` (`id_promocion`, `fecha`, `titulo`, `contenido`, `ruta`, `imagen`, `nombre_empresa`, `direccion`, `region`, `imagen2`, `tipo`) VALUES
(1, 'Valido hasta 12-12-2017', 'La Casa del Agricultor', 'Descuento del 10% en productos seleccionados', 'http://192.168.0.13/recursos/', 'descarga.jpg', 'La casa del Agricultor', 'San Pedro Sac. San Marcos', 0, 'agricultor2.jpg', 1),
(2, 'Valido hasta 12-12-2017', 'La Casa del Agricultor', 'Descuento del 10% en productos seleccionados', 'http://192.168.0.13/recursos/', 'descarga.jpg', 'La casa del Agricultor', 'San Pedro Sac. San Marcos', 0, 'agricultor2.jpg', 1),
(3, 'Valido hasta 12-12-2017', 'Chilero y Rustico', 'Descuento del 10% en productos seleccionados', 'http://192.168.0.13/recursos/', '1.png', 'Chilero y Rustico', 'San Pedro Sac. San Marcos', 0, 'agricultor2.jpg', 0),
(4, 'Valido hasta 12-12-2017', 'La Casa del Agricultor', 'Descuento del 10% en productos seleccionados', 'http://192.168.0.13/recursos/', 'descarga.jpg', 'La casa del Agricultor', 'San Pedro Sac. San Marcos', 0, 'agricultor2.jpg', 0),
(5, 'Valido hasta 12-12-2017', 'Chilero y Rustico', 'Descuento del 10% en productos seleccionados', 'http://192.168.0.13/recursos/', '1.png', 'Chilero y Rustico', 'San Pedro Sac. San Marcos', 0, 'agricultor2.jpg', 0),
(6, 'Valido hasta 12-12-2017', 'La Casa del Agricultor', 'Descuento del 10% en productos seleccionados', 'http://192.168.0.13/recursos/', 'descarga.jpg', 'La casa del Agricultor', 'San Pedro Sac. San Marcos', 0, 'agricultor2.jpg', 0),
(7, 'Valido hasta 12-12-2017', 'Chilero y Rustico', 'Descuento del 10% en productos seleccionados', 'http://192.168.0.13/recursos/', '1.png', 'Chilero y Rustico', 'San Pedro Sac. San Marcos', 0, 'agricultor2.jpg', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `Id_Usuario` int(11) NOT NULL,
  `Nombre` varchar(255) DEFAULT NULL,
  `Usuario` varchar(255) DEFAULT NULL,
  `Correo` varchar(255) DEFAULT NULL,
  `Contra` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`Id_Usuario`, `Nombre`, `Usuario`, `Correo`, `Contra`) VALUES
(10, 'Manuel Fuentes', 'manuel13580@gmail.com', 'manuel13580@gmail.com', '105127730594923250676'),
(11, 'Manuel Antonio Fuentes Fuentes', 'chileroyrustico@gmail.com', 'chileroyrustico@gmail.com', '102362255542714721140'),
(12, 'Manuel Antonio Fuentes Fuentes', 'soportemicupongt@gmail.com', 'soportemicupongt@gmail.com', '115760378352745876922');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_promocion`
--

CREATE TABLE `usuario_promocion` (
  `Id_UsuPromo` int(11) NOT NULL,
  `Codigo` varchar(255) NOT NULL,
  `Estado` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Promocion_id_promocion` int(11) NOT NULL,
  `Usuario_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario_promocion`
--

INSERT INTO `usuario_promocion` (`Id_UsuPromo`, `Codigo`, `Estado`, `Fecha`, `Promocion_id_promocion`, `Usuario_Id`) VALUES
(307, 'E10125', 0, '2018-01-24', 1, 10),
(308, 'E10225', 0, '2018-01-24', 2, 10),
(309, 'E10325', 0, '2018-01-24', 3, 10),
(310, 'E10425', 0, '2018-01-24', 4, 10),
(311, 'E10525', 0, '2018-01-24', 5, 10),
(312, 'E10625', 0, '2018-01-24', 6, 10),
(313, 'E10725', 0, '2018-01-24', 7, 10),
(314, 'E10126', 0, '2018-01-25', 1, 10),
(315, 'E10226', 0, '2018-01-25', 2, 10),
(316, 'E10326', 0, '2018-01-25', 3, 10),
(317, 'E10426', 0, '2018-01-25', 4, 10),
(318, 'E10526', 0, '2018-01-25', 5, 10),
(319, 'E10626', 0, '2018-01-25', 6, 10),
(320, 'E10726', 0, '2018-01-25', 7, 10),
(439, 'E11126', 0, '2018-01-25', 1, 11),
(440, 'E11226', 0, '2018-01-25', 2, 11),
(441, 'E11326', 0, '2018-01-25', 3, 11),
(442, 'E11426', 0, '2018-01-25', 4, 11),
(443, 'E11526', 0, '2018-01-25', 5, 11),
(444, 'E11626', 0, '2018-01-25', 6, 11),
(445, 'E11726', 0, '2018-01-25', 7, 11),
(456, 'E12126', 0, '2018-01-25', 1, 12),
(457, 'E12226', 0, '2018-01-25', 2, 12),
(458, 'E12326', 0, '2018-01-25', 3, 12),
(459, 'E12426', 0, '2018-01-25', 4, 12),
(460, 'E12526', 0, '2018-01-25', 5, 12),
(461, 'E12626', 0, '2018-01-25', 6, 12),
(462, 'E12726', 0, '2018-01-25', 7, 12),
(463, 'E10131', 0, '2018-01-30', 1, 10),
(464, 'E10231', 0, '2018-01-30', 2, 10),
(465, 'E10331', 0, '2018-01-30', 3, 10),
(466, 'E10431', 0, '2018-01-30', 4, 10),
(467, 'E10531', 0, '2018-01-30', 5, 10),
(468, 'E10631', 0, '2018-01-30', 6, 10),
(469, 'E10731', 0, '2018-01-30', 7, 10),
(470, 'E10132', 0, '2018-01-31', 1, 10),
(471, 'E10232', 0, '2018-01-31', 2, 10),
(472, 'E10332', 0, '2018-01-31', 3, 10),
(473, 'E10432', 0, '2018-01-31', 4, 10),
(474, 'E10532', 0, '2018-01-31', 5, 10),
(475, 'E10632', 0, '2018-01-31', 6, 10),
(476, 'E10732', 0, '2018-01-31', 7, 10),
(477, 'F1016', 0, '2018-02-04', 1, 10),
(478, 'F1026', 0, '2018-02-04', 2, 10),
(479, 'F1036', 0, '2018-02-04', 3, 10),
(480, 'F1046', 0, '2018-02-04', 4, 10),
(481, 'F1056', 0, '2018-02-04', 5, 10),
(482, 'F1066', 0, '2018-02-04', 6, 10),
(483, 'F1076', 0, '2018-02-04', 7, 10),
(484, 'M10122', 0, '2018-03-19', 1, 10),
(485, 'M10222', 0, '2018-03-19', 2, 10),
(486, 'M10322', 0, '2018-03-19', 3, 10),
(487, 'M10422', 0, '2018-03-19', 4, 10),
(488, 'M10522', 0, '2018-03-19', 5, 10),
(489, 'M10622', 0, '2018-03-19', 6, 10),
(490, 'M10722', 0, '2018-03-19', 7, 10),
(520, 'M11122', 0, '2018-03-19', 1, 11),
(521, 'M11222', 0, '2018-03-19', 2, 11),
(522, 'M11322', 0, '2018-03-19', 3, 11),
(523, 'M11422', 0, '2018-03-19', 4, 11),
(524, 'M11522', 0, '2018-03-19', 5, 11),
(525, 'M11622', 0, '2018-03-19', 6, 11),
(526, 'M11722', 0, '2018-03-19', 7, 11);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `promocion`
--
ALTER TABLE `promocion`
  ADD PRIMARY KEY (`id_promocion`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Id_Usuario`);

--
-- Indices de la tabla `usuario_promocion`
--
ALTER TABLE `usuario_promocion`
  ADD PRIMARY KEY (`Id_UsuPromo`),
  ADD UNIQUE KEY `Codigo` (`Codigo`),
  ADD KEY `fk_Usuario_Promocion_Promocion_id` (`Promocion_id_promocion`),
  ADD KEY `fk_Usuario_Promocion_Usuario1_id` (`Usuario_Id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `promocion`
--
ALTER TABLE `promocion`
  MODIFY `id_promocion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `Id_Usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT de la tabla `usuario_promocion`
--
ALTER TABLE `usuario_promocion`
  MODIFY `Id_UsuPromo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=562;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `usuario_promocion`
--
ALTER TABLE `usuario_promocion`
  ADD CONSTRAINT `fk_Usuario_Promocion_Promocion` FOREIGN KEY (`Promocion_id_promocion`) REFERENCES `promocion` (`id_promocion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Usuario_Promocion_Usuario1` FOREIGN KEY (`Usuario_Id`) REFERENCES `usuario` (`Id_Usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
