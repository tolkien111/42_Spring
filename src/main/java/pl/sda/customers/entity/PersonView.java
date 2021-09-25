package pl.sda.customers.entity;


import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Immutable
@Subselect("SELECT p.id, p.email, p.pesel FROM customers p WHERE p.customer_type = 'PERSON'") //encja powstaje z danego zapytania, piszemy w SQL
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
public class PersonView {

    @Id
    private UUID id;
    private String email;
    private String pesel;
}
