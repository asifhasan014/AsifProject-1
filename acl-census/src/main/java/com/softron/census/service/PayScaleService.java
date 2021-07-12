/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.schema.entity.Grade;
import com.softron.census.schema.entity.PayScale;
import com.softron.census.schema.entity.PayScaleYear;
import com.softron.census.schema.entity.PostClass;
import com.softron.census.schema.filter.PayScaleFilter;
import com.softron.census.schema.repository.PayScaleRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class PayScaleService {

    @Autowired
    private PayScaleRepository payScaleRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(PayScale payScale) {
        payScaleRepository.save(payScale);
    }

    @Transactional(readOnly = true)
    public List<PayScale> getAllActive() {
        List<PayScale> payScales = payScaleRepository.findAllActive();
        return payScales;
    }

    @Transactional(readOnly = true)
    public List<PayScale> getAllPayScale() {
        List<PayScale> payScales = payScaleRepository.findAllPayScale();
        return payScales;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        payScaleRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<PayScale> findDuplicatePayScale(Long payScaleYearId, Long postClassId, Long gradeId, String name) {
        return payScaleRepository.findDuplicatePayScale(payScaleYearId, postClassId, gradeId, name);
    }

    public PayScale getPayScaleById(Long id) {
        return payScaleRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Pay Scale doesn't exist for id=" + id));
    }

    @Transactional(readOnly = true)
    public List<PayScale> getAllPayScaleFilter(PostClass postClass, Grade grade, PayScaleYear payScaleYear) {
        PayScale payScale = new PayScale();
        payScale.setPostClass(postClass);
        payScale.setGrade(grade);
        payScale.setPayScaleYear(payScaleYear);
        Specification<PayScale> specification = new PayScaleFilter(payScale);
        List<PayScale> result = payScaleRepository.findAll(specification);
        return result;
    }
}
