/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softron.census.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.census.schema.entity.PayScaleYear;
import com.softron.census.schema.repository.PayScaleYearRepository;
import com.softron.common.exceptions.NoRecordExistsException;

/**
 *
 * @author Mozahid
 */
@Service
public class PayScaleYearService {

    @Autowired
    private PayScaleYearRepository payScaleYearRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(PayScaleYear payScaleYear) {
        payScaleYearRepository.save(payScaleYear);
    }

    @Transactional(readOnly = true)
    public List<PayScaleYear> getAllActive() {
        List<PayScaleYear> payScaleYears = payScaleYearRepository.findAllActive();
        return payScaleYears;
    }

    @Transactional(readOnly = true)
    public List<PayScaleYear> findDuplicatePayScaleYear(String name) {
        List<PayScaleYear> payScaleYears = payScaleYearRepository.findDuplicatePayScaleYear(name);
        return payScaleYears;
    }

    @Transactional(readOnly = true)
    public List<PayScaleYear> getAllPayScaleYear() {
        List<PayScaleYear> payScaleYears = payScaleYearRepository.findAllPayScaleYear();
        return payScaleYears;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final Long id) {
        payScaleYearRepository.deleteById(id);
    }

    public PayScaleYear getPayScaleYearById(Long id) {
        return payScaleYearRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Pay Scale Year doesn't exist for id=" + id));
    }
}
