package ru.t1.java.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="account_type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name="balance")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name="client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    public enum AccountType {
        DEBIT, CREDIT
    }
}
