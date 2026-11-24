package com.info3c.enemPront.service;

import com.info3c.enemPront.exception.RecursoNaoEncontradoException;
import com.info3c.enemPront.model.Questao;
import com.info3c.enemPront.model.TentativaResposta;
import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.repository.QuestaoRepository;
import com.info3c.enemPront.repository.TentativaRespostaRepository;
import com.info3c.enemPront.repository.UsuarioRepository;
import com.info3c.enemPront.util.Textos;
import java.time.Clock;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TentativaService {

    private final TentativaRespostaRepository tentativas;
    private final QuestaoRepository questoes;
    private final UsuarioRepository usuarios;
    private final Clock clock;

    public TentativaService(TentativaRespostaRepository tentativas, QuestaoRepository questoes,
            UsuarioRepository usuarios, Clock clock) {
        this.tentativas = tentativas;
        this.questoes = questoes;
        this.usuarios = usuarios;
        this.clock = clock;
    }

    @Transactional
    public boolean responder(Long usuarioId, Long questaoId, String letra) {
        String alternativa = Textos.alternativa(letra);
        Questao questao = questoes.findById(questaoId).orElseThrow(RecursoNaoEncontradoException::new);
        Usuario usuario = usuarios.findById(usuarioId).orElseThrow(RecursoNaoEncontradoException::new);
        boolean correta = questao.getAlternativaCorreta().equals(alternativa);
        tentativas.save(new TentativaResposta(usuario, questao, alternativa, LocalDate.now(clock), correta));
        return correta;
    }
}
