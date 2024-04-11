document.querySelectorAll(".refundBtn").forEach(btn => {
    btn.addEventListener('click', (e) => {
        e.preventDefault();

        const valueArr = e.target.value.split("/");

        if(typeof valueArr[0] == "string"){
            console.log("valueArr[0]은 문자열입니다.")
        }
        if(typeof valueArr[1] == "string"){
            console.log("valueArr[1]은 문자열입니다.")
        }
        refund(valueArr[0], valueArr[1]);

    });
});

function refund(productLogId, amount){
    $.ajax({
        url: `${contextPath}product/productRefund.do`,
        method: "post",
        headers: { "Content-Type": "application/json",
            [csrfHeaderName] : csrfToken
        },
        data:  JSON.stringify({
            merchant_uid: productLogId,
            amount: amount,
            checksum: 0
        }),
        success() {
            console.log('refund success진입');
        },
        complete() {
            console.log('refund complete진입');
        }
    });
}