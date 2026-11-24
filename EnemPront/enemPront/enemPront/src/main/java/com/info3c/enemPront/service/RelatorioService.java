package com.info3c.enemPront.service;

import com.info3c.enemPront.dto.UsuarioRelatorio;
import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.repository.TentativaRespostaRepository;
import com.info3c.enemPront.util.Textos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RelatorioService {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final UsuarioService usuarioService;
    private final TentativaRespostaRepository tentativas;

    public RelatorioService(UsuarioService usuarioService, TentativaRespostaRepository tentativas) {
        this.usuarioService = usuarioService;
        this.tentativas = tentativas;
    }

    @Transactional(readOnly = true)
    public List<UsuarioRelatorio> usuarios() {
        Map<Long, Object[]> resumos = new HashMap<>();
        for (Object[] linha : tentativas.resumoPorUsuario()) {
            resumos.put((Long) linha[0], linha);
        }
        return usuarioService.listar().stream().map(u -> montar(u, resumos.get(u.getId()))).toList();
    }

    public String csv(List<UsuarioRelatorio> usuarios) {
        StringBuilder csv = new StringBuilder("\uFEFF");
        csv.append("ID;Nome;Email;Nascimento;Situação;Questões respondidas;Acertos;Taxa de acerto (%);Última atividade\r\n");
        for (UsuarioRelatorio u : usuarios) {
            csv.append(u.id()).append(';')
                    .append(Textos.csv(u.nome())).append(';')
                    .append(Textos.csv(u.email())).append(';')
                    .append(Textos.csv(u.nascimento())).append(';')
                    .append(u.ativo() ? "Ativo" : "Inativo").append(';')
                    .append(u.total()).append(';')
                    .append(u.acertos()).append(';')
                    .append(u.taxa()).append(';')
                    .append(Textos.csv(u.ultimaAtividade())).append("\r\n");
        }
        return csv.toString();
    }

    private UsuarioRelatorio montar(Usuario usuario, Object[] resumo) {
        long total = resumo == null ? 0 : ((Number) resumo[1]).longValue();
        long acertos = resumo == null || resumo[2] == null ? 0 : ((Number) resumo[2]).longValue();
        String ultima = resumo == null || resumo[3] == null ? "—" : ((LocalDate) resumo[3]).format(FORMATO_DATA);
        int taxa = total == 0 ? 0 : (int) Math.round(acertos * 100.0 / total);
        return new UsuarioRelatorio(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDtNascimento().format(FORMATO_DATA),
                usuario.isAtivo(),
                total,
                acertos,
                taxa,
                ultima);
    }
}
