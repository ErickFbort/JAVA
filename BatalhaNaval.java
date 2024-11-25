import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class BatalhaNaval extends JFrame {
    private JButton[][] tabuleiroJogador;
    private JButton[][] tabuleiroComputador;
    private int[][] matrizJogador;
    private int[][] matrizComputador;
    private final int TAMANHO = 5;
    private final int TOTAL_NAVIOS = 9; // Quantidade de células com navios
    private int naviosRestantesJogador = TOTAL_NAVIOS;
    private int naviosRestantesComputador = TOTAL_NAVIOS;
    private boolean turnoDoJogador = true;
    private Stack<int[][]> estadosAnteriores;

    private JLabel statusLabel;
    private JButton voltarJogadaBtn;

    public BatalhaNaval() {
        setTitle("Batalha Naval");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializa estados e matrizes
        matrizJogador = new int[TAMANHO][TAMANHO];
        matrizComputador = new int[TAMANHO][TAMANHO];
        estadosAnteriores = new Stack<>();

        // Posiciona os navios no tabuleiro do computador aleatoriamente
        posicionarNaviosAutomaticamente(matrizComputador);

        // Painéis principais
        JPanel painelCentral = new JPanel(new GridLayout(1, 2));
        JPanel painelJogador = new JPanel(new GridLayout(TAMANHO, TAMANHO));
        JPanel painelComputador = new JPanel(new GridLayout(TAMANHO, TAMANHO));

        // Tabuleiros
        tabuleiroJogador = new JButton[TAMANHO][TAMANHO];
        tabuleiroComputador = new JButton[TAMANHO][TAMANHO];

        inicializarTabuleiro(painelJogador, tabuleiroJogador, true);
        inicializarTabuleiro(painelComputador, tabuleiroComputador, false);

        painelCentral.add(painelJogador);
        painelCentral.add(painelComputador);

        // Barra de status
        statusLabel = new JLabel("Posicione seus navios (clique para colocar navios)", JLabel.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        // Botão de "Voltar Jogada"
        voltarJogadaBtn = new JButton("Voltar Jogada");
        voltarJogadaBtn.setEnabled(false);
        voltarJogadaBtn.addActionListener(e -> voltarJogada());
        add(voltarJogadaBtn, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);
    }

private void inicializarTabuleiro(JPanel painel, JButton[][] tabuleiro, boolean jogador) {
    for (int i = 0; i < TAMANHO; i++) {
        for (int j = 0; j < TAMANHO; j++) {
            final int x = i; // Coordenada final para uso no lambda
            final int y = j; // Coordenada final para uso no lambda
            
            JButton botao = new JButton();
            botao.setBackground(Color.LIGHT_GRAY);
            botao.setFocusPainted(false);
            tabuleiro[x][y] = botao;

            if (jogador) {
                // Configura ação para posicionamento de navios do jogador
                botao.addActionListener(e -> {
                    if (matrizJogador[x][y] == 0 && naviosRestantesJogador > 0) {
                        matrizJogador[x][y] = 1; // Marca posição com um navio
                        botao.setText("N");
                        naviosRestantesJogador--;

                        if (naviosRestantesJogador == 0) {
                            statusLabel.setText("Todos os navios posicionados! É hora de jogar.");
                        }
                    } else {
                        statusLabel.setText("Posição inválida ou todos os navios já foram posicionados.");
                    }
                });
            } else {
                // Configura ação para o ataque no tabuleiro do computador
                botao.addActionListener(e -> {
                    if (turnoDoJogador && botao.isEnabled()) {
                        salvarEstadoAtual(); // Salva o estado atual (para desfazer jogada, se necessário)
                        
                        if (matrizComputador[x][y] == 1) {
                            botao.setText("D");
                            matrizComputador[x][y] = 3; // Marca como acertado
                            naviosRestantesComputador--;
                            statusLabel.setText("Você acertou! Jogue novamente.");
                        } else if (matrizComputador[x][y] == 0) {
                            botao.setText("X");
                            matrizComputador[x][y] = 2; // Marca como erro
                            statusLabel.setText("Você errou! Turno do computador.");
                            turnoDoJogador = false;
                            jogarComputador(); // Executa a jogada do computador
                        }
                        botao.setEnabled(false); // Desabilita botão após o clique
                    } else {
                        statusLabel.setText("Não é sua vez de jogar!");
                    }
                });
            }

            painel.add(botao);
        }
    }
}

    private void posicionarNaviosAutomaticamente(int[][] matriz) {
        int naviosColocados = 0;
        while (naviosColocados < TOTAL_NAVIOS) {
            int x = (int) (Math.random() * TAMANHO);
            int y = (int) (Math.random() * TAMANHO);

            if (matriz[x][y] == 0) {
                matriz[x][y] = 1; // Posiciona navio
                naviosColocados++;
            }
        }
    }

    private void salvarEstadoAtual() {
        int[][] estadoAtual = new int[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            System.arraycopy(matrizComputador[i], 0, estadoAtual[i], 0, TAMANHO);
        }
        estadosAnteriores.push(estadoAtual);
        voltarJogadaBtn.setEnabled(true);
    }

    private void voltarJogada() {
        if (!estadosAnteriores.isEmpty()) {
            matrizComputador = estadosAnteriores.pop();
            atualizarTabuleiro(tabuleiroComputador, matrizComputador, false);
            turnoDoJogador = true;
            statusLabel.setText("Jogada desfeita! Seu turno.");
            if (estadosAnteriores.isEmpty()) {
                voltarJogadaBtn.setEnabled(false);
            }
        }
    }

    private void atualizarTabuleiro(JButton[][] tabuleiro, int[][] matriz, boolean jogador) {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                switch (matriz[i][j]) {
                    case 0 -> tabuleiro[i][j].setText("");
                    case 1 -> tabuleiro[i][j].setText(jogador ? "N" : "");
                    case 2 -> tabuleiro[i][j].setText("X");
                    case 3 -> tabuleiro[i][j].setText("D");
                }
            }
        }
    }

    private void jogarComputador() {
        while (!turnoDoJogador) {
            int x = (int) (Math.random() * TAMANHO);
            int y = (int) (Math.random() * TAMANHO);

            if (matrizJogador[x][y] == 1) {
                matrizJogador[x][y] = 3; // Acerto
                tabuleiroJogador[x][y].setText("D");
                naviosRestantesJogador--;
                statusLabel.setText("Computador acertou! Sua vez.");
            } else if (matrizJogador[x][y] == 0) {
                matrizJogador[x][y] = 2; // Erro
                tabuleiroJogador[x][y].setText("X");
                statusLabel.setText("Computador errou! Sua vez.");
                turnoDoJogador = true;
            }

            if (naviosRestantesJogador == 0 || naviosRestantesComputador == 0) {
                encerrarJogo();
                break;
            }
        }
    }

    private void encerrarJogo() {
        String mensagem = (naviosRestantesJogador == 0) ? "Computador venceu!" : "Você venceu!";
        JOptionPane.showMessageDialog(this, mensagem);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BatalhaNaval jogo = new BatalhaNaval();
            jogo.setVisible(true);
        });
    }
}