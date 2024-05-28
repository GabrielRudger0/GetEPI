(function ($) {
    "use strict";

    // epis mais utilizados
    var worldwide_epi_1 = document.getElementById('worldwide_epi_1').innerText;
    var worldwide_epi_2 = document.getElementById('worldwide_epi_2').innerText;
    var worldwide_epi_3 = document.getElementById('worldwide_epi_3').innerText;

    // sete meses anterires
    var worldwide_mes_1 = document.getElementById('worldwide_mes_1').innerText;
    var worldwide_mes_2 = document.getElementById('worldwide_mes_2').innerText;
    var worldwide_mes_3 = document.getElementById('worldwide_mes_3').innerText;
    var worldwide_mes_4 = document.getElementById('worldwide_mes_4').innerText;
    var worldwide_mes_5 = document.getElementById('worldwide_mes_5').innerText;
    var worldwide_mes_6 = document.getElementById('worldwide_mes_6').innerText;
    var worldwide_mes_7 = document.getElementById('worldwide_mes_7').innerText;

    // Quantidade de emprestimos do epi1 nos meses estipulados
    var worldwide_mes1_epi1 = document.getElementById('worldwide_mes1_epi1').innerText;
    var worldwide_mes2_epi1 = document.getElementById('worldwide_mes2_epi1').innerText;
    var worldwide_mes3_epi1 = document.getElementById('worldwide_mes3_epi1').innerText;
    var worldwide_mes4_epi1 = document.getElementById('worldwide_mes4_epi1').innerText;
    var worldwide_mes5_epi1 = document.getElementById('worldwide_mes5_epi1').innerText;
    var worldwide_mes6_epi1 = document.getElementById('worldwide_mes6_epi1').innerText;
    var worldwide_mes7_epi1 = document.getElementById('worldwide_mes7_epi1').innerText;

    // Quantidade de emprestimos do epi2 nos meses estipulados
    var worldwide_mes1_epi2 = document.getElementById('worldwide_mes1_epi2').innerText;
    var worldwide_mes2_epi2 = document.getElementById('worldwide_mes2_epi2').innerText;
    var worldwide_mes3_epi2 = document.getElementById('worldwide_mes3_epi2').innerText;
    var worldwide_mes4_epi2 = document.getElementById('worldwide_mes4_epi2').innerText;
    var worldwide_mes5_epi2 = document.getElementById('worldwide_mes5_epi2').innerText;
    var worldwide_mes6_epi2 = document.getElementById('worldwide_mes6_epi2').innerText;
    var worldwide_mes7_epi2 = document.getElementById('worldwide_mes7_epi2').innerText;

    // Quantidade de emprestimos do epi3 nos meses estipulados
    var worldwide_mes1_epi3 = document.getElementById('worldwide_mes1_epi3').innerText;
    var worldwide_mes2_epi3 = document.getElementById('worldwide_mes2_epi3').innerText;
    var worldwide_mes3_epi3 = document.getElementById('worldwide_mes3_epi3').innerText;
    var worldwide_mes4_epi3 = document.getElementById('worldwide_mes4_epi3').innerText;
    var worldwide_mes5_epi3 = document.getElementById('worldwide_mes5_epi3').innerText;
    var worldwide_mes6_epi3 = document.getElementById('worldwide_mes6_epi3').innerText;
    var worldwide_mes7_epi3 = document.getElementById('worldwide_mes7_epi3').innerText;


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
            labels: [worldwide_mes_1, worldwide_mes_2, worldwide_mes_3, worldwide_mes_4, worldwide_mes_5, worldwide_mes_6, worldwide_mes_7],
            datasets: [{
                    label: worldwide_epi_1,
                    data: [worldwide_mes1_epi1, worldwide_mes2_epi1, worldwide_mes3_epi1, worldwide_mes4_epi1, worldwide_mes5_epi1, worldwide_mes6_epi1, worldwide_mes7_epi1],
                    backgroundColor: "rgb(255, 213, 25, .7)"
                },
                {
                    label: worldwide_epi_2,
                    data: [worldwide_mes1_epi2, worldwide_mes2_epi2, worldwide_mes3_epi2, worldwide_mes4_epi2, worldwide_mes5_epi2, worldwide_mes6_epi2, worldwide_mes7_epi2],
                    backgroundColor: "rgb(255, 213, 25, .5)"
                },
                {
                    label: worldwide_epi_3,
                    data: [worldwide_mes1_epi3, worldwide_mes2_epi3, worldwide_mes3_epi3, worldwide_mes4_epi3, worldwide_mes5_epi3, worldwide_mes6_epi3, worldwide_mes7_epi3],
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
            labels: ["Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"],
            datasets: [{
                    label: "Devoluções",
                    data: [1, 0, 0, 0, 0, 0, 0],
                    backgroundColor: "rgb(255, 213, 25, .5)",
                    fill: true
                },
                {
                    label: "Empréstimos",
                    data: [1, 0, 0, 0, 0, 0, 0],
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

