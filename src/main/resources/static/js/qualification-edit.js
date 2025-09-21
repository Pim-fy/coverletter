const USER_ID = new URLSearchParams(window.location.search).get('userId');

document.addEventListener('DOMContentLoaded', () => {
    if (!USER_ID) { alert("사용자 ID가 없습니다."); return; }
    loadQualifications();
    setupEventListeners();
});

async function loadQualifications() {
    try {
        const response = await fetch(`/Qualification/${USER_ID}`);
        if (!response.ok) throw new Error("데이터 로딩 실패");
        const result = await response.json();
        
        const container = document.getElementById('qualification-list-container');
        const template = document.getElementById('qualification-row-template');
        container.innerHTML = '';

        if (result.success && result.qualifications) {
            result.qualifications.forEach(qual => {
                const newRow = template.cloneNode(true);
                newRow.dataset.qualificationId = qual.qualificationId;
                newRow.dataset.qualificationData = JSON.stringify(qual);
                newRow.querySelector('.name').textContent = qual.qualificationName;
                newRow.querySelector('.date').textContent = qual.qualificationDate;
                newRow.querySelector('.company').textContent = qual.qualificationCompany;
                container.appendChild(newRow);
            });
        }
    } catch (error) { console.error('자격사항 목록 로딩 오류:', error); }
}

async function handleFormSubmit(e) {
    e.preventDefault();
    const qualificationId = document.getElementById('qualificationId').value;
    const isUpdate = !!qualificationId;
    const qualificationData = {
        qualificationName: document.getElementById('qualificationName').value,
        qualificationDate: document.getElementById('qualificationDate').value,
        qualificationCompany: document.getElementById('qualificationCompany').value,
    };
    if (isUpdate) qualificationData.qualificationId = qualificationId;

    const url = `/Qualification/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(qualificationData)
        });
        const result = await response.json();
        if (result.success) {
            alert(isUpdate ? '수정되었습니다.' : '추가되었습니다.');
            clearForm();
            loadQualifications();
        } else { alert('오류: ' + result.message); }
    } catch (error) { console.error('저장 오류:', error); }
}

async function deleteQualification(qualificationId) {
    try {
        const response = await fetch(`/Qualification/${USER_ID}/${qualificationId}`, { method: 'DELETE' });
        const result = await response.json();
        if (result.success) {
            alert('삭제되었습니다.');
            loadQualifications();
        } else { alert('삭제 실패: ' + result.message); }
    } catch (error) { console.error('삭제 오류:', error); }
}

function clearForm() {
    document.getElementById('form-title').textContent = '새 자격사항 추가';
    document.getElementById('qualification-form').reset();
    document.getElementById('qualificationId').value = '';
}

function handleListClick(e) {
    const target = e.target;
    const row = target.closest('tr');
    if (!row) return;
    const qualificationId = row.dataset.qualificationId;

    if (target.classList.contains('edit-btn')) {
        const qualData = JSON.parse(row.dataset.qualificationData);
        document.getElementById('form-title').textContent = '자격사항 수정';
        document.getElementById('qualificationId').value = qualificationId;
        document.getElementById('qualificationName').value = qualData.qualificationName;
        document.getElementById('qualificationDate').value = qualData.qualificationDate;
        document.getElementById('qualificationCompany').value = qualData.qualificationCompany;
        window.scrollTo(0, document.body.scrollHeight);
    }
    if (target.classList.contains('delete-btn')) {
        if (confirm('정말로 이 자격사항을 삭제하시겠습니까?')) {
            deleteQualification(qualificationId);
        }
    }
}

function setupEventListeners() {
    document.getElementById('qualification-form').addEventListener('submit', handleFormSubmit);
    document.getElementById('qualification-list-container').addEventListener('click', handleListClick);
    document.getElementById('clear-form-btn').addEventListener('click', clearForm);
}