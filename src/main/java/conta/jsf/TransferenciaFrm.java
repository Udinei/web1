package conta.jsf;

import conta.sistema.casouso.porta.PortaTransferencia;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

// Adaptador Primário JSF
@Named
@Scope(value = "session")
public class TransferenciaFrm {

    private Integer conta1;
    private String descricao1;
    private Integer conta2;
    private String descricao2;
    private BigDecimal valor;

    @Inject
    private PortaTransferencia porta;

    // operações privadas de apoio

    private void limpar1() {
        conta1 = null;
        descricao1 = null;
    }

    private void limpar2() {
        conta2 = null;
        descricao2 = null;
    }

    private void limpar() {
        limpar1();
        limpar2();
    }

    private void erro(String mensagem) {
        var fc = FacesContext.getCurrentInstance();
        var fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        fc.addMessage(null, fm);
    }

    private void aviso(String mensagem) {
        var fc = FacesContext.getCurrentInstance();
        var fm = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
        fc.addMessage(null, fm);
    }

    // operações publicas enventos jsf

    public void pesquisaConta1() {
        try {
                var conta = porta.getConta(conta1);
            if (conta == null) {
                limpar1();
            } else {
                descricao1 = conta.getCorrentista() + " - Saldo R$ " + conta.getSaldo();
            }
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }

    public void pesquisaConta2() {
        try {
            var conta = porta.getConta(conta2);
            if (conta == null) {
                limpar2();
            } else {
                descricao2 = conta.getCorrentista() + " - Saldo R$ " + conta.getSaldo();
            }
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }

    public void transferir() {
        try {
            porta.transferir(conta1, conta2, valor);
            limpar1();
            limpar2();
            valor = null;
            aviso("Transferência feita com sucesso!");
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }

    // gets e sets

    public Integer getConta1() {
        return conta1;
    }

    public void setConta1(Integer conta1) {
        this.conta1 = conta1;
    }

    public String getDescricao1() {
        return descricao1;
    }

    public void setDescricao1(String descricao1) {
        this.descricao1 = descricao1;
    }

    public Integer getConta2() {
        return conta2;
    }

    public void setConta2(Integer conta2) {
        this.conta2 = conta2;
    }

    public String getDescricao2() {
        return descricao2;
    }

    public void setDescricao2(String descricao2) {
        this.descricao2 = descricao2;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public PortaTransferencia getPorta() {
        return porta;
    }

    public void setPorta(PortaTransferencia porta) {
        this.porta = porta;
    }
}
