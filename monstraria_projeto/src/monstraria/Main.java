package monstraria;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Ponto de entrada: menu interativo em modo texto para testar captura,
 * batalha, evolução, equipe e bestiário.
 */
public class Main {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Treinador treinador = new Treinador("Rithym");
        Bestiario bestiario = new Bestiario();

        System.out.println("Escolha seu monstro inicial:");
        System.out.println("1 - Emberit (Fogo)");
        System.out.println("2 - Ripplet (Água)");
        System.out.println("3 - Sproutle (Planta)");
        System.out.print("Escolha: ");
        int escolha = lerInteiro(scanner);
        Tipo tipoInicial = switch (escolha) {
            case 1 -> Tipo.FOGO;
            case 2 -> Tipo.AGUA;
            default -> Tipo.PLANTA;
        };
        Monstro inicial = FabricaMonstros.novoInicial(tipoInicial);
        treinador.adicionarMonstro(inicial);
        bestiario.registrar(inicial);

        int opcao;
        do {
            System.out.println();
            System.out.println("===== MONSTRARIA: CAPTURA E BATALHA =====");
            System.out.println("Treinador: " + treinador.getNome());
            System.out.println("1 - Procurar criatura selvagem e batalhar");
            System.out.println("2 - Ver equipe");
            System.out.println("3 - Ver bestiário");
            System.out.println("4 - Curar equipe");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1 -> procurarSelvagem(treinador, bestiario, scanner);
                case 2 -> listarEquipe(treinador);
                case 3 -> listarBestiario(bestiario);
                case 4 -> curarEquipe(treinador);
                case 0 -> System.out.println("Até a próxima!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void procurarSelvagem(Treinador treinador, Bestiario bestiario, Scanner scanner) {
        Monstro selvagem = FabricaMonstros.selvagemAleatorio();
        System.out.println("Uma criatura selvagem apareceu: " + selvagem);
        bestiario.registrar(selvagem);

        Monstro meu = escolherMonstroBatalha(treinador, scanner);
        if (meu == null) return;
        if (!meu.estaVivo()) {
            System.out.println(meu.getNome() + " está desmaiado. Cure a equipe (opção 4).");
            return;
        }
        batalhar(meu, selvagem, treinador, bestiario, scanner);
    }

    private static void batalhar(Monstro meu, Monstro selvagem, Treinador treinador,
                                  Bestiario bestiario, Scanner scanner) {
        System.out.println();
        System.out.println("===== BATALHA =====");
        System.out.println(meu.getNome() + " VS " + selvagem.getNome());

        boolean lutando = true;
        while (lutando && meu.estaVivo() && selvagem.estaVivo()) {
            System.out.println();
            System.out.println(meu.getNome() + " (Nv." + meu.getNivel() + ") - HP: " + meu.getVidaAtual() + "/" + meu.getVidaMaxima());
            System.out.println(selvagem.getNome() + " (Nv." + selvagem.getNivel() + ") - HP: " + selvagem.getVidaAtual() + "/" + selvagem.getVidaMaxima());
            System.out.println("1 - Atacar");
            System.out.println("2 - Tentar capturar");
            System.out.println("3 - Fugir");
            System.out.print("Escolha: ");
            int acao = lerInteiro(scanner);

            switch (acao) {
                case 1 -> {
                    Ataque golpe = escolherGolpe(meu, scanner);
                    if (golpe != null) {
                        int dano = meu.atacar(selvagem, golpe);
                        System.out.println(meu.getNome() + " usou " + golpe.getNome() + "! Dano: " + dano);
                        if (selvagem.estaVivo()) {
                            Ataque golpeSelvagem = Batalha.golpeAleatorio(selvagem);
                            int danoRecebido = selvagem.atacar(meu, golpeSelvagem);
                            System.out.println(selvagem.getNome() + " revidou com " + golpeSelvagem.getNome() + "! Dano: " + danoRecebido);
                        }
                    }
                }
                case 2 -> {
                    boolean capturado = treinador.tentarCapturar(selvagem);
                    if (capturado) {
                        bestiario.registrar(selvagem);
                        System.out.println(selvagem.getNome() + " foi capturado e entrou para a equipe!");
                        lutando = false;
                    } else {
                        System.out.println(selvagem.getNome() + " escapou da captura!");
                        Ataque golpeSelvagem = Batalha.golpeAleatorio(selvagem);
                        selvagem.atacar(meu, golpeSelvagem);
                    }
                }
                case 3 -> {
                    System.out.println("Você fugiu da batalha.");
                    lutando = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }

        if (!meu.estaVivo()) {
            System.out.println(meu.getNome() + " desmaiou! Você perdeu a batalha.");
        } else if (!selvagem.estaVivo()) {
            System.out.println(selvagem.getNome() + " desmaiou!");
            int xp = Batalha.xpDeVitoria(selvagem);
            boolean evoluiu = meu.ganharExperiencia(xp);
            System.out.println(meu.getNome() + " ganhou " + xp + " de XP!");
            if (evoluiu) {
                System.out.println("✨ " + meu.getNome() + " evoluiu!");
            }
        }
        System.out.println("===== FIM DA BATALHA =====");
    }

    private static Ataque escolherGolpe(Monstro monstro, Scanner scanner) {
        List<Ataque> golpes = monstro.getGolpes();
        System.out.println("Golpes de " + monstro.getNome() + ":");
        for (int i = 0; i < golpes.size(); i++) {
            System.out.println(" [" + i + "] " + golpes.get(i));
        }
        System.out.print("Escolha o golpe: ");
        int indice = lerInteiro(scanner);
        if (indice < 0 || indice >= golpes.size()) {
            System.out.println("Golpe inválido. Você perdeu a vez!");
            return null;
        }
        return golpes.get(indice);
    }

    private static void listarEquipe(Treinador treinador) {
        System.out.println("===== EQUIPE (" + treinador.getEquipe().size() + "/6) =====");
        for (int i = 0; i < treinador.getEquipe().size(); i++) {
            System.out.println(" [" + i + "] " + treinador.getEquipe().get(i));
        }
    }

    private static void listarBestiario(Bestiario bestiario) {
        System.out.println("===== BESTIÁRIO (" + bestiario.getQuantidadeRegistrada() + "/18) =====");
        for (EspecieInfo info : CatalogoEspecies.TODAS) {
            String status = bestiario.foiDescoberto(info.nome) ? info.nome : "???";
            System.out.println(" - " + status + " [" + info.tipo.nomeExibicao() + "]");
        }
    }

    private static void curarEquipe(Treinador treinador) {
        for (Monstro m : treinador.getEquipe()) m.curar();
        System.out.println("Equipe totalmente curada!");
    }

    private static Monstro escolherMonstroBatalha(Treinador treinador, Scanner scanner) {
        listarEquipe(treinador);
        System.out.print("Escolha o monstro para batalhar: ");
        int indice = lerInteiro(scanner);
        Monstro escolhido = treinador.escolherMonstro(indice);
        if (escolhido == null) System.out.println("Índice inválido.");
        return escolhido;
    }

    private static int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
