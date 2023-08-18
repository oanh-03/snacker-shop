package com.example.shose.server.repository;

import com.example.shose.server.dto.request.address.FindAddressRequest;
import com.example.shose.server.dto.response.address.AddressResponse;
import com.example.shose.server.dto.response.address.AddressUserReponse;
import com.example.shose.server.dto.response.user.SimpleUserResponse;
import com.example.shose.server.entity.Address;
import com.example.shose.server.infrastructure.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nguyễn Vinh
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    @Query(value = """
            SELECT 
                ROW_NUMBER() OVER (ORDER BY a.last_modified_date DESC ) AS stt,
                a.id AS id,
                a.line AS line,
                a.district AS district,
                a.province AS province,
                a.ward AS ward,
                a.status AS status,
                a.created_date AS createdDate,
                a.last_modified_date AS lastModifiedDate,
                u.id AS idUser
            FROM address a
            JOIN user u on a.id_user = u.id
            WHERE u.status = 'DANG_SU_DUNG'
              AND 
                  ( u.id LIKE %:#{#req.id_user}% )
            AND 
                  ( :#{#req.line} IS NULL 
                      OR :#{#req.line} LIKE '' 
                      OR a.line LIKE %:#{#req.line}% )
            AND 
                  ( :#{#req.district} IS NULL 
                      OR :#{#req.district} LIKE '' 
                      OR a.district LIKE %:#{#req.district}% )
            AND 
                  ( :#{#req.province} IS NULL 
                      OR :#{#req.province} LIKE '' 
                      OR a.province LIKE %:#{#req.province}% )
            AND 
                  ( :#{#req.ward} IS NULL 
                      OR :#{#req.ward} LIKE '' 
                      OR a.ward LIKE %:#{#req.ward}% )  
            AND 
                 ( :#{#req.status} IS NULL 
                    OR :#{#req.status} LIKE '' 
                    OR a.status LIKE :#{#req.status} )      
            GROUP BY a.id
            ORDER BY a.last_modified_date DESC
                  """,
            nativeQuery = true
    )
    List<AddressResponse> getAll(@Param("req") FindAddressRequest req);

    @Query(value = """
             SELECT ac.id, us.full_name FROM account ac
                        LEFT JOIN user us ON us.id = ac.id_user
                        
            """, nativeQuery = true)
    List<SimpleUserResponse> getAllSimpleEntityUser();

    Address getAddressByUserIdAndStatus(String id, Status status);

    @Query("SELECT a FROM  Address a WHERE (a.status =:status) and (a.user.id =:idUser)")
    Address getOneAddressByStatus(@Param("status") Status status, @Param("idUser") String idUser);

    @Query("SELECT a FROM  Address a WHERE (a.status =:status) and (a.user.id =:idUser)")
    List<Address> findAllAddressByStatus(@Param("status") Status status, @Param("idUser") String idUser);

    @Query(value = """
            SELECT 
                ROW_NUMBER() OVER (ORDER BY a.last_modified_date DESC ) AS stt,
                a.id AS id,
                CONCAT(a.line, ', ', a.district,', ', a.ward,', ', a.province ) AS address,
                a.line AS line,
                a.district AS district,
                a.province AS province,
                a.ward AS ward,
                a.status AS status,
                a.province_id AS provinceId,
                a.to_district_id AS toDistrictId,
                a.ward_code AS wardCode,
                u.full_name AS fullName,
                u.phone_number AS phonenumber
            FROM address a
            JOIN user u on a.id_user = u.id
            WHERE u.id LIKE :#{#idUser}
            GROUP BY a.id
            ORDER BY a.last_modified_date DESC
                  """,
            nativeQuery = true
    )
    List<AddressUserReponse> findAddressByUserId(@Param("idUser") String idUser);
//    Address getOneAddressByStatus(@Param("status") Status status,@Param("idUser") String idUser);

}



