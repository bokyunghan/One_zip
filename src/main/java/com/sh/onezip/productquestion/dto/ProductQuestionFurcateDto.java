package com.sh.onezip.productquestion.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class ProductQuestionFurcateDto {

//    public String qnafurcate(@RequestParam("action") String action,
//                             @RequestParam("questionId") Long questionId,
//                             @RequestParam("productId") Long productId,

      String action;
      Long questionId;
      Long productId;

}
