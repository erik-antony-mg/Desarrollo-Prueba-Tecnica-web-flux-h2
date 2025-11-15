package com.pruebatenica01.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("usuarios")
public class UsuarioEntity {
    @Id
    private Long id;
    public String email;
    private String password;
    private String roles;
}
