POKEMON: CAPTURA E BATALHA - Projeto POO (Java)
================================================

COMO COMPILAR E EXECUTAR (interface gráfica)
---------------------------------------------
1) Entre na pasta do projeto:
   cd monstraria_projeto

2) Compile todo o código-fonte:
   `javac -d bin src\monstraria\*.java src\monstraria\view\*.java`

3) Execute a versão com interface gráfica (Swing):
   `java -cp bin monstraria.MainGUI`

COMO EXECUTAR A VERSÃO EM MODO TEXTO
-------------------------------------
   java -cp bin monstraria.Main

COMO RODAR OS TESTES (JUnit 5)
--------------------------------
Com o junit-platform-console-standalone.jar no projeto:
   javac -cp bin;junit-platform-console-standalone.jar -d bin src/monstraria/testes/*.java
   java -jar junit-platform-console-standalone.jar -cp bin --select-package monstraria.testes

(No Linux/Mac, troque ';' por ':')

ESTRUTURA
---------
src/monstraria/
  Tipo.java           -> enum com os tipos elementares
  Monstro.java         -> classe base (atributos protegidos, encapsulados)
  MonstroFogo.java      -> subclasse: vence Planta, perde para Agua
  MonstroAgua.java      -> subclasse: vence Fogo, perde para Planta
  MonstroPlanta.java    -> subclasse: vence Agua, perde para Fogo
  Treinador.java      -> equipe (ArrayList, max 6) + captura com chance por vida restante
  Bestiario.java        -> registros sem duplicar (compara pelo nome)
  Batalha.java        -> turnos 1x1, usa polimorfismo do atacar()
  SaveManager.java    -> salvar/carregar progresso em disco
  Main.java           -> menu interativo em modo texto
  MainGUI.java        -> ponto de entrada da interface gráfica (Swing)
src/monstraria/view/
  JanelaPrincipal.java  -> janela principal e navegação entre telas
  PainelMenu.java       -> tela de título "Monster Realm"
  FundoFloresta.java    -> paisagem de floresta em pixel art (fundo do menu)
  PixelUtil.java        -> utilitário de renderização pixelizada
  MonstroSprite.java    -> sprites vetoriais dos monstros
  Estilo.java           -> cores, fontes e componentes visuais reutilizáveis
  (demais painéis: Escolha, Batalha, Arena, Equipe, Bestiário/MonsterDex)
src/monstraria/testes/
  MonstroTest.java    -> dano com vantagem, neutro, desvantagem
  CapturaTest.java    -> chance de captura e limites da equipe
  BestiarioTest.java  -> sem duplicacao de registros

IDEIA CENTRAL (vantagem de tipo)
----------------------------------
A classe Monstro define atacar(), que chama getMultiplicadorContra(alvo).
Cada subclasse sobrescreve esse metodo retornando 2.0 (vantagem),
0.5 (desvantagem) ou 1.0 (neutro). Para criar um novo tipo, basta
criar uma nova subclasse - o codigo de Batalha nao muda.

A classe Pokemon define atacar(), que chama getMultiplicadorContra(alvo).
Cada subclasse sobrescreve esse metodo retornando 2.0 (vantagem),
0.5 (desvantagem) ou 1.0 (neutro). Para criar um novo tipo, basta
criar uma nova subclasse - o codigo de Batalha nao muda.
