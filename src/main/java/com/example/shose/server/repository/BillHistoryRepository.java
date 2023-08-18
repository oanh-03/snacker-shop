package com.example.shose.server.repository;

import com.example.shose.server.dto.response.billhistory.BillHistoryResponse;
import com.example.shose.server.entity.BillHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nguyễn Vinh
 */
@Repository
public interface BillHistoryRepository extends JpaRepository<BillHistory, String> {

    @Query(value = """
            SELECT ROW_NUMBER() OVER( ORDER BY bihi.created_date ASC ) AS stt, bihi.id, bihi.status_bill, bihi.created_date, bihi.action_description, us.full_name\s
                            FROM bill_history bihi
                            LEFT JOIN bill bi ON bi.id = bihi.id_bill
                            LEFT JOIN account ac On ac.id = bihi.id_employees
                            LEFT JOIN user us On us.id = ac.id_user
                            WHERE id_bill = :idBill
                            ORDER BY bihi.created_date ASC
            """, nativeQuery = true)
    List<BillHistoryResponse> findAllByIdBill(String idBill);

    @Query(value = """
             DELETE FROM  bill_history
                   WHERE id_bill = :id
            """, nativeQuery = true)
    boolean deleteAllByIdBill(@Param("id") String idBill);

}
