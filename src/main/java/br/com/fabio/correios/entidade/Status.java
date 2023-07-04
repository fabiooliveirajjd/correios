package br.com.fabio.correios.entidade;

public enum Status {
    NEED_SETUP,    // PRECISA BAIXAR O CSV DOS CORREIOS
    SETUP_RUNNING, // ESTÁ BAIXANDO/ SALVANDO NO BANCO
    READY;         // SERVIÇO ESTÁ APTO PARA SER CON
}
