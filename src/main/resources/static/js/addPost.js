var saleButton = document.getElementById('sale');
var rentButton = document.getElementById('rent');

saleButton.addEventListener('click', function() {
     if (!saleButton.classList.contains('active')) {
        saleButton.classList.add('active');
     }
     if (rentButton.classList.contains('active')) {
        rentButton.classList.remove('active');
     }
});

rentButton.addEventListener('click', function() {
     if (!rentButton.classList.contains('active')) {
        rentButton.classList.add('active');
     }
     if (saleButton.classList.contains('active')) {
        saleButton.classList.remove('active');
     }
});