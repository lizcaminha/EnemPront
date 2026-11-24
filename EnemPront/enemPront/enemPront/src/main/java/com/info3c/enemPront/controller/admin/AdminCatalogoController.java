package com.info3c.enemPront.controller.admin;

import com.info3c.enemPront.exception.RegraNegocioException;
import com.info3c.enemPront.service.CatalogoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/catalogos")
public class AdminCatalogoController {

    private static final String PAGINA = "redirect:/admin/catalogos";

    private final CatalogoService catalogoService;

    public AdminCatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("areas", catalogoService.areas());
        model.addAttribute("topicos", catalogoService.topicos());
        model.addAttribute("edicoes", catalogoService.edicoes());
        return "admin/catalogos";
    }

    @PostMapping("/areas")
    public String criarArea(@RequestParam("nome") String nome, RedirectAttributes redirect) {
        return executar(() -> catalogoService.criarArea(nome), "Área cadastrada", redirect);
    }

    @PostMapping("/areas/{id}/excluir")
    public String excluirArea(@PathVariable("id") Long id, RedirectAttributes redirect) {
        return executar(() -> catalogoService.excluirArea(id), "Área excluída", redirect);
    }

    @PostMapping("/topicos")
    public String criarTopico(@RequestParam(name = "areaId", required = false) Long areaId,
            @RequestParam("materia") String materia, @RequestParam("nome") String nome,
            @RequestParam(name = "incidencia", required = false) String incidencia, RedirectAttributes redirect) {
        return executar(() -> catalogoService.criarTopico(areaId, materia, nome, incidencia),
                "Tópico cadastrado", redirect);
    }

    @PostMapping("/topicos/{id}/excluir")
    public String excluirTopico(@PathVariable("id") Long id, RedirectAttributes redirect) {
        return executar(() -> catalogoService.excluirTopico(id), "Tópico excluído", redirect);
    }

    @PostMapping("/edicoes")
    public String criarEdicao(@RequestParam(name = "ano", required = false) Integer ano,
            @RequestParam(name = "tipo", required = false) String tipo, RedirectAttributes redirect) {
        return executar(() -> catalogoService.criarEdicao(ano, tipo), "Edição cadastrada", redirect);
    }

    @PostMapping("/edicoes/{id}/excluir")
    public String excluirEdicao(@PathVariable("id") Long id, RedirectAttributes redirect) {
        return executar(() -> catalogoService.excluirEdicao(id), "Edição excluída", redirect);
    }

    private String executar(Runnable acao, String sucesso, RedirectAttributes redirect) {
        try {
            acao.run();
            redirect.addFlashAttribute("sucesso", sucesso);
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return PAGINA;
    }
}
