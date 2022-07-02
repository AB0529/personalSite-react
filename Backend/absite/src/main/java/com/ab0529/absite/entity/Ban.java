package com.ab0529.absite.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bans")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@DynamicUpdate
@Transactional
public class Ban {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NonNull
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
}
