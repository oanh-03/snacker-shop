package com.example.shose.server.service.impl;

import com.example.shose.server.dto.request.sole.CreateSoleRequest;
import com.example.shose.server.dto.request.sole.FindSoleRequest;
import com.example.shose.server.dto.request.sole.UpdateSoleRequest;
import com.example.shose.server.dto.response.SoleResponse;
import com.example.shose.server.entity.Sole;
import com.example.shose.server.infrastructure.constant.Message;
import com.example.shose.server.infrastructure.exception.rest.RestApiException;
import com.example.shose.server.repository.SoleRepository;
import com.example.shose.server.service.SoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author Nguyễn Vinh
 */
@Service
public class SoleServiceImpl implements SoleService {

    @Autowired
    private SoleRepository soleRepository;


    @Override
    public List<SoleResponse> findAll(FindSoleRequest req) {
        return soleRepository.getAll(req);
    }

    @Override
    public Sole create(CreateSoleRequest req) {
        Sole checkName = soleRepository.getByName(req.getName());
        if (checkName != null) {
            throw new RestApiException(Message.NAME_EXISTS);
        }
        Sole add = new Sole();
        add.setStatus(req.getStatus());
        add.setName(req.getName());
        return soleRepository.save(add);
    }

    @Override
    public Sole update(UpdateSoleRequest req) {
        Optional<Sole> optional = soleRepository.findById(req.getId());
        if (!optional.isPresent()) {
            throw new RestApiException(Message.NOT_EXISTS);
        }
        Sole existence = soleRepository.getByNameExistence(req.getName(), req.getId());
        if (existence != null) {
            throw new RestApiException(Message.NAME_EXISTS);
        }
        Sole update = optional.get();
        update.setName(req.getName());
        update.setStatus(req.getStatus());
        return soleRepository.save(update);
    }

    @Override
    public Boolean delete(String id) {
        Optional<Sole> optional = soleRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.NOT_EXISTS);
        }
        soleRepository.delete(optional.get());
        return true;
    }

    @Override
    public Sole getOneById(String id) {
        Optional<Sole> optional = soleRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.NOT_EXISTS);
        }
        return optional.get();
    }
}
