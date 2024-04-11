document.querySelector("#createOptionBtn").addEventListener('click', (e) => {
    e.preventDefault();
    const innerOption =  document.querySelector("#innerOption")
    const innerOptionIndex = innerOption.childElementCount / 3;
    innerOption.insertAdjacentHTML('beforeend', `
    <div class="sm:col-span-1">
        <label for="optionName" class="block text-sm font-semibold leading-6 text-gray-900">옵션명</label>
        <div class="mt-2.5">
            <input type="optionName" name="innerOptionName${innerOptionIndex}" id="innerOptionName${innerOptionIndex}" class="optionInput block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" placeholder="옵션 추가 시 입력해주세요." />
        </div>
    </div>
    <div class="sm:col-span-1">
        <label for="optionCost" class="block text-sm font-semibold leading-6 text-gray-900">재고수량</label>
        <div class="mt-2.5">
            <input type="optionCost" name="innerOptionStock${innerOptionIndex}" id="innerOptionStock${innerOptionIndex}" class="optionInput block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" placeholder="옵션 재고 수량을 입력해주세요." />
        </div>
    </div>
    <div class="sm:col-span-1">
        <label for="optionCost" class="block text-sm font-semibold leading-6 text-gray-900">추가요금</label>
        <div class="mt-2.5">
            <input type="optionCost" name="innerOptionPrice${innerOptionIndex}" id="innerOptionPrice${innerOptionIndex}" class="optionInput block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" placeholder="옵션 추가 시 입력해주세요." />
        </div>
    </div>

`);
    innerOption.value = parseInt(innerOptionIndex + 1);
});

document.querySelector("#deleteOptionBtn").addEventListener('click', (e) => {
    e.preventDefault();
    const innerOption = document.querySelector("#innerOption");
    const innerOptionCount = innerOption.childElementCount;
    // 삭제될 태그의 개수가 3의 배수인지 확인
    if (innerOptionCount % 3 === 0 && innerOptionCount > 0) {
        for (let i = 0; i < 3; i++) {
            innerOption.removeChild(innerOption.lastElementChild);
        }
        // 새로운 인덱스 계산 및 적용
        const innerOptionIndex = innerOption.childElementCount / 3;
        innerOption.value = parseInt(innerOptionIndex);
    } else {
        console.log("삭제할 항목이 없습니다.");
    }
});