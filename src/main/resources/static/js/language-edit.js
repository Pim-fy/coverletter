const USER_ID = new URLSearchParams(window.location.search).get('userId');

document.addEventListener('DOMContentLoaded', () => {
    if (!USER_ID) { alert("사용자 ID가 없습니다."); return; }
    loadLanguages();
    setupEventListeners();
});

async function loadLanguages() {
    try {
        const response = await fetch(`/Language/${USER_ID}`);
        if (!response.ok) throw new Error("데이터 로딩 실패");
        const result = await response.json();
        
        const container = document.getElementById('language-list-container');
        const template = document.getElementById('language-row-template');
        container.innerHTML = '';

        if (result.success && result.languages) {
            result.languages.forEach(lang => {
                const newRow = template.cloneNode(true);
                newRow.dataset.languageId = lang.languageId;
                newRow.dataset.languageData = JSON.stringify(lang);
                newRow.querySelector('.name').textContent = lang.language;
                newRow.querySelector('.date').textContent = lang.languageDate;
                newRow.querySelector('.score').textContent = lang.languageScore;
                container.appendChild(newRow);
            });
        }
    } catch (error) { console.error('외국어 목록 로딩 오류:', error); }
}

async function handleFormSubmit(e) {
    e.preventDefault();
    const languageId = document.getElementById('languageId').value;
    const isUpdate = !!languageId;
    const languageData = {
        language: document.getElementById('Language').value,
        languageDate: document.getElementById('LanguageDate').value,
        languageScore: document.getElementById('LanguageScore').value,
    };
    if (isUpdate) languageData.languageId = languageId;

    const url = `/Language/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(languageData)
        });
        const result = await response.json();
        if (result.success) {
            alert(isUpdate ? '수정되었습니다.' : '추가되었습니다.');
            clearForm();
            loadLanguages();
        } else { alert('오류: ' + result.message); }
    } catch (error) { console.error('저장 오류:', error); }
}

async function deleteLanguage(languageId) {
    try {
        const response = await fetch(`/Language/${USER_ID}/${languageId}`, { method: 'DELETE' });
        const result = await response.json();
        if (result.success) {
            alert('삭제되었습니다.');
            loadLanguages();
        } else { alert('삭제 실패: ' + result.message); }
    } catch (error) { console.error('삭제 오류:', error); }
}

function clearForm() {
    document.getElementById('form-title').textContent = '새 외국어 능력 추가';
    document.getElementById('language-form').reset();
    document.getElementById('languageId').value = '';
}

function handleListClick(e) {
    const target = e.target;
    const row = target.closest('tr');
    if (!row) return;
    const languageId = row.dataset.languageId;

    if (target.classList.contains('edit-btn')) {
        const langData = JSON.parse(row.dataset.languageData);
        document.getElementById('form-title').textContent = '외국어 능력 수정';
        document.getElementById('languageId').value = languageId;
        document.getElementById('Language').value = langData.language;
        document.getElementById('LanguageDate').value = langData.languageDate;
        document.getElementById('LanguageScore').value = langData.languageScore;
        window.scrollTo(0, document.body.scrollHeight);
    }
    if (target.classList.contains('delete-btn')) {
        if (confirm('정말로 이 외국어 능력을 삭제하시겠습니까?')) {
            deleteLanguage(languageId);
        }
    }
}

function setupEventListeners() {
    document.getElementById('language-form').addEventListener('submit', handleFormSubmit);
    document.getElementById('language-list-container').addEventListener('click', handleListClick);
    document.getElementById('clear-form-btn').addEventListener('click', clearForm);
}