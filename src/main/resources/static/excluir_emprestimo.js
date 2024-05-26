
// Adicione um ouvinte de eventos aos botões de exclusão
document.querySelectorAll('.excluir').forEach(function(button) {
    button.addEventListener('click',
    function() {
        Swal.fire({
          title: "Continuar com a exclusão do empréstimo?",
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

            const emprestimoId = this.dataset.emprestimoId;

            // Realize a chamada AJAX para excluir o recurso
            fetch(`/listaemprestimo/${emprestimoId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => {
                if (response.ok) {
                    // A exclusão foi bem-sucedida
                    console.log('Empréstimo excluído com sucesso.');

                    // Remove a linha da tabela após a exclusão
                    //row.remove();
                    Swal.fire({
                        title: "Empréstimo excluído com sucesso!",
                        icon: "success",
                        showConfirmButton: false
                    });
                    setTimeout(function() {
                        window.location.href = "/listaemprestimo";
                    }, 1700);

                } else {
                    // A solicitação DELETE falhou
                    console.error('Erro ao excluir empréstimo.');
                    alert('Erro ao excluir empréstimo');
                }
            })
            .catch(error => {
                // Lidar com erros de rede ou outros erros
                console.error('Erro de rede:', error);
                alert('Erro de rede:' + error);
            });
          }
        });
    });
});
