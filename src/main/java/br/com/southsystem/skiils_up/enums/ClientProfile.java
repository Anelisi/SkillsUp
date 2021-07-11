package br.com.southsystem.skiils_up.enums;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ClientProfile {
    ADMIN(1L, "ROLE_ADMIN"),
    CLIENT(2L, "ROLE_CLIENTE");

    private Long cod;
    private String descricao;

    public Long getCod() {
        return this.cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ClientProfile toEnum(Object cod) {

        if (cod == null) {
            return null;
        }

        for (ClientProfile x : ClientProfile.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}
