package br.com.devmedia.webservice.model.domain.dto;

import org.modelmapper.ModelMapper;

import br.com.devmedia.webservice.model.domain.Marca;

public class MarcaDTO {

	private long id;
    private String nome;
    private String descricao;

    public MarcaDTO () {}
    
    // conteúdo do método gerenciado pela dependência <artifactId>modelmapper</artifactId> do pom.xml
 	public static MarcaDTO criarDTO(Marca marca) {
         ModelMapper modelMapper = new ModelMapper();
         return modelMapper.map(marca, MarcaDTO.class);
     }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
