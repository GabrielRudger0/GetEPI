(function ($) {
    "use strict";

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

    //CARREGAMENTO DO GRAFICO DE RESULTADOS DO MES (GRAFICO 1)
    fetch(`/home/carregargraficoresultadosmes`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return response.text()
                .then(data => {
                    Swal.fire({
                        title: "Erro ao carregar o gráfico!",
                        text: data,
                        confirmButtonColor: "#0d6efd",
                        iconHtml: '<i class="fas fa-exclamation-circle" style="color: #dc3545; font-size: 5rem;"></i>'
                    });
                    throw new Error('Erro na requisição: ' + response.status);
                });
        }
    })
    .then(data => {
        var mes = data.meses;
        var graficoEpis = data.graficoEPIs;
        var graficoResultadosEPI1 = data.resultadosMesEPI1;
        var graficoResultadosEPI2 = data.resultadosMesEPI2;
        var graficoResultadosEPI3 = data.resultadosMesEPI3;

        // CONFIGURAÇÃO DO GRAFICO 1
        var ctx1 = $("#worldwide-sales").get(0).getContext("2d");
        var myChart1 = new Chart(ctx1, {
            type: "bar",
            data: {
                labels: [mes[0], mes[1], mes[2], mes[3], mes[4], mes[5], mes[6]],
                datasets: [{
                        label: graficoEpis[0],
                        data: [graficoResultadosEPI1[0], graficoResultadosEPI1[1], graficoResultadosEPI1[2], graficoResultadosEPI1[3], graficoResultadosEPI1[4], graficoResultadosEPI1[5], graficoResultadosEPI1[6]],
                        backgroundColor: "rgb(255, 213, 25, .7)"
                    },
                    {
                        label: graficoEpis[1],
                        data: [graficoResultadosEPI2[0], graficoResultadosEPI2[1], graficoResultadosEPI2[2], graficoResultadosEPI2[3], graficoResultadosEPI2[4], graficoResultadosEPI2[5], graficoResultadosEPI2[6]],
                        backgroundColor: "rgb(255, 213, 25, .5)"
                    },
                    {
                        label: graficoEpis[2],
                        data: [graficoResultadosEPI3[0], graficoResultadosEPI3[1], graficoResultadosEPI3[2], graficoResultadosEPI3[3], graficoResultadosEPI3[4], graficoResultadosEPI3[5], graficoResultadosEPI3[6]],
                        backgroundColor: "rgb(255, 213, 25, .3)"
                    }
                ]
                },
            options: {
                responsive: true
            }
        });
    })
    .catch(error => {
        console.error('Erro na requisição fetch:', error);
        Swal.fire({
            title: "Erro ao carregar o gráfico!",
            text: "Ocorreu um erro na requisição.",
            confirmButtonColor: "#0d6efd",
            iconHtml: '<i class="fas fa-exclamation-circle" style="color: #dc3545; font-size: 5rem;"></i>'
        });
    });


    //CARREGAR GRAFICO MOVIMENTAÇÕES DA SEMANA
    fetch(`/home/carregargraficomovimentacoessemana`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return response.text()
                .then(data => {
                    Swal.fire({
                        title: "Erro ao carregar o gráfico!",
                        text: data,
                        confirmButtonColor: "#0d6efd",
                        iconHtml: '<i class="fas fa-exclamation-circle" style="color: #dc3545; font-size: 5rem;"></i>'
                    });
                    throw new Error('Erro na requisição: ' + response.status);
                });
        }
    })
    .then(data => {
        var diasSemana = data.diasSemana;
        var resultadoSemanaDevolucao = data.resultadoSemanaDevolucao;
        var resultadoSemanaEmprestimo = data.resultadoSemanaEmprestimo;

        // CONFIGURAÇÃO DO GRAFICO 2
        var ctx2 = $("#salse-revenue").get(0).getContext("2d");
        var myChart2 = new Chart(ctx2, {
            type: "line",
            data: {
                labels: [diasSemana[0], diasSemana[1], diasSemana[2], diasSemana[3], diasSemana[4], diasSemana[5], diasSemana[6]],
                datasets: [{
                        label: "Devoluções",
                        data: [resultadoSemanaDevolucao[0], resultadoSemanaDevolucao[1], resultadoSemanaDevolucao[2], resultadoSemanaDevolucao[3], resultadoSemanaDevolucao[4], resultadoSemanaDevolucao[5], resultadoSemanaDevolucao[6]],
                        backgroundColor: "rgb(255, 213, 25, .5)",
                        fill: true
                    },
                    {
                        label: "Empréstimos",
                        data: [resultadoSemanaEmprestimo[0], resultadoSemanaEmprestimo[1], resultadoSemanaEmprestimo[2], resultadoSemanaEmprestimo[3], resultadoSemanaEmprestimo[4], resultadoSemanaEmprestimo[5], resultadoSemanaEmprestimo[6]],
                        backgroundColor: "rgb(253, 126, 20, .5)",
                        fill: true
                    }
                ]
                },
            options: {
                responsive: true
            }
        });
    })
    .catch(error => {
        console.error('Erro na requisição fetch:', error);
        Swal.fire({
            title: "Erro ao carregar o gráfico!",
            text: error,
            confirmButtonColor: "#0d6efd",
            iconHtml: '<i class="fas fa-exclamation-circle" style="color: #dc3545; font-size: 5rem;"></i>'
        });
    });

})(jQuery);

