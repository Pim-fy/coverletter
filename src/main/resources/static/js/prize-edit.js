const USER_ID = new URLSearchParams(window.location.search).get('userId');

document.addEventListener('DOMContentLoaded', () => {
    if (!USER_ID) { alert("사용자 ID가 없습니다."); return; }
    loadPrizes();
    setupEventListeners();
});

async function loadPrizes() {
    try {
        const response = await fetch(`/Prize/${USER_ID}`);
        if (!response.ok) throw new Error("데이터 로딩 실패");
        const result = await response.json();
        
        const container = document.getElementById('prize-list-container');
        const template = document.getElementById('prize-row-template');
        container.innerHTML = '';

        if (result.success && result.prizes) {
            result.prizes.forEach(prize => {
                const newRow = template.cloneNode(true);
                newRow.dataset.prizeId = prize.prizeId;
                newRow.dataset.prizeData = JSON.stringify(prize);
                newRow.querySelector('.name').textContent = prize.prizeName;
                newRow.querySelector('.company').textContent = prize.prizeCompany;
                newRow.querySelector('.year').textContent = prize.prizeYear;
                newRow.querySelector('.etc').textContent = prize.etc;
                container.appendChild(newRow);
            });
        }
    } catch (error) { console.error('수상경력 목록 로딩 오류:', error); }
}

async function handleFormSubmit(e) {
    e.preventDefault();
    const prizeId = document.getElementById('prizeId').value;
    const isUpdate = !!prizeId;
    const prizeData = {
        prizeName: document.getElementById('PrizeName').value,
        prizeCompany: document.getElementById('PrizeCompany').value,
        prizeYear: document.getElementById('PrizeYear').value,
        etc: document.getElementById('Etc').value,
    };
    if (isUpdate) prizeData.prizeId = prizeId;

    const url = `/Prize/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(prizeData)
        });
        const result = await response.json();
        if (result.success) {
            alert(isUpdate ? '수정되었습니다.' : '추가되었습니다.');
            clearForm();
            loadPrizes();
        } else { alert('오류: ' + result.message); }
    } catch (error) { console.error('저장 오류:', error); }
}

async function deletePrize(prizeId) {
    try {
        const response = await fetch(`/Prize/${USER_ID}/${prizeId}`, { method: 'DELETE' });
        const result = await response.json();
        if (result.success) {
            alert('삭제되었습니다.');
            loadPrizes();
        } else { alert('삭제 실패: ' + result.message); }
    } catch (error) { console.error('삭제 오류:', error); }
}

function clearForm() {
    document.getElementById('form-title').textContent = '새 수상경력 추가';
    document.getElementById('prize-form').reset();
    document.getElementById('prizeId').value = '';
}

function handleListClick(e) {
    const target = e.target;
    const row = target.closest('tr');
    if (!row) return;
    const prizeId = row.dataset.prizeId;

    if (target.classList.contains('edit-btn')) {
        const prizeData = JSON.parse(row.dataset.prizeData);
        document.getElementById('form-title').textContent = '수상경력 수정';
        document.getElementById('prizeId').value = prizeId;
        document.getElementById('PrizeName').value = prizeData.prizeName;
        document.getElementById('PrizeCompany').value = prizeData.prizeCompany;
        document.getElementById('PrizeYear').value = prizeData.prizeYear;
        document.getElementById('Etc').value = prizeData.etc;
        window.scrollTo(0, document.body.scrollHeight);
    }
    if (target.classList.contains('delete-btn')) {
        if (confirm('정말로 이 수상경력을 삭제하시겠습니까?')) {
            deletePrize(prizeId);
        }
    }
}

function setupEventListeners() {
    document.getElementById('prize-form').addEventListener('submit', handleFormSubmit);
    document.getElementById('prize-list-container').addEventListener('click', handleListClick);
    document.getElementById('clear-form-btn').addEventListener('click', clearForm);
}