class MemberCard {
    
    constructor(divElement, mbNo) {
        this.divElement = divElement;
        this.mbNo = mbNo;

    }
    
    create = function() {
        const mbInfo = new Object();
        mbInfo['mbNo'] = this.mbNo;

        const self = this;

        Async.httpRequest('POST', '/user/selectMemberCardInfo', JSON.stringify(mbInfo), function (success) {
            let memberCardInfo = JSON.parse(success.response);

            let memberCardElement = `
                <div class="r-infor-writer">
                    `;

                    if(memberCardInfo['profileImage'] != null) {
                        memberCardElement += `<img class="r-profile" src="data:image/jpeg;base64,${memberCardInfo['profileImage']}" alt="">`;
                    } else {
                        memberCardElement += `<img class="r-profile" src="/images/user/profile.png" alt="">`;
                    }
                    memberCardElement += `
                    
                    <div>
                        <p class="r-writer"><span>${memberCardInfo['MB_NM']}</span> 요리사님</p>
                        <!--<p class="r-writer-dscpt">안녕하세요! 먹는 기쁨을 선사하는 학생입니다~</p>-->
                    </div>
                    <!--<button class="btn sm primary">팔로우</button>-->
                </div>
            `;

            self.divElement.innerHTML = memberCardElement;

        }, function (fail) {
            alert("멤버 카드 정보를 불러오지 못했습니다.");
        });

    }
    
}