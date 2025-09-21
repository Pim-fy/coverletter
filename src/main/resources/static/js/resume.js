// 전역 변수로 현재 사용자 ID를 저장할 공간을 만듭니다.
let currentUserId;

document.addEventListener('DOMContentLoaded', () => {
    loadResumeData();
    addClickListeners();
});

function translateRecruitmentType(type) {
    const typeMap = { 'NEWCOMMER': '신입', 'EXPERIENCED': '경력' };
    return typeMap[type] || type;
}

function translateEducationStatus(status) {
    const statusMap = {
        'GRADUATED': '졸업', 'ENROLLED': '재학', 'ON_LEAVE': '휴학',
        'DROPPED_OUT': '중퇴', 'EXPELLED': '제적'
    };
    return statusMap[status] || status;
}

function translateEducationType(type) {
    const typeMap = {
        'HIGH_SCHOOL': '고등학교', 'UNIVERSITY': '대학교', 'GRADUATE_SCHOOL': '대학원'
    };
    return typeMap[type] || type;
}

function translateTrainingStatus(status) {
    const statusMap = {
        'COMPLETED': '수료',
        'INCOMPLETED': '미수료',
        'IN_PROGRESS': '교육중',
        'NOT_APPLICABLE': '해당없음'
    };
    return statusMap[status] || status;
}

function translateOAGrade(grade) {
    const gradeMap = {
        'HIGH': '상',
        'MEDIUM': '중',
        'LOW': '하'
    };
    return gradeMap[grade] || grade;
}

function translateMilitaryStatus(status) {
    const statusMap = {
        'COMPLETED': '군필', 'INCOMPLETE': '미필',
        'EXEMPTION': '면제', 'NA': '해당없음'
    };
    return statusMap[status] || status;
}

// 클릭 가능한 모든 섹션에 이벤트 리스너를 추가하는 함수
function addClickListeners() {
    document.querySelectorAll('.clickable-section').forEach(section => {
        section.addEventListener('click', () => {
            const url = section.dataset.url;
            // 저장된 currentUserId를 사용합니다.
            if (url && currentUserId) {
                window.location.href = `${url}?userId=${currentUserId}`;
            } else if (url) {
                // 혹시라도 userId를 못가져온 경우를 대비
                 window.location.href = url;
            }
        });
    });
}

async function loadResumeData() {
    try {
        const response = await fetch('/api/resume');
        if (!response.ok) {
            if (response.status === 401 || response.status === 403) {
                alert('로그인이 필요합니다.');
                window.location.href = '/login.html';
                return;
            }
            throw new Error(`데이터를 불러오는데 실패했습니다: ${response.statusText}`);
        }
        const data = await response.json();
        console.log('서버로부터 받은 전체 데이터:', data);

        // [핵심 수정] 데이터를 불러올 때 memberInfo에서 userId를 추출하여 전역 변수에 저장
        if (data.memberInfo && data.memberInfo.userId) {
            currentUserId = data.memberInfo.userId;
        }

        fillAllData(data);
    } catch (error) {
        console.error('오류:', error);
        alert(error.message);
    }
}

function setText(id, text) {
    const element = document.getElementById(id);
    if (element) {
        element.textContent = text || '';
    }
}

function renderList(containerId, templateId, list, populateRow) {
    const container = document.getElementById(containerId);
    const template = document.getElementById(templateId);
    if (!container || !template) return;

    // 첫 번째 자식(헤더 tr)을 제외한 모든 데이터 tr을 제거
    while (container.children.length > 1) {
        container.removeChild(container.lastChild);
    }

    if (list && list.length > 0) {
        // 헤더의 rowspan을 데이터 길이에 맞게 동적으로 설정
        const headerCell = container.querySelector('.left-header');
        if(headerCell) {
            headerCell.rowSpan = list.length + 1;
        }

        list.forEach(item => {
            const newRow = template.cloneNode(true);
            newRow.removeAttribute('id');
            newRow.style.display = ''; // 또는 'table-row'
            populateRow(newRow, item);
            container.appendChild(newRow);
        });
    } else {
        // 데이터가 없을 경우 rowspan을 2로 유지
        const headerCell = container.querySelector('.left-header');
        if(headerCell) {
            headerCell.rowSpan = 2;
        }
    }
}

function fillAllData(data) {
    if (!data) return;

    // 1. 개인정보 및 지원정보
    if (data.memberInfo) {
        const info = data.memberInfo;
        setText('name', info.name);
        setText('dateOfBirth', info.dateOfBirth);
        setText('address', info.address);
        setText('phoneNumber', info.phoneNumber);
        setText('emergencyPhoneNumber', info.emergencyPhoneNumber);
        setText('email', info.email);
        const imageElement = document.getElementById('profile-image-display');
        const fallbackElement = document.getElementById('profile-image-fallback');
        if (imageElement && fallbackElement) {
            // DB에 저장된 이미지 경로가 있는 경우
            if (info.profileImagePath) {
                imageElement.src = info.profileImagePath;
                imageElement.style.display = 'block'; // 이미지를 보여줌
                fallbackElement.style.display = 'none'; // 안내 문구는 숨김
            } 
            // 이미지 경로가 없는 경우
            else {
                imageElement.style.display = 'none'; // 이미지를 숨김
                fallbackElement.style.display = 'block'; // 안내 문구를 보여줌
            }
        }
    }
    if (data.recruitInfo && data.recruitInfo.length > 0) {
        const recruit = data.recruitInfo[0];
        setText('recruitmentType', translateRecruitmentType(recruit.recruitmentType));
        setText('recruitmentPart', recruit.recruitmentPart);
        setText('salaryRequirement', recruit.salaryRequirement);
    }

    // 2. 학력 목록
    if (data.educationList && data.educationList.length > 0) {
        // 고등학교 데이터 찾기
        const highSchoolData = data.educationList.find(edu => edu.educationType === 'HIGH_SCHOOL');
        if (highSchoolData) {
            setText('hs_period', `${highSchoolData.educationStartDate || ''} - ${highSchoolData.educationEndDate || ''}`);
            setText('hs_schoolName', highSchoolData.educationSchoolName || '');
            setText('hs_schoolType', translateEducationType(highSchoolData.educationType));
            setText('hs_status', translateEducationStatus(highSchoolData.educationStatus));
            setText('hs_major', highSchoolData.educationMajor || '');
            setText('hs_grade', highSchoolData.educationGrade || '');
            setText('hs_location', highSchoolData.educationLocation || '');
        }

        // 대학교 데이터 찾기
        const universityData = data.educationList.find(edu => edu.educationType === 'UNIVERSITY');
        if (universityData) {
            setText('uni_period', `${universityData.educationStartDate || ''} - ${universityData.educationEndDate || ''}`);
            setText('uni_schoolName', universityData.educationSchoolName || '');
            setText('uni_schoolType', translateEducationType(universityData.educationType));
            setText('uni_status', translateEducationStatus(universityData.educationStatus));
            setText('uni_major', universityData.educationMajor || '');
            setText('uni_grade', universityData.educationGrade || '');
            setText('uni_location', universityData.educationLocation || '');

            // 휴학 정보는 대학교 데이터에 종속된 것으로 간주하여 표시
            let absenceInfo = '';
            if (universityData.absenceStartDate) {
                absenceInfo = `${universityData.absenceStartDate} - ${universityData.absenceEndDate}`;
                if (universityData.absenceReason) {
                    absenceInfo += ` (${universityData.absenceReason})`;
                }
            }
            setText('uni_absence', absenceInfo);
        }
    }

    // 3. 경력 목록
    renderList('career-list-container', 'career-row-template', data.careerList, (row, career) => {
        row.querySelector('.company').textContent = career.company || '';
        const period = `${career.careerStartDate || ''} - ${career.careerEndDate || ''}`;
        row.querySelector('.period').textContent = period;
        row.querySelector('.task').textContent = career.careerTask || '';
        row.querySelector('.reason').textContent = career.reasonForLeaving || '';
    });

    // 4. 교육사항 목록
    renderList('training-list-container', 'training-row-template', data.trainingList, (row, train) => {
        const period = `${train.trainingStartDate || ''} - ${train.trainingEndDate || ''}`;
        row.querySelector('.period').textContent = period;
        row.querySelector('.name').textContent = train.trainingName || '';
        row.querySelector('.content').textContent = train.trainingContent || '';
        row.querySelector('.company').textContent = train.trainingCompany || '';
        row.querySelector('.status').textContent = translateTrainingStatus(train.trainingStatus);
    });

    // 5. 수상경력 목록
    renderList('prize-list-container', 'prize-row-template', data.prizeList, (row, prize) => {
        row.querySelector('.name').textContent = prize.prizeName || '';
        row.querySelector('.company').textContent = prize.prizeCompany || '';
        row.querySelector('.year').textContent = prize.prizeYear || '';
        row.querySelector('.etc').textContent = prize.etc || '';
    });
    
    // 6. 자격사항 및 외국어 목록 (수정됨)
    // 복잡한 중첩 구조가 사라졌으므로, 다른 목록들과 동일한 방식으로 간단하게 호출합니다.
    renderList('qualification-list-container', 'qualification-row-template', data.qualificationList, (row, qual) => {
        row.querySelector('.name').textContent = qual.qualificationName || '';
        row.querySelector('.date').textContent = qual.qualificationDate || '';
        row.querySelector('.company').textContent = qual.qualificationCompany || '';
    });
    renderList('language-list-container', 'language-row-template', data.languageList, (row, lang) => {
        row.querySelector('.name').textContent = lang.language || '';
        row.querySelector('.date').textContent = lang.languageDate || '';
        row.querySelector('.score').textContent = lang.languageScore || '';
    });

    // 7. OA, 컴퓨터, 병역 (단일 항목)
    if (data.oaList && data.oaList.length > 0) {
        const oa = data.oaList[0];
        setText('oa_hancom', translateOAGrade(oa.hancom));
        setText('oa_excel', translateOAGrade(oa.excel));
        setText('oa_powerpoint', translateOAGrade(oa.powerpoint));
        setText('oa_etc', oa.etc);
    }
    if (data.computerList && data.computerList.length > 0) {
        const comp = data.computerList[0];
        setText('computer_skills', comp.computer);
    }
    if (data.militaryList && data.militaryList.length > 0) {
        const military = data.militaryList[0];
        setText('military_status', translateMilitaryStatus(military.militaryStatus));
        setText('military_rank', military.militaryRank);
        setText('military_branch', military.militaryBranch);
        setText('military_discharge', military.militaryDischarge);
        const period = `${military.militaryStartDate || ''} - ${military.militaryEndDate || ''}`;
        setText('military_period', period);
    }
}