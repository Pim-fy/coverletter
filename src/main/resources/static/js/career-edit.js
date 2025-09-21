const USER_ID = new URLSearchParams(window.location.search).get('userId');

document.addEventListener('DOMContentLoaded', () => {
    if (!USER_ID) {
        alert("사용자 ID가 없습니다.");
        return;
    }
    loadCareers();
    setupEventListeners();
});

async function loadCareers() {
    try {
        const response = await fetch(`/Career/${USER_ID}`);
        if (!response.ok) throw new Error("데이터 로딩 실패");
        const result = await response.json();
        
        const container = document.getElementById('career-list-container');
        const template = document.getElementById('career-row-template');
        container.innerHTML = '';

        if (result.success && result.careers) {
            result.careers.forEach(career => {
                const newRow = template.cloneNode(true);
                newRow.dataset.careerId = career.careerId;
                newRow.dataset.careerData = JSON.stringify(career);
                newRow.querySelector('.company').textContent = career.company;
                newRow.querySelector('.period').textContent = `${career.careerStartDate || ''} - ${career.careerEndDate || ''}`;
                newRow.querySelector('.task').textContent = career.careerTask;
                container.appendChild(newRow);
            });
        }
    } catch (error) { console.error('경력 목록 로딩 오류:', error); }
}

async function handleFormSubmit(e) {
    e.preventDefault();
    const careerId = document.getElementById('careerId').value;
    const isUpdate = !!careerId;
    const careerData = {
        company: document.getElementById('company').value,
        careerStartDate: document.getElementById('careerStartDate').value,
        careerEndDate: document.getElementById('careerEndDate').value,
        careerTask: document.getElementById('careerTask').value,
        reasonForLeaving: document.getElementById('reasonForLeaving').value,
    };
    if (isUpdate) careerData.careerId = careerId;

    const url = isUpdate ? `/Career/${USER_ID}` : `/Career/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(careerData)
        });
        const result = await response.json();
        if (result.success) {
            alert(isUpdate ? '수정되었습니다.' : '추가되었습니다.');
            clearForm();
            loadCareers();
        } else { alert('오류: ' + result.message); }
    } catch (error) { console.error('저장 오류:', error); }
}

async function deleteCareer(careerId) {
    try {
        const response = await fetch(`/Career/${USER_ID}/${careerId}`, { method: 'DELETE' });
        const result = await response.json();
        if (result.success) {
            alert('삭제되었습니다.');
            loadCareers();
        } else { alert('삭제 실패: ' + result.message); }
    } catch (error) { console.error('삭제 오류:', error); }
}

function clearForm() {
    document.getElementById('form-title').textContent = '새 경력 추가';
    document.getElementById('career-form').reset();
    document.getElementById('careerId').value = '';
}

function handleListClick(e) {
    const target = e.target;
    const row = target.closest('tr');
    if (!row) return;
    const careerId = row.dataset.careerId;

    if (target.classList.contains('edit-btn')) {
        const careerData = JSON.parse(row.dataset.careerData);
        document.getElementById('form-title').textContent = '경력 수정';
        document.getElementById('careerId').value = careerId;
        document.getElementById('company').value = careerData.company;
        document.getElementById('careerStartDate').value = careerData.careerStartDate;
        document.getElementById('careerEndDate').value = careerData.careerEndDate;
        document.getElementById('careerTask').value = careerData.careerTask;
        document.getElementById('reasonForLeaving').value = careerData.reasonForLeaving;
        window.scrollTo(0, document.body.scrollHeight);
    }
    if (target.classList.contains('delete-btn')) {
        if (confirm('정말로 이 경력을 삭제하시겠습니까?')) {
            deleteCareer(careerId);
        }
    }
}

function setupEventListeners() {
    document.getElementById('career-form').addEventListener('submit', handleFormSubmit);
    document.getElementById('career-list-container').addEventListener('click', handleListClick);
    document.getElementById('clear-form-btn').addEventListener('click', clearForm);
}