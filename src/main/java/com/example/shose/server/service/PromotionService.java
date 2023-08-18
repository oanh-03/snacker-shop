package com.example.shose.server.service;
/*
 *  @author diemdz
 */

import com.example.shose.server.dto.request.promotion.CreatePromotionRequest;
import com.example.shose.server.dto.request.promotion.FindPromotionRequest;
import com.example.shose.server.dto.request.promotion.UpdatePromotionRequest;
import com.example.shose.server.dto.response.promotion.PromotionByIdRespone;
import com.example.shose.server.dto.response.promotion.PromotionByProDuctDetail;
import com.example.shose.server.dto.response.promotion.PromotionRespone;
import com.example.shose.server.entity.Promotion;

import java.util.List;

public interface PromotionService {
    List<PromotionRespone> getAll(FindPromotionRequest findPromotionRequest);
    Promotion add(CreatePromotionRequest request);
    Promotion update(UpdatePromotionRequest request);

    Promotion getById(String id);
    List<Promotion> expiredVoucher();
    List<Promotion> startVoucher();
    PromotionByIdRespone getByIdPromotion(String id);
    List<PromotionByProDuctDetail> getByIdProductDetail(String id);
}
