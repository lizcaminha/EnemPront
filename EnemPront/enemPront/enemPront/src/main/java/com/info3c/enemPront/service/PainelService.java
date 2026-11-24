package com.info3c.enemPront.service;

import com.info3c.enemPront.dto.Indicador;
import com.info3c.enemPront.repository.EdicaoEnemRepository;
import com.info3c.enemPront.repository.QuestaoRepository;
import com.info3c.enemPront.repository.TentativaRespostaRepository;
import com.info3c.enemPront.repository.TopicoRepository;
import com.info3c.enemPront.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PainelService {

    private final UsuarioRepository usuarios;
    private final QuestaoRepository questoes;
    private final TopicoRepository topicos;
    private final EdicaoEnemRepository edicoes;
    private final TentativaRespostaRepository tentativas;

    public PainelService(UsuarioRepository usuarios, QuestaoRepository questoes, TopicoRepository topicos,
            EdicaoEnemRepository edicoes, TentativaRespostaRepository tentativas) {
        this.usuarios = usuarios;
        this.questoes = questoes;
        this.topicos = topicos;
        this.edicoes = edicoes;
        this.tentativas = tentativas;
    }

    @Transactional(readOnly = true)
    public List<Indicador> totais() {
        return List.of(
                new Indicador("Usuários", usuarios.count()),
                new Indicador("Questões", questoes.count()),
                new Indicador("Tópicos", topicos.count()),
                new Indicador("Edições do ENEM", edicoes.count()),
                new Indicador("Tentativas de resposta", tentativas.count()));
    }
}
