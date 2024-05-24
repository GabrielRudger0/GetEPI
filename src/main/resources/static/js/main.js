(function ($) {
    "use strict";

    var worldwide_epi_1 = document.getElementById('worldwide_epi_1').innerText;
    var worldwide_epi_2 = document.getElementById('worldwide_epi_2').innerText;
    var worldwide_epi_3 = document.getElementById('worldwide_epi_3').innerText;

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();

    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Calender
    $('#calender').datetimepicker({
        inline: true,
        format: 'L'
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        items: 1,
        dots: true,
        loop: true,
        nav : false
    });


    // Chart Global Color
    Chart.defaults.color = "#6C7293";
    Chart.defaults.borderColor = "#000000";


    // Worldwide Sales Chart
    var ctx1 = $("#worldwide-sales").get(0).getContext("2d");
    var myChart1 = new Chart(ctx1, {
        type: "bar",
        data: {
            labels: ["Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro"],
            datasets: [{
                    label: worldwide_epi_1,
                    data: [10, 30, 55, 65, 60, 80, 95],
                    backgroundColor: "rgb(255, 213, 25, .7)"
                },
                {
                    label: worldwide_epi_2,
                    data: [8, 35, 40, 60, 70, 55, 75],
                    backgroundColor: "rgb(255, 213, 25, .5)"
                },
                {
                    label: worldwide_epi_3,
                    data: [12, 25, 45, 55, 65, 70, 60],
                    backgroundColor: "rgb(255, 213, 25, .3)"
                }
            ]
            },
        options: {
            responsive: true
        }
    });


    // Salse & Revenue Chart
    var ctx2 = $("#salse-revenue").get(0).getContext("2d");
    var myChart2 = new Chart(ctx2, {
        type: "line",
        data: {
            labels: ["2016", "2017", "2018", "2019", "2020", "2021", "2022"],
            datasets: [{
                    label: "Devoluções",
                    data: [15, 30, 55, 45, 70, 65, 85],
                    backgroundColor: "rgb(255, 213, 25, .5)",
                    fill: true
                },
                {
                    label: "Empréstimos",
                    data: [99, 135, 170, 130, 190, 180, 270],
                    backgroundColor: "rgb(255, 154, 0, .5)",
                    fill: true
                }
            ]
            },
        options: {
            responsive: true
        }
    });

})(jQuery);

