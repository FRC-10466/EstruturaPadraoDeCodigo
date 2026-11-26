# Padrao de codigo - Federal Force #10466

Esqueleto de referencia da equipe. A tracao esta completa como exemplo do molde;
os demais mecanismos sao copias desse molde.

## Estrutura

    src/main/java/frc/
      robot/            codigo DESTA temporada
        config/         qual robo e este (IDs e inversoes que mudam por robo)
        subsystems/     um pacote por mecanismo
          drive/        exemplo completo do molde
        commands/       so o que coordena mais de um mecanismo
        auto/           rotinas autonomas
        controls/       mapeamento dos joysticks
        visualization/  o que aparece no AdvantageScope
      lib/              codigo que ATRAVESSA as temporadas
        motors/ math/ util/

Regra que sustenta a estrutura: nada dentro de `lib/` pode mencionar o jogo do
ano. Na virada de temporada, copie o repositorio, esvazie `subsystems/` e
`auto/`, e `lib/` continua valendo.

## O molde de um mecanismo

Para cada mecanismo, sete arquivos de papel fixo (exemplo: `drive/`):

| Arquivo | Papel |
| --- | --- |
| `DriveIO.java` | O contrato: o que o hardware informa e o que ele aceita |
| `DriveIOSparkMax.java` | Implementacao real. Unica a conhecer a REVLib |
| `DriveIOSim.java` | Implementacao simulada, com modelo fisico |
| `Drive.java` | A logica e os comandos simples |
| `DriveCommands.java` | Comandos compostos do mesmo mecanismo |
| `DriveConstants.java` | Os numeros do mecanismo |

Tres regras:

1. O subsistema nunca importa classe de fabricante. Um `import com.revrobotics`
   fora de `*IOSparkMax` significa molde violado.
2. Toda leitura de sensor passa pelas entradas registradas (`DriveIOInputs`).
   O que nao esta la nao existe no replay.
3. A conversao de unidade acontece na implementacao IO. A logica recebe metros,
   radianos e segundos, e nunca converte nada.

## Convencoes

- Codigo e pacotes em ingles; comentarios, `docs/` e guias em portugues.
- Constantes em `MAIUSCULAS_COM_UNDERLINE`, com a unidade no fim do nome.
- Tudo em SI. Nenhum numero solto no codigo, fora de `0` e `1`.

## Como rodar

    ./gradlew build      compila
    ./gradlew simulateJava   roda em simulacao, sem robo
    ./gradlew deploy     envia para o robo

## Pendencias conhecidas

- Projeto Gradle (`build.gradle`, `vendordeps`) ainda nao gerado: criar pelo
  plugin da WPILib e colar estes arquivos dentro.
- Encoder: o codigo assume encoder no eixo de saida, pela porta de dados do
  SPARK MAX. Se estiver no eixo do motor, dividir tambem pela reducao.
- Ganhos de velocidade (`VELOCITY_KP`, feedforward) sao valores de partida e
  precisam ser calibrados na bancada.
