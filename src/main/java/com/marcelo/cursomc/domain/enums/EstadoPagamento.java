package com.marcelo.cursomc.domain.enums;

public enum EstadoPagamento {
    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");
    
    private int codigo;
	private String descricao;
	
	private EstadoPagamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public static EstadoPagamento tipoEstadoPagamento(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(EstadoPagamento x : EstadoPagamento.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id Inválido " + cod);
	}
	
	
	

    
}
