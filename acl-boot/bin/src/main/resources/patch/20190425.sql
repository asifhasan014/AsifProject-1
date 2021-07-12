CREATE OR REPLACE VIEW civil_officer_and_staff11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
 max((
SELECT
  e.name
FROM
  post_class e
WHERE
  (c.post_class_id = e.id)
)) AS name,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'MINISTRY') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS ministry_division_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'MINISTRY') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS ministry_division_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'MINISTRY') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS ministry_division_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DEPARTMENT') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS department_directorate_sancton,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DEPARTMENT') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS department_directorate_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DEPARTMENT') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS department_directorate_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DIVISIONAL') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS divisional_deputy_comm_office_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DIVISIONAL') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS divisional_deputy_comm_office_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DIVISIONAL') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS divisional_deputy_comm_office_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'AUTONOMOUS') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS autonomous_corporation_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'AUTONOMOUS') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS autonomous_corporation_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'AUTONOMOUS') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS autonomous_corporation_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'COMMISSION') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS commission_corporation_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'COMMISSION') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS commission_corporation_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'COMMISSION') AND(
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS commission_corporation_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_vacant
FROM
  census_data_entry c
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW summary_of_manpower_of_all AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'MINISTRY') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS ministry_division_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'MINISTRY') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS ministry_division_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'MINISTRY') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS ministry_division_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DEPARTMENT') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS department_directorate_sancton,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DEPARTMENT') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS department_directorate_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DEPARTMENT') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS department_directorate_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DIVISIONAL') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS divisional_deputy_comm_office_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DIVISIONAL') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS divisional_deputy_comm_office_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'DIVISIONAL') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS divisional_deputy_comm_office_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'AUTONOMOUS') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS autonomous_corporation_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'AUTONOMOUS') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS autonomous_corporation_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'AUTONOMOUS') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS autonomous_corporation_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'COMMISSION') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS commission_corporation_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'COMMISSION') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS commission_corporation_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (e.org_type = 'COMMISSION') AND(
      c.census_period_id = e.census_period_id
    )
  )
)) AS commission_corporation_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    c.census_period_id = e.census_period_id
  )
)) AS total_sanction,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    c.census_period_id = e.census_period_id
  )
)) AS total_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    c.census_period_id = e.census_period_id
  )
)) AS total_vacant
FROM
  census_data_entry c
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW male_female_officer_of_div_comm_dep_commo11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
 max((
SELECT DISTINCT
  e.name
FROM
  division e
WHERE
  (co.division_id = e.id)
)) AS parent,
 max((
SELECT DISTINCT
  e.name
FROM
  district e
WHERE
  (co.district_id = e.id)
)) AS title,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male1,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female1,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total1,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male2,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female2,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total2,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male3,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female3,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total3,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male4,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female4,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total4,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male5,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female5,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_female + e.non_cadre_female
        ) + e.cadre_male
      ) + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total5
FROM
  (
    (
      census_data_entry c
    JOIN
      post_class cc
    ON
      ((c.post_class_id = cc.id))
    )
  JOIN
    census_organization co
  ON
    ((co.id = c.organization_id))
  )
GROUP BY
  co.division_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW male_female_officer_of_autonomous_and_corporation11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.parent_org_id = e.id)
)) AS parent,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.id = e.id)
)) AS title,
max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male1,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female1,
max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total1,
max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male2,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female2,
max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total2,
max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male3,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female3,
max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total3,
max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male4,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female4,
max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total4,
max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male5,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female5,
max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_female + e.non_cadre_female
        ) + e.cadre_male
      ) + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total5
FROM
  (
    (
      census_data_entry c
    JOIN
      post_class cc
    ON
      ((c.post_class_id = cc.id))
    )
  JOIN
    census_organization co
  ON
    ((co.id = c.organization_id))
  )
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW male_female_of_ministry_division11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
 max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.parent_org_id = e.id)
)) AS parent,
 max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.id = e.id)
)) AS title,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male1,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female1,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total1,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male2,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female2,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total2,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male3,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female3,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total3,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male4,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female4,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total4,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male5,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female5,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_female + e.non_cadre_female
        ) + e.cadre_male
      ) + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total5
FROM
  (
    (
      census_data_entry c
    JOIN
      post_class cc
    ON
      ((c.post_class_id = cc.id))
    )
  JOIN
    census_organization co
  ON
    ((co.id = c.organization_id))
  )
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW male_female_of_department_and_directorate11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
 max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.parent_org_id = e.id)
)) AS parent,
 max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.id = e.id)
)) AS title,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male1,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female1,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total1,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male2,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female2,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total2,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male3,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female3,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total3,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male4,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female4,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_male + e.non_cadre_male
        ) + e.cadre_female
      ) + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total4,
 max((
SELECT
  SUM(
    (
      e.cadre_male + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS male5,
 max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS female5,
 max((
SELECT
  SUM(
    (
      (
        (
          e.cadre_female + e.non_cadre_female
        ) + e.cadre_male
      ) + e.non_cadre_male
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS Total5
FROM
  (
    (
      census_data_entry c
    JOIN
      post_class cc
    ON
      ((c.post_class_id = cc.id))
    )
  JOIN
    census_organization co
  ON
    ((co.id = c.organization_id))
  )
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class1_officer_of_ministries_division_by_national_pay11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (
    (co.parent_org_id = e.id) AND(cc.name = 'class I') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS parent,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (
    (co.id = e.id) AND(cc.name = 'class I') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS title,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.78000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class1_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.78000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class1_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.78000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class1_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.66000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class2_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.66000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class2_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.66000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class2_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.56500') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class3_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.56500') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class3_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.56500') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class3_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.50000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class4_sanction,
max((
SELECT
  SUM(c.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.50000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class4_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.50000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class4_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.43000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class5_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.43000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class5_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.43000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class5_vacant
FROM
  (
    (
      (
        census_data_entry c
      JOIN
        post_class cc
      ON
        ((c.post_class_id = cc.id))
      )
    JOIN
      census_organization co
    ON
      ((co.id = c.organization_id))
    )
  JOIN
    pay_scale ps
  ON
    ((ps.id = c.pay_scale_id))
  )
GROUP BY
  c.pay_scale_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class1_officer_of_div_comm_dep_comm_national_pay_scale11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
 max((
SELECT DISTINCT
  e.name
FROM
  division e
WHERE
  (co.division_id = e.id)
)) AS parent,
 max((
SELECT DISTINCT
  e.name
FROM
  district e
WHERE
  (co.district_id = e.id)
)) AS title,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.78000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_sanction,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.78000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_exist,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.78000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.66000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_sanction,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.66000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_exist,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.66000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.56500') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_sanction,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.56500') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_exist,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.56500') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.50000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_sanction,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.50000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_exist,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.50000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_vacant,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.43000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class5_sanction,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.43000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class5_exist,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.pay_scale_id = c.pay_scale_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(ps.name = 'Tk.43000') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class5_vacant
FROM
  (
    (
      (
        census_data_entry c
      JOIN
        post_class cc
      ON
        ((c.post_class_id = cc.id))
      )
    JOIN
      census_organization co
    ON
      ((co.id = c.organization_id))
    )
  JOIN
    pay_scale ps
  ON
    ((ps.id = c.pay_scale_id))
  )
GROUP BY
  co.division_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class1_officer_of_department_and_directorate_by_national_pay11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
 max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (
    (co.parent_org_id = e.id) AND(cc.name = 'class I') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS parent,
 max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (
    (co.id = e.id) AND(cc.name = 'class I') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS title,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.78000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class1_sanction,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.78000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class1_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.78000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class1_vacant,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.66000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class2_sanction,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.66000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class2_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.66000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class2_vacant,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.56500') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class3_sanction,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.56500') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class3_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.56500') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class3_vacant,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.50000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class4_sanction,
 max((
SELECT
  SUM(c.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.50000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class4_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.50000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class4_vacant,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.43000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class5_sanction,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.43000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class5_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.43000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class5_vacant
FROM
  (
    (
      (
        census_data_entry c
      JOIN
        post_class cc
      ON
        ((c.post_class_id = cc.id))
      )
    JOIN
      census_organization co
    ON
      ((co.id = c.organization_id))
    )
  JOIN
    pay_scale ps
  ON
    ((ps.id = c.pay_scale_id))
  )
GROUP BY
  c.pay_scale_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class1_officer_of_autonomous_bodies_and_corporation_pay11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (
    (co.parent_org_id = e.id) AND(cc.name = 'class I') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS parent,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (
    (co.id = e.id) AND(cc.name = 'class I') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS title,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.78000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class1_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.78000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class1_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.78000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class1_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.66000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class2_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.66000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class2_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.66000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class2_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.56500') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class3_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.56500') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class3_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.56500') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class3_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.50000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class4_sanction,
max((
SELECT
  SUM(c.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.50000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class4_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.50000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class4_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.43000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class5_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.43000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class5_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (c.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.pay_scale_id = c.pay_scale_id
    ) AND(ps.name = 'Tk.43000') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS class5_vacant
FROM
  (
    (
      (
        census_data_entry c
      JOIN
        post_class cc
      ON
        ((c.post_class_id = cc.id))
      )
    JOIN
      census_organization co
    ON
      ((co.id = c.organization_id))
    )
  JOIN
    pay_scale ps
  ON
    ((ps.id = c.pay_scale_id))
  )
GROUP BY
  c.pay_scale_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class_wise_statiststics_of_project_personnel1 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
 max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (
    (e.id = c.organization_id) AND(c.org_type = 'MINISTRY') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS ministry_division,
 max((
SELECT
  COUNT(0)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS no_of_project,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS classI,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class II') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS classII,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class III') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS classIII,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class IV') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS classIV,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(
      c.census_period_id = c.census_period_id
    )
  )
)) AS Total
FROM
  (
    census_data_entry c
  JOIN
    post_class cc
  ON
    ((c.post_class_id = cc.id))
  )
WHERE
  (c.org_type = 'MINISTRY')
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class_wise_statiststics_of_project_personnel AS
SELECT
  max((
  SELECT DISTINCT
    e.name_english
  FROM
    census_organization e
  WHERE
    (
      (e.id = c.organization_id) AND(c.org_type = 'MINISTRY')
    )
)) AS ministry_division,
max((
SELECT
  COUNT(0)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY')
  )
)) AS no_of_project,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class I')
  )
)) AS classI,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class II')
  )
)) AS classII,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class III')
  )
)) AS classIII,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class IV')
  )
)) AS classIV,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry_project e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY')
  )
)) AS Total
FROM
  (
    census_data_entry c
  JOIN
    post_class cc
  ON
    ((c.post_class_id = cc.id))
  )
WHERE
  (c.org_type = 'MINISTRY')
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class_wise_statics_of_officer_and_staff_of_div_comm_dep_comm11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.parent_org_id = e.id)
)) AS parent,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.id = e.id)
)) AS title,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(c.org_type = 'DIVISIONAL') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_vacant
FROM
  (
    (
      census_data_entry c
    JOIN
      post_class cc
    ON
      ((c.post_class_id = cc.id))
    )
  JOIN
    census_organization co
  ON
    ((co.id = c.organization_id))
  )
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class_wise_statics_of_ministries_and_division11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.parent_org_id = e.id)
)) AS parent,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.id = e.id)
)) AS title,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(c.org_type = 'MINISTRY') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_vacant
FROM
  (
    (
      census_data_entry c
    JOIN
      post_class cc
    ON
      ((c.post_class_id = cc.id))
    )
  JOIN
    census_organization co
  ON
    ((co.id = c.organization_id))
  )
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class_wise_statics_of_female_officers_and_staff11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
max((
SELECT DISTINCT
  e.name
FROM
  post_class e
WHERE
  (c.post_class_id = e.id)
)) AS class,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DIVISIONAL') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS ministry_division,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'MINISTRY') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS department_directorate,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'DEPARTMENT') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS divisional_deputycommoffice,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS autonomous_corporation,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'COMMISSION') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS commission,
max((
SELECT
  SUM(
    (
      e.cadre_female + e.non_cadre_female
    )
  )
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total
FROM
  census_data_entry c
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class_wise_statics_of_department_and_directorate11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.parent_org_id = e.id)
)) AS parent,
max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.id = e.id)
)) AS title,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class I')
  )
)) AS class1_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class I')
  )
)) AS class1_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_vacant,
max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_sanction,
max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_exist,
max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(c.org_type = 'DEPARTMENT') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_vacant
FROM
  (
    (
      census_data_entry c
    JOIN
      post_class cc
    ON
      ((c.post_class_id = cc.id))
    )
  JOIN
    census_organization co
  ON
    ((co.id = c.organization_id))
  )
GROUP BY
  c.post_class_id;
-- 
-- 
-- 
CREATE OR REPLACE VIEW class_wise_statics_of_autonomous_bodies_corporation11 AS
SELECT
  max((
  SELECT
    e.census_period
  FROM
    census_period e
  WHERE
    (c.census_period_id = e.id)
)) AS year,
 max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.parent_org_id = e.id)
)) AS parent,
 max((
SELECT DISTINCT
  e.name_english
FROM
  census_organization e
WHERE
  (co.id = e.id)
)) AS title,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_sanction,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class I') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class1_vacant,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_sanction,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class II') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class2_vacant,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_sanction,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class III') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class3_vacant,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_sanction,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      e.post_class_id = c.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(cc.name = 'class IV') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS class4_vacant,
 max((
SELECT
  SUM(e.total_sanc_post)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_sanction,
 max((
SELECT
  SUM(e.total_existing_manpower)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_exist,
 max((
SELECT
  SUM(e.vacant)
FROM
  census_data_entry e
WHERE
  (
    (
      c.post_class_id = e.post_class_id
    ) AND(e.org_type = 'AUTONOMOUS') AND(
      e.census_period_id = c.census_period_id
    )
  )
)) AS total_vacant
FROM
  (
    (
      census_data_entry c
    JOIN
      post_class cc
    ON
      ((c.post_class_id = cc.id))
    )
  JOIN
    census_organization co
  ON
    ((co.id = c.organization_id))
  )
GROUP BY
  c.post_class_id;
  
  ---
  ---
  ---
  
  UPDATE MODULE_MENUS SET PARENT_MENU_ID = 0 WHERE PARENT_MENU_ID = null;
  UPDATE MODULE_MENUS SET PARENT_MENU_ID = 0 WHERE PARENT_MENU_ID is null;
  