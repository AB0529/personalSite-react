package com.ab0529.absite.entity;

import com.ab0529.absite.model.EBlacklistReason;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "token_blacklist", uniqueConstraints ={
		@UniqueConstraint(columnNames =  "token"),
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TokenBlacklist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NonNull
	@NotNull
	private String token;

	@NonNull
	@NotNull
	@Enumerated(EnumType.STRING)
	private EBlacklistReason reason;
}
