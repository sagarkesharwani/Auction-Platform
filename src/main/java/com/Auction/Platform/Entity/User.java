package com.Auction.Platform.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "users")
public class User {

		@Id
		@GeneratedValue
		@org.hibernate.annotations.GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
		@Column(name = "userid", updatable = false, nullable = false)
		private UUID userId;
	
	    @Column(name = "name")
	    private String name;

	    @Column(name = "email", unique = true)
	    private String email;

	    @Column(name = "passwordhash")
	    private String passwordHash;

	    @Column(name = "role")
	    private String role;

	    @Column(name = "createdat")
	    private LocalDateTime createdAt;

}
