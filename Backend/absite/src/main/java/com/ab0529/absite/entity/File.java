package com.ab0529.absite.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@DynamicUpdate
@Transactional
public class File {
	@Id
	@org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
	private UUID id;

	@NonNull
	@Lob
	private byte[] content;
	@NonNull
	private String name;
	@NonNull
	private String contentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@NonNull
	private User user;
}
