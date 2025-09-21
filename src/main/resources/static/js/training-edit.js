const USER_ID = new URLSearchParams(window.location.search).get('userId');

document.addEventListener('DOMContentLoaded', () => {
    if (!USER_ID) { alert("사용자 ID가 없습니다."); return; }
    loadTrainings();
    setupEventListeners();
});

async function loadTrainings() {
    try {
        const response = await fetch(`/Training/${USER_ID}`);
        if (!response.ok) throw new Error("데이터 로딩 실패");
        const result = await response.json();
        
        const container = document.getElementById('training-list-container');
        const template = document.getElementById('training-row-template');
        container.innerHTML = '';

        if (result.success && result.trainings) {
            result.trainings.forEach(train => {
                const newRow = template.cloneNode(true);
                newRow.dataset.trainingId = train.trainingId;
                newRow.dataset.trainingData = JSON.stringify(train);
                newRow.querySelector('.name').textContent = train.trainingName;
                newRow.querySelector('.company').textContent = train.trainingCompany;
                newRow.querySelector('.period').textContent = `${train.trainingStartDate || ''} - ${train.trainingEndDate || ''}`;
                newRow.querySelector('.status').textContent = train.trainingStatus;
                container.appendChild(newRow);
            });
        }
    } catch (error) { console.error('교육사항 목록 로딩 오류:', error); }
}

async function handleFormSubmit(e) {
    e.preventDefault();
    const trainingId = document.getElementById('trainingId').value;
    const isUpdate = !!trainingId;
    const trainingData = {
        trainingName: document.getElementById('trainingName').value,
        trainingCompany: document.getElementById('trainingCompany').value,
        trainingStartDate: document.getElementById('trainingStartDate').value,
        trainingEndDate: document.getElementById('trainingEndDate').value,
        trainingContent: document.getElementById('trainingContent').value,
        trainingStatus: document.getElementById('trainingStatus').value,
    };
    if (isUpdate) trainingData.trainingId = trainingId;

    const url = `/Training/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(trainingData)
        });
        const result = await response.json();
        if (result.success) {
            alert(isUpdate ? '수정되었습니다.' : '추가되었습니다.');
            clearForm();
            loadTrainings();
        } else { alert('오류: ' + result.message); }
    } catch (error) { console.error('저장 오류:', error); }
}

async function deleteTraining(trainingId) {
    try {
        const response = await fetch(`/Training/${USER_ID}/${trainingId}`, { method: 'DELETE' });
        const result = await response.json();
        if (result.success) {
            alert('삭제되었습니다.');
            loadTrainings();
        } else { alert('삭제 실패: ' + result.message); }
    } catch (error) { console.error('삭제 오류:', error); }
}

function clearForm() {
    document.getElementById('form-title').textContent = '새 교육사항 추가';
    document.getElementById('training-form').reset();
    document.getElementById('trainingId').value = '';
}

function handleListClick(e) {
    const target = e.target;
    const row = target.closest('tr');
    if (!row) return;
    const trainingId = row.dataset.trainingId;

    if (target.classList.contains('edit-btn')) {
        const trainData = JSON.parse(row.dataset.trainingData);
        document.getElementById('form-title').textContent = '교육사항 수정';
        document.getElementById('trainingId').value = trainingId;
        document.getElementById('trainingName').value = trainData.trainingName;
        document.getElementById('trainingCompany').value = trainData.trainingCompany;
        document.getElementById('trainingStartDate').value = trainData.trainingStartDate;
        document.getElementById('trainingEndDate').value = trainData.trainingEndDate;
        document.getElementById('trainingContent').value = trainData.trainingContent;
        document.getElementById('trainingStatus').value = trainData.trainingStatus;
        window.scrollTo(0, document.body.scrollHeight);
    }
    if (target.classList.contains('delete-btn')) {
        if (confirm('정말로 이 교육사항을 삭제하시겠습니까?')) {
            deleteTraining(trainingId);
        }
    }
}

function setupEventListeners() {
    document.getElementById('training-form').addEventListener('submit', handleFormSubmit);
    document.getElementById('training-list-container').addEventListener('click', handleListClick);
    document.getElementById('clear-form-btn').addEventListener('click', clearForm);
}