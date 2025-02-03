function initCarousel(containerId, prevBtnId, nextBtnId) {
    const container = document.getElementById(containerId);
    const wrapper = container.querySelector('.carousel-wrapper');
    const prevBtn = document.getElementById(prevBtnId);
    const nextBtn = document.getElementById(nextBtnId);

    const cards = wrapper.children;
    const cardWidth = cards[0].offsetWidth; // 카드 하나의 너비
    const moviesPerPage = 4; // 한 번에 보여줄 영화 수
    let currentPosition = 0; // 현재 위치

    function updateButtons() {
        prevBtn.disabled = currentPosition === 0;
        nextBtn.disabled = currentPosition === (cards.length - moviesPerPage) * cardWidth;
    }

    prevBtn.addEventListener('click', () => {
        if (currentPosition > 0) {
            currentPosition -= cardWidth * moviesPerPage;
            wrapper.style.transform = `translateX(-${currentPosition}px)`;
            updateButtons();
        }
    });

    nextBtn.addEventListener('click', () => {
        if (currentPosition < (cards.length - moviesPerPage) * cardWidth) {
            currentPosition += cardWidth * moviesPerPage;
            wrapper.style.transform = `translateX(-${currentPosition}px)`;
            updateButtons();
        }
    });

    // 초기 버튼 상태 업데이트
    updateButtons();
}

// 박스오피스 캐러셀 초기화
initCarousel('show-movie-container', 'prev-btn-show', 'next-btn-show');
// 상영 예정 영화 캐러셀 초기화
initCarousel('prev-movie-container', 'prev-btn-prev', 'next-btn-prev');
