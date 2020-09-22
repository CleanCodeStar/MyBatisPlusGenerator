package com.citrsw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import com.citrsw.service.AccusationService;

/**
* 资源举报Controller
*
* @author Zhenfeng Li
* @version 0.0.1
* @date 2020-03-23 15:36:23
*/
@RestController
@Slf4j
public class AccusationController {

    private final AccusationService accusationService;

    public AccusationController(AccusationService accusationService) {
        this.accusationService = accusationService;
    }

}