package com.sh.onezip.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.onezip.attachment.repository.AttachmentRepository;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.orderproduct.entity.OrderProduct;
import com.sh.onezip.orderproduct.repository.OrderProductRepository;
import com.sh.onezip.payment.entity.Payment;
import com.sh.onezip.payment.repository.PaymentRepository;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.dto.ProductPurchaseInfoDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.repository.ProductRepository;
import com.sh.onezip.productlog.entity.ProductLog;
import com.sh.onezip.productlog.repository.ProductLogRepository;
import com.sh.onezip.productoption.dto.ProductOptionDto;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.repository.ProductOptionRepository;
import com.sh.onezip.productoption.service.ProductOptionService;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.sh.onezip.productlog.entity.ProductLog;
import com.sh.onezip.productlog.repository.ProductLogRepository;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.repository.ProductOptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Slf4j
@Service
@Transactional
public class ProductService {

    @Value("${port_one_api-key}")
    private String PORT_ONE_API_KEY;
    @Value("${port-one_api-secret-key}")
    private String PORT_ONE_API_SECRET_KEY;

    // variable 선언 start

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    ProductLogRepository productLogRepository;
    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    ProductOptionService productOptionService;
<<<<<<< HEAD

    ObjectMapper objectMapper = new ObjectMapper();

    // variable 선언 end
=======
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da

    ObjectMapper objectMapper = new ObjectMapper();

    // variable 선언 end

    // KMJ start

    public Page<ProductListDto> productListDtoFindAllByPrice(Pageable pageable, int price) {
        Page<Product> productPage = null;
        if ((price == 0) || (price == 100001)) {
            productPage = productRepository.findAllByPriceUpper(pageable, price);
        } else {
            productPage = productRepository.findAllByPriceUnder(pageable, price);
        }
        return productPage.map((product) -> convertToProductListDto(product));
    }

    public List<ProductListDto> productListDtoFindAllByPrice(int price) {
        List<Product> products = new ArrayList<>();
        if ((price == 0) || (price == 100001)) {
            products = productRepository.findAllByPriceUpper(price);
        } else {
            products = productRepository.findAllByPriceUnder(price);
        }
        List<ProductListDto> productListDtos = new ArrayList<>();
        for (Product product : products) {
            productListDtos.add(convertToProductListDto(product));
        }
        return productListDtos;
    }

    private ProductListDto convertToProductListDto(Product product) {
        ProductListDto productListDto = modelMapper.map(product, ProductListDto.class);
        productListDto.setMemberName(product.getMember().getName());
        productListDto.setApplyPrice((int) (product.getProductPrice() * (1 - product.getDiscountRate())));
<<<<<<< HEAD
        productListDto.setAttachmentList(attachmentRepository.findProductAttachmentToList(productListDto.getId(), "SP"));
=======
        productListDto
                .setAttachmentList(attachmentRepository.findProductAttachmentToList(productListDto.getId(), "SP"));
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        return productListDto;
    }

    public ProductDetailDto productDetailDtofindById(Long id) {
        return productRepository.findById(id)
                .map((product) -> convertToProductDetailDto(product))
                .orElseThrow();
    }

    private ProductDetailDto convertToProductDetailDto(Product product) {
        ProductDetailDto productDetailDto = modelMapper.map(product, ProductDetailDto.class);
        productDetailDto.setApplyPrice((int) (product.getProductPrice() * (1 - product.getDiscountRate())));
        return productDetailDto;
    }

    public ProductPurchaseInfoDto productPurchaseInfoDtofindById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        Product product = productOpt.orElse(null);
        ProductPurchaseInfoDto productPurchaseInfoDto = modelMapper.map(product, ProductPurchaseInfoDto.class);
        return productPurchaseInfoDto;
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public boolean postVerify(Map<String, String> requestData, Member member) {
        Payment payment = paymentRepository.findById(Long.parseLong(requestData.get("merchant_uid"))).orElse(null);
<<<<<<< HEAD
        if((payment.getMerchantUid() == requestData.get("merchant_uid")) &&
                (payment.getAmount() == Integer.parseInt(requestData.get("amount")))){
=======
        if ((payment.getMerchantUid() == requestData.get("merchant_uid")) &&
                (payment.getAmount() == Integer.parseInt(requestData.get("amount")))) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
            System.out.println("결제후 검증 완료!");
            return true;
        }
        return false;
    }

<<<<<<< HEAD

    public void preVerify(Map<String, String> requestData, Member member) {

        ProductLog productLog = productLogRepository.findById(Long.parseLong(requestData.get("merchant_uid"))).orElse(null);
        Product product = productRepository.findById(Long.parseLong(requestData.get("productId"))).orElse(null);
        ProductOption productOption = productOptionRepository.findById(Long.parseLong(requestData.get("productOptId"))).orElse(null);;
=======
    public void preVerify(Map<String, String> requestData, Member member) {

        ProductLog productLog = productLogRepository.findById(Long.parseLong(requestData.get("merchant_uid")))
                .orElse(null);
        Product product = productRepository.findById(Long.parseLong(requestData.get("productId"))).orElse(null);
        ProductOption productOption = productOptionRepository.findById(Long.parseLong(requestData.get("productOptId")))
                .orElse(null);
        ;
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        int productQuantity = Integer.parseInt(requestData.get("productQuantity"));

        // 결제 객체 생성
        Payment payment = Payment
                .builder()
                .productLog(productLog)
                .member(member)
                .buyerTel(member.getPhone() == null ? "" : member.getPhone())
                .buyerAddr("하드코딩했습니다.")
                .buyerPostcode("123-456")
                .amount(Integer.parseInt(requestData.get("amount")))
                .merchantUid(requestData.get("merchant_uid"))
                .build();

<<<<<<< HEAD
        int afterApplyPrice = (int)(productOption.getOptionCost() + (product.getProductPrice() * (1 - product.getDiscountRate())));
        System.out.println("afterApplyPrice: " + afterApplyPrice);


        //주문 객체 생성
=======
        int afterApplyPrice = (int) (productOption.getOptionCost()
                + (product.getProductPrice() * (1 - product.getDiscountRate())));
        System.out.println("afterApplyPrice: " + afterApplyPrice);

        // 주문 객체 생성
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        OrderProduct orderProduct = OrderProduct
                .builder()
                .productLog(productLog)
                .product(product)
                .productOption(productOption)
                .purchaseQuantity(productQuantity)
                .payAmount((productQuantity * afterApplyPrice)) // 단품 가격
                .build();

        orderProductRepository.save(orderProduct);
        paymentRepository.save(payment);
    }

    public void productRefund(Map<String, String> requestData) {
        String accessToken = getAccessToken();
        doProductRefund(accessToken, requestData);
    }

<<<<<<< HEAD
    public String getAccessToken(){

        RestTemplate restTemplate = new RestTemplate();


=======
    public String getAccessToken() {

        RestTemplate restTemplate = new RestTemplate();

>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        // HttpHeaders 객체 생성 (header 설정)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String, String> responseBody = new HashMap<>();
<<<<<<< HEAD
//        responseBody.put("imp_secret", PORT_ONE_API_SECRET_KEY);
//        responseBody.put("imp_key", PORT_ONE_API_KEY);
=======
        responseBody.put("imp_secret", PORT_ONE_API_SECRET_KEY);
        responseBody.put("imp_key", PORT_ONE_API_KEY);
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da

        String requestByJSON = null;

        try {
            requestByJSON = objectMapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(requestByJSON, headers);

        // 요청 url 가공
        String url = "https://api.iamport.kr/users/getToken";
        // 실제 요청을 보내는 코드
        // 모든 전송 방식 공용인 exchange()사용, put, post 방식등도 지원
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                URI.create(url),
                HttpMethod.POST,
                httpEntity,
<<<<<<< HEAD
                Map.class
        );

        String []strArr = responseEntity.getBody().get("response").toString().split(",");
        String [] nextStrArrIndex = strArr[0].split("=");
        String accessToken = nextStrArrIndex[1];


=======
                Map.class);

        String[] strArr = responseEntity.getBody().get("response").toString().split(",");
        String[] nextStrArrIndex = strArr[0].split("=");
        String accessToken = nextStrArrIndex[1];

>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        return accessToken;

    }

<<<<<<< HEAD
    public void doProductRefund(String accessToken, Map<String, String> requestData){
=======
    public void doProductRefund(String accessToken, Map<String, String> requestData) {
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        RestTemplate restTemplate = new RestTemplate();

        Long productLogId = Long.parseLong(requestData.get("merchant_uid"));
        ProductLog productLog = productLogRepository.findById(productLogId).orElse(null);
        Integer amount = Integer.parseInt(requestData.get("amount"));
        Integer checksum = Integer.parseInt(requestData.get("checksum"));

        System.out.println("requestData는 :" + requestData);

<<<<<<< HEAD
//        if(productLog.getShppingState().toString().equals("RE")){
//
//        }

=======
        // if(productLog.getShppingState().toString().equals("RE")){
        //
        // }
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da

        // HttpHeaders 객체 생성 (header 설정)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", accessToken);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("merchant_uid", productLogId.toString());
        responseBody.put("amount", amount);

        String requestByJSON = null;

        try {
            requestByJSON = objectMapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(requestByJSON, headers);

        // 요청 url 가공
        String url = "https://api.iamport.kr/payments/cancel";
        // 실제 요청을 보내는 코드
        // 모든 전송 방식 공용인 exchange()사용, put, post 방식등도 지원
        System.out.println("flag-1:");
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                URI.create(url),
                HttpMethod.POST,
                httpEntity,
<<<<<<< HEAD
                Map.class
        );
=======
                Map.class);
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
        System.out.println("flag-2:");

        System.out.println("responseEntity결제 취소 정보: " + responseEntity);

<<<<<<< HEAD

=======
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
    }

    // KMJ end



    // HBK start

    public Page<ProductListDto> findAllBizIdProduct(Long id, Pageable pageable) {
        Page<Product> productListDtoPage = productRepository.findAllBizIdProduct(id, pageable);
        return productListDtoPage.map(product -> convertToProductListDto(product));
    }

    // 사업자 상품 등록
    public Product createProductBiz(ProductDetailDto productDetailDto) {
        Product tempProduct = convertToProductDetailInsertDto(productDetailDto);
        Product product = productRepository.save(tempProduct);
        List<ProductOptionDto> productOptionList = productDetailDto.getProductOptionlist();
        if (productOptionList != null) {
            for (ProductOptionDto productOptionDto : productOptionList) {
                productOptionDto.setProductId(product.getId());
                productOptionService.createProductOption(productOptionDto);
            }
        }
        productDetailDto.getAttachments().forEach(attachmentCreateDto -> {
            attachmentCreateDto.setRefId(product.getId());
            attachmentCreateDto.setRefType("SP");
            attachmentService.createAttachment(attachmentCreateDto);
        });

        return product;
    }

    private Product convertToProductDetailInsertDto(ProductDetailDto productDetailDto) {
        Product product = modelMapper.map(productDetailDto, Product.class);
        return product;
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductListDto findByBizProductId(Long id) {
        Product product = productRepository.findByBizProductId(id);
        return convertToProductListDto(product);
    }

    public void updateBizProduct(ProductDetailDto productDetailDto) {
        Product product = productRepository.save(convertToProductDetailUpdateDto(productDetailDto));
        productDetailDto.getAttachments().forEach(attachmentCreateDto -> {
            attachmentCreateDto.setRefId(product.getId());
            attachmentCreateDto.setRefType("SP");
            attachmentService.createAttachment(attachmentCreateDto);
        });

    }

    private Product convertToProductDetailUpdateDto(ProductDetailDto productDetailDto) {
        Product product = modelMapper.map(productDetailDto, Product.class);
        return product;
    }

}
