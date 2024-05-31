const serviceModal = {};

serviceModal.reviewStar = async function () {
    const modalElement = `
        <div class="modal" id="reviewStarModal">
            <div class="modal-content">
                <div class="modal-body">
                    <p>요리를 마치셨네요!</p>
                    <p>이번 레시피는 어떠셨나요?</p>
                    <div class="review-star">
                        <label>
                            <input type="radio" value="1" name="review-star" hidden>
                        </label>
                        <label>
                            <input type="radio" value="2" name="review-star" hidden>
                        </label>
                        <label>
                            <input type="radio" value="3" name="review-star" hidden>
                        </label>
                        <label>
                            <input type="radio" value="4" name="review-star" hidden>
                        </label>
                        <label>
                            <input type="radio" value="5" name="review-star" hidden checked>
                        </label>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="btn-wrap">
                        <a class="btn" id="cancelBtn">취소</a>
                        <a class="btn primary" id="positiveBtn">확인</a>
                    </div>
                </div>
            </div>
        </div>
    `;

    window.document.body.insertAdjacentHTML('beforeend', modalElement);

    return new Promise((resolve, reject) => {
        document.getElementById("reviewStarModal").style.display = 'block';

        document.getElementById("positiveBtn").addEventListener("click", () => {
            resolve(document.querySelector('input[name="review-star"]:checked').value);
            document.getElementById("reviewStarModal").remove();
        });

        document.getElementById("cancelBtn").addEventListener("click", () => {
            document.getElementById("reviewStarModal").remove();
        });
    });
}

serviceModal.removeConfirm = async function () {
    const modalElement = `
        <div class="modal" id="removeConfirmModal">
            <div class="modal-content">
                <div class="modal-body">
                    <p>해당 식자제를 삭제하시겠습니까?</p>
                </div>
                <div class="modal-footer">
                    <div class="btn-wrap">
                        <a id="negativeBtn" class="btn">취소</a>
                        <a id="positiveBtn" class="btn primary">확인</a>
                    </div>
                </div>
            </div>
        </div>
    `;

    window.document.body.insertAdjacentHTML('beforeend', modalElement);

    return new Promise((resolve, reject) => {
        document.getElementById("removeConfirmModal").style.display = 'block';

        document.getElementById("positiveBtn").addEventListener("click", () => {
            resolve(true);
            document.getElementById("removeConfirmModal").remove();
        });

        document.getElementById("negativeBtn").addEventListener("click", () => {
            document.getElementById("removeConfirmModal").remove();
        });
    });
}