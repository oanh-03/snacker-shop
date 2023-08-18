package com.example.shose.server.service.impl;

import com.example.shose.server.dto.request.size.CreateSizeRequest;
import com.example.shose.server.dto.request.size.FindSizeRequest;
import com.example.shose.server.dto.request.size.UpdateSizeRequest;
import com.example.shose.server.dto.response.SizeResponse;
import com.example.shose.server.entity.Size;
import com.example.shose.server.infrastructure.constant.Message;
import com.example.shose.server.infrastructure.exception.rest.RestApiException;
import com.example.shose.server.repository.SizeRepository;
import com.example.shose.server.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Nguyễn Vinh
 */
@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;


    @Override
    public List<SizeResponse> findAll(FindSizeRequest req) {
        return sizeRepository.getAll(req);
    }

    @Override
    public List<Size> getAll() {
        return sizeRepository.findAll();
    }

    @Override
    public Size create(CreateSizeRequest req) {
        Size checkName = sizeRepository.getOneByName(req.getName());
        if (checkName != null) {
            throw new RestApiException(Message.NAME_EXISTS);
        }
        Size add = new Size();
        add.setStatus(req.getStatus());
        add.setName(req.getName());
        return sizeRepository.save(add);
    }

    @Override
    public Size update(UpdateSizeRequest req) {
        Optional<Size> optional = sizeRepository.findById(req.getId());
        if (!optional.isPresent()) {
            throw new RestApiException(Message.NOT_EXISTS);
        }
        Size update = optional.get();
        update.setName(req.getName());
        update.setStatus(req.getStatus());
        return sizeRepository.save(update);
    }

    @Override
    public Boolean delete(String id) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.NOT_EXISTS);
        }
        sizeRepository.delete(optional.get());
        return true;
    }

    @Override
    public Size getOneById(String id) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.NOT_EXISTS);
        }
        return optional.get();
    }

    @Override
    public Size getOneByName(int name) {
        return sizeRepository.getOneByName(name);
    }
}
