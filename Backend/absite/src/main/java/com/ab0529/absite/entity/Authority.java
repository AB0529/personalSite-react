package com.ab0529.absite.entity;

import com.ab0529.absite.model.ERole;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Transactional
public class Authority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	@NonNull
	private EAuthority name;
}
