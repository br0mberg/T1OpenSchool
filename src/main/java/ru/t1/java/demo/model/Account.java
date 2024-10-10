package ru.t1.java.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account extends AbstractPersistable<Long> {
    @Column(name="account_type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name="balance", precision = 19, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name="client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    public enum AccountType {
        DEBIT, CREDIT
    }
}
