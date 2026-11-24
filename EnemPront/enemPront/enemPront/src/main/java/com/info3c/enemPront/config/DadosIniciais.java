package com.info3c.enemPront.config;

import com.info3c.enemPront.dto.QuestaoForm;
import com.info3c.enemPront.model.AreaConhecimento;
import com.info3c.enemPront.model.EdicaoEnem;
import com.info3c.enemPront.model.Topico;
import com.info3c.enemPront.repository.AreaConhecimentoRepository;
import com.info3c.enemPront.service.CatalogoService;
import com.info3c.enemPront.service.QuestaoService;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DadosIniciais implements ApplicationRunner {

    private final AreaConhecimentoRepository areas;
    private final CatalogoService catalogo;
    private final QuestaoService questaoService;

    public DadosIniciais(AreaConhecimentoRepository areas, CatalogoService catalogo, QuestaoService questaoService) {
        this.areas = areas;
        this.catalogo = catalogo;
        this.questaoService = questaoService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (areas.count() > 0) {
            return;
        }
        catalogo.criarArea("Matemática e suas Tecnologias");
        AreaConhecimento natureza = catalogo.criarArea("Ciências da Natureza e suas Tecnologias");
        AreaConhecimento humanas = catalogo.criarArea("Ciências Humanas e suas Tecnologias");
        catalogo.criarArea("Linguagens, Códigos e suas Tecnologias");

        EdicaoEnem enem2009 = catalogo.criarEdicao(2009, "R");
        catalogo.criarEdicao(2012, "R");
        catalogo.criarEdicao(2019, "R");

        Topico termodinamica = catalogo.criarTopico(natureza.getId(), "Física", "Termodinâmica", "A");
        Topico zoologia = catalogo.criarTopico(natureza.getId(), "Biologia", "Zoologia", "M");
        Topico eraVargas = catalogo.criarTopico(humanas.getId(), "História", "Era Vargas", "M");

        questaoService.salvar(questao(enem2009.getId(), termodinamica.getId(), "M", "B",
                "A invenção da geladeira proporcionou uma revolução no aproveitamento dos alimentos, ao permitir "
                        + "que fossem armazenados e transportados por longos períodos. O processo cíclico de "
                        + "funcionamento de uma geladeira utiliza um gás que circula entre o congelador e a parte "
                        + "externa. É por meio dos processos de compressão, que ocorre na parte externa, e de "
                        + "expansão, que ocorre na parte interna, que o gás proporciona a troca de calor entre o "
                        + "interior e o exterior da geladeira. Com base nessa informação, qual das afirmações "
                        + "abaixo está correta?",
                List.of("A expansão do gás é um processo que cede a energia necessária ao resfriamento da parte "
                                + "interna da geladeira.",
                        "O calor flui de forma não-espontânea da parte mais fria, no interior, para a mais quente, "
                                + "no exterior da geladeira.",
                        "A quantidade de calor cedida ao meio externo é igual ao calor retirado da geladeira.",
                        "A eficiência é tanto maior quanto menos isolado termicamente do ambiente externo for o "
                                + "seu compartimento interno.",
                        "A energia retirada do interior pode ser devolvida à geladeira abrindo-se a sua porta, o "
                                + "que reduz seu consumo de energia.")), null);

        questaoService.salvar(questao(enem2009.getId(), zoologia.getId(), "B", "D",
                "O exoesqueleto dos crustáceos é formado por quitina e impregnações de sais calcários e, por isso, "
                        + "é mais duro quando comparado com o exoesqueleto de outros artrópodes. Esse revestimento "
                        + "externo confere proteção, mas, por ser duro, limita o crescimento desses animais. Para "
                        + "superar essa limitação, o exoesqueleto deve ser",
                List.of("Formado somente na fase adulta do animal.",
                        "Fragmentado para expansão nas áreas de articulação.",
                        "Modelado continuamente para ajuste ao tamanho do corpo.",
                        "Substituído por meio de mudas que ocorrem periodicamente.",
                        "Impregnado por pequena quantidade de sais para sua distensão.")), null);

        questaoService.salvar(questao(enem2009.getId(), eraVargas.getId(), "A", "D",
                "A partir de 1942 e estendendo-se até o final do Estado Novo, o Ministro do Trabalho, Indústria e "
                        + "Comércio de Getúlio Vargas falou aos ouvintes da Rádio Nacional semanalmente, por dez "
                        + "minutos, no programa “Hora do Brasil”. O objetivo declarado do governo era esclarecer os "
                        + "trabalhadores acerca das inovações na legislação de proteção ao trabalho. Os programas "
                        + "“Hora do Brasil” contribuíram para",
                List.of("Conscientizar os trabalhadores de que os direitos sociais foram conquistados por seu "
                                + "esforço, após anos de lutas sindicais.",
                        "Promover a autonomia dos grupos sociais, por meio de uma linguagem simples e de fácil "
                                + "entendimento.",
                        "Estimular os movimentos grevistas, que reivindicavam um aprofundamento dos direitos "
                                + "trabalhistas.",
                        "Consolidar a imagem de Vargas como um governante protetor das massas.",
                        "Aumentar os grupos de discussão política dos trabalhadores, estimulados pelas palavras do "
                                + "ministro.")), null);
    }

    private static QuestaoForm questao(Long edicaoId, Long topicoId, String dificuldade, String gabarito,
            String enunciado, List<String> alternativas) {
        QuestaoForm form = new QuestaoForm();
        form.setEdicaoId(edicaoId);
        form.setEnunciado(enunciado);
        form.setAlternativaA(alternativas.get(0));
        form.setAlternativaB(alternativas.get(1));
        form.setAlternativaC(alternativas.get(2));
        form.setAlternativaD(alternativas.get(3));
        form.setAlternativaE(alternativas.get(4));
        form.setAlternativaCorreta(gabarito);
        form.getTopicos().get(0).setTopicoId(topicoId);
        form.getTopicos().get(0).setDificuldade(dificuldade);
        return form;
    }
}
