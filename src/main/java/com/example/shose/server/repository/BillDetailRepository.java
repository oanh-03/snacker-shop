package com.example.shose.server.repository;

import com.example.shose.server.dto.request.bill.FindNewBillCreateAtCounterRequest;
import com.example.shose.server.dto.response.billdetail.BillDetailResponse;
import com.example.shose.server.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangdt
 */

public interface BillDetailRepository extends JpaRepository<BillDetail, String> {

    @Query(value = """
           SELECT ROW_NUMBER() OVER( ORDER BY bide.created_date ASC ) AS stt, bi.id AS id_bill, im.name AS image, bide.id, prde.id AS id_product, pr.code AS code_product, pr.name AS product_name, co.name AS name_color, si.name AS name_size, so.name AS name_sole, ma.name AS name_material, ca.name As name_category, bide.price, bide.quantity, prde.quantity AS max_quantity, bide.status_bill from bill_detail bide
           LEFT JOIN bill bi ON bide.id_bill = bi.id
           LEFT JOIN product_detail prde ON bide.id_product_detail = prde.id
           LEFT JOIN image im ON prde.id = im.id_product_detail
           LEFT JOIN product pr ON pr.id = prde.id_product
           LEFT JOIN color co ON co.id = prde.id_color
           LEFT JOIN size si ON si.id = prde.id_size
           LEFT JOIN sole so ON so.id = prde.id_sole
           LEFT JOIN material ma ON ma.id = prde.id_material
           LEFT JOIN category ca ON ca.id = prde.id_category
            WHERE bi.id LIKE :idBill
           GROUP BY bi.id, im.name, bide.id, prde.id, pr.code, pr.name, co.name, si.name, so.name, ma.name, ca.name, bide.price, bide.quantity, prde.quantity, bide.status_bill
            """, nativeQuery = true)
    List<BillDetailResponse> findAllByIdBill(String idBill);

    @Query(value = """
            SELECT ROW_NUMBER() OVER( ORDER BY bide.created_date ASC ) AS stt, prde.id AS id_product, im.name AS image, bi.id AS id_bill, bide.id, pr.code AS code_product, pr.name AS product_name, co.name AS name_color, si.name AS name_size, so.name AS name_sole, ma.name AS name_material, ca.name As name_category, bide.price, bide.quantity , prde.quantity AS max_quantity , bide.status_bill from bill_detail bide
            LEFT JOIN bill bi ON bide.id_bill = bi.id
            LEFT JOIN product_detail prde ON bide.id_product_detail = prde.id
            LEFT JOIN image im ON prde.id = im.id_product_detail
            LEFT JOIN product pr ON pr.id = prde.id_product
            LEFT JOIN color co ON co.id = prde.id_color
            LEFT JOIN size si ON si.id = prde.id_size
            LEFT JOIN sole so ON so.id = prde.id_sole
            LEFT JOIN material ma ON ma.id = prde.id_material
            LEFT JOIN category ca ON ca.id = prde.id_category
            WHERE bide.id LIKE :id
            GROUP BY id
            """, nativeQuery = true)
    BillDetailResponse findBillById(String id);

    @Query(value = """
            SELECT ROW_NUMBER() OVER( ORDER BY bide.created_date ASC ) AS stt, bi.id AS id_bill, pr.id As id_product, bide.id, pr.code AS code_product, pr.name AS product_name, co.name AS name_color, si.name AS name_size, so.name AS name_sole, ma.name AS name_material, ca.name As name_category, bide.price, bide.quantity , prde.quantity AS max_quantity , bide.status_bill FROM bill_detail bide
            LEFT JOIN bill bi ON bi.id = bide.id_bill
            LEFT JOIN product_detail prde ON prde.id = bide.id_product_detail
            LEFT JOIN product pr ON pr.id = prde.id_product
            LEFT JOIN color co ON co.id = prde.id_color
             LEFT JOIN size si ON si.id = prde.id_size
            LEFT JOIN sole so ON so.id = prde.id_sole
            LEFT JOIN material ma ON ma.id = prde.id_material
            LEFT JOIN category ca ON ca.id = prde.id_category
            WHERE bi.type = 1 AND bi.status_bill = 'TAO_HOA_DON'
              AND (:#{#request.startCreateBill} <= bi.created_date)
            AND ( :#{#request.nameUser} IS NULL
                     OR :#{#request.nameUser} LIKE ''
                     OR bi.user_name LIKE :#{#request.nameUser})
            AND ( :#{#request.phoneNumber} IS NULL
                     OR :#{#request.phoneNumber} LIKE ''
                     OR bi.phone_number LIKE :#{#request.phoneNumber})
            ORDER BY bi.created_date
            """, nativeQuery = true)
    List<BillDetailResponse> findAllBillAtCounterAndStatusNewBill(FindNewBillCreateAtCounterRequest request);

    @Query(value = """
             DELETE FROM  bill_detail
                   WHERE id_bill = :id
            """, nativeQuery = true)
    boolean deleteAllByIdBill(@Param("id") String idBill);
}
