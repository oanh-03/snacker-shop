package com.example.shose.server.service;

import com.example.shose.server.dto.request.address.CreateAddressRequest;
import com.example.shose.server.dto.request.address.FindAddressRequest;
import com.example.shose.server.dto.request.address.UpdateAddressRequest;
import com.example.shose.server.dto.response.address.AddressResponse;
import com.example.shose.server.dto.response.address.AddressUserReponse;
import com.example.shose.server.dto.response.user.SimpleUserResponse;
import com.example.shose.server.entity.Address;
import com.example.shose.server.infrastructure.constant.Status;

import java.util.List;

/**
 * @author Hào Ngô
 */
public interface AddressService {

    List<AddressUserReponse> findAddressByUserId(String idUser);

    List<AddressResponse> getList(FindAddressRequest req);

    Address create(final CreateAddressRequest req);

    Address update(final UpdateAddressRequest req);

    Boolean delete(String id);

    Address getOneById(String id);

    List<SimpleUserResponse> getAllSimpleEntityUser();

    Address getAddressByUserIdAndStatus(String id, Status status);

}
