package com.sh.onezip.business.controller;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.attachment.service.S3FileService;
import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.business.service.BusinessService;
import com.sh.onezip.common.HelloMvcUtils;
import com.sh.onezip.customerquestioncenter.entity.AnswerCheck;
import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import com.sh.onezip.customerquestioncenter.service.QuestionCenterService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import com.sh.onezip.product.dto.BizProductDetailDto;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.product.service.ProductService;
import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productanswer.service.ProductAnswerService;
import com.sh.onezip.productquestion.dto.ProductQuestionDto;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productquestion.service.ProductQuestionService;
import com.sh.onezip.productreview.dto.ProductReviewDto;
import com.sh.onezip.productreview.service.ProductReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/business")
@Slf4j
public class BusinessController {
    @Autowired
    BusinessService businessService;
    @Autowired
    MemberService memberService;
    @Autowired
    ProductService productService;
    @Autowired
    S3FileService s3FileService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    ProductQuestionService productQuestionService;
    @Autowired
    ProductAnswerService productAnswerService;
    @Autowired
    ProductReviewService productReviewService;

    @GetMapping("/productList.do")
    public void productList(@AuthenticationPrincipal MemberDetails memberDetails, @PageableDefault(size = 6, page = 0) Pageable pageable, Model model) {
        // findAllBizIdProduct 에서 member.id(회원고유번호)를 찾고 productListDtoPage Long id(상품고유번호)와 매핑하여 사업자가 등록한 상품을 조회한다.
        Page<ProductListDto> productListDtoPage = productService.findAllBizIdProduct(memberDetails.getMember().getId(), pageable);
        log.debug("productListDtoPage = {}", productListDtoPage);
        model.addAttribute("products", productListDtoPage.getContent()); // 상품 목록
        model.addAttribute("totalCount", productListDtoPage.getTotalElements()); // 전체 상품 수
        model.addAttribute("FOCount", calculateProductCount(productListDtoPage.getContent(), ProductType.FO));
        model.addAttribute("FUCount", calculateProductCount(productListDtoPage.getContent(), ProductType.FU));
        model.addAttribute("size", productListDtoPage.getSize()); // 페이지당 표시되는 상품 수
        model.addAttribute("number", productListDtoPage.getNumber()); // 현재 페이지 번호
        model.addAttribute("totalPages", productListDtoPage.getTotalPages()); // 전체 페이지 수

    }

    private long calculateProductCount(List<ProductListDto> products, ProductType type) {
        return products.stream()
                .filter(ptype -> ptype.getProductTypecode() == type)
                .count();
    }

    @PostMapping("/productList.do")
    public String productList(@RequestParam Long id,
                              RedirectAttributes redirectAttributes) {
        productService.deleteById(id);
        attachmentService.deleteByphotoId(id);
        return "redirect:/business/productList.do";
    }

    @GetMapping("/productDetailList.do")
    public void productDetailList(@RequestParam Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
    }

    @PostMapping("/productDetailList.do")
    public String productDetailList(
            @Valid BizProductDetailDto bizProductDetailDto,
            BindingResult bindingResult,
            @RequestParam("upFile") List<MultipartFile> upFiles,
            @AuthenticationPrincipal MemberDetails memberDetails,
            RedirectAttributes redirectAttributes)
            throws IOException {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        // 첨부파일 S3에 저장
        for (MultipartFile upFile : upFiles) {
            if (upFile.getSize() > 0) {
                AttachmentCreateDto attachmentCreateDto = s3FileService.upload(upFile);
                log.debug("attachmentCreateDto = {}", attachmentCreateDto);
                bizProductDetailDto.addAttachmentCreateDto(attachmentCreateDto);
            }
        }

        // 회원 정보 설정
        Member member = memberDetails.getMember();
//        bizProductDetailDto.setMemberId(member.getId());
        bizProductDetailDto.setMember(member);

        // DB 저장(사업자 상품 등록, 첨부파일)
        productService.createProductBiz(bizProductDetailDto);

        redirectAttributes.addFlashAttribute("msg", "상품이 등록되었습니다 🎁");
        return "redirect:/business/productList.do";
    }

    @GetMapping("/productUpdateList.do")
    public void productUpdateList(@RequestParam Long id, Model model) {
        // 회원 고유번호를 찾고 productListDto랑 매핑
        ProductListDto productListDto = productService.findByBizProductId(id);
        model.addAttribute("product", productListDto);
    }

    @PostMapping("/productUpdateList.do")
    public String productUpdateList(
            @Valid ProductDetailDto productDetailDto,
            BindingResult bindingResult,
            @RequestParam("upFile") List<MultipartFile> upFiles,
            @AuthenticationPrincipal MemberDetails memberDetails,
            RedirectAttributes redirectAttributes)
            throws IOException {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        log.debug("productDetailDto ={}", productDetailDto);
        // 이미지 업로드 및 수정
        updateImagesAndAttachments(productDetailDto, upFiles);

        // 회원 정보 설정
        productDetailDto.setMember(memberDetails.getMember()); // 사용자 설정

        // DB 저장 (사업자 상품 수정, 첨부파일)
        productService.updateBizProduct(productDetailDto);
        redirectAttributes.addFlashAttribute("msg", "상품 변경이 완료되었습니다.");
        return "redirect:/business/productList.do";
    }

    private void updateImagesAndAttachments(ProductDetailDto productDetailDto, List<MultipartFile> upFiles) throws IOException {
        // 새로운 이미지가 있는 경우에만 업로드 및 추가
        for (MultipartFile upFile : upFiles) {
            if (upFile.getSize() > 0) {
                AttachmentCreateDto attachmentCreateDto = s3FileService.upload(upFile);
                log.debug("attachmentCreateDto = {}", attachmentCreateDto);
                productDetailDto.addAttachmentCreateDto(attachmentCreateDto);
            }
        }
    }
    @GetMapping("/businessQnACenter.do")
    public void businessQnACenter(@PageableDefault(size = 2, page = 0) Pageable pageable, @RequestParam Long id, Model model){
        // 상품고유번호, 질문작성자, 답변 고유번호 가져오기(답변없으면 고유번호 X null 대기중/ 답변 O 고유번호 O 답변완료)
        // 상품고유번호 불러오기
        Product product = productService.findById(id);
        // 상품고유번호에 질문을 단 사람 찾아오기 (상품고유번호를 productQuestionPage변수 안에 넣고 매핑하기)
        Page<ProductQuestionDto> productQuestionPage = productQuestionService.productQuestionDtoFindAllByProductId(pageable, product.getId());
        model.addAttribute("product", product); // 상품 고유번호
        model.addAttribute("pquestion", productQuestionPage); // 상품에 대한 질문 목록
        model.addAttribute("totalCount", productQuestionPage.getTotalElements()); // 총 질문 수
        model.addAttribute("size", productQuestionPage.getSize()); // 페이지당 표시되는 상품 수
        model.addAttribute("number", productQuestionPage.getNumber()); // 현재 페이지 번호
        model.addAttribute("totalPages", productQuestionPage.getTotalPages()); // 전체 페이지 수
    }

    @PostMapping("/businessQnACenter.do")
    public String businessQnACenter(@RequestParam Long id,
                                    @RequestParam String aContent,
                                    RedirectAttributes redirectAttributes){
        // 질문 고유번호 가져오기
        ProductQuestion productQuestion = productQuestionService.findQuestionById(id);
        log.debug("productQuestion ={}",productQuestion);

        // 답변 객체 생성
        ProductAnswer productAnswer = new ProductAnswer();
        // 답변 내용 설정
        productAnswer.setAContent(aContent);
        productAnswer.setMember(productQuestion.getMember());
        // 답변 내용 해당 질문 문의글에 설정
        productAnswer.setProductQuestion(productQuestion);
        log.debug("productAnswer ={}", productAnswer);
        // 답변 등록
        productAnswerService.createPAnswer(productAnswer);
        // 리다이렉트 후 메시지 전달
        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 답변등록이 완료되었습니다. 🎈🎈🎈");
        return "redirect:/business/businessQnACenter.do?id=" + productQuestion.getProduct().getId();
    }

    @GetMapping("/businessAllReview.do")
    public void businessAllReview(@PageableDefault(size = 5, page = 0) Pageable pageable, @RequestParam Long id, Model model){
        // 상품고유번호 불러오기
        Product product = productService.findById(id);
        Page<ProductReviewDto> productReviewDtoPage = productReviewService.productReviewFindAllByProductId(pageable, product.getId());
        model.addAttribute("product", product); // 상품 고유번호
        model.addAttribute("preview", productReviewDtoPage); // 상품에 대한 질문 목록
        model.addAttribute("totalCount", productReviewDtoPage.getTotalElements()); // 총 질문 수
        model.addAttribute("size", productReviewDtoPage.getSize()); // 페이지당 표시되는 상품 수
        model.addAttribute("number", productReviewDtoPage.getNumber()); // 현재 페이지 번호
        model.addAttribute("totalPages", productReviewDtoPage.getTotalPages()); // 전체 페이지 수
    }
    @GetMapping("businessPayDeliveryList.do")
    public void businessPayDeliveryList(@RequestParam Long id, Model model){
        // 상품고유번호 불러오기
        Product product = productService.findById(id);
        model.addAttribute("product", product); // 상품 고유번호
    }
}
//    @GetMapping("/businessQnACenter.do")
//    public void businessQnACenter(@AuthenticationPrincipal MemberDetails memberDetails,
//                                  @PageableDefault(size = 6, page = 0) Pageable pageable,
//                                  @RequestParam Long id, Model model){
//        Page <ProductQuestion> productQuestionPage = productQuestionService.findAllBizQuestion(memberDetails.getMember().getId(),pageable);
//        log.debug("값을 주세요 ={}" ,productQuestionPage);
//        // 답변 대기 / 답변 완료
//        Optional<ProductAnswer> productAnswer = productAnswerService.findById(id);
//
//        model.addAttribute("questions",productQuestionPage);
//        model.addAttribute("questions", productQuestionPage.getContent()); // 질문 목록을 나타내는 리스트
//        model.addAttribute("totalCount", productQuestionPage.getTotalElements()); // 전체 질문수
//        model.addAttribute("size", productQuestionPage.getSize()); // 페이지당 표시되는 질문 수
//        model.addAttribute("number", productQuestionPage.getNumber()); // 현재 페이지 번호
//        model.addAttribute("totalPages", productQuestionPage.getTotalPages()); // 전체 페이지 수
//        model.addAttribute("unansweredCount", productAnswer == null);
//        model.addAttribute("answeredCount", productAnswer != null);
//    }

    //    @PostMapping("/productUpdateList.do")
//    public String productUpdateList(
//            @Valid ProductUpdateDto productUpdateDto,
//            BindingResult bindingResult,
//            @RequestParam("upFile") List<MultipartFile> upFiles,
//            @RequestParam(value = "fileType", required = false) String[] fileType,
//            @AuthenticationPrincipal MemberDetails memberDetails,
//            RedirectAttributes redirectAttributes)
//            throws IOException {
//        if (bindingResult.hasErrors()) {
//            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
//            log.debug("message = {}", message);
//            throw new RuntimeException(message);
//        }
//        // 첨부파일 S3에 저장
//        List<AttachmentCreateDto> AttachmentCreates = new ArrayList<>();
//        if (upFiles != null) {
//            IntStream.range(0, upFiles.size()).filter(i -> upFiles.get(i).getSize() > 0)
//                    .mapToObj(i -> {
//                        try {
//                            AttachmentCreateDto attachmentCreateDto = s3FileService.upload(upFiles.get(i));
//                            attachmentCreateDto.setRefType(fileType[i]);
//                            return attachmentCreateDto;
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }).forEach(AttachmentCreates::add);
//        }
//
//        // DB 저장 (사업자 상품 수정, 첨부파일)
//        productService.findById(memberDetails.getMember().getId()); // 상품 고유번호 찾기
//        productUpdateDto.setMember(memberDetails.getMember());
//        Product product = new Product();
//        productUpdateDto.setProduct(product);
//
//        productService.updateBizProduct(productUpdateDto);
//        redirectAttributes.addFlashAttribute("msg", "상품 변경이 완료되었습니다.");
//        return "redirect:/business/productList.do";
//    }
//}


