const USER_ID = new URLSearchParams(window.location.search).get('userId');

document.addEventListener('DOMContentLoaded', () => {
    if (!USER_ID) {
        alert("사용자 ID가 없습니다.");
        return;
    }
    loadOAData();
    loadComputerData();
    loadMilitaryData();
    setupEventListeners();
});

// --- 데이터 로딩 함수들 ---
async function loadOAData() {
    try {
        const response = await fetch(`/OA/${USER_ID}`);
        const result = await response.json();
        if (result.success && result.oas && result.oas.length > 0) {
            const oa = result.oas[0];
            document.getElementById('oaId').value = oa.oaId || '';
            document.getElementById('hancom').value = oa.hancom || 'LOW';
            document.getElementById('excel').value = oa.excel || 'LOW';
            document.getElementById('powerpoint').value = oa.powerpoint || 'LOW';
            document.getElementById('etc').value = oa.etc || '';
        }
    } catch (error) { console.error('OA 정보 로딩 오류:', error); }
}

async function loadComputerData() {
    try {
        const response = await fetch(`/Computer/${USER_ID}`);
        const result = await response.json();
        if (result.success && result.computers && result.computers.length > 0) {
            const computer = result.computers[0];
            document.getElementById('computerId').value = computer.computerId || '';
            document.getElementById('computer').value = computer.computer || '';
        }
    } catch (error) { console.error('컴퓨터 능력 정보 로딩 오류:', error); }
}

async function loadMilitaryData() {
    try {
        const response = await fetch(`/Military/${USER_ID}`);
        const result = await response.json();
        if (result.success && result.militaries && result.militaries.length > 0) {
            const military = result.militaries[0];
            document.getElementById('militaryId').value = military.militaryId || '';
            document.getElementById('militaryStatus').value = military.militaryStatus || 'NA';
            document.getElementById('militaryRank').value = military.militaryRank || '';
            document.getElementById('militaryBranch').value = military.militaryBranch || '';
            document.getElementById('militaryDischarge').value = military.militaryDischarge || '';
            document.getElementById('militaryStartDate').value = military.militaryStartDate || '';
            document.getElementById('militaryEndDate').value = military.militaryEndDate || '';
        }
    } catch (error) { console.error('병역 정보 로딩 오류:', error); }
}

// --- 이벤트 리스너 설정 ---
function setupEventListeners() {
    document.getElementById('oa-form').addEventListener('submit', handleOASubmit);
    document.getElementById('computer-form').addEventListener('submit', handleComputerSubmit);
    document.getElementById('military-form').addEventListener('submit', handleMilitarySubmit);
    document.getElementById('oa-delete-all-btn').addEventListener('click', handleDeleteAllOA);
    document.getElementById('computer-delete-all-btn').addEventListener('click', handleDeleteAllComputer);
    document.getElementById('military-delete-all-btn').addEventListener('click', handleDeleteAllMilitary);
}

// --- 삭제 핸들러 함수들 ---
async function handleDeleteAllOA() {
    if (!confirm('정말로 모든 OA 정보를 삭제하시겠습니까?')) return;
    try {
        const response = await fetch(`/OA/user/${USER_ID}`, { method: 'DELETE' });
        const result = await response.json();
        if (result.success) {
            alert(result.message);
            loadOAData();
        } else { alert('삭제 실패: ' + result.message); }
    } catch (error) { console.error('OA 전체 삭제 오류:', error); }
}

async function handleDeleteAllComputer() {
    if (!confirm('정말로 모든 컴퓨터 사용능력 정보를 삭제하시겠습니까?')) return;
    try {
        const response = await fetch(`/Computer/user/${USER_ID}`, { method: 'DELETE' });
        const result = await response.json();
        if (result.success) {
            alert(result.message);
            loadComputerData();
        } else { alert('삭제 실패: ' + result.message); }
    } catch (error) { console.error('컴퓨터 전체 삭제 오류:', error); }
}

async function handleDeleteAllMilitary() {
    if (!confirm('정말로 모든 병역사항 정보를 삭제하시겠습니까?')) return;
    try {
        const response = await fetch(`/Military/user/${USER_ID}`, { method: 'DELETE' });
        const result = await response.json();
        if (result.success) {
            alert(result.message);
            loadMilitaryData();
        } else { alert('삭제 실패: ' + result.message); }
    } catch (error) { console.error('병역사항 전체 삭제 오류:', error); }
}

// --- 폼 제출 핸들러들 ---
async function handleOASubmit(e) {
    e.preventDefault();
    const oaId = document.getElementById('oaId').value;
    const isUpdate = !!oaId;
    const data = {
        hancom: document.getElementById('hancom').value,
        excel: document.getElementById('excel').value,
        powerpoint: document.getElementById('powerpoint').value,
        etc: document.getElementById('etc').value,
    };
    if (isUpdate) data.oaId = oaId;
    
    const url = `/OA/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';
    
    try {
        const response = await fetch(url, { method: method, headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data) });
        const result = await response.json();
        if (result.success) {
            alert('OA 정보가 저장되었습니다.');
            loadOAData();
        } else { alert('오류: ' + result.message); }
    } catch (error) { console.error('OA 정보 저장 오류:', error); }
}

async function handleComputerSubmit(e) {
    e.preventDefault();
    const computerId = document.getElementById('computerId').value;
    const isUpdate = !!computerId;
    const data = { computer: document.getElementById('computer').value };
    if (isUpdate) data.computerId = computerId;

    const url = `/Computer/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, { method: method, headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data) });
        const result = await response.json();
        if (result.success) {
            alert('컴퓨터 사용능력이 저장되었습니다.');
            loadComputerData();
        } else { alert('오류: ' + result.message); }
    } catch (error) { console.error('컴퓨터 사용능력 저장 오류:', error); }
}

async function handleMilitarySubmit(e) {
    e.preventDefault();
    const militaryId = document.getElementById('militaryId').value;
    const isUpdate = !!militaryId;
    const data = {
        militaryStatus: document.getElementById('militaryStatus').value,
        militaryRank: document.getElementById('militaryRank').value,
        militaryBranch: document.getElementById('militaryBranch').value,
        militaryDischarge: document.getElementById('militaryDischarge').value,
        militaryStartDate: document.getElementById('militaryStartDate').value,
        militaryEndDate: document.getElementById('militaryEndDate').value,
    };
    if (isUpdate) data.militaryId = militaryId;

    const url = `/Military/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, { method: method, headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data) });
        const result = await response.json();
        if (result.success) {
            alert('병역사항이 저장되었습니다.');
            loadMilitaryData();
        } else { alert('오류: ' + result.message); }
    } catch (error) { console.error('병역사항 저장 오류:', error); }
}