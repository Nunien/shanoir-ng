-- Populates database
-- Has to be executed manually inside the docker container, command example : mysql -u {db user name} -p{password} < populate.sql
-- ! But remember to wait for the java web server to have started since the schema is created by hibernate on startup !
--
use studies;

INSERT INTO study
	(id, clinical, coordinator_id, downloadable_by_default, end_date, mono_center, name, start_date, study_status, study_type, visible_by_default, with_examination)
VALUES
	(1, '\0', NULL, '\0', NULL, 1, 'NATIVE Divers', '2009-11-02 00:00:00', 1, 3, '\0', 1),
	(2, 1, 2, '\0', '2015-12-31 00:00:00', '\0', 'USPIO-6', '2009-07-01 00:00:00', 1, 1, '\0', 1),
	(3, '\0', NULL, '\0', NULL, 1, 'Phantom Qualite', NULL, 1, 3, '\0', 1),
	(4, '\0', NULL, '\0', NULL, 1, 'AtlasDIR@Neurinfo', NULL, 1, 3, '\0', 1),
	(5, '\0', NULL, '\0', NULL, 1, 'DIR@Epilepsie', NULL, 1, 3, '\0', 1),
	(6, '\0', NULL, '\0', NULL, 1, 'Emodes', NULL, 1, 3, '\0', 1),
	(7, '\0', NULL, '\0', NULL, 1, 'Moelle', NULL, 1, 3, '\0', 1),
	(9, '\0', NULL, '\0', NULL, 1, '3D T1 ', NULL, 1, 3, '\0', '\0'),
	(10, '\0', NULL, '\0', NULL, 1, 'CIS DIR', NULL, 1, 3, '\0', 1),
	(11, '\0', NULL, '\0', NULL, 1, 'Transfert', NULL, 1, 3, '\0', '\0'),
	(12, '\0', NULL, '\0', NULL, 1, 'IRMf ', NULL, 1, 3, '\0', 1),
	(13, '\0', NULL, '\0', NULL, 1, 'isaSegTum', '2010-12-02 00:00:00', 1, 3, '\0', 1),
	(14, 1, NULL, '\0', NULL, 1, 'asl_SEP', '2011-01-17 00:00:00', 1, 1, '\0', 1),
	(15, '\0', NULL, '\0', NULL, 1, 'asl_CTL', '2011-01-24 00:00:00', 1, 3, '\0', 1),
	(17, '\0', NULL, '\0', NULL, 1, 'DIFF_ISCHEMIE', NULL, 1, 3, '\0', 1),
	(18, 1, NULL, '\0', NULL, 1, 'USPIO-6 ASL', NULL, 1, 1, '\0', 1),
	(19, 1, NULL, '\0', '2012-04-01 00:00:00', 1, 'transIRMf', '2011-03-01 00:00:00', 2, 1, '\0', 1),
	(20, '\0', NULL, '\0', NULL, 1, 'AngioIRM_NATIVE_QISS', NULL, 1, 3, '\0', 1),
	(21, '\0', NULL, '\0', NULL, 1, 'FIM', '2011-05-03 00:00:00', 1, 3, '\0', 1),
	(22, '\0', NULL, '\0', NULL, 1, 'DEPRESIST', NULL, 1, 3, '\0', 1),
	(23, '\0', NULL, '\0', NULL, 1, 'FIM ft', NULL, 1, 3, '\0', 1),
	(24, '\0', NULL, '\0', NULL, 1, 'Jonin', NULL, 1, 3, '\0', 1),
	(25, '\0', NULL, '\0', NULL, 1, 'DEEP', NULL, 1, 3, '\0', 1),
	(26, 1, NULL, '\0', '2010-01-29 00:00:00', 1, 'ASL IP', '2009-12-01 00:00:00', 2, 1, '\0', 1),
	(27, 1, NULL, '\0', '2010-01-29 00:00:00', 1, 'ASLf mt', '2009-12-01 00:00:00', 2, 1, '\0', 1),
	(28, 1, NULL, '\0', NULL, 1, 'asl_tum', NULL, 1, 1, '\0', 1),
	(29, 1, NULL, '\0', NULL, 1, 'asl_vasc', NULL, 1, 1, '\0', 1),
	(30, '\0', NULL, '\0', NULL, 1, 'PNGI', NULL, 1, 3, '\0', 1),
	(31, '\0', NULL, '\0', NULL, 1, 'DIR EP CLIN', NULL, 1, 3, '\0', 1),
	(32, '\0', NULL, '\0', NULL, 1, 'ASL SENSE 2', NULL, 2, 3, '\0', '\0'),
	(33, '\0', 22, '\0', '2013-03-14 00:00:00', '\0', 'OFSEP_Test_Lyon', '2012-12-14 00:00:00', 2, 1, '\0', 1),
	(34, '\0', 2, '\0', NULL, '\0', 'iQALY-SEP', NULL, 1, 3, '\0', 1),
	(35, '\0', NULL, '\0', NULL, 1, 'AINSI-GIN', NULL, 2, 1, '\0', 1),
	(36, '\0', NULL, '\0', NULL, 1, 'PerfT1', NULL, 1, 3, '\0', 1),
	(37, '\0', NULL, '\0', NULL, 1, 'ASL DEM', NULL, 1, 3, '\0', 1),
	(38, '\0', NULL, '\0', NULL, 1, 'Dysphasie', NULL, 2, 3, '\0', 1),
	(39, '\0', NULL, '\0', NULL, 1, 'NCE MRA', '2011-12-01 00:00:00', 1, 3, '\0', 1),
	(40, '\0', NULL, '\0', NULL, 1, 'VPIPRO', NULL, 1, 3, '\0', 1),
	(41, '\0', NULL, '\0', NULL, 1, 'MTX_sep', NULL, 1, 3, '\0', 1),
	(42, '\0', NULL, '\0', NULL, 1, 'SURFER', NULL, 1, 3, '\0', 1),
	(43, '\0', 1, '\0', NULL, '\0', 'AINSI', NULL, 1, 3, '\0', 1),
	(44, '\0', NULL, '\0', NULL, 1, '1RO100', NULL, 1, 3, '\0', 1),
	(45, '\0', 37, '\0', NULL, '\0', 'CoCoA', '2012-01-03 00:00:00', 1, 3, '\0', 1),
	(46, '\0', 37, '\0', NULL, '\0', 'Synesthesia', '2012-01-05 00:00:00', 2, 1, '\0', 1),
	(47, '\0', NULL, '\0', NULL, 1, 'HYP_T2', NULL, 1, 3, '\0', 1),
	(48, '\0', NULL, '\0', NULL, 1, 'DEPAPATHIE', '2012-07-13 00:00:00', 1, 3, '\0', 1),
	(49, '\0', NULL, '\0', NULL, 1, 'AfaCorVis3D', '2012-07-16 00:00:00', 1, 3, '\0', 1),
	(50, '\0', NULL, '\0', '2013-08-30 00:00:00', 1, 'DIVASL', '2012-07-25 00:00:00', 2, 3, '\0', 1),
	(51, '\0', NULL, '\0', NULL, 1, 'IQALY-SEP+', NULL, 1, 3, '\0', 1),
	(52, '\0', NULL, '\0', '2012-09-01 00:00:00', 1, 'MIDI', '2012-08-02 00:00:00', 2, 3, '\0', 1),
	(53, '\0', NULL, '\0', '2011-04-01 00:00:00', 1, 'Emodes_Pilote', '2010-01-01 00:00:00', 2, 1, '\0', 1),
	(54, '\0', NULL, '\0', '2012-08-09 00:00:00', 1, 'ATLDIF', '2012-08-08 00:00:00', 2, 3, '\0', 1),
	(55, '\0', NULL, '\0', NULL, 1, 'AVCPOSTIM', '2012-09-13 00:00:00', 1, 3, '\0', 1),
	(56, '\0', NULL, '\0', NULL, 1, 'EMOCAR', NULL, 1, 3, '\0', 1),
	(57, '\0', NULL, '\0', NULL, 1, 'MALTA', '2012-11-14 00:00:00', 1, 3, '\0', 1),
	(58, '\0', NULL, '\0', NULL, 1, 'NCE MRA PHANTOM', NULL, 1, 3, '\0', 1),
	(59, 1, 2, '\0', '2015-12-31 00:00:00', '\0', 'USPIO-6 C', '2009-07-01 00:00:00', 1, 1, '\0', 1),
	(60, '\0', 2, '\0', '2013-07-31 00:00:00', '\0', 'OFSEP_Pilot_Marseille', '2013-01-01 00:00:00', 2, 3, '\0', 1),
	(61, 1, NULL, '\0', NULL, 1, 'HEPAT_M', '2013-01-10 00:00:00', 1, 1, '\0', 1),
	(62, '\0', NULL, '\0', NULL, 1, 'OFSEP_Pilot_U825', '2013-01-16 00:00:00', 1, 3, '\0', 1),
	(63, 1, NULL, '\0', NULL, 1, 'GRECCAR 4', NULL, 1, 1, '\0', 1),
	(64, 1, 2, '\0', '2013-05-06 00:00:00', '\0', 'OFSEP_Pilot_Rennes', '2013-12-02 00:00:00', 2, 1, '\0', 1),
	(65, 1, NULL, '\0', NULL, 1, 'OFSEP_Pilot_Vannes', '2013-01-14 00:00:00', 1, 1, '\0', 1),
	(66, 1, NULL, '\0', NULL, 1, 'TMS Depression', NULL, 1, 1, '\0', 1),
	(67, 1, NULL, '\0', NULL, 1, 'OFSEP_Pilot_Reims', '2013-01-29 00:00:00', 1, 1, '\0', 1),
	(68, '\0', NULL, '\0', NULL, 1, 'OFSEP_Pilot_Paris', '2013-02-01 00:00:00', 1, 3, '\0', 1),
	(69, '\0', NULL, '\0', NULL, 1, 'EPI-DISTO', '2013-02-05 00:00:00', 1, 3, '\0', 1),
	(70, '\0', NULL, '\0', NULL, 1, 'SSS-DIMO', NULL, 1, 3, '\0', 1),
	(71, '\0', NULL, '\0', NULL, 1, 'Methodo ASL', '2013-02-18 00:00:00', 1, 3, '\0', 1),
	(72, '\0', NULL, '\0', '2013-05-06 00:00:00', 1, 'OFSEP_Rennes', '2013-01-07 00:00:00', 2, 3, '\0', 1),
	(73, '\0', NULL, '\0', '2013-05-06 00:00:00', 1, 'OFSEP_Grenoble', '2013-02-04 00:00:00', 2, 1, '\0', 1),
	(74, '\0', NULL, '\0', NULL, 1, 'HORAW', NULL, 1, 3, '\0', 1),
	(75, '\0', NULL, '\0', '2013-05-06 00:00:00', 1, 'OFSEP_Pilot_CHLS', '2013-02-04 00:00:00', 2, 1, '\0', 1),
	(76, 1, 71, '\0', '2016-12-30 00:00:00', 1, 'PSY MORPHO ASL', '2013-03-06 00:00:00', 1, 1, '\0', 1),
	(77, '\0', 5, '\0', NULL, '\0', 'MS-REPAIR', NULL, 1, 3, '\0', 1),
	(78, '\0', NULL, '\0', '2013-05-06 00:00:00', 1, 'OFSEP_Pilot_Hopital-Neuro', '2013-03-12 00:00:00', 2, 1, '\0', 1),
	(79, '\0', NULL, '\0', '2013-05-06 00:00:00', 1, 'OFSEP_Pilot_Bordeaux', '2013-02-04 00:00:00', 2, 1, '\0', 1),
	(80, '\0', NULL, '\0', '2014-10-31 00:00:00', 1, 'AVCPOSTIM MORPHO', '2013-10-11 00:00:00', 1, 3, '\0', 1),
	(81, '\0', NULL, '\0', NULL, 1, 'CAPP-CATI', '2013-01-01 00:00:00', 1, 3, '\0', 1),
	(82, '\0', 80, '\0', NULL, '\0', 'MS-SPI', NULL, 1, 1, '\0', 1),
	(83, '\0', NULL, '\0', NULL, 1, 'DEEP GREEN', '2013-12-03 00:00:00', 1, 3, '\0', 1),
	(84, '\0', NULL, '\0', NULL, 1, '*TEST', NULL, 1, 3, '\0', 1),
	(85, '\0', NULL, '\0', NULL, 1, '*Volontaires Sains', NULL, 1, 3, '\0', 1),
	(86, '\0', 2, '\0', NULL, '\0', 'EMISEP_Pilote', NULL, 1, 3, '\0', 1),
	(87, '\0', NULL, '\0', NULL, 1, 'LONGIDEP', '2014-03-03 00:00:00', 1, 3, '\0', '\0'),
	(88, '\0', NULL, '\0', NULL, 1, 'MP2Relaxo', '2014-03-18 00:00:00', 1, 3, '\0', 1),
	(89, '\0', NULL, '\0', NULL, 1, 'PERINE_Pilote', '2014-04-22 00:00:00', 1, 3, '\0', 1),
	(90, '\0', NULL, '\0', NULL, 1, 'VEP', '2014-05-07 00:00:00', 1, 3, '\0', 1),
	(91, '\0', NULL, '\0', NULL, 1, 'OptimMS', '2014-05-22 00:00:00', 1, 3, '\0', 1),
	(92, '\0', 84, '\0', NULL, '\0', 'MoNICa', NULL, 1, 3, '\0', 1),
	(93, '\0', NULL, '\0', NULL, 1, 'ASL_Pilote', NULL, 1, 3, '\0', 1),
	(94, '\0', 2, '\0', NULL, '\0', 'EMISEP', '2014-02-03 00:00:00', 1, 3, '\0', 1),
	(96, '\0', 1, '\0', NULL, '\0', 'ASL_Pilote_CATI', NULL, 1, 3, '\0', 1),
	(97, '\0', NULL, '\0', NULL, 1, 'HEMISFER_Pilote', '2014-06-20 00:00:00', 1, 3, '\0', 1),
	(98, '\0', NULL, '\0', NULL, 1, 'HEMOCOEUR', '2014-07-01 00:00:00', 1, 3, '\0', 1),
	(99, '\0', 91, '\0', NULL, '\0', 'F-Tract', '2014-07-17 00:00:00', 1, 3, '\0', 1),
	(100, '\0', 95, '\0', NULL, '\0', 'OxyTC', NULL, 1, 1, '\0', 1),
	(101, '\0', NULL, '\0', NULL, 1, 'PERINE', '2014-11-19 00:00:00', 1, 3, '\0', 1),
	(102, '\0', NULL, '\0', NULL, 1, 'LUNG', '2014-11-20 00:00:00', 1, 3, '\0', 1),
	(103, '\0', NULL, '\0', NULL, 1, 'T1Mapping_Repro', '2014-11-20 00:00:00', 1, 3, '\0', 1),
	(104, '\0', NULL, '\0', '2015-12-02 00:00:00', 1, 'Sprite', '2014-12-02 00:00:00', 2, 1, '\0', 1),
	(105, '\0', NULL, '\0', '2017-12-01 00:00:00', 1, 'AGIR-PARK', '2014-12-02 00:00:00', 1, 3, '\0', 1),
	(106, '\0', NULL, '\0', NULL, 1, 'RICART', NULL, 1, 3, '\0', 1),
	(107, 1, NULL, '\0', NULL, 1, 'HEMO MAV', '2015-01-20 00:00:00', 1, 1, '\0', 1),
	(108, 1, NULL, '\0', NULL, 1, 'Tracto-SCP-Pilote', '2015-01-13 00:00:00', 1, 1, '\0', 1),
	(109, '\0', NULL, '\0', NULL, 1, 'Tracto-SCP', '2015-01-13 00:00:00', 1, 3, '\0', 1),
	(110, '\0', NULL, '\0', NULL, 1, 'ReproRelaxo', '2015-03-12 00:00:00', 1, 3, '\0', 1),
	(111, '\0', NULL, '\0', NULL, 1, 'CineRT', NULL, 1, 3, '\0', 1),
	(112, '\0', NULL, '\0', NULL, 1, 'BrainGraphs', '2015-04-10 00:00:00', 1, 3, '\0', 1),
	(113, '\0', NULL, '\0', NULL, 1, 'EPMR-MA Pilote OSS', NULL, 1, 3, '\0', 1),
	(114, '\0', NULL, '\0', NULL, 1, 'HED-O-SHIFT Pilote OSS', '2015-07-02 00:00:00', 1, 3, '\0', 1),
	(115, '\0', NULL, '\0', NULL, 1, 'EPMR-MA', NULL, 1, 1, '\0', 1),
	(116, '\0', NULL, '\0', NULL, 1, 'HED-O-SHIFT', NULL, 1, 3, '\0', 1),
	(117, '\0', NULL, '\0', NULL, 1, 'CineIRM', '2015-07-03 00:00:00', 1, 3, '\0', 1),
	(118, '\0', NULL, '\0', NULL, 1, 'COGNISEP Pilote OSS', '2015-07-10 00:00:00', 1, 3, '\0', 1),
	(119, '\0', NULL, '\0', NULL, 1, 'PEPS EC', NULL, 1, 3, '\0', 1),
	(120, '\0', NULL, '\0', NULL, 1, 'ASL DISTO', '2015-10-30 00:00:00', 1, 1, '\0', 1),
	(121, '\0', NULL, '\0', NULL, 1, 'ASL_pedia', NULL, 1, 1, '\0', 1),
	(122, '\0', NULL, '\0', NULL, 1, 'DAbdo3D', '2016-02-03 00:00:00', 1, 1, '\0', 1),
	(123, 1, 152, '\0', NULL, '\0', 'RESSTORE', '2016-02-15 00:00:00', 1, 1, '\0', 1),
	(124, '\0', 153, '\0', NULL, '\0', 'ICAN', NULL, 1, 1, '\0', 1),
	(125, '\0', NULL, '\0', NULL, 1, 'ADERASL', '2016-07-20 00:00:00', 1, 1, '\0', 1),
	(126, '\0', NULL, '\0', NULL, 1, 'test', NULL, 1, 1, '\0', 1),
	(127, '\0', NULL, '\0', NULL, 1, 'Test_DerivedStudy', NULL, 1, 1, '\0', 1),
	(128, '\0', NULL, '\0', NULL, 1, 'QUANT_MRI', '2016-08-04 00:00:00', 1, 1, '\0', 1),
	(129, '\0', NULL, '\0', NULL, 1, 'MB-Diffusion', '2016-11-14 00:00:00', 1, 1, '\0', 1),
	(130, '\0', NULL, '\0', NULL, 1, 'COGNISEP Patients', NULL, 1, 1, '\0', 1),
	(131, '\0', NULL, '\0', NULL, 1, 'UTE4EEG', '2017-03-21 00:00:00', 1, 1, '\0', 1),
	(132, '\0', NULL, '\0', NULL, 1, 'FastMicroDiff', NULL, 1, 1, '\0', 1),
	(133, '\0', 191, '\0', NULL, '\0', 'CaractRF', NULL, 1, 1, '\0', 1),
	(134, '\0', NULL, '\0', NULL, 1, 'Sharing_SynesthesiaData', NULL, 1, 1, '\0', 1);

INSERT INTO center(id, country, name, phone_number, postal_code, street, city, website)
VALUES
	(1, 'France', 'CHU Rennes', '', '', '', 'Rennes', ''),
	(2, 'France', 'CHU Reims', '', '', '', 'Reims', ''),
	(3, 'France', 'LPS - CENIR', '0157274007', '75013', 'Bâtiment ICM 	(niv -1) ,  47 Bd de l\'Hôpital', 'Paris', 'www.cenir.org'),
	(4, 'France', 'CHU Marseille', '', '', '', 'Marseille', ''),
	(5, 'France', 'HCL - NeuroCardio', '', '', '', 'Lyon', ''),
	(7, 'France', 'Hôpitaux Universitaires de Strasbourg', '', '', '', 'Strasbourg', ''),
	(8, 'France', 'Centre Hospitalier Yves Le Foll', '', '', '', 'Saint Brieuc', ''),
	(9, 'France', 'CHU Nîmes', '', '', '', 'Nîmes', ''),
	(10, 'France', 'CHU Toulouse', '', '', '', 'Toulouse', ''),
	(11, 'France', 'CHU Nantes', '', '', '', 'Nantes', ''),
	(12, 'France', 'CHU Brabois', '', '', '', 'Nancy', ''),
	(13, 'France', 'CHU de Fort-de-France', '', '', '', 'Fort-de-France', ''),
	(14, 'France', 'CHU Michallon', '', '38700', 'Site Santé', 'Grenoble', ''),
	(15, 'France', 'GIN', '', '38706', '', 'Grenoble', 'http://neurosciences.ujf-grenoble.fr/main-home-1-sub-home-0-lang-en.html'),
	(16, 'France', 'CHU Brest', '', '', '', 'Brest', ''),
	(17, 'France', 'CHGR', '', '', '', 'Rennes', ''),
	(18, 'France', 'HCL - NeuroCardio to delete', '', '', '', 'Lyon', ''),
	(19, 'France', 'HCL - Hopital Edouard Herriot', '', '', '', 'Lyon', ''),
	(20, 'France', 'HCL - CHLS RMN', '', '', '', 'Lyon', ''),
	(21, 'France', 'HCL - NeuroCardio HFME', '', '', '', 'Lyon', ''),
	(22, 'France', 'HCL - GIE Lyon Nord', '', '', '', 'Lyon', ''),
	(23, 'France', 'INSERM_825', '', '', '', 'Toulouse', ''),
	(24, 'Belgique', 'UCL', '', '', '', 'Bruxelles', ''),
	(25, 'France', 'CH Bretagne Atlantique Vannes', '', '', '', 'Vannes', ''),
	(27, 'France', 'ICM', '', '', '', 'Paris', ''),
	(28, 'France', 'CHU Bordeaux', '', '', '', 'Bordeaux', ''),
	(29, 'France', 'HCL - CHLS', '', '', '', 'Lyon', ''),
	(30, 'France', 'CHRU Clermont-Ferrand Gabriel Montpied', '', '', '', 'Clermont-Ferrand', ''),
	(31, 'France', 'CHRU Montpellier Gui de Chauliac', '', '', '', 'Montpellier', ''),
	(33, 'France', 'IRM des sources', '', '', '', 'Lyon', ''),
	(35, 'France', 'IPB Strasbourg', '', '', '', 'Strasbourg', ''),
	(36, 'France', 'Hôpital de la Timone - Marseille', '', '13005', '264 rue St Pierre', 'Marseille', 'http://fr.ap-hm.fr/nos-hopitaux/hopital-de-la-timone'),
	(37, 'France', 'HCL - Hôpital Pierre Wertheimer', '', '69677', '59 bvd Pinel', 'Lyon', 'http://www.chu-lyon.fr/web/Hopital_Pierre-w_2346.html'),
	(38, 'France', 'CHU - Charles-Nicolle- Rouen', '', '76031', '1 rue de Germont', 'Rouen', 'http://www3.chu-rouen.fr/internet/connaitreCHU/reperes/soins/charles_nicolle/'),
	(39, 'France', 'CHU - Dijon', '', '21079', '14 rue Gaffarel', 'Dijon', 'http://www.chu-dijon.fr/'),
	(40, 'France', 'CHU - Hôpital Central - Nancy', '', '54035', '29 avenue du Maréchal de Lattre de Tassigny', 'Nancy', 'http://www.chu-nancy.fr/'),
	(41, 'France', 'CHRU - Lille', '', '59037', '2 avenue Oscar Lambret', 'Lille', 'http://www.chru-lille.fr/'),
	(43, 'France', 'Hôpital Beaujon -Paris', '', '92110', '100 boulevard du général Leclerc', 'Clichy', 'http://www.aphp.fr/hopital/beaujon/'),
	(44, 'France', 'Hôpital Bicêtre - Paris', '', '94275', '78 rue du général Leclerc', 'Le Kremlin Bicetre', ''),
	(45, 'France', 'Hopital d\'Instruction des Armées Sainte Anne', '0483162014', '83800', '2 bvd Sainte Anne', 'Toulon', 'http://www.sainteanne.org/'),
	(46, 'France', 'CHU St-Etienne', '0477829226', '42277', 'Hôpital Nord- avenue albert raimond', 'St Priest en Jarez - St Etienne', 'http://www.chu-st-etienne.fr/'),
	(47, 'France', 'CH Annecy Genevois', '', '74374', '1avenue de l\'hôpital', 'Metz - Tessy', 'http://www.ch-annecygenevois.fr/fr'),
	(48, 'France', 'Hopital Nord - Marseille', '0491380000', '13915', 'Chemin des Bourrely', 'Marseille', 'http://fr.ap-hm.fr/nos-hopitaux/hopital-nord'),
	(49, 'France', 'Hopital Pitié Salpétrière', '0157274007', '75013', '47-83 bd de l’hôpital', 'Paris', ''),
	(50, 'France', 'CHU St Roch - Nice', '', '06006', '5 rue Pierre Devoluy', 'Nice', ''),
	(51, 'France', 'CHU Poitiers', '', '86000', 'rue de la miletrie', 'Poitiers', ''),
	(52, 'France', 'Hôpital Saint-Anne', '', '', '', 'Paris', ''),
	(53, '', 'CHU Limoges', '', '', '', 'Limoges', ''),
	(54, '', 'CHU Angers', '', '', '', 'Angers', ''),
	(55, 'France', 'CHU Sud Reunion', '0262359000', '97448', 'Avenue du Président Mitterrand', 'SAINT-PIERRE', 'http://www.chu-reunion.fr'),
	(56, '', 'Hôpital Clairval', '', '', '', 'Marseille', ''),
	(57, '', 'CHU Amiens', '', '', '', 'Amiens', ''),
	(58, '', 'CHU Rouen', '', '', '', 'Rouen', ''),
	(59, '', 'CHU Clermont Ferrand', '', '', '', '', ''),
	(60, '', 'Fondation Ophtalmologique de Rothschild', '', '', '', '', ''),
	(61, '', 'CHU Montpellier', '', '', '', 'Montpellier', ''),
	(62, '', 'CHRU Besançon', '', '', '', '', ''),
	(63, '', 'CHD de Vendée', '', '', '', '', ''),
	(64, '', 'CHU Grenoble', '', '', '', 'Grenoble', ''),
	(65, '', 'CHRU de Tours', '', '', '', '', ''),
	(66, 'Colmar', 'Hôpitaux Civils de Colmar', '', '', '', '', ''),
	(67, '', 'CH Colmar', '', '', '', 'Colmar', ''),
	(68, '', 'CREATIS', '', '', '', 'Lyon', '');

INSERT INTO study_center
	(id, center_id, study_id)
VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 2, 2),
	(4, 3, 2),
	(5, 4, 2),
	(6, 1, 3),
	(7, 1, 4),
	(8, 1, 5),
	(10, 1, 7),
	(12, 1, 9),
	(13, 1, 10),
	(14, 1, 11),
	(15, 1, 12),
	(16, 1, 13),
	(17, 1, 14),
	(18, 1, 15),
	(21, 1, 17),
	(22, 1, 18),
	(23, 1, 19),
	(24, 1, 20),
	(25, 1, 21),
	(26, 1, 22),
	(27, 1, 23),
	(28, 1, 24),
	(29, 1, 25),
	(30, 1, 26),
	(31, 1, 27),
	(32, 1, 28),
	(33, 1, 29),
	(34, 1, 30),
	(35, 1, 31),
	(36, 1, 18),
	(37, 1, 32),
	(39, 1, 33),
	(40, 9, 34),
	(41, 2, 34),
	(42, 7, 34),
	(43, 10, 34),
	(44, 11, 34),
	(45, 12, 34),
	(46, 1, 34),
	(47, 13, 34),
	(48, 1, 34),
	(50, 1, 36),
	(51, 1, 37),
	(52, 1, 38),
	(53, 1, 39),
	(54, 1, 40),
	(55, 1, 41),
	(56, 1, 42),
	(57, 1, 43),
	(58, 14, 43),
	(59, 1, 44),
	(60, 15, 45),
	(61, 15, 46),
	(65, 14, 46),
	(66, 14, 45),
	(67, 14, 35),
	(68, 1, 47),
	(69, 1, 48),
	(72, 1, 49),
	(73, 1, 50),
	(74, 1, 51),
	(75, 1, 18),
	(76, 1, 51),
	(77, 1, 51),
	(78, 1, 51),
	(79, 1, 52),
	(80, 1, 51),
	(81, 1, 51),
	(82, 1, 53),
	(83, 1, 54),
	(84, 1, 53),
	(85, 1, 6),
	(86, 1, 55),
	(87, 1, 56),
	(88, 1, 55),
	(89, 1, 57),
	(90, 5, 33),
	(91, 18, 33),
	(92, 1, 58),
	(93, 19, 33),
	(94, 20, 33),
	(95, 21, 33),
	(96, 22, 33),
	(97, 10, 46),
	(98, 1, 59),
	(99, 2, 59),
	(100, 4, 59),
	(101, 3, 59),
	(102, 10, 59),
	(103, 4, 60),
	(104, 1, 60),
	(105, 1, 61),
	(106, 23, 62),
	(107, 1, 63),
	(108, 1, 64),
	(109, 24, 64),
	(110, 25, 65),
	(111, 1, 66),
	(112, 2, 67),
	(114, 27, 68),
	(115, 1, 69),
	(116, 1, 70),
	(117, 1, 71),
	(118, 1, 72),
	(119, 14, 73),
	(120, 1, 74),
	(121, 29, 75),
	(126, 1, 76),
	(127, 1, 77),
	(128, 4, 77),
	(129, 2, 77),
	(132, 5, 78),
	(133, 28, 79),
	(134, 1, 80),
	(135, 1, 81),
	(136, 1, 82),
	(137, 2, 82),
	(138, 4, 82),
	(139, 10, 82),
	(140, 30, 82),
	(141, 3, 82),
	(142, 31, 34),
	(143, 1, 83),
	(144, 1, 84),
	(145, 1, 85),
	(146, 1, 86),
	(147, 12, 86),
	(148, 4, 86),
	(149, 2, 86),
	(150, 10, 86),
	(151, 3, 86),
	(152, 1, 87),
	(153, 1, 88),
	(154, 1, 89),
	(155, 1, 90),
	(156, 1, 91),
	(157, 5, 92),
	(158, 33, 92),
	(160, 31, 86),
	(161, 30, 86),
	(163, 29, 86),
	(164, 28, 86),
	(165, 35, 86),
	(166, 1, 93),
	(167, 1, 94),
	(168, 12, 94),
	(169, 31, 94),
	(170, 30, 94),
	(171, 4, 94),
	(172, 29, 94),
	(173, 35, 94),
	(174, 28, 94),
	(175, 10, 94),
	(176, 2, 94),
	(177, 3, 94),
	(182, 1, 96),
	(183, 14, 96),
	(184, 1, 97),
	(188, 15, 99),
	(189, 14, 99),
	(192, 14, 100),
	(193, 1, 101),
	(194, 1, 102),
	(195, 1, 103),
	(196, 10, 104),
	(198, 14, 105),
	(200, 1, 106),
	(201, 1, 107),
	(202, 1, 108),
	(203, 1, 109),
	(204, 1, 98),
	(205, 36, 100),
	(207, 28, 100),
	(208, 9, 100),
	(210, 1, 100),
	(212, 39, 100),
	(213, 40, 100),
	(214, 41, 100),
	(216, 43, 100),
	(217, 44, 100),
	(218, 1, 110),
	(219, 1, 111),
	(220, 1, 112),
	(221, 1, 113),
	(222, 31, 100),
	(223, 45, 100),
	(224, 46, 100),
	(225, 1, 114),
	(226, 1, 115),
	(227, 1, 116),
	(228, 1, 117),
	(229, 1, 118),
	(230, 1, 119),
	(231, 47, 100),
	(232, 38, 100),
	(233, 48, 100),
	(236, 49, 100),
	(237, 50, 100),
	(238, 1, 120),
	(239, 51, 100),
	(240, 1, 121),
	(243, 1, 122),
	(244, 14, 123),
	(245, 15, 123),
	(246, 30, 100),
	(248, 5, 100),
	(249, 10, 100),
	(250, 11, 124),
	(251, 52, 124),
	(252, 53, 124),
	(253, 54, 124),
	(254, 55, 124),
	(255, 56, 124),
	(256, 57, 124),
	(257, 1, 124),
	(258, 58, 124),
	(259, 59, 124),
	(260, 60, 124),
	(261, 46, 124),
	(262, 61, 124),
	(263, 44, 124),
	(264, 40, 124),
	(265, 62, 124),
	(266, 1, 125),
	(267, 16, 124),
	(268, 28, 126),
	(269, 1, 127),
	(270, 1, 128),
	(271, 51, 124),
	(272, 63, 124),
	(273, 10, 124),
	(274, 49, 124),
	(275, 28, 124),
	(276, 64, 124),
	(277, 39, 124),
	(278, 65, 124),
	(279, 1, 129),
	(280, 66, 124),
	(281, 67, 124),
	(282, 1, 130),
	(283, 2, 124),
	(284, 1, 131),
	(285, 1, 132),
	(286, 1, 133),
	(287, 68, 133),
	(288, 15, 134);

INSERT INTO study_examination
	(examination_id, study_id)
VALUES
	(1, 1),
	(2, 1),
	(2, 2),
	(3, 3),
	(4, 3);

INSERT INTO study_user
	(id, study_id, user_id, user_name, receive_anonymization_report, receive_new_import_report, study_user_right)
VALUES (1, 1, 3, 'yyao', 1, 1, 1);

INSERT INTO pseudonymus_hash_values
 (id, birth_name_hash1, birth_name_hash2, birth_name_hash3, last_name_hash1, last_name_hash2, last_name_hash3, first_name_hash1, first_name_hash2, first_name_hash3, birth_date_hash)
VALUES
 (  1,
    'edbee6d1302d1b5a749aeb42e5747ea8503f3f5ae3f2b41247cac3e735106ed5',
    'f5b1f63c652852724daec3ab2fc51ba20792a0cf85c102066d412746dda72b84',
    'f7ca8a978bd2ba11ee0d843453103938562b6e48ef3237e2daf3a743f826f7ee',
    'edbee6d1302d1b5a749aeb42e5747ea8503f3f5ae3f2b41247cac3e735106ed5',
    'f5b1f63c652852724daec3ab2fc51ba20792a0cf85c102066d412746dda72b84',
    'f7ca8a978bd2ba11ee0d843453103938562b6e48ef3237e2daf3a743f826f7ee',
    'edbee6d1302d1b5a749aeb42e5747ea8503f3f5ae3f2b41247cac3e735106ed5',
    'f5b1f63c652852724daec3ab2fc51ba20792a0cf85c102066d412746dda72b84',
    'f7ca8a978bd2ba11ee0d843453103938562b6e48ef3237e2daf3a743f826f7ee',
    'efa0bd9d3793157b8b44cd76814c079e0eb1f8a3a3017dc0a58959f581d7a097');

INSERT INTO subject
	(id, name, identifier, birth_date, imaged_object_category, language_hemispheric_dominance,  manual_hemispheric_dominance, sex,  pseudonymus_hash_values_id)
VALUES
	(1,'subject1', 'sub1', '2013/01/01', 2, 1, 1, 2, 1),
	(2,'subject2', 'sub2', '2001/02/01', 2, 2, 1, 2, 1),
	(3,'0010001', 'sub3', '2001/02/01', 2, 1, 2, 2, 1);


INSERT INTO subject_study
	(id, physically_involved, study_id, subject_id, subject_study_identifier, subject_type)
VALUES
	(1, 0, 1, 1, 'Subject 1 for study 1', 1),
	(2, 0, 1, 2, 'Subject 2 for study 1', 2),
	(3, 0, 2, 1, 'Subject 1 for study 2', 2);

INSERT INTO group_of_subjects
	(id, dtype, group_name, study_id)
VALUES
	(1, 'EXPERIMENTAL', 'group 1', 1);
	
INSERT INTO subject_group_of_subjects
	(id, group_of_subjects_id, subject_id)
VALUES
	(1, 1, 1),
	(2, 1, 2);

INSERT INTO `manufacturer`
	(id, name)
VALUES
	(1, 'GE MEDICAL SYSTEMS'),
	(2, 'Philips Medical Systems'),
	(3, 'SIEMENS'),
	(5, 'Philips Healthcare'),
	(6, 'AXIOM ARTIS DBA');

INSERT INTO `manufacturer_model`
	(id, dataset_modality_type, name, magnetic_field, manufacturer_id)
VALUES
	(1,1,'Achieva',3,2),
	(2,1,'Symphony',1.5,3),
	(3,1,'Verio',3,3),
	(4,1,'Signa HDxt',3,1),
	(5,1,'Sonata',1.5,3),
	(6,1,'Aera',1.5,3),
	(7,1,'Avanto',1.5,3),
	(8,1,'DISCOVERY MR750',3,1),
	(12,1,'Intera',1.5,2),
	(13,1,'Achieva',1.5,2),
	(14,1,'TrioTim',3,3),
	(16,1,'Ingenia',3,5),
	(17,1,'DISCOVERY MR750w',3,1),
	(18,1,'GENESIS_SIGNA',1.5,1),
	(19,1,'Skyra',3,3),
	(20,1,'Signa',3,1),
	(21,1,'SIGNA',15,1),
	(22,1,'SIGNA HDX',1.5,1),
	(23,1,'Optima MR 450w',1.5,1),
	(24,1,'Ingenia',1.5,5),
	(25,1,'DSA',0,5),
	(26,1,'DSA',0,3),
	(27,1,'DSA',0,1),
	(28,1,'TDM NANTES',0,1),
	(29,1,' LIGHTSPEED VCT64',0,1),
	(30,1,'IGS INNOVA',0,1),
	(31,1,'Artis Zee Pure-DSA',0,3),
	(32,1,'ALLURA DSA',0,5),
	(33,1,'Brillance190p',0,5),
	(34,1,'Brightspeed 16',0,1),
	(35,1,'MR450W W GEM XP',1.5,1),
	(36,1,'TDM',0,1),
	(37,1,'Discovery',0,1),
	(38,1,'LightSpeed Optima CT660 ',0,1),
	(39,1,'Prisma',3,3),
	(40,1,'Definition As64',0,3),
	(41,1,'SKYRA',1.5,3),
	(42,1,'somatom',0,3),
	(43,1,'BUTTERFLY',0,1),
	(44,1,'Artis Q',0,3),
	(45,1,'AXIOM ARTIS DBA',0,3),
	(46,1,'Ingenuity CT 128',0,5),
	(47,1,'Brillance 40',0,5),
	(48,1,'Revolution',0,1),
	(49,1,'Optima 540',0,1),
	(50,1,'Optima540',0,1),
	(51,1,'Achieva-dStream',3,2),
	(52,1,'SIGNA HDe',1.5,1),
	(53,1,'INGENUITY CORE',0,5),
	(54,1,'Ingenia',3,2),
	(55,1,'Optima CT 660',0,1),
	(56,1,'Optima CT660',0,1),
	(57,1,'Revolution EVO',0,1),
	(58,1,'Integris V',0,2);

INSERT INTO `acquisition_equipment`
	(id, serial_number, center_id, manufacturer_model_id)
VALUES
	(1,'40296',1,3),
	(2,'17072',1,1),
	(3,'17131',2,1),
	(4,'40146',4,3),
	(6,'17569',5,1),
	(7,'00000000H4858977',7,4),
	(8,'38235',14,1),
	(9,'21221',11,5),
	(10,'00000000A4194823',9,4),
	(11,'23257',1,5),
	(13,'38058',15,1),
	(14,'41196',5,6),
	(15,'25299',5,7),
	(17,'08259',20,1),
	(20,'08867',5,13),
	(21,'08867',5,12),
	(22,'00000000B4498422',19,8),
	(23,'23145',22,2),
	(24,'731041',10,1),
	(25,'34111',23,1),
	(26,'17035',24,1),
	(27,'41335',25,6),
	(28,'35181',27,14),
	(29,'21024',14,1),
	(31,'38014',14,1),
	(32,'22070',28,13),
	(33,'42025',29,16),
	(34,'00000000M4166994',28,17),
	(35,'40527',3,3),
	(36,'45212',31,19),
	(37,'00000000M4171551',30,8),
	(38,'21692',33,13),
	(39,'40480',35,3),
	(40,'71051',1,16),
	(42,'71050',1,16),
	(43,'45600',10,19),
	(44,'00000000I4192908',12,4),
	(46,'60173981',37,1),
	(49,'00000000T4185506',41,23),
	(51,'41235',9,24),
	(52,'42265',43,16),
	(53,'00000000M4475661',45,4),
	(54,'41433',46,6),
	(55,'42259',47,6),
	(56,'45293',36,19),
	(57,'27865',38,7),
	(58,'0000000',40,22),
	(59,'45986',48,19),
	(60,'41087',39,6),
	(61,'00000000M40345186',49,4),
	(62,'HM1069',50,23),
	(63,'40724',51,3),
	(64,'41575',1,6),
	(65,'42445',30,6),
	(66,'41987',11,6),
	(67,'17465',44,1),
	(68,'64665787',11,24),
	(69,'71266',11,54),
	(70,'',52,8),
	(72,'',11,25),
	(73,'* ',11,56),
	(75,'',52,29),
	(76,'',52,30),
	(77,'',52,22),
	(78,'',53,31),
	(79,'',53,32),
	(80,'42021',53,6),
	(81,'45553',54,19),
	(82,'',54,33),
	(83,'XXXX',54,32),
	(84,'45095',55,19),
	(85,'42102',55,6),
	(86,'0000',55,32),
	(87,'',55,34),
	(88,'HM0439',56,35),
	(89,'00000',56,31),
	(90,'0000',56,36),
	(91,'00000000S4160096',57,35),
	(92,'0000',57,31),
	(93,'0000',57,37),
	(94,'00000',1,32),
	(95,'0000',1,29),
	(96,'00000000 C4200438',58,17),
	(97,'000000',58,32),
	(98,'0000000',58,37),
	(99,'000',58,38),
	(100,'42446',59,6),
	(101,'0000',59,17),
	(102,'0000000',59,32),
	(103,'00',59,37),
	(104,'42015',60,16),
	(105,'0000001',60,32),
	(106,'01',60,37),
	(107,'66062',46,39),
	(108,'000001',46,31),
	(109,'0',46,40),
	(110,'0110',61,32),
	(111,'0101',61,38),
	(112,'45212B',61,19),
	(113,'42613',61,41),
	(114,'0101',44,32),
	(115,'0102',44,42),
	(116,'00000000N4192928',40,22),
	(117,'00000000N4192951',40,8),
	(118,'0000',40,30),
	(119,'000',40,43),
	(120,'000000',40,29),
	(121,'46098',62,19),
	(122,'0101',62,42),
	(123,'0101',62,25),
	(124,'46055',2,19),
	(125,'38214',16,1),
	(126,'010000',16,44),
	(127,'02000',16,42),
	(128,'46219',45,19),
	(129,'00000000A4197627',51,35),
	(130,'000001',51,45),
	(131,'14183042',63,52),
	(132,'00000010',63,32),
	(133,'0101',63,33),
	(134,'01',63,47),
	(135,'0001010',58,7),
	(136,'24027',10,1),
	(137,'00000141',10,32),
	(138,'00010',10,38),
	(140,'000001',58,57),
	(141,'0001',58,49),
	(142,'34111',10,51),
	(143,'M40345203',49,23),
	(144,'M40345186',49,19),
	(145,'',49,44),
	(146,'',49,37),
	(147,'M4166994',28,17),
	(148,'01000',28,30),
	(149,'011110',28,32),
	(150,'00000000',28,49),
	(151,'21024',64,13),
	(152,'00003',64,32),
	(153,'0000002',64,48),
	(154,'038014',64,1),
	(155,'145036',39,19),
	(156,'MR 40679',65,3),
	(157,'E4212413',65,22),
	(158,'000110',65,37),
	(159,'0002',65,27),
	(160,'48013640',66,13),
	(161,'58555281',66,24),
	(162,'000002',66,32),
	(163,'01000',66,46),
	(164,'011',58,29),
	(165,'00123',58,48),
	(166,'0',2,44),
	(167,'0001',2,48),
	(168,'000010',28,58),
	(169,'141228',63,6);

INSERT INTO `coil`
	(id, center_id, coil_type, manufacturer_model_id, name, number_of_channels, serial_number)
VALUES
	(1, 1, 1, 3, 'BODY', 1, NULL),
	(2, 1, 2, 3, '32 CNX TETE', 32, NULL),
	(3, 1, 2, 3, '12 CNX', 12, NULL),
	(4, 1, 3, 3, 'SPINE', 24, NULL),
	(5, 1, 3, 3, 'BODY Matrix', 6, NULL),
	(6, 1, 3, 3, 'NECK', 4, NULL),
	(7, 1, 3, 3, 'FLEX', 4, NULL),
	(9, 1, 3, 3, '32 CNX COEUR', 32, NULL),
	(10, 2, 2, 1, '8 CNX', 8, NULL),
	(11, 2, 1, 1, 'BODY', 1, NULL),
	(12, 5, 1, 1, 'BODY', 1, NULL),
	(13, 5, 2, 1, '8 CNX', 8, NULL),
	(15, 14, 2, 1, 'BODY', 1, NULL),
	(16, 14, 2, 1, '32 ch', 32, NULL),
	(17, 7, 2, 4, '8 CNX', 8, NULL),
	(18, 11, 2, 5, '8 CNX', 8, NULL),
	(19, 9, 2, 4, '8 CNX', 8, NULL),
	(20, 1, 2, 1, '8 CNX', 8, NULL),
	(21, 1, 1, 1, 'BODY', NULL, NULL),
	(22, 4, 2, 3, '32 CNX', 32, NULL),
	(23, 5, 1, 6, 'BODY', NULL, NULL),
	(24, 5, 2, 6, 'HEAD', NULL, NULL),
	(25, 5, NULL, 6, 'NECK', NULL, NULL),
	(26, 18, 2, 7, 'HEAD', NULL, NULL),
	(27, 18, 1, 7, 'BODY', NULL, NULL),
	(28, 19, 2, 8, 'HEAD', NULL, NULL),
	(29, 19, 1, 8, 'BODY', NULL, NULL),
	(30, 20, 1, 1, 'BODY', NULL, NULL),
	(31, 20, 2, 1, '8 CNX', 8, NULL),
	(34, 21, 2, 12, '6 CNX', 6, NULL),
	(35, 21, 1, 12, 'BODY', NULL, NULL),
	(36, 21, NULL, 13, '6 CNX', 6, NULL),
	(37, 21, 1, 13, 'BODY', NULL, NULL),
	(38, 22, 2, 2, 'HEAD', NULL, NULL),
	(39, 22, 1, 2, 'BODY', NULL, NULL),
	(40, 10, 2, 1, '32 ch', 32, NULL),
	(41, 23, 1, 1, 'BODY', NULL, NULL),
	(42, 23, 2, 1, '8 CNX', 8, NULL),
	(43, 24, 2, 1, '8 CNX', 8, NULL),
	(44, 24, 1, 1, 'BODY', NULL, NULL),
	(45, 25, 1, 6, 'BODY', NULL, NULL),
	(46, 25, 2, 6, '8 CNX', 8, NULL),
	(47, 27, 2, 14, 'HEAD', NULL, NULL),
	(48, 27, 1, 14, 'BODY', NULL, NULL),
	(51, 28, 1, 13, 'BODY', NULL, NULL),
	(52, 29, 1, 16, 'BODY', NULL, NULL),
	(53, 4, 1, 3, 'BODY', NULL, NULL),
	(54, 11, 1, 5, 'BODY', NULL, NULL),
	(55, 9, 1, 4, 'BODY', NULL, NULL),
	(56, 10, 1, 1, 'BODY', NULL, NULL),
	(57, 7, 1, 4, 'BODY', NULL, NULL),
	(58, 30, 1, 17, 'BODY', NULL, NULL),
	(59, 30, 2, 17, 'HNS HEAD', NULL, NULL),
	(60, 3, 1, 3, 'BODY', NULL, NULL),
	(61, 3, 2, 3, 'HEAD', 32, NULL),
	(62, 33, 1, 13, 'BODY', NULL, NULL),
	(63, 33, 2, 13, 'HEAD', NULL, NULL),
	(64, 28, 1, 17, 'BODY', NULL, NULL),
	(65, 28, 2, 17, 'HEAD', NULL, NULL),
	(66, 35, 2, 3, '32 CH', NULL, NULL),
	(67, 35, 2, 3, '12 CH', 12, NULL),
	(68, 35, 3, 3, 'SPINE', NULL, NULL),
	(69, 35, 3, 3, 'NECK', NULL, NULL),
	(74, 1, 2, 16, '12 CH CUR', 12, NULL),
	(75, 1, 3, 16, 'NECK CUR', NULL, NULL),
	(76, 1, 2, 16, '12 CH Bloc', 12, NULL),
	(77, 1, 2, 16, '32 CH Bloc', 32, NULL),
	(78, 1, 3, 16, 'NECK Bloc', NULL, NULL),
	(79, 1, 1, 16, 'BODY Bloc', NULL, NULL),
	(80, 1, 1, 16, 'BODY CUR', NULL, NULL),
	(81, 10, 1, 19, 'BODY', NULL, NULL),
	(82, 10, 3, 19, 'SPINE', NULL, NULL),
	(83, 10, 3, 19, 'NECK', NULL, NULL),
	(84, 10, 2, 19, '32 CH', NULL, NULL),
	(85, 31, 3, 19, 'SPINE', NULL, NULL),
	(86, 31, 3, 19, 'NECK', NULL, NULL),
	(87, 31, 2, 19, '32 CH', NULL, NULL),
	(88, 31, 1, 19, 'BODY', NULL, NULL),
	(89, 12, 1, 4, 'BODY', NULL, NULL),
	(90, 12, 3, 4, 'SPINE', NULL, NULL),
	(91, 12, 2, 4, 'HEAD', NULL, NULL),
	(92, 43, 2, 16, 'DS HEAD', 32, NULL),
	(93, 1, 2, 6, '20 cnx', 20, NULL),
	(94, 1, 3, 6, 'SPINE 32 cnx', 32, NULL),
	(95, 1, 1, 6, 'BODY', NULL, NULL),
	(96, 11, 1, 6, 'BODY', NULL, NULL),
	(97, 11, 2, 6, 'HEAD', NULL, NULL),
	(98, 2, 2, 19, 'HEAD/NECK 64', 64, NULL),
	(99, 2, 2, 19, 'HEAD/NECK 20', 20, NULL),
	(100, 2, 2, 19, 'Rx/Tx Cp', NULL, NULL),
	(101, 23, 2, 51, 'dS HEAD 32ch', 32, NULL);
