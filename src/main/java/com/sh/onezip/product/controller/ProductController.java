package com.sh.onezip.product.controller;

import com.sh.onezip.orderproduct.dto.OrderProductDto;
import com.sh.onezip.orderproduct.entity.OrderProduct;
import com.sh.onezip.orderproduct.service.OrderProductService;
import com.sh.onezip.productlog.entity.ProductLog;
import com.sh.onezip.productlog.entity.RefundCheck;
import com.sh.onezip.productlog.entity.ShppingState;
import com.sh.onezip.productlog.service.ProductLogService;
import com.sh.onezip.productreview.dto.ProductReviewCreateDto;
import com.sh.onezip.productreview.dto.ProductReviewDto;
import com.sh.onezip.productreview.entity.ProductReview;
import com.sh.onezip.productreview.service.ProductReviewService;
import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.common.HelloMvcUtils;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.dto.ProductPurchaseInfoDto;
<<<<<<< HEAD
import com.sh.onezip.product.dto.UseProductPurchaseInfoDto;
=======

import com.sh.onezip.product.dto.useProductPurchaseInfoDto;
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.product.service.ProductService;
import com.sh.onezip.productoption.service.ProductOptionService;
import com.sh.onezip.productquestion.dto.ProductQuestionCreateDto;
import com.sh.onezip.productquestion.dto.ProductQuestionDto;
<<<<<<< HEAD
=======

>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
import com.sh.onezip.productquestion.dto.ProductQuestionFurcateDto;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productquestion.service.ProductQuestionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    ProductOptionService productOptionService;
    @Autowired
    ProductQuestionService productQuestionService;
    @Autowired
    ProductReviewService productReviewService;
    @Autowired
    ProductLogService productLogService;
    @Autowired
    OrderProductService orderProductService;

    @Autowired
    ProductReviewService productReviewService;
    @Autowired
    ProductLogService productLogService;
    @Autowired
    OrderProductService orderProductService;

    @GetMapping("/productList.do")
    public void productList(Model model,
            @RequestParam(name = "price", required = false, defaultValue = "0") int price,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            HttpServletRequest httpServletRequest) {
        int limit = 6;
        Pageable pageable = PageRequest.of(page - 1, limit);
        String url = httpServletRequest.getRequestURI() + "?price=" + price;
        Page<ProductListDto> productPage = productService.productListDtoFindAllByPrice(pageable, price);
        List<ProductListDto> productListDtos = productService.productListDtoFindAllByPrice(price);

        String pagebar = HelloMvcUtils.getPagebar(
                page, limit, productListDtos.size(), url);

        model.addAttribute("pagebar", pagebar);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalCount", productListDtos.size());
    }

    @GetMapping("/productDetail.do")
    public void productDetail(@RequestParam("id") Long id,
            Model model) {
        ProductDetailDto productDetailDto = productService.productDetailDtofindById(id);
        List<ProductOption> productOptions = productOptionService.findAllByProductId(productDetailDto.getId());
        List<Attachment> attachmentList = attachmentService.findProductAttachmentToList(productDetailDto.getId());
        productDetailDto.setProductOptions(productOptions);
        productDetailDto.setAttachmentList(attachmentList);

        model.addAttribute("product", productDetailDto);
    }

    @PostMapping("/productPurchaseInfo.do")
    public void productPurchaseInfo(
            @AuthenticationPrincipal MemberDetails memberDetails,
<<<<<<< HEAD
            @Valid UseProductPurchaseInfoDto useProductPurchaseInfoDto,
            Model model){
=======

            @Valid useProductPurchaseInfoDto useProductPurchaseInfoDto,

            Model model) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        Member member = memberDetails.getMember();
        List<ProductOption> productOptions = productOptionService
                .findAllByProductId(useProductPurchaseInfoDto.getProductId());

        Long Optionid = 0L;
        Optionid = Long.parseLong(useProductPurchaseInfoDto.getSelectOption().split("#")[0]);
        String[] selectOptionArr = useProductPurchaseInfoDto.getSelectOption().split("#"); // index-0: option id,
                                                                                           // index-1: option additional
                                                                                           // cost
        Long optionId = Long.parseLong(selectOptionArr[0]); // 선택 옵션 고유번호
        int additionalCost = Integer.parseInt(selectOptionArr[1]); // 선택 옵션 추가 요금

        ProductOption productOption = productOptionService.findById(Optionid);

        ProductPurchaseInfoDto productPurchaseInfoDto = productService
                .productPurchaseInfoDtofindById(useProductPurchaseInfoDto.getProductId());

        productPurchaseInfoDto.setOptionId(optionId);
        productPurchaseInfoDto.setAdditionalCost(additionalCost);

        int totalOptionCost = 0;
        if (productOption != null) {
            productPurchaseInfoDto.setProductOption(productOption);
            totalOptionCost = productPurchaseInfoDto.getProductOption().getOptionCost()
                    * useProductPurchaseInfoDto.getProductQuantity();
            productPurchaseInfoDto.setTotalOptionCost(totalOptionCost);
        }

        productPurchaseInfoDto.setMember(member);
        productPurchaseInfoDto.setProductQuantity(useProductPurchaseInfoDto.getProductQuantity());
        productPurchaseInfoDto.setProductOptionLists(productOptions);

        int totalPrice = productPurchaseInfoDto.getProductPrice() * useProductPurchaseInfoDto.getProductQuantity();

        productPurchaseInfoDto.setTotalProductPrice(totalPrice);
        int totalDiscountPrice = (int) ((totalPrice) * (productPurchaseInfoDto.getDiscountRate()));
        productPurchaseInfoDto.setTotalDiscountPrice(totalDiscountPrice);
        productPurchaseInfoDto.setTotalApplyPrice(totalPrice - totalDiscountPrice + totalOptionCost);

        ProductLog newProductLog = ProductLog
                .builder()
                .member(member)
                .purchaseDate(LocalDate.now().toString())
                .shppingState(ShppingState.R)
                .refundCheck(RefundCheck.N)
                .memo("하드코딩입니다.")
                .arrAddr("하드코딩입니다.")
                .totalPayAmount(productPurchaseInfoDto.getTotalApplyPrice())
                .build();

        newProductLog = productLogService.createProductLog(newProductLog);
        productPurchaseInfoDto.setProductLogId(newProductLog.getId());

        model.addAttribute("productPurchaseInfoDto", productPurchaseInfoDto);
    }

    @GetMapping("/productQna.do")
    public void productQna(@RequestParam("id") Long id,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            Model model,
            @AuthenticationPrincipal MemberDetails memberDetails,
            HttpServletRequest httpServletRequest) {
        Product product = productService.findById(id);
        List<ProductQuestion> productQuestions = productQuestionService.pquestionFindByProductid(product.getId());
        String url = httpServletRequest.getRequestURI() + "?id=" + id;
        int limit = 5;

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ProductQuestionDto> productQuestionPage = productQuestionService
                .productQuestionDtoFindAllByProductId(pageable, product.getId());
        List<ProductQuestionDto> productQuestionDtos = productQuestionService
                .productQuestionDtoFindAllByProductId(product.getId());

        String pagebar = HelloMvcUtils.getPagebar(
                page, limit, productQuestions.size(), url);

        Member member = memberDetails.getMember();

        model.addAttribute("pagebar", pagebar);
        model.addAttribute("questions", productQuestionPage.getContent());
        model.addAttribute("productId", id);
        model.addAttribute("member", member);
        model.addAttribute("totalCount", productQuestionDtos.size());

    }

    @GetMapping("/productQnaCreatePage.do")
    public void productQnaCreatePage(@RequestParam("id") Long id,
            @RequestParam(value = "questionId", required = false, defaultValue = "0") Long questionId,
            @RequestParam(value = "productId", required = false, defaultValue = "0") Long productId,
            @AuthenticationPrincipal MemberDetails memberDetails,
            Model model) {
        Member member = memberDetails.getMember();

        model.addAttribute("id", id);
        model.addAttribute("Member", member);
        if (questionId != 0L) {
            model.addAttribute("questionId", questionId);
        }
        model.addAttribute("productId", productId);

    }

    @PostMapping("/productQnaCreate.do")
    public String productQnaCreate(@RequestParam("productNo") Long productId,
            @RequestParam(value = "questionId", required = false, defaultValue = "0") Long questionId,
            @Valid ProductQuestionCreateDto productQuestionCreateDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberDetails memberDetails) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Product product = productService.findById(productId);

        if (questionId != 0L) {
            productQuestionCreateDto.setId(questionId);
        }

        Member member = memberDetails.getMember();

        productQuestionCreateDto.setProduct(product);
        productQuestionCreateDto.setMember(member);
        productQuestionCreateDto.setQRegdate(LocalDate.now());
        productQuestionService.createQuestion(productQuestionCreateDto);

        return "redirect:/product/productQna.do?id=" + productId;
    }

    @PostMapping("/qnafurcate.do")

    public String qnafurcate(ProductQuestionFurcateDto productQuestionFurcateDto,
<<<<<<< HEAD
                             Model model,
                             RedirectAttributes redirectAttributes){
        model.addAttribute("questionId", productQuestionFurcateDto.getQuestionId());
        model.addAttribute("productId", productQuestionFurcateDto.getProductId());
        if ("update".equals(productQuestionFurcateDto.getAction())) {
            ProductQuestion productQuestion = productQuestionService.findQuestionById(productQuestionFurcateDto.getQuestionId());
=======
            Model model,
            RedirectAttributes redirectAttributes) {
        model.addAttribute("questionId", productQuestionFurcateDto.getQuestionId());
        model.addAttribute("productId", productQuestionFurcateDto.getProductId());
        if ("update".equals(productQuestionFurcateDto.getAction())) {
            ProductQuestion productQuestion = productQuestionService
                    .findQuestionById(productQuestionFurcateDto.getQuestionId());
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
            redirectAttributes.addFlashAttribute("questionId", productQuestionFurcateDto.getQuestionId());
            redirectAttributes.addFlashAttribute("productQuestion", productQuestion);
            return "redirect:/product/productQnaCreatePage.do?id=" + productQuestionFurcateDto.getProductId();
        } else if ("delete".equals(productQuestionFurcateDto.getAction())) {
            productQuestionService.deleteQuestionById(productQuestionFurcateDto.getQuestionId());
            return "redirect:/product/productQna.do?id=" + productQuestionFurcateDto.getProductId();
        }
        return "redirect:/product/productList.do?id=" + productQuestionFurcateDto.getProductId();
    }

    @GetMapping("/productReview.do")
    public void productReview(@RequestParam("id") Long id,
<<<<<<< HEAD
                              @RequestParam(value = "page", required = false, defaultValue = "1")  int page,
                              Model model,
                              @AuthenticationPrincipal MemberDetails memberDetails,
                              HttpServletRequest httpServletRequest){
=======
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            Model model,
            @AuthenticationPrincipal MemberDetails memberDetails,
            HttpServletRequest httpServletRequest) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        Product product = productService.findById(id);
        int limit = 5;
        Member member = memberDetails.getMember();

        List<ProductReview> productReviews = productReviewService.productReviewFindByProductid(product.getId());
<<<<<<< HEAD
        String url = httpServletRequest.getRequestURI() + "?id=" +id;

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ProductReviewDto> productReviewPage = productReviewService.productReviewFindAllByProductId(pageable, product.getId());
        List<ProductReviewDto> productReviewDtos = productReviewService.productReviewDtoFindAllByProductId(product.getId());
=======
        String url = httpServletRequest.getRequestURI() + "?id=" + id;

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ProductReviewDto> productReviewPage = productReviewService.productReviewFindAllByProductId(pageable,
                product.getId());
        List<ProductReviewDto> productReviewDtos = productReviewService
                .productReviewDtoFindAllByProductId(product.getId());
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da

        // 1: 현재 페이지 번호
        // 2: 한 페이지당 표시할 개체 수
        // 3: 전체 개체수
        // 4: 요청 url
        String pagebar = HelloMvcUtils.getPagebar(
<<<<<<< HEAD
                page, limit, productReviews.size() , url);
=======
                page, limit, productReviews.size(), url);
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        model.addAttribute("pagebar", pagebar);
        model.addAttribute("reviews", productReviewPage.getContent());
        model.addAttribute("totalCount", productReviewDtos.size());
        model.addAttribute("productId", id);
        model.addAttribute("member", member);
    }

    @GetMapping("/productReviewCreatePage.do")
    public void productReviewCreatePage(@RequestParam("id") Long id,
<<<<<<< HEAD
                                        @RequestParam(value = "reviewId", defaultValue = "0") Long reviewId,
                                        @RequestParam(value = "productId", defaultValue = "0") Long productId,
                                        @AuthenticationPrincipal MemberDetails memberDetails,
                                        HttpServletRequest httpServletRequest,
                                        Model model){
        Member member = memberDetails.getMember();
        model.addAttribute("id", id);
        model.addAttribute("Member", member);
        if(reviewId != 0L) {
=======
            @RequestParam(value = "reviewId", defaultValue = "0") Long reviewId,
            @RequestParam(value = "productId", defaultValue = "0") Long productId,
            @AuthenticationPrincipal MemberDetails memberDetails,
            HttpServletRequest httpServletRequest,
            Model model) {
        Member member = memberDetails.getMember();
        model.addAttribute("id", id);
        model.addAttribute("Member", member);
        if (reviewId != 0L) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
            model.addAttribute("reviewId", reviewId);
        }
        model.addAttribute("productId", productId);
    }

    @PostMapping("/productReviewCreate.do")
    public String productCreateReview(@RequestParam("productNo") Long productId,
<<<<<<< HEAD
                                      @Valid ProductReviewCreateDto productReviewCreateDto,
                                      BindingResult bindingResult,
                                      HttpServletRequest httpServletRequest,
                                      @AuthenticationPrincipal MemberDetails memberDetails) throws IOException {
=======
            @Valid ProductReviewCreateDto productReviewCreateDto,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest,
            @AuthenticationPrincipal MemberDetails memberDetails) throws IOException {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Long reviewId = 0L;

        try {
            reviewId = Long.parseLong(httpServletRequest.getParameter("reviewId"));
<<<<<<< HEAD
        } catch (NumberFormatException ignore) {}

        Product product = productService.findById(productId);

        if(reviewId != 0L){
=======
        } catch (NumberFormatException ignore) {
        }

        Product product = productService.findById(productId);

        if (reviewId != 0L) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
            productReviewCreateDto.setId(reviewId);
        }

        Member member = memberDetails.getMember();
        productReviewCreateDto.setProduct(product);
        productReviewCreateDto.setMember(member);
        productReviewCreateDto.setReviewRegdate(LocalDate.now());
        productReviewService.createReview(productReviewCreateDto);

        return "redirect:/product/productReview.do?id=" + productId;
    }

    @PostMapping("/reviewfurcate.do")
    public String reviewfurcate(@RequestParam("action") String action,
<<<<<<< HEAD
                                @RequestParam("productId") Long productId,
                                @RequestParam("reviewId") Long reviewId,
                                RedirectAttributes redirectAttributes){
=======
            @RequestParam("productId") Long productId,
            @RequestParam("reviewId") Long reviewId,
            RedirectAttributes redirectAttributes) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        if ("update".equals(action)) {
            ProductReview productReview = productReviewService.findById(reviewId);
            redirectAttributes.addFlashAttribute("reviewId", reviewId);
            redirectAttributes.addFlashAttribute("productReview", productReview);
            return "redirect:/product/productReviewCreatePage.do?id=" + productId;
        } else if ("delete".equals(action)) {
            productReviewService.deleteById(reviewId);
            return "redirect:/product/productReview.do?id=" + productId;
        }
        return "redirect:/product/productList.do?id=" + productId;
    }

    @PostMapping("/productPreverify.do")
    public void productPreverify(@RequestBody Map<String, String> requestData,
<<<<<<< HEAD
                                   @AuthenticationPrincipal MemberDetails memberDetails){
        Member member = memberDetails.getMember();
        productService.preVerify(requestData, member);

//        return "";
=======
            @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        productService.preVerify(requestData, member);

        // return "";
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
    }

    @PostMapping("/productPostverify.do")
    public ResponseEntity<?> productPostverify(@RequestBody Map<String, String> requestData,
<<<<<<< HEAD
                                               @AuthenticationPrincipal MemberDetails memberDetails){
=======
            @AuthenticationPrincipal MemberDetails memberDetails) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        Member member = memberDetails.getMember();
        boolean satisfyVerify = productService.postVerify(requestData, member);
        return ResponseEntity.ok(Map.of("result", satisfyVerify));
    }

    @PostMapping("/productOrderReverse.do")
<<<<<<< HEAD
    public void productOrderReverse(@RequestBody Map<String, String> requestData){
=======
    public void productOrderReverse(@RequestBody Map<String, String> requestData) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        orderProductService.orderRollBack(requestData);
    }

    @GetMapping("/productOrderList.do")
    public void productOrderList(@AuthenticationPrincipal MemberDetails memberDetails,
<<<<<<< HEAD
                                 @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                 HttpServletRequest httpServletRequest,
                                 Model model){
=======
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            HttpServletRequest httpServletRequest,
            Model model) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        Member member = memberDetails.getMember();
        String memberId = member.getMemberId();
        List<OrderProduct> orderProducts = orderProductService.findAllOrderProductByMemberId(memberId);
        int limit = 5;

        String url = httpServletRequest.getRequestURI();

        Pageable pageable = PageRequest.of(page - 1, limit);
<<<<<<< HEAD
        Page<OrderProductDto> productOrderPage = orderProductService.productOrderFindAllByMemberId(pageable, member.getMemberId());
        List<OrderProductDto> productOrderDtos = orderProductService.productOrderDtoFindAllByMemberId(member.getMemberId());
=======
        Page<OrderProductDto> productOrderPage = orderProductService.productOrderFindAllByMemberId(pageable,
                member.getMemberId());
        List<OrderProductDto> productOrderDtos = orderProductService
                .productOrderDtoFindAllByMemberId(member.getMemberId());
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da

        // 1: 현재 페이지 번호
        // 2: 한 페이지당 표시할 개체 수
        // 3: 전체 개체수
        // 4: 요청 url
        String pagebar = HelloMvcUtils.getPagebar(
<<<<<<< HEAD
                page, limit, orderProducts.size() , url);
=======
                page, limit, orderProducts.size(), url);
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        model.addAttribute("pagebar", pagebar);
        model.addAttribute("orders", productOrderPage.getContent());
        model.addAttribute("totalCount", productOrderDtos.size());

    }

    @PostMapping("/productRefund.do")
<<<<<<< HEAD
    public String productRefund(@RequestBody Map<String, String> requestData){
=======
    public String productRefund(@RequestBody Map<String, String> requestData) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        productService.productRefund(requestData);

        return "redirect:/product/productOrderList.do";
    }

}
