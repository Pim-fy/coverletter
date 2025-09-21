// /resources/static/js/education-edit.js

const USER_ID = new URLSearchParams(window.location.search).get('userId');

document.addEventListener('DOMContentLoaded', () => {
    if (!USER_ID) {
        alert("사용자 ID가 없습니다.");
        return;
    }
    loadEducations();
    setupEventListeners();
});

// Read: 학력 목록 불러오기
async function loadEducations() {
    try {
        const response = await fetch(`/Education/${USER_ID}`);
        if (!response.ok) throw new Error("데이터 로딩 실패");
        const result = await response.json();
        
        const container = document.getElementById('education-list-container');
        const template = document.getElementById('education-row-template');
        container.innerHTML = '';

        if (result.success && result.education) {
            result.education.forEach(edu => {
                const newRow = template.cloneNode(true);
                newRow.dataset.educationId = edu.educationId;
                newRow.dataset.educationData = JSON.stringify(edu);

                newRow.querySelector('.schoolName').textContent = edu.educationSchoolName;
                newRow.querySelector('.major').textContent = edu.educationMajor;
                newRow.querySelector('.period').textContent = `${edu.educationStartDate || ''} - ${edu.educationEndDate || ''}`;
                newRow.querySelector('.status').textContent = edu.educationStatus;
                
                container.appendChild(newRow);
            });
        }
    } catch (error) { console.error('학력 목록 로딩 오류:', error); }
}

// Create & Update: 폼 제출 이벤트 처리
async function handleFormSubmit(e) {
    e.preventDefault();
    const educationId = document.getElementById('educationId').value;
    const isUpdate = !!educationId;
    const educationData = {
        educationSchoolName: document.getElementById('educationSchoolName').value,
        educationMajor: document.getElementById('educationMajor').value,
        educationStartDate: document.getElementById('educationStartDate').value,
        educationEndDate: document.getElementById('educationEndDate').value,
        educationGrade: document.getElementById('educationGrade').value,
        educationType: document.getElementById('educationType').value,
        educationStatus: document.getElementById('educationStatus').value,
        educationLocation: document.getElementById('educationLocation').value,
        absenceStartDate: document.getElementById('absenceStartDate').value,
        absenceEndDate: document.getElementById('absenceEndDate').value,
        absenceReason: document.getElementById('absenceReason').value,
    };
    if (isUpdate) educationData.educationId = educationId;

    const url = isUpdate ? `/Education/${USER_ID}` : `/Education/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(educationData)
        });
        const result = await response.json();
        if (result.success) {
            alert(isUpdate ? '수정되었습니다.' : '추가되었습니다.');
            clearForm();
            loadEducations();
        } else { alert('오류: ' + result.message); }
    } catch (error) { console.error('저장 오류:', error); }
}

// Delete: 학력 삭제 함수
async function deleteEducation(educationId) {
    try {
        const response = await fetch(`/Education/${USER_ID}/${educationId}`, { method: 'DELETE' });
        const result = await response.json();
        if (result.success) {
            alert('삭제되었습니다.');
            loadEducations();
        } else { alert('삭제 실패: ' + result.message); }
    } catch (error) { console.error('삭제 오류:', error); }
}

// 폼 초기화 함수
function clearForm() {
    document.getElementById('form-title').textContent = '새 학력 추가';
    document.getElementById('education-form').reset();
    document.getElementById('educationId').value = '';
}

// 수정/삭제 버튼 이벤트 처리
function handleListClick(e) {
    const target = e.target;
    const row = target.closest('tr');
    if (!row) return;
    const educationId = row.dataset.educationId;

    if (target.classList.contains('edit-btn')) {
        const eduData = JSON.parse(row.dataset.educationData);
        document.getElementById('form-title').textContent = '학력 수정';
        document.getElementById('educationId').value = educationId;
        document.getElementById('educationSchoolName').value = eduData.educationSchoolName;
        document.getElementById('educationMajor').value = eduData.educationMajor;
        document.getElementById('educationStartDate').value = eduData.educationStartDate;
        document.getElementById('educationEndDate').value = eduData.educationEndDate;
        document.getElementById('educationGrade').value = eduData.educationGrade;
        document.getElementById('educationType').value = eduData.educationType;
        document.getElementById('educationStatus').value = eduData.educationStatus;
        document.getElementById('educationLocation').value = eduData.educationLocation;
        document.getElementById('absenceStartDate').value = eduData.absenceStartDate;
        document.getElementById('absenceEndDate').value = eduData.absenceEndDate;
        document.getElementById('absenceReason').value = eduData.absenceReason;
        window.scrollTo(0, document.body.scrollHeight);
    }
    if (target.classList.contains('delete-btn')) {
        if (confirm('정말로 이 학력 정보를 삭제하시겠습니까?')) {
            deleteEducation(educationId);
        }
    }
}

// 모든 이벤트 리스너 설정
function setupEventListeners() {
    document.getElementById('education-form').addEventListener('submit', handleFormSubmit);
    document.getElementById('education-list-container').addEventListener('click', handleListClick);
    document.getElementById('clear-form-btn').addEventListener('click', clearForm);
}