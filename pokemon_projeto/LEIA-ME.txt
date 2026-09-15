POKEMON: CAPTURA E BATALHA - Projeto POO (Java)
================================================

COMO COMPILAR E EXECUTAR
------------------------
1) Entre na pasta do projeto:
   cd pokemon_projeto

2) Compile:
   javac -d bin src/pokemon/*.java

3) Execute:
   java -cp bin pokemon.Main

COMO RODAR OS TESTES (JUnit 5)
------------------------------
Com o junit-platform-console-standalone.jar no projeto:
   javac -cp bin;junit-platform-console-standalone.jar -d bin src/pokemon/testes/*.java
   java -jar junit-platform-console-standalone.jar -cp bin --select-package pokemon.testes

(No Linux/Mac, troque ';' por ':')

ESTRUTURA
---------
src/pokemon/
  Tipo.java           -> enum com os tipos elementares
  Pokemon.java        -> classe base (atributos protegidos, encapsulados)
  PokemonFogo.java    -> subclasse: vence Planta, perde para Agua
  PokemonAgua.java    -> subclasse: vence Fogo, perde para Planta
  PokemonPlanta.java  -> subclasse: vence Agua, perde para Fogo
  Treinador.java      -> equipe (ArrayList, max 6) + captura com chance por vida restante
  Pokedex.java        -> registros sem duplicar (compara pelo nome)
  Batalha.java        -> turnos 1x1, usa polimorfismo do atacar()
  Main.java           -> menu interativo (gera os prints da entrega)
src/pokemon/testes/
  PokemonTest.java    -> dano com vantagem, neutro, desvantagem (6 testes)
  CapturaTest.java    -> chance de captura e limites da equipe (5 testes)
  PokedexTest.java    -> sem duplicacao de registros (3 testes)

Total: 14 testes (minimo exigido: 5)

IDEIA CENTRAL (vantagem de tipo)
--------------------------------
A classe Pokemon define atacar(), que chama getMultiplicadorContra(alvo).
Cada subclasse sobrescreve esse metodo retornando 2.0 (vantagem),
0.5 (desvantagem) ou 1.0 (neutro). Para criar um novo tipo, basta
criar uma nova subclasse - o codigo de Batalha nao muda.
