
// Adicione um ouvinte de eventos aos botões de exclusão
document.querySelectorAll('.excluir').forEach(function(button) {
    button.addEventListener('click',
    function() {
        Swal.fire({
          title: "Continuar com a exclusão da função?",
          text: "",
          icon: "question",
          showCancelButton: true,
          confirmButtonColor: "#0d6efd",
          cancelButtonColor: "#dc3545",
          cancelButtonText: "Não",
          confirmButtonText: "Sim"
        }).then((result) => {
          if (result.isConfirmed) {
            const row = this.closest('tr'); // Obtém a linha atual da tabela

            const funcaoId = this.dataset.funcaoId;

            // Realize a chamada AJAX para excluir o recurso
            fetch(`/listafuncao/${funcaoId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => {
                if (response.ok) {
                    // A exclusão foi bem-sucedida
                    console.log('Função excluída com sucesso.');

                    // Remove a linha da tabela após a exclusão
                    //row.remove();
                    Swal.fire({
                        title: "Função excluída com sucesso!",
                        icon: "success",
                        showConfirmButton: false
                    });
                    setTimeout(function() {
                        window.location.href = "/listafuncao";
                    }, 1700);

                } else {
                    return response.text()
                    .then(data => {
                      Swal.fire({
                          title: "Erro na exclusão do registro",
                          text: data,
                          confirmButtonColor: "#0d6efd",
                          iconHtml: '<i class="fas fa-exclamation-circle" style="color: #dc3545; font-size: 5rem;"></i>'
                      });
                    });
                }
            })
            .catch(error => {
                Swal.fire({
                  title: "Erro crítico na exclusão do registro",
                  text: data,
                  confirmButtonColor: "#0d6efd",
                  iconHtml: '<i class="fas fa-exclamation-triangle" style="color: #dc3545; font-size: 5rem;"></i>'
                });
            });
          }
        });
    });
});
