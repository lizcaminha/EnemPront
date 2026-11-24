(function () {
  'use strict';

  function pronto(funcao) {
    if (document.readyState !== 'loading') {
      funcao();
    } else {
      document.addEventListener('DOMContentLoaded', funcao);
    }
  }

  function iniciarOlhos() {
    document.querySelectorAll('.eye').forEach(function (botao) {
      botao.addEventListener('click', function () {
        var campo = botao.parentElement.querySelector('input');
        if (!campo) {
          return;
        }
        var mostrar = campo.type === 'password';
        campo.type = mostrar ? 'text' : 'password';
        botao.textContent = botao.textContent.indexOf('👁') >= 0 ? '🙈' : '👁️';
        botao.setAttribute('aria-label', mostrar ? 'Ocultar senha' : 'Mostrar senha');
      });
    });
  }

  function iniciarCadastro() {
    var form = document.getElementById('fCad');
    if (!form) {
      return;
    }
    var nome = document.getElementById('nome');
    var email = document.getElementById('email');
    var nasc = document.getElementById('nasc');
    var p1 = document.getElementById('p1');
    var p2 = document.getElementById('p2');
    var botao = document.getElementById('cadBtn');
    var erro = document.getElementById('perr');
    var itens = document.querySelectorAll('#chk li');

    function atualizar() {
      var senha = p1.value;
      var regras = [
        senha.length >= 8,
        /[0-9]/.test(senha),
        /[^\p{L}\p{N}\s]/u.test(senha)
      ];
      itens.forEach(function (item, indice) {
        item.classList.toggle('ok', Boolean(regras[indice]));
      });
      var iguais = p1.value === p2.value;
      erro.hidden = p2.value === '' || iguais;
      var completo = nome.value.trim() !== '' && email.value.trim() !== '' && nasc.value !== '';
      botao.disabled = !(regras.every(Boolean) && iguais && p2.value !== '' && completo);
    }

    [nome, email, nasc, p1, p2].forEach(function (campo) {
      campo.addEventListener('input', atualizar);
      campo.addEventListener('change', atualizar);
    });
    atualizar();
  }

  function iniciarFiltros() {
    var form = document.getElementById('filtros');
    if (!form) {
      return;
    }
    form.querySelectorAll('select[data-auto]').forEach(function (campo) {
      campo.addEventListener('change', function () {
        form.submit();
      });
    });
    var limpar = form.querySelector('[data-limpar]');
    if (limpar) {
      limpar.addEventListener('click', function () {
        window.location.href = form.getAttribute('action');
      });
    }
  }

  function formatarHoras(minutos) {
    var horas = Math.floor(minutos / 60);
    var resto = minutos % 60;
    return horas + ':' + (resto < 10 ? '0' : '') + resto;
  }

  function iniciarSimulado() {
    var contador = document.getElementById('cnt');
    if (!contador) {
      return;
    }
    var form = contador.closest('form');
    var campo = document.getElementById('quantidade');
    var area = document.getElementById('area');
    var saidaQuantidade = document.getElementById('sQ');
    var saidaTempo = document.getElementById('sT');
    var saidaArea = document.getElementById('sA');
    var aviso = document.getElementById('simMsg');
    var minutosPorQuestao = parseInt(form.getAttribute('data-min-questao') || '3', 10);

    function atualizar() {
      var quantidade = parseInt(campo.value || '0', 10);
      saidaQuantidade.textContent = quantidade;
      saidaTempo.textContent = formatarHoras(quantidade * minutosPorQuestao);
      saidaArea.textContent = area.value === '' ? 'Todas' : area.options[area.selectedIndex].text;
    }

    contador.querySelectorAll('button').forEach(function (botao) {
      botao.addEventListener('click', function () {
        campo.value = botao.getAttribute('data-q');
        contador.querySelectorAll('button').forEach(function (outro) {
          outro.classList.toggle('on', outro === botao);
        });
        aviso.hidden = true;
        atualizar();
      });
    });
    area.addEventListener('change', atualizar);
    form.addEventListener('submit', function (evento) {
      if (!campo.value) {
        evento.preventDefault();
        aviso.hidden = false;
      }
    });
    atualizar();
  }

  function formatarCronometro(segundos) {
    var horas = Math.floor(segundos / 3600);
    var minutos = Math.floor((segundos % 3600) / 60);
    var resto = segundos % 60;
    var mm = (horas > 0 && minutos < 10 ? '0' : '') + minutos;
    var ss = (resto < 10 ? '0' : '') + resto;
    return (horas > 0 ? horas + ':' : '') + mm + ':' + ss;
  }

  function iniciarCronometro() {
    var relogio = document.getElementById('cron');
    var form = document.getElementById('fProva');
    if (!relogio || !form) {
      return;
    }
    var restante = parseInt(relogio.getAttribute('data-restante'), 10) || 0;
    var temporizador = null;

    function tic() {
      relogio.textContent = formatarCronometro(restante);
      if (restante <= 0) {
        window.clearInterval(temporizador);
        form.submit();
        return;
      }
      restante -= 1;
    }

    temporizador = window.setInterval(tic, 1000);
    tic();
  }

  function iniciarConfirmacoes() {
    document.querySelectorAll('form[data-confirm]').forEach(function (form) {
      form.addEventListener('submit', function (evento) {
        if (!window.confirm(form.getAttribute('data-confirm'))) {
          evento.preventDefault();
        }
      });
    });
  }

  pronto(function () {
    iniciarOlhos();
    iniciarCadastro();
    iniciarFiltros();
    iniciarSimulado();
    iniciarCronometro();
    iniciarConfirmacoes();
  });
})();
