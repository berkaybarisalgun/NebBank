package com.nebbank.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @Column(name = "date_of_birth")
    private String dateOfBirth;
}

/*
Yaptığımız değişikliklerle @JoinColumn anotasyonunun hedeflediği sütun adını ve entity yapısını doğru hale getirdik. İşte yaptığımız değişiklikler ve sebebi:

Customer Entity'sinde ID Alanı Adını Güncelledik:

Customer entity'sindeki ID alanının adı Id iken id olarak güncellendi. Bu, @JoinColumn anotasyonunun referans aldığı sütun adı ile uyumlu hale getirildi.
Sebep: Hibernate, @JoinColumn anotasyonunda belirtilen sütun adını entity içinde arar. Eğer bu sütun adıyla eşleşen bir alan bulamazsa hata verir. JoinColumn için referencedColumnName değeri id olarak belirtildiği için, Customer entity'sinde de bu sütun adı olmalıdır.
Account Entity'sindeki İlişki Türünü ManyToOne Olarak Değiştirdik:

Account entity'sindeki müşteri ilişkisini @OneToOne yerine @ManyToOne olarak değiştirdik.
Sebep: Bir müşteri birden fazla hesaba sahip olabilir, bu yüzden ManyToOne ilişki daha uygun olur. Ayrıca, @OneToOne ilişkiler genellikle her iki entity'de de tek bir ilişki gerektirir ve bu da tasarımı karmaşık hale getirebilir.
Sütun Adlandırmalarını ve Alan Tiplerini Düzenledik:

Customer entity'sindeki email ve mobile_number alanlarına unique = true ekledik. Bu, veritabanında benzersiz olmasını sağlar.
Account entity'sindeki account_number alanına unique = true ekledik. Bu, her hesabın benzersiz bir numarası olmasını sağlar.
Sebep: Veritabanı düzeyinde benzersiz kısıtlamalar, veri tutarlılığını ve bütünlüğünü sağlar.
Özetle, Customer entity'sindeki ID alanının adını id olarak değiştirerek ve ilişki türünü ManyToOne yaparak Hibernate'in doğru sütunu bulmasını ve ilişkiyi düzgün bir şekilde kurmasını sağladık. Bu değişiklikler, veritabanı modelinin doğru bir şekilde yapılandırılmasını ve ilişkilerin doğru bir şekilde kurulmasını sağlar.
 */
