package com.marcelo.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcelo.cursomc.domain.enums.EstadoPagamento;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;

@Entity
public class Pedido implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instante;

	
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido") //necessário para não ficar transiente
    private Pagamento pagamento;
    
    @ManyToOne
    @JoinColumn(name="cliente_id")
	
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name="endereco_de_entrega_id")
    private Endereco enderecoDeEntrega;

	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
    
    public Pedido() {
    	
    }

	public Pedido(Integer id, Date instante,Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}


	public double getValorTotal(){
		double soma = 0;
		for(ItemPedido item : itens){
			soma += item.getSubTotal();
		}
		return soma;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		return Objects.hash(enderecoDeEntrega);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(enderecoDeEntrega, other.enderecoDeEntrega);
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		final StringBuffer sb = new StringBuffer();
		sb.append("Pedido número: ");
		sb.append(getId());
		sb.append(", Instante:");
		sb.append(df.format(getInstante()));
		sb.append(", Cliente: ");
		sb.append(getCliente().getNome());
		sb.append(", Situação do pagamento: ");
		sb.append(getPagamento().getEstado().getDescricao());
		sb.append("\nDetalhes\n");
		for(ItemPedido item : getItens()){
			sb.append(item.toString());
		}

		sb.append("Valor Total: ");
		sb.append(nf.format(getValorTotal()));
		return sb.toString();
	}
}
