 document.addEventListener('DOMContentLoaded', function() {
    const addrBtn = document.querySelector("#addrBtn");
    if (addrBtn) {
    addrBtn.addEventListener("click", function() {
    new daum.Postcode({
    oncomplete: function(data) {
    // 선택된 주소 타입에 따라 주소 설정
    let fullAddr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
    let extraAddr = '';

    // 도로명 주소 선택 시 건물명 추가
    if (data.userSelectedType === 'R' && data.bname !== '') {
    extraAddr = data.bname;
    if (data.buildingName !== '') {
    extraAddr += extraAddr ? ', ' + data.buildingName : data.buildingName;
}
    fullAddr += extraAddr ? ' (' + extraAddr + ')' : '';
}

    // 주소 입력 폼에 주소 설정
    const baseAddressInput = document.querySelector("#baseAddress");
    if (baseAddressInput) {
    baseAddressInput.value = fullAddr;
} else {
    console.error("Base address input not found");
}
}
}).open();
});
} else {
    console.error("#addrBtn not found");
}
});

