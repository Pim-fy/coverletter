// 1. URL의 쿼리 파라미터에서 userId를 가져옵니다. (예: ...?userId=1)
const USER_ID = new URLSearchParams(window.location.search).get('userId');

// --- 페이지가 완전히 로드되면 아래 함수들을 실행합니다 ---
document.addEventListener('DOMContentLoaded', () => {
    // userId가 없으면 기능이 동작할 수 없으므로, 사용자에게 알리고 중단합니다.
    if (!USER_ID) {
        alert("사용자 ID를 찾을 수 없습니다. 메인 페이지로 돌아갑니다.");
        window.location.href = '/index.html';
        return;
    }

    // 2. 개인정보와 지원정보를 각각 불러오는 함수를 호출합니다.
    loadMemberData();
    loadRecruitData();
    
    // 폼 제출(저장)을 위한 이벤트 리스너도 설정합니다.
    setupEventListeners();

    setupImageUploadListeners(); // 이미지 업로드 리스너 설정 함수 호출
});


// --- 함수 정의 ---

// 3. 백엔드에 개인정보(Member)를 요청하고, 응답받은 값으로 폼을 채웁니다.
async function loadMemberData() {
    try {
        const response = await fetch(`/Member/${USER_ID}`);
        if (!response.ok) throw new Error("개인정보를 불러오는 데 실패했습니다.");
        
        const result = await response.json();

        if (result.success) {
            // id를 이용해 각 input의 value를 채웁니다.
            document.getElementById('name').value = result.name || '';
            document.getElementById('dateOfBirth').value = result.dateOfBirth || '';
            document.getElementById('address').value = result.address || '';
            document.getElementById('phoneNumber').value = result.phoneNumber || '';
            document.getElementById('emergencyPhoneNumber').value = result.emergencyPhoneNumber || '';
            document.getElementById('email').value = result.email || '';
            // 이미지 경로가 있으면 미리보기에 설정
            const imagePreview = document.getElementById('image-preview');
            if (result.profileImagePath) {
                imagePreview.src = result.profileImagePath;
            } else {
                imagePreview.src = "/path/to/default-image.png"; // 기본 이미지 경로
            }
        } else {
            console.warn("개인정보가 없습니다:", result.message);
        }
    } catch (error) { 
        console.error('개인정보 로딩 중 오류:', error);
    }
}

// 4. 백엔드에 지원정보(Recruit)를 요청하고, 응답받은 값으로 폼을 채웁니다.
async function loadRecruitData() {
    try {
        const response = await fetch(`/Recruit/${USER_ID}`);
        if (!response.ok) throw new Error("지원정보를 불러오는 데 실패했습니다.");

        const result = await response.json();

        // 지원정보는 여러 개일 수 있으나, 여기서는 첫 번째 데이터를 사용합니다.
        if (result.success && result.recruits && result.recruits.length > 0) {
            const recruit = result.recruits[0];
            document.getElementById('recruitId').value = recruit.recruitId || '';
            document.getElementById('recruitmentType').value = recruit.recruitmentType || '';
            document.getElementById('recruitmentPart').value = recruit.recruitmentPart || '';
            document.getElementById('salaryRequirement').value = recruit.salaryRequirement || '';
        } else {
            console.warn("지원정보가 없습니다:", result.message);
        }
    } catch (error) { 
        console.error('지원정보 로딩 중 오류:', error);
    }
}

async function handleDeleteAllRecruits() {
    if (!confirm('정말로 모든 지원 정보를 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.')) {
        return;
    }
    try {
        const response = await fetch(`/Recruit/user/${USER_ID}`, {
            method: 'DELETE'
        });
        const result = await response.json();
        if (result.success) {
            alert(result.message);
            loadRecruitData(); // 폼을 비우고 새로고침
        } else {
            alert('삭제 실패: ' + result.message);
        }
    } catch (error) {
        console.error('전체 삭제 오류:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}


// 지원 정보 폼을 비우는 작은 함수
function clearRecruitForm() {
    document.getElementById('recruitId').value = '';
    document.getElementById('recruit-form').reset();
}


// --- 아래는 저장(Update/Create) 기능입니다 ---

function setupEventListeners() {
    document.getElementById('member-form').addEventListener('submit', handleMemberSubmit);
    document.getElementById('recruit-form').addEventListener('submit', handleRecruitSubmit);
    document.getElementById('recruit-delete-all-btn').addEventListener('click', handleDeleteAllRecruits);
}

async function handleMemberSubmit(e) {
    e.preventDefault();
    const data = {
        name: document.getElementById('name').value,
        dateOfBirth: document.getElementById('dateOfBirth').value,
        address: document.getElementById('address').value,
        phoneNumber: document.getElementById('phoneNumber').value,
        emergencyPhoneNumber: document.getElementById('emergencyPhoneNumber').value,
        email: document.getElementById('email').value,
    };
    try {
        const response = await fetch(`/Member/${USER_ID}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        const result = await response.json();
        if (result.success) {
            alert('개인정보가 수정되었습니다.');
        } else {
            alert('오류: ' + result.message);
        }
    } catch (error) { console.error('개인정보 저장 오류:', error); }
}

async function handleRecruitSubmit(e) {
    e.preventDefault();
    const recruitId = document.getElementById('recruitId').value;
    const isUpdate = !!recruitId;
    const data = {
        recruitmentType: document.getElementById('recruitmentType').value,
        recruitmentPart: document.getElementById('recruitmentPart').value,
        salaryRequirement: document.getElementById('salaryRequirement').value,
    };
    if(isUpdate) data.recruitId = recruitId;

    const url = isUpdate ? `/Recruit/${USER_ID}` : `/Recruit/${USER_ID}`;
    const method = isUpdate ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        const result = await response.json();
        if (result.success) {
            alert('지원정보가 저장되었습니다.');
            loadRecruitData();
        } else {
            alert('오류: ' + result.message);
        }
    } catch (error) { console.error('지원정보 저장 오류:', error); }
}

// 이미지 업로드 관련 이벤트 리스너 설정
function setupImageUploadListeners() {
    const uploadBtn = document.getElementById('upload-btn');
    const fileInput = document.getElementById('profileImage');

    // '이미지 변경' 버튼 클릭 시 파일 선택창 열기
    uploadBtn.addEventListener('click', () => {
        fileInput.click();
    });

    // 파일이 선택되면 업로드 실행
    fileInput.addEventListener('change', () => {
        const file = fileInput.files[0];
        if (file) {
            uploadImage(file);
        }
    });
}

// 이미지 업로드 함수
async function uploadImage(file) {
    const formData = new FormData();
    formData.append('profileImage', file);

    try {
        const response = await fetch(`/Member/${USER_ID}/profileImage`, {
            method: 'POST',
            body: formData,
        });

        const result = await response.json();

        if (result.success) {
            alert('이미지가 성공적으로 업로드되었습니다.');
            // 이미지 미리보기 업데이트 및 개인정보 다시 로드
            loadMemberData(); 
        } else {
            alert('이미지 업로드 실패: ' + result.message);
        }
    } catch (error) {
        console.error('이미지 업로드 오류:', error);
        alert('이미지 업로드 중 오류가 발생했습니다.');
    }
}

