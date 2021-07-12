package com.softron.masterdata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.masterdata.Customer;
import com.softron.schema.admin.entity.masterdata.OrderEntity;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
	
	public List<OrderEntity> findAllByActiveTrue();
	
//	@Query(value="SELECT * FROM `order_entity` WHERE customer_id=:customerId",nativeQuery = true)
//	public List<OrderEntity> getStyle(@Param("customerId") Long customerId);
	
	@Query(value="SELECT * FROM `order_entity` WHERE customer_id=:customerId AND active=true ORDER BY style_id",nativeQuery = true)
	public List<OrderEntity> findAllByCustomerOrderByStyle(@Param("customerId") Long customerId);
	
	public List<OrderEntity> findAllByActiveTrueAndOrganizationIdIn(List<Long> orgIds);
	
	@Query(value="SELECT DISTINCT order_entity.style_id,style.name\r\n" + 
			"FROM order_entity,style WHERE order_entity.customer_id=:customerId AND order_entity.style_id=style.id AND order_entity.active=1",nativeQuery = true)
	public List<Object[]> findAllByActiveTrueAndCustomerId(@Param("customerId") Long customerId);
	
	@Query(value="SELECT * FROM order_entity WHERE customer_id=:customerId AND style_id=:styleId AND active=1",nativeQuery = true)
	public List<OrderEntity> findAllByActiveTrueAndStyleIdAndCustomerId(@Param("styleId") Long styleId,@Param("customerId") Long customerId);
	
	public Optional<OrderEntity> findById(Long id);
	
	@Query(value="SELECT\r\n" + 
			"    table1.orderId,\r\n" + 
			"    table1.STATUS,\r\n" + 
			"    table1.orderName,\r\n" + 
			"    table1.buyer,\r\n" + 
			"    table1.styleName,\r\n" + 
			"    table1.quantity,\r\n" + 
			"    table1.smv,\r\n" + 
			"    table2.Production_Start_Date,\r\n" + 
			"    table3.toatalProduction,\r\n" + 
			"    table4.totalAlter,\r\n" + 
			"    table5.totalReject,\r\n" + 
			"    FORMAT(\r\n" + 
			"        (\r\n" + 
			"            (100 * table4.totalAlter) / NULLIF(table2.totalcheck,0)\r\n" + 
			"        ),\r\n" + 
			"        2\r\n" + 
			"    ) AS alterPercentage,\r\n" + 
			"    FORMAT(\r\n" + 
			"        (\r\n" + 
			"            (100 * table5.totalReject) / NULLIF(table2.totalcheck,0)\r\n" + 
			"        ),\r\n" + 
			"        2\r\n" + 
			"    ) AS rejectPercentage,\r\n" + 
			"    table6.defect,\r\n" + 
			"    FORMAT(((100*table6.defect)/table2.totalcheck),2) as DHU,\r\n" + 
			"    FORMAT(((table3.toatalProduction*table1.smv*100)/(60*1*NULLIF(table1.totalManpower,0))),2) as efficiency,\r\n" + 
			"    table7.lineInvolve,\r\n" + 
			"    table8.numerOfOperation,\r\n" + 
			"    table9.avg_Hr_Target,\r\n" + 
			"    table9.avgMp,\r\n" + 
			"    table10.last_Day_Production,\r\n" + 
			"    table11.Current_Day_Production,\r\n" + 
			"    table12.last_Hr_Production,\r\n" + 
			"    table12.Running_Line,\r\n" + 
			"    table14.avgHourProduction,\r\n" + 
			"    table13.Current_Hr_Production,\r\n" + 
			"    (table14.avgHourProduction/NULLIF(table9.avg_Hr_Target,0)) as avgAchivement\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        order_entity.id AS orderId,\r\n" + 
			"        order_entity.status AS\r\n" + 
			"    STATUS\r\n" + 
			"        ,\r\n" + 
			"        order_entity.name AS orderName,\r\n" + 
			"        customer.name AS buyer,\r\n" + 
			"        style.name AS styleName,\r\n" + 
			"        order_entity.quantity AS quantity,\r\n" + 
			"        style.smv AS smv,\r\n" + 
			"        SUM(target_and_manpower.number_of_helper+target_and_manpower.number_of_operator) as totalManpower\r\n" + 
			"    FROM\r\n" + 
			"        order_entity,\r\n" + 
			"        customer,\r\n" + 
			"        style,\r\n" + 
			"        target_and_manpower\r\n" + 
			"    WHERE\r\n" + 
			"        order_entity.id IN(:orderIds) AND order_entity.active = 1 AND order_entity.customer_id = customer.id AND customer.active = 1 AND order_entity.style_id = style.id AND style.active = 1\r\n" + 
			"AND target_and_manpower.org_id = order_entity.org_id\r\n" + 
			"    GROUP BY\r\n" + 
			"        order_entity.org_id\r\n" + 
			") AS table1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        quality_transaction.orderentity_id AS orderId,\r\n" + 
			"        SUM(\r\n" + 
			"            quality_transaction.sample_size\r\n" + 
			"        ) AS totalcheck,\r\n" + 
			"        quality_transaction.id AS qualityId,\r\n" + 
			"        quality_transaction.created_at AS Production_Start_Date\r\n" + 
			"    FROM\r\n" + 
			"        quality_transaction\r\n" + 
			"    WHERE\r\n" + 
			"        quality_transaction.active = 1 AND quality_transaction.orderentity_id IN(:orderIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        quality_transaction.orderentity_id\r\n" + 
			"    ORDER BY\r\n" + 
			"        quality_transaction.created_at ASC\r\n" + 
			") AS table2\r\n" + 
			"ON\r\n" + 
			"    table1.orderId = table2.orderId\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        quality_transaction.orderentity_id AS orderId,\r\n" + 
			"        SUM(\r\n" + 
			"            quality_transaction.sample_size\r\n" + 
			"        ) AS toatalProduction\r\n" + 
			"    FROM\r\n" + 
			"        quality_transaction\r\n" + 
			"    WHERE\r\n" + 
			"        quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
			"    AND quality_transaction.orderentity_id IN(:orderIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        quality_transaction.orderentity_id\r\n" + 
			") AS table3\r\n" + 
			"ON\r\n" + 
			"    table2.orderId = table3.orderId\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        quality_transaction.orderentity_id AS orderId,\r\n" + 
			"        SUM(\r\n" + 
			"            quality_transaction.sample_size\r\n" + 
			"        ) AS totalAlter\r\n" + 
			"    FROM\r\n" + 
			"        quality_transaction\r\n" + 
			"    WHERE\r\n" + 
			"        quality_transaction.active = 1 AND quality_transaction.check_output = 'alter' \r\n" + 
			"    AND quality_transaction.orderentity_id IN(:orderIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        quality_transaction.orderentity_id\r\n" + 
			") AS table4\r\n" + 
			"ON\r\n" + 
			"    table3.orderId = table4.orderId\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        quality_transaction.orderentity_id AS orderId,\r\n" + 
			"        SUM(\r\n" + 
			"            quality_transaction.sample_size\r\n" + 
			"        ) AS totalReject\r\n" + 
			"    FROM\r\n" + 
			"        quality_transaction\r\n" + 
			"    WHERE\r\n" + 
			"        quality_transaction.active = 1 AND quality_transaction.check_output = 'reject' \r\n" + 
			"    AND quality_transaction.orderentity_id IN(:orderIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        quality_transaction.orderentity_id\r\n" + 
			") AS table5\r\n" + 
			"ON\r\n" + 
			"    table5.orderId = table4.orderId\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        quality_transaction.orderentity_id AS orderId,\r\n" + 
			"        COUNT(quality_defect.id) AS defect\r\n" + 
			"    FROM\r\n" + 
			"        quality_transaction,\r\n" + 
			"        quality_defect\r\n" + 
			"    WHERE\r\n" + 
			"        quality_transaction.orderentity_id IN(:orderIds) AND quality_defect.qualitytransaction_id = quality_transaction.id AND quality_transaction.active = 1\r\n" + 
			"    GROUP BY\r\n" + 
			"        quality_transaction.orderentity_id\r\n" + 
			") AS table6\r\n" + 
			"ON\r\n" + 
			"    table5.orderId = table6.orderId\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        quality_transaction.orderentity_id AS orderId,\r\n" + 
			"        COUNT(\r\n" + 
			"            DISTINCT quality_transaction.org_id\r\n" + 
			"        ) AS lineInvolve\r\n" + 
			"    FROM\r\n" + 
			"        quality_transaction\r\n" + 
			"    WHERE\r\n" + 
			"        quality_transaction.orderentity_id IN(:orderIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        quality_transaction.orderentity_id\r\n" + 
			") AS table7\r\n" + 
			"ON\r\n" + 
			"    table6.orderId = table7.orderId\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        order_entity.id AS orderId,\r\n" + 
			"        COUNT(operation.id) AS numerOfOperation\r\n" + 
			"    FROM\r\n" + 
			"        order_entity,\r\n" + 
			"        style,\r\n" + 
			"        operation_break_down,\r\n" + 
			"        operation\r\n" + 
			"    WHERE\r\n" + 
			"        order_entity.id IN(:orderIds) AND order_entity.active = 1 AND order_entity.style_id = style.id AND style.active = 1 AND style.id = operation_break_down.style_id AND operation_break_down.active = 1 AND operation_break_down.operation_id = operation.id AND operation.active = 1\r\n" + 
			"    GROUP BY\r\n" + 
			"        order_entity.id\r\n" + 
			") AS table8\r\n" + 
			"ON\r\n" + 
			"    table7.orderId = table8.orderId\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        quality_transaction.orderentity_id AS orderId,\r\n" + 
			"        FORMAT(\r\n" + 
			"            AVG(\r\n" + 
			"                target_and_manpower.productiontarget\r\n" + 
			"            ),\r\n" + 
			"            2\r\n" + 
			"        ) AS avg_Hr_Target,\r\n" + 
			"        FORMAT(\r\n" + 
			"            AVG(\r\n" + 
			"                target_and_manpower.number_of_helper + target_and_manpower.number_of_operator\r\n" + 
			"            ),\r\n" + 
			"            2\r\n" + 
			"        ) AS avgMp\r\n" + 
			"    FROM\r\n" + 
			"        `quality_transaction`,\r\n" + 
			"        target_and_manpower\r\n" + 
			"    WHERE\r\n" + 
			"        quality_transaction.orderentity_id IN(:orderIds) AND quality_transaction.org_id = target_and_manpower.org_id\r\n" + 
			"    GROUP BY\r\n" + 
			"        quality_transaction.orderentity_id\r\n" + 
			") AS table9\r\n" + 
			"ON\r\n" + 
			"    table8.orderId = table9.orderId\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        quality_transaction.orderentity_id AS orderId,\r\n" + 
			"        SUM(\r\n" + 
			"            quality_transaction.sample_size\r\n" + 
			"        ) AS last_Day_Production\r\n" + 
			"    FROM\r\n" + 
			"        quality_transaction\r\n" + 
			"    WHERE\r\n" + 
			"        quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
			"    AND quality_transaction.created_at BETWEEN CURDATE() - INTERVAL 1 DAY AND CURDATE() AND quality_transaction.orderentity_id IN(:orderIds)\r\n" + 
			"    GROUP BY\r\n" + 
			"        quality_transaction.orderentity_id) AS table10\r\n" + 
			"    ON\r\n" + 
			"        table9.orderId = table10.orderId\r\n" + 
			"    LEFT JOIN(\r\n" + 
			"        SELECT\r\n" + 
			"            quality_transaction.orderentity_id AS orderId,\r\n" + 
			"            SUM(\r\n" + 
			"                quality_transaction.sample_size\r\n" + 
			"            ) AS Current_Day_Production\r\n" + 
			"        FROM\r\n" + 
			"            quality_transaction\r\n" + 
			"        WHERE\r\n" + 
			"            quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
			"        AND DATE(quality_transaction.created_at) = CURDATE() AND quality_transaction.orderentity_id IN(:orderIds)\r\n" + 
			"        GROUP BY\r\n" + 
			"            quality_transaction.orderentity_id) AS table11\r\n" + 
			"        ON\r\n" + 
			"            table10.orderId = table11.orderId\r\n" + 
			"        LEFT JOIN(\r\n" + 
			"            SELECT\r\n" + 
			"                quality_transaction.orderentity_id AS orderId,\r\n" + 
			"                SUM(\r\n" + 
			"                    quality_transaction.sample_size\r\n" + 
			"                ) AS last_Hr_Production,\r\n" + 
			"                COUNT(quality_transaction.org_id) AS Running_Line\r\n" + 
			"            FROM\r\n" + 
			"                quality_transaction\r\n" + 
			"            WHERE\r\n" + 
			"                quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
			"            AND quality_transaction.created_at BETWEEN(NOW() - INTERVAL 1 HOUR) AND NOW() AND quality_transaction.orderentity_id IN(:orderIds)\r\n" + 
			"            GROUP BY\r\n" + 
			"                quality_transaction.orderentity_id) AS table12\r\n" + 
			"            ON\r\n" + 
			"                table11.orderId = table12.orderId\r\n" + 
			"            LEFT JOIN(\r\n" + 
			"                SELECT\r\n" + 
			"                    quality_transaction.orderentity_id AS orderId,\r\n" + 
			"                    SUM(\r\n" + 
			"                        quality_transaction.sample_size\r\n" + 
			"                    ) AS Current_Hr_Production\r\n" + 
			"                FROM\r\n" + 
			"                    quality_transaction\r\n" + 
			"                WHERE\r\n" + 
			"                    quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
			"                BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') AND quality_transaction.orderentity_id IN(:orderIds)\r\n" + 
			"                GROUP BY\r\n" + 
			"                    quality_transaction.orderentity_id) AS table13\r\n" + 
			"                ON\r\n" + 
			"                    table12.orderId = table13.orderId\r\n" + 
			"                LEFT JOIN(\r\n" + 
			"                    SELECT\r\n" + 
			"                        quality_transaction.orderentity_id AS orderId,\r\n" + 
			"                        AVG(\r\n" + 
			"                            quality_transaction.sample_size\r\n" + 
			"                        ) AS avgHourProduction\r\n" + 
			"                    FROM\r\n" + 
			"                        quality_transaction\r\n" + 
			"                    WHERE\r\n" + 
			"                        quality_transaction.orderentity_id IN(:orderIds) AND quality_transaction.check_output = 'ok' \r\n" + 
			"                    BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') AND quality_transaction.active = 1\r\n" + 
			"                    GROUP BY\r\n" + 
			"                        quality_transaction.orderentity_id) AS table14\r\n" + 
			"                    ON\r\n" + 
			"                        table13.orderId = table14.orderId",nativeQuery = true)
	public List<Object[]> findAllOrderSummery(@Param("orderIds") String orderIds);
	
	
}
