/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.schema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.CensusDataEntry;
import com.softron.census.schema.entity.CensusOrganization;
import com.softron.census.schema.entity.Division;

/**
 *
 * @author Mozahid
 */
@Repository
public interface CensusDataEntryRepository extends JpaRepository<CensusDataEntry, Long>, JpaSpecificationExecutor<CensusDataEntry>, CensusDataEntryRepositoryCustom {

    @Query("Select c from CensusDataEntry c WHERE active=true")
    public List<CensusDataEntry> findAllActive();

    @Query("Select c from CensusDataEntry c")
    public List<CensusDataEntry> findAllCensusDataEntry();
    
    @Query(value = "Select NAME,MINISTRY_DIVISION_SANCTION,MINISTRY_DIVISION_EXIST,MINISTRY_DIVISION_VACANT,DEPARTMENT_DIRECTORATE_SANCTON,DEPARTMENT_DIRECTORATE_EXIST,DEPARTMENT_DIRECTORATE_VACANT,DIVISIONAL_DEPUTY_COMM_OFFICE_SANCTION,DIVISIONAL_DEPUTY_COMM_OFFICE_EXIST,"
            + "DIVISIONAL_DEPUTY_COMM_OFFICE_VACANT,AUTONOMOUS_CORPORATION_SANCTION,AUTONOMOUS_CORPORATION_EXIST,AUTONOMOUS_CORPORATION_VACANT,COMMISSION_CORPORATION_SANCTION,"
            + "COMMISSION_CORPORATION_EXIST,COMMISSION_CORPORATION_VACANT,TOTAL_SANCTION,TOTAL_EXIST,TOTAL_VACANT from civil_officer_and_staff11 WHERE year=:year ", nativeQuery = true)
    public List<Object[]> getCivilOfficerAndStaffDataByYear(@Param("year") String year);

    @Query(value = "Select * from summary_of_manpower_of_all ", nativeQuery = true)
    public List<Object[]> getSummaryOfManpower();

    @Query(value = "Select YEAR,MINISTRY_DIVISION_SANCTION,MINISTRY_DIVISION_EXIST,MINISTRY_DIVISION_VACANT,DEPARTMENT_DIRECTORATE_SANCTON,DEPARTMENT_DIRECTORATE_EXIST,DEPARTMENT_DIRECTORATE_VACANT,DIVISIONAL_DEPUTY_COMM_OFFICE_SANCTION,DIVISIONAL_DEPUTY_COMM_OFFICE_EXIST,"
            + "DIVISIONAL_DEPUTY_COMM_OFFICE_VACANT,AUTONOMOUS_CORPORATION_SANCTION,AUTONOMOUS_CORPORATION_EXIST,AUTONOMOUS_CORPORATION_VACANT,COMMISSION_CORPORATION_SANCTION,COMMISSION_CORPORATION_EXIST,COMMISSION_CORPORATION_VACANT, TOTAL_SANCTION,TOTAL_EXIST,TOTAL_VACANT  from summary_of_manpower_of_all  WHERE year=:year ", nativeQuery = true)
    public List<Object[]> getSummaryOfManpower(@Param("year") String year);

    @Query(value = "Select SUM(MINISTRY_DIVISION_SANCTION),SUM(MINISTRY_DIVISION_EXIST),SUM(MINISTRY_DIVISION_VACANT),SUM(DEPARTMENT_DIRECTORATE_SANCTON),SUM(DEPARTMENT_DIRECTORATE_EXIST),SUM(DEPARTMENT_DIRECTORATE_VACANT),SUM(DIVISIONAL_DEPUTY_COMM_OFFICE_SANCTION),SUM(DIVISIONAL_DEPUTY_COMM_OFFICE_EXIST),"
            + "SUM(DIVISIONAL_DEPUTY_COMM_OFFICE_VACANT),SUM(AUTONOMOUS_CORPORATION_SANCTION),SUM(AUTONOMOUS_CORPORATION_EXIST),SUM(AUTONOMOUS_CORPORATION_VACANT),SUM(COMMISSION_CORPORATION_SANCTION),"
            + "SUM(COMMISSION_CORPORATION_EXIST),SUM(COMMISSION_CORPORATION_VACANT),SUM(TOTAL_SANCTION),SUM(TOTAL_EXIST),SUM(TOTAL_VACANT) from civil_officer_and_staff11  WHERE year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfCivilOfficerAndStaffByYear(@Param("year") String year);

    @Query(value = "Select SUM(MINISTRY_DIVISION_SANCTION),SUM(MINISTRY_DIVISION_EXIST),SUM(MINISTRY_DIVISION_VACANT),SUM(DEPARTMENT_DIRECTORATE_SANCTON),SUM(DEPARTMENT_DIRECTORATE_EXIST),SUM(DEPARTMENT_DIRECTORATE_VACANT),SUM(DIVISIONAL_DEPUTY_COMM_OFFICE_SANCTION),SUM(DIVISIONAL_DEPUTY_COMM_OFFICE_EXIST),"
            + "SUM(DIVISIONAL_DEPUTY_COMM_OFFICE_VACANT),SUM(AUTONOMOUS_CORPORATION_SANCTION),SUM(AUTONOMOUS_CORPORATION_EXIST),SUM(AUTONOMOUS_CORPORATION_VACANT),SUM(COMMISSION_CORPORATION_SANCTION),SUM(COMMISSION_CORPORATION_EXIST),SUM(COMMISSION_CORPORATION_VACANT), SUM(TOTAL_SANCTION),SUM(TOTAL_EXIST),SUM(TOTAL_VACANT)  from summary_of_manpower_of_all  WHERE year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfSummaryOfManpower(@Param("year") String year);

    @Query(value = "Select CLASS,MINISTRY_DIVISION,DEPARTMENT_DIRECTORATE,DIVISIONAL_DEPUTYCOMMOFFICE,AUTONOMOUS_CORPORATION,COMMISSION,TOTAL from class_wise_statics_of_female_officers_and_staff11  WHERE year=:year ", nativeQuery = true)
    public List<Object[]> getClassWiseStaticsOfFemaleOfficersAndStaff11Data(@Param("year") String year);

    @Query(value = "Select SUM(MINISTRY_DIVISION),SUM(DEPARTMENT_DIRECTORATE),SUM(DIVISIONAL_DEPUTYCOMMOFFICE),SUM(AUTONOMOUS_CORPORATION),SUM(COMMISSION),SUM(TOTAL) from class_wise_statics_of_female_officers_and_staff11 WHERE year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClassWiseStaticsOfFemaleOfficersAndStaff11Data(@Param("year") String year);

    @Query(value = "Select MINISTRY_DIVISION,NO_OF_PROJECT,CLASSI,CLASSII,CLASSIII,CLASSIV,TOTAL from class_wise_statiststics_of_project_personnel1  WHERE year=:year  ", nativeQuery = true)
    public List<Object[]> getClassWiseStaticsOfProjectPersonnel11Data(@Param("year") String year);

    @Query(value = "Select * from class_wise_statiststics_of_project_personnel ", nativeQuery = true)
    public List<Object[]> getClassWiseStaticsOfProjectPersonnelData();

    @Query(value = "SELECT o FROM  CensusOrganization AS o WHERE o.id IN(SELECT co.parentId FROM CensusOrganization AS co) ")
    public List<CensusOrganization> getAllCensusOrganization();

    @Query(value = "SELECT o FROM  Division AS o WHERE o.id IN(SELECT co.id FROM Division AS co) ")
    public List<Division> getAllDivision();

    @Query(value = "SELECT PARENT,TITLE,CLASS1_SANCTION,CLASS1_EXIST,CLASS1_VACANT,CLASS2_SANCTION,CLASS2_EXIST,CLASS2_VACANT,CLASS3_SANCTION,CLASS3_EXIST,CLASS3_VACANT,"
            + "CLASS4_SANCTION,CLASS4_EXIST,CLASS4_VACANT,TOTAL_SANCTION,TOTAL_EXIST,TOTAL_VACANT FROM class_wise_statics_of_department_and_directorate11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getClassWiseStatisticsOfDepartmentAndDirectorateByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,CLASS1_SANCTION,CLASS1_EXIST,CLASS1_VACANT,CLASS2_SANCTION,CLASS2_EXIST,CLASS2_VACANT,CLASS3_SANCTION,CLASS3_EXIST,CLASS3_VACANT,"
            + "CLASS4_SANCTION,CLASS4_EXIST,CLASS4_VACANT,TOTAL_SANCTION,TOTAL_EXIST,TOTAL_VACANT FROM class_wise_statics_of_autonomous_bodies_corporation11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getClassWiseStatisticsOfAutonomousBodiesCorporationByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,MALE1,FEMALE1,TOTAL1,MALE2,FEMALE2,TOTAL2,MALE3,FEMALE3,TOTAL3,MALE4,FEMALE4,TOTAL4,MALE5,FEMALE5,TOTAL5 FROM male_female_of_ministry_division11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getMaleAndFemaleOfMinistryDivisionByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,CLASS1_SANCTION,CLASS1_EXIST,CLASS1_VACANT,CLASS2_SANCTION,CLASS2_EXIST,CLASS2_VACANT,CLASS3_SANCTION,CLASS3_EXIST,CLASS3_VACANT,CLASS4_SANCTION,CLASS4_EXIST,CLASS4_VACANT,TOTAL_SANCTION,TOTAL_EXIST,TOTAL_VACANT FROM class_wise_statics_of_ministries_and_division11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getClassWiseMinistriesAndDivision11ByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,CLASS1_SANCTION,CLASS1_EXIST,CLASS1_VACANT,CLASS2_SANCTION,CLASS2_EXIST,CLASS2_VACANT,CLASS3_SANCTION,CLASS3_EXIST,CLASS3_VACANT,CLASS4_SANCTION,CLASS4_EXIST,CLASS4_VACANT,TOTAL_SANCTION,TOTAL_EXIST,TOTAL_VACANT FROM class_wise_statics_of_officer_and_staff_of_div_comm_dep_comm11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getClassWiseDivisionalCommDeputyCommByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,CLASS1_SANCTION,CLASS1_EXIST,CLASS1_VACANT,CLASS2_SANCTION,CLASS2_EXIST,CLASS2_VACANT,CLASS3_SANCTION,CLASS3_EXIST,CLASS3_VACANT,CLASS4_SANCTION,CLASS4_EXIST,CLASS4_VACANT,CLASS5_SANCTION,CLASS5_EXIST,CLASS5_VACANT FROM class1_officer_of_autonomous_bodies_and_corporation_pay11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,CLASS1_SANCTION,CLASS1_EXIST,CLASS1_VACANT,CLASS2_SANCTION,CLASS2_EXIST,CLASS2_VACANT,CLASS3_SANCTION,CLASS3_EXIST,CLASS3_VACANT,CLASS4_SANCTION,CLASS4_EXIST,CLASS4_VACANT,CLASS5_SANCTION,CLASS5_EXIST,CLASS5_VACANT FROM class1_officer_of_department_and_directorate_by_national_pay11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getClass1OfficerOfDepartmentAndDirectorateByPayscaleByName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,CLASS1_SANCTION,CLASS1_EXIST,CLASS1_VACANT,CLASS2_SANCTION,CLASS2_EXIST,CLASS2_VACANT,CLASS3_SANCTION,CLASS3_EXIST,CLASS3_VACANT,CLASS4_SANCTION,CLASS4_EXIST,CLASS4_VACANT,CLASS5_SANCTION,CLASS5_EXIST,CLASS5_VACANT  FROM class1_officer_of_ministries_division_by_national_pay11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getClass1OfficerOfMinistriesDivisionByPayscaleByName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,CLASS1_SANCTION,CLASS1_EXIST,CLASS1_VACANT,CLASS2_SANCTION,CLASS2_EXIST,CLASS2_VACANT,CLASS3_SANCTION,CLASS3_EXIST,CLASS3_VACANT,CLASS4_SANCTION,CLASS4_EXIST,CLASS4_VACANT,CLASS5_SANCTION,CLASS5_EXIST,CLASS5_VACANT FROM class1_officer_of_div_comm_dep_comm_national_pay_scale11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getClass1OfficerOfDivCommDepCommByPayscaleByName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,MALE1,FEMALE1,TOTAL1,MALE2,FEMALE2,TOTAL2,MALE3,FEMALE3,TOTAL3,MALE4,FEMALE4,TOTAL4,MALE5,FEMALE5,TOTAL5 FROM male_female_of_department_and_directorate11   WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getMaleAndFemaleOfDepartmentAndDirectorateByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,MALE1,FEMALE1,TOTAL1,MALE2,FEMALE2,TOTAL2,MALE3,FEMALE3,TOTAL3,MALE4,FEMALE4,TOTAL4,MALE5,FEMALE5,TOTAL5 FROM male_female_officer_of_div_comm_dep_commo11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getMaleAndFemaleOfDivCommDeputyCommByParentNameByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT PARENT,TITLE,MALE1,FEMALE1,TOTAL1,MALE2,FEMALE2,TOTAL2,MALE3,FEMALE3,TOTAL3,MALE4,FEMALE4,TOTAL4,MALE5,FEMALE5,TOTAL5 FROM male_female_officer_of_autonomous_and_corporation11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getMaleAndFemaleOfAutomousAndCorporationByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(CLASS1_SANCTION),SUM(CLASS1_EXIST),SUM(CLASS1_VACANT),SUM(CLASS2_SANCTION),SUM(CLASS2_EXIST),SUM(CLASS2_VACANT),SUM(CLASS3_SANCTION),SUM(CLASS3_EXIST),SUM(CLASS3_VACANT),SUM(CLASS4_SANCTION),SUM(CLASS4_EXIST),SUM(CLASS4_VACANT),SUM(TOTAL_SANCTION),SUM(TOTAL_EXIST),SUM(TOTAL_VACANT)\n"
            + "FROM class_wise_statics_of_department_and_directorate11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClassWiseStatisticsOfDepartmentAndDirectorate(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(CLASS1_SANCTION),SUM(CLASS1_EXIST),SUM(CLASS1_VACANT),SUM(CLASS2_SANCTION),SUM(CLASS2_EXIST),SUM(CLASS2_VACANT),SUM(CLASS3_SANCTION),SUM(CLASS3_EXIST),SUM(CLASS3_VACANT),SUM(CLASS4_SANCTION),SUM(CLASS4_EXIST),SUM(CLASS4_VACANT),SUM(TOTAL_SANCTION),SUM(TOTAL_EXIST),SUM(TOTAL_VACANT)\n"
            + "FROM class_wise_statics_of_autonomous_bodies_corporation11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClassWiseStatisticsOfAutonomousBodiesCorporation(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(MALE1),SUM(FEMALE1),SUM(TOTAL1),SUM(MALE2),SUM(FEMALE2),SUM(TOTAL2),SUM(MALE3),SUM(FEMALE3),SUM(TOTAL3),SUM(MALE4),SUM(FEMALE4),SUM(TOTAL4),SUM(MALE5),SUM(FEMALE5),SUM(TOTAL5)\n"
            + "FROM male_female_of_department_and_directorate11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfMaleAndFemaleOfDepartmentAndDirectorateByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(MALE1),SUM(FEMALE1),SUM(TOTAL1),SUM(MALE2),SUM(FEMALE2),SUM(TOTAL2),SUM(MALE3),SUM(FEMALE3),SUM(TOTAL3),SUM(MALE4),SUM(FEMALE4),SUM(TOTAL4),SUM(MALE5),SUM(FEMALE5),SUM(TOTAL5)\n"
            + "FROM male_female_of_ministry_division11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfMaleAndFemaleOfMinistryDivisionByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT  MAX(PARENT),MAX(TITLE),SUM(MALE1),SUM(FEMALE1),SUM(TOTAL1),SUM(MALE2),SUM(FEMALE2),SUM(TOTAL2),SUM(MALE3),SUM(FEMALE3),SUM(TOTAL3),SUM(MALE4),SUM(FEMALE4),SUM(TOTAL4),SUM(MALE5),SUM(FEMALE5),SUM(TOTAL5)\n"
            + "FROM male_female_officer_of_div_comm_dep_commo11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfMaleAndFemaleDivCommDeputyCommByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(MALE1),SUM(FEMALE1),SUM(TOTAL1),SUM(MALE2),SUM(FEMALE2),SUM(TOTAL2),SUM(MALE3),SUM(FEMALE3),SUM(TOTAL3),SUM(MALE4),SUM(FEMALE4),SUM(TOTAL4),SUM(MALE5),SUM(FEMALE5),SUM(TOTAL5)\n"
            + "FROM male_female_officer_of_autonomous_and_corporation11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfMaleAndFemaleOfAutomousAndCorporationByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(CLASS1_SANCTION),SUM(CLASS1_EXIST),SUM(CLASS1_VACANT),SUM(CLASS2_SANCTION),SUM(CLASS2_EXIST),SUM(CLASS2_VACANT),SUM(CLASS3_SANCTION),SUM(CLASS3_EXIST),SUM(CLASS3_VACANT),SUM(CLASS4_SANCTION),SUM(CLASS4_EXIST),SUM(CLASS4_VACANT),SUM(TOTAL_SANCTION),SUM(TOTAL_EXIST),SUM(TOTAL_VACANT)\n"
            + "FROM class_wise_statics_of_ministries_and_division11   WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClassWiseMinistriesAndDivisionByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT * FROM class_wise_statics_of_ministries_and_division11  WHERE  year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClassWiseMinistriesAndDivisionForChart(@Param("year") String year);

    @Query(value = "SELECT * FROM class_wise_statics_of_department_and_directorate11  WHERE  year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClassWiseDepartmentAndDirectorateForChart(@Param("year") String year);

    @Query(value = "SELECT * FROM class_wise_statics_of_autonomous_bodies_corporation11  WHERE  year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClassWiseAutonomousBodiesAndCorporationForChart(@Param("year") String year);

    @Query(value = "SELECT * FROM class_wise_statics_of_officer_and_staff_of_div_comm_dep_comm11  WHERE  year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClassWiseDivisionalCommAndDeputyCommForChart(@Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(CLASS1_SANCTION),SUM(CLASS1_EXIST),SUM(CLASS1_VACANT),SUM(CLASS2_SANCTION),SUM(CLASS2_EXIST),SUM(CLASS2_VACANT),SUM(CLASS3_SANCTION),SUM(CLASS3_EXIST),SUM(CLASS3_VACANT),SUM(CLASS4_SANCTION),SUM(CLASS4_EXIST),SUM(CLASS4_VACANT),SUM(TOTAL_SANCTION),SUM(TOTAL_EXIST),SUM(TOTAL_VACANT)\n"
            + "FROM class_wise_statics_of_officer_and_staff_of_div_comm_dep_comm11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfDivisionalCommDeputyCommByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(CLASS1_SANCTION),SUM(CLASS1_EXIST),SUM(CLASS1_VACANT),SUM(CLASS2_SANCTION),SUM(CLASS2_EXIST),SUM(CLASS2_VACANT),SUM(CLASS3_SANCTION),SUM(CLASS3_EXIST),SUM(CLASS3_VACANT),SUM(CLASS4_SANCTION),SUM(CLASS4_EXIST),SUM(CLASS4_VACANT),SUM(CLASS5_SANCTION),SUM(CLASS5_EXIST),SUM(CLASS5_VACANT)\n"
            + "FROM class1_officer_of_autonomous_bodies_and_corporation_pay11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClass1OfficerOfAutonomousBodiesAndCorporationPayscaleByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(CLASS1_SANCTION),SUM(CLASS1_EXIST),SUM(CLASS1_VACANT),SUM(CLASS2_SANCTION),SUM(CLASS2_EXIST),SUM(CLASS2_VACANT),SUM(CLASS3_SANCTION),SUM(CLASS3_EXIST),SUM(CLASS3_VACANT),SUM(CLASS4_SANCTION),SUM(CLASS4_EXIST),SUM(CLASS4_VACANT),SUM(CLASS5_SANCTION),SUM(CLASS5_EXIST),SUM(CLASS5_VACANT)\n"
            + "FROM class1_officer_of_department_and_directorate_by_national_pay11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClass1OfficerOfDepartmentAndDirectorateByPayscaleByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(CLASS1_SANCTION),SUM(CLASS1_EXIST),SUM(CLASS1_VACANT),SUM(CLASS2_SANCTION),SUM(CLASS2_EXIST),SUM(CLASS2_VACANT),SUM(CLASS3_SANCTION),SUM(CLASS3_EXIST),SUM(CLASS3_VACANT),SUM(CLASS4_SANCTION),SUM(CLASS4_EXIST),SUM(CLASS4_VACANT),SUM(CLASS5_SANCTION),SUM(CLASS5_EXIST),SUM(CLASS5_VACANT)\n"
            + "FROM class1_officer_of_ministries_division_by_national_pay11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClass1OfficerOfMinistriesDivisionByPayscaleByParentName(@Param("parent") String parent, @Param("year") String year);

    @Query(value = "SELECT MAX(PARENT),MAX(TITLE),SUM(CLASS1_SANCTION),SUM(CLASS1_EXIST),SUM(CLASS1_VACANT),SUM(CLASS2_SANCTION),SUM(CLASS2_EXIST),SUM(CLASS2_VACANT),SUM(CLASS3_SANCTION),SUM(CLASS3_EXIST),SUM(CLASS3_VACANT),SUM(CLASS4_SANCTION),SUM(CLASS4_EXIST),SUM(CLASS4_VACANT),SUM(CLASS5_SANCTION),SUM(CLASS5_EXIST),SUM(CLASS5_VACANT)\n"
            + "FROM class1_officer_of_div_comm_dep_comm_national_pay_scale11  WHERE parent=:parent and year=:year ", nativeQuery = true)
    public List<Object[]> getTotalOfClass1OfficerOfDivCommDepCommByPayscaleByParentName(@Param("parent") String parent, @Param("year") String year);

}
