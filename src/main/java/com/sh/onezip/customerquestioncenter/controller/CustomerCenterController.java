//package com.sh.onezip.customerquestioncenter.controller;

import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import com.sh.onezip.customerquestioncenter.service.QuestionCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@Controller
//@RequestMapping("/customercenter")
//@Slf4j
//public class CustomerCenterController {
//    @Autowired
//    QuestionCenterService questionCenterService;
    // HBK start
//    @GetMapping("/customerCenterList.do")
//    public void customerCenterLists (@PageableDefault(size = 8, page = 0) Pageable pageable, Model model) {
//
//        Page<QuestionCenter> questionCenterPages = questionCenterService.findAllQuestions(pageable);
//
//        model.addAttribute("questions", questionCenterPages.getContent()); // 질문 목록을 나타내는 리스트
//        model.addAttribute("totalCount", questionCenterPages.getTotalElements()); // 전체 질문수
//        model.addAttribute("size", questionCenterPages.getSize()); // 페이지당 표시되는 질문 수
//        model.addAttribute("number", questionCenterPages.getNumber()); // 현재 페이지 번호
//        model.addAttribute("totalPages", questionCenterPages.getTotalPages()); // 전체 페이지 수
//
//    }
//    @PostMapping("/customerCenterList.do")
//    public String customerCenterList(@RequestParam Long id,
//                                     RedirectAttributes redirectAttributes){
//        questionCenterService.deleteByQId(id);
//        return "redirect:/customercenter/customerCenterList.do";
//    }

//}
