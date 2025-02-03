function initFeaturedCarousel() {
    const featuredContainer = document.getElementById('featured-movie-container');
    const prevBtn = document.getElementById('prev-btn-feature');
    const nextBtn = document.getElementById('next-btn-feature');

    let currentIndex = 0;
    const cards = Array.from(featuredContainer.children); // 카드 리스트
    const totalCards = cards.length;

    // 각 카드 너비 계산
    const cardWidth = featuredContainer.offsetWidth;

    // 컨테이너 너비 설정
    featuredContainer.style.width = `${cardWidth * totalCards}px`;

    function updateCarousel() {
        const offset = -currentIndex * cardWidth;
        featuredContainer.style.transform = `translateX(${offset}px)`;
    }

    prevBtn.addEventListener('click', () => {
        currentIndex = (currentIndex > 0) ? currentIndex - 1 : totalCards - 1;
        updateCarousel();
    });

    nextBtn.addEventListener('click', () => {
        currentIndex = (currentIndex < totalCards - 1) ? currentIndex + 1 : 0;
        updateCarousel();
    });

    // 초기화
    updateCarousel();
}

// 캐러셀 초기화
initFeaturedCarousel();