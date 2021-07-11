package br.com.southsystem.skiils_up.models;


import br.com.southsystem.skiils_up.enums.ClientProfile;
import br.com.southsystem.skiils_up.enums.UserCategories;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String cpf;

    private String birthday;

    @JsonIgnore
    @ElementCollection
    @CollectionTable(name = "tb_phones")
    private Set<String> phone = new HashSet<>();

    private String email;

    @JsonIgnore
    private String password;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "tb_clients", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "client_id")
    private Set<Long> clients = new HashSet<>();


    @Enumerated(value = EnumType.STRING)
    private UserCategories category;


    public User(Long id, String firstName, String lastName, String cpf, String birthday, String email, String password, UserCategories category) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.category = category;
        addClient(ClientProfile.CLIENT);
    }

    public User() {    }

    public Set<ClientProfile> getClient() {
        return clients.stream().map(x -> ClientProfile.toEnum(x)).collect(Collectors.toSet());
    }

    public void addClient(ClientProfile client) {
        clients.add(client.getCod());
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public Set<String> getPhone() {
        return phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<Long> getClients() {
        return this.clients;
    }

    public UserCategories getCategory() {
        return this.category;
    }
}