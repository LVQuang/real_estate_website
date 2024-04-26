var carousel = document.getElementById('carouselExampleControls');
var pagination = document.getElementById('pagination');
carousel.addEventListener('slide.bs.carousel', function (event) {
    var activeIndex = event.to;
    var pageLinks = pagination.getElementsByTagName('a');
    for (var i = 0; i < pageLinks.length; i++) {
        if (i === activeIndex) {
            pageLinks[i].parentNode.classList.add('active');
        } else {
            pageLinks[i].parentNode.classList.remove('active');
        }
    }
});

// Chuyen trang theo nut Previous va Next
var carousel = new bootstrap.Carousel(document.getElementById('carouselExampleControls'));
document.querySelector('.carousel-control-prev').addEventListener('click', function () {
    carousel.prev();
});
document.querySelector('.carousel-control-next').addEventListener('click', function () {
    carousel.next();
});