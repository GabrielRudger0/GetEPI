
// Adicione um ouvinte de eventos aos botões de exclusão
document.querySelectorAll('.paginacaomovimentacao').forEach(function(button) {
    button.addEventListener('click',
    function() {
        const row = this.closest('tr'); // Obtém a linha atual da tabela

        const movimentopagina = this.dataset.movimentopagina;
        const paginaatual = this.dataset.paginaatual;

        // Realize a chamada AJAX para excluir o recurso
        fetch(`/listamovimentacoes/passarpagina/${movimentopagina}/${paginaatual}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(response => {
          if (response.ok) {
          window.location.href = "/listamovimentacoes";
          }
      });
    });
});
